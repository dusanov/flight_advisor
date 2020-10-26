package me.dusanov.fa.services;

import static me.dusanov.fa.dijkstra.Dijkstra.calculateCheapestPathFromSource;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.GraphComponent;
import me.dusanov.fa.dijkstra.Graph;
import me.dusanov.fa.dijkstra.Node;
import me.dusanov.fa.domains.Airport;
import me.dusanov.fa.domains.RoutesView;
import me.dusanov.fa.dtos.SearchResult;
import me.dusanov.fa.dtos.SearchResultItem;
import me.dusanov.fa.repos.RoutesViewRepo;

@Service
@RequiredArgsConstructor
public class RoutesViewService {
	
	private final RoutesViewRepo routesViewRepo;
	private final GraphComponent graphComponent;
	
	public SearchResult findTheCheapestRoute(String source, String destination) {

		//TODO: cache this
		//TODO: add search time
		Graph graph = graphComponent.getADeepCopyOfGraph();
		graph = calculateCheapestPathFromSource(graph, graph.getNodes().get(source));		
		BigDecimal totalPrice = graph.getNodes().get(destination).getPrice();
		List<Node> nodes = graph.getNodes().get(destination).getCheapestPath();
		int size = nodes.size();
		if (size == 0) return new SearchResult(String.format("no results for %s to %s", source,destination));
		
		int index = 0;
		double sumLength = 0;
		SearchResult result = new SearchResult();
		//geoJson
		JSONObject lineString = new JSONObject();
		lineString.put("type", "LineString");
		JSONArray coordinates = new JSONArray();
		while (index+1 < size) {
			Node srcNode = nodes.get(index);
			Node destNode = nodes.get(index+1);						
			double length = RoutesViewService.computeDistance(srcNode.getAirport(),destNode.getAirport());
			sumLength += length;
			BigDecimal routePrice = srcNode.getDestinationPrice(destNode);
			SearchResultItem item = new SearchResultItem(srcNode.getAirport().getName(),destNode.getAirport().getName(),((null!=routePrice)?routePrice.doubleValue():0),length);
			result.addRoute(item);
			
			JSONObject src = srcNode.toGeoJsonString();
			coordinates.put(src.get("coordinates"));			
			
			index++;
		}
		
		double length = RoutesViewService.computeDistance(nodes.get(size-1).getAirport(),graph.getNodes().get(destination).getAirport());
		sumLength += length;
		BigDecimal routePrice = nodes.get(size-1).getDestinationPrice(graph.getNodes().get(destination));
		SearchResultItem item = new SearchResultItem(nodes.get(size-1).getAirport().getName(),
													graph.getNodes().get(destination).getAirport().getName(),
													((null!=routePrice)?routePrice.doubleValue():0),
													length);
		
		JSONObject src = nodes.get(size-1).toGeoJsonString();
		JSONObject dest = graph.getNodes().get(destination).toGeoJsonString();
		coordinates.put(src.get("coordinates"));
		coordinates.put(dest.get("coordinates"));				
		lineString.put("coordinates", coordinates);
		result.setGeoJson(lineString.toMap());
		
		result.addRoute(item);
		result.setTotalLength(sumLength);
		result.setTotalPrice(totalPrice.doubleValue());
		result.setDescription(String.format(
				"Search result for cheapest path from %s to %s. Total cost: %.2f usd. Total distance: %.2f km", source,destination,totalPrice.doubleValue(),sumLength));
		
		return result;
	}
	
	public List<RoutesView> getRoute(String source, String destination) {
		return routesViewRepo.findBySourceAndDestination(source, destination);
	}
	
	public List<RoutesView> getAllRoutes(){
		return (List<RoutesView>) routesViewRepo.findAll();
	}
	
	public List<RoutesView> getAllBySource(String source){
		return routesViewRepo.findBySource(source);
	}
	
	//Haversine formula
    public static double computeDistance(RoutesView route) {
    	return computeDistance(route.getSourceLatitude(), route.getSourceLongitude(), route.getDestinationLatitude(), route.getDestinationLongitude());
    }
    
    public static double computeDistance(Airport source, Airport destination) {
    	return computeDistance(source.getLatitude(), source.getLongitude(), destination.getLatitude(), destination.getLongitude());
    }
    
    public static double computeDistance(double srcLat, double srcLong, double destLat, double destLong) {
        double R = 6372.8; // Earth's Radius, in kilometers
        
        double dLat = Math.toRadians(destLat - srcLat);
        double dLon = Math.toRadians(destLong - srcLong);
        double lat1 = Math.toRadians(srcLat);
        double lat2 = Math.toRadians(destLat);
 
        double a = Math.pow(Math.sin(dLat / 2),2)
          + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        
        return R * c;
    }
}
