package me.dusanov.fa;

import java.math.BigDecimal;
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
			nodes.put(airport.getCity(), new Node(airport));
		});
	}	
	
	public void loadGraph() {
			this.loadGraph(false);
	}
	
	public void loadGraph(boolean genPrice) {
		nodes.forEach((city,node) -> {
			routesViewService.getAllBySource(city).forEach(routeView -> {
				if (genPrice && null == routeView.getPrice()) routeView.setPrice( BigDecimal.valueOf(RoutesViewService.computeDistance(routeView)).divide(BigDecimal.valueOf(10)) );
				node.addDestination(nodes.get(routeView.getDestination()), routeView.getPrice());
			});
			graph.addNode(city,node);
		});
	}

}
