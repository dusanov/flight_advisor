package me.dusanov.fa.services;

import static me.dusanov.fa.dijkstra.Dijkstra.calculateCheapestPathFromSource;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.GraphComponent;
import me.dusanov.fa.dijkstra.Graph;
import me.dusanov.fa.dijkstra.Node;
import me.dusanov.fa.domains.RoutesView;
import me.dusanov.fa.dtos.SearchResult;
import me.dusanov.fa.dtos.SearchResultItem;
import me.dusanov.fa.repos.RoutesViewRepo;

@Service
@RequiredArgsConstructor
public class RoutesViewService {
	
	private final RoutesViewRepo routesViewRepo;
	private final GraphComponent graph;
	
	public SearchResult findTheCheapestRoute(String source, String destination) {

		Graph calcGraph = calculateCheapestPathFromSource(graph.getGraph(), graph.getNodes().get(source));
		BigDecimal totalPrice = calcGraph.getNodes().get(destination).getPrice();
		
		SearchResult result = new SearchResult();
		
		int index = 0;
		List<Node> nodes = calcGraph.getNodes().get(destination).getCheapestPath();
		int size = nodes.size();
		double sumLength = 0;
		while (index+1 < size) {
			Node srcNode = nodes.get(index);
			Node destNode = nodes.get(index+1);			
			RoutesView routeView = this.getRoute(srcNode.getName(), destNode.getName());
			double length = this.computeDistance(routeView);
			sumLength += length;
			SearchResultItem item = new SearchResultItem(routeView.getSource(),routeView.getDestination(),routeView.getPrice().doubleValue(),length);
			result.addRoute(item);
			index++;
		}
		
		RoutesView lastRouteView = this.getRoute(nodes.get(size-1).getName(), calcGraph.getNodes().get(destination).getName());
		double length = this.computeDistance(lastRouteView);
		sumLength += length;
		SearchResultItem item = new SearchResultItem(lastRouteView.getSource(),lastRouteView.getDestination(),lastRouteView.getPrice().doubleValue(),length);
		result.addRoute(item);
		
		result.setTotalLength(sumLength);
		result.setTotalPrice(totalPrice.doubleValue());
		result.setDescription(String.format(
				"Search result for cheapest path from %s, to %s. Total cost: %.2f usd. Total distance: %.2f km", source,destination,totalPrice.doubleValue(),sumLength));
		
		return result;
	}
	
	public RoutesView getRoute(String source, String destination) {
		return routesViewRepo.findBySourceAndDestination(source, destination);
	}
	
	public List<RoutesView> getAllRoutes(){
		return (List<RoutesView>) routesViewRepo.findAll();
	}
	
	public List<RoutesView> getAllBySource(String source){
		return routesViewRepo.findBySource(source);
	}
	
	//Haversine formula
    public double computeDistance(RoutesView route) {
    	
        double R = 6372.8; // Earth's Radius, in kilometers
 
        double dLat = Math.toRadians(route.getDestinationLatitude() - route.getSourceLatitude());
        double dLon = Math.toRadians(route.getDestinationLongitude() - route.getSourceLongitude());
        double lat1 = Math.toRadians(route.getSourceLatitude());
        double lat2 = Math.toRadians(route.getDestinationLatitude());
 
        double a = Math.pow(Math.sin(dLat / 2),2)
          + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        
        return R * c;
    }
}
