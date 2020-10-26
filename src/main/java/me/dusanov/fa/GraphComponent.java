package me.dusanov.fa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.Getter;
import me.dusanov.fa.dijkstra.Graph;
import me.dusanov.fa.dijkstra.Node;
import me.dusanov.fa.domains.Route;
import me.dusanov.fa.dtos.ImportResult;
import me.dusanov.fa.services.AirportService;
import me.dusanov.fa.services.RouteService;
import me.dusanov.fa.services.RoutesViewService;

@Component
public class GraphComponent implements ApplicationRunner  {

	@Autowired private AirportService airportService;
	//@Autowired private RoutesViewService routesViewService;
	@Autowired private RouteService routesService;
	
			private Graph graph = new Graph();
	@Getter private Map<Long, Node> nodes = new HashMap</*String*/Long, Node>();
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		loadNodes();
		loadGraph();
	}
	
	public Graph getADeepCopyOfGraph() {
		return graph.deepCopy();
	}

	public void loadNodes() {
		//create nodes
		airportService.getAll().forEach( airport -> {
			nodes.put(/*airport.getCity() */ airport.getAirportId(), new Node(airport));
		});
	}	
	
	
	
	public void loadGraph() {
			this.loadGraph(this.routesService.getAll(),false,null);
	}
	
	public void loadGraph(boolean genPrice) {
		this.loadGraph(this.routesService.getAll(),genPrice,null);
	}
	
	public void loadGraph(List<Route> routes, boolean genPrice,ImportResult<Route> result) {
		
		if (result == null) result = new ImportResult<Route>();
		else {result.setSuccessful(0); result.setFailed(new ArrayList<Route>());}
		
		int count = 0;
		for (Route route : routes) {
			Node srcNode = nodes.get(route.getSourceAirportId());
			Node destNode = nodes.get(route.getDestinationAirportId());
			
			if (srcNode != null && destNode != null)
			{
				if (genPrice && null == route.getPrice() && (srcNode.getAirport() != null && destNode.getAirport() != null )) 
					route.setPrice(BigDecimal.valueOf(RoutesViewService.computeDistance(srcNode.getAirport(),destNode.getAirport())).divide(BigDecimal.valueOf(10)) );
				srcNode.addDestination(destNode, route.getPrice());
				graph.addNode(srcNode.getAirport().getCity(), srcNode);
				count++;
			}
			else { result.getFailed().add(route);}
		}
		result.setSuccessful(count);
	}

}
