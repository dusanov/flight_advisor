package me.dusanov.fa;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.Getter;
import me.dusanov.fa.dijkstra.Graph;
import me.dusanov.fa.dijkstra.Node;
import me.dusanov.fa.services.AirportService;
import me.dusanov.fa.services.RoutesViewService;

@Component
public class GraphComponent implements ApplicationRunner  {

	@Autowired private AirportService airportService;
	@Autowired private RoutesViewService routesViewService;
	
			private Graph graph = new Graph();
	@Getter private Map<String, Node> nodes = new HashMap<String, Node>();
	
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
			nodes.put(airport.getCity(), new Node(airport.getCity()));
		});
	}	
	
	public void loadGraph() {
		nodes.forEach((city,node) -> {
			routesViewService.getAllBySource(city).forEach(routeView -> {
				node.addDestination(nodes.get(routeView.getDestination()), routeView.getPrice());
			});
			//add built node to graph
			graph.addNode(city,node);
		});
	}

}
