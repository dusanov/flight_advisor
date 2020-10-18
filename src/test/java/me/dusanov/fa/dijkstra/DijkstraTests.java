package me.dusanov.fa.dijkstra;

import static me.dusanov.fa.dijkstra.Dijkstra.calculateShortestPathFromSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.dusanov.fa.services.AirportService;
import me.dusanov.fa.services.RoutesViewService;

@SpringBootTest
public class DijkstraTests {
	
	@Autowired	private RoutesViewService routesViewService;
	@Autowired	private AirportService airportService;
	
	@Test public void testBg_Stg_Bg_PuQ() {
		
		Graph graph = new Graph();
		Map<String, Node> nodes = new HashMap<String, Node>();
		
		//create nodes
		airportService.getAll().forEach( airport -> {
			nodes.put(airport.getCity(), new Node(airport.getCity()));
		});

		nodes.forEach((city,node) -> {
			routesViewService.getAllBySource(city).forEach(routeView -> {
				node.addDestination(nodes.get(routeView.getDestination()), routeView.getPrice());
			});
			//add built node to graph
			graph.addNode(city,node);
		});
		
		try {
			Graph bgGraph = calculateShortestPathFromSource(graph, nodes.get("Belgrade"));
			
			assertNotNull(bgGraph);
			assertEquals(900.20d,  bgGraph.getNodes().get("Santiago").getPrice().doubleValue());		
			
			System.out.println("Shortest path from Belgrado to Santiago: \n" + bgGraph.getNodes().get("Santiago").getShortestPath()); 
			System.out.println("Price: " + bgGraph.getNodes().get("Santiago").getPrice());

			assertEquals(1020.20d,  bgGraph.getNodes().get("Punta Arenas").getPrice().doubleValue());
			System.out.println("Shortest path from Belgrado to Punta Arenas: \n" + bgGraph.getNodes().get("Punta Arenas").getShortestPath()); 
			System.out.println("Price: " + bgGraph.getNodes().get("Punta Arenas").getPrice());
			
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}

}
