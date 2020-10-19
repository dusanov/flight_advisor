package me.dusanov.fa.services;

import static me.dusanov.fa.dijkstra.Dijkstra.calculateCheapestPathFromSource;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.GraphComponent;
import me.dusanov.fa.dijkstra.Graph;
import me.dusanov.fa.domains.RoutesView;
import me.dusanov.fa.repos.RoutesViewRepo;

@Service
@RequiredArgsConstructor
public class RoutesViewService {
	
	private final RoutesViewRepo routesViewRepo;
	private final GraphComponent graph;
	
	@PostConstruct
    public void init(){}
	
	public List<RoutesView> findTheCheapestRoute(String source, String destination) {
/*
		//create nodes
		airportService.getAll().forEach( airport -> {
			nodes.put(airport.getCity(), new Node(airport.getCity()));
		});

		nodes.forEach((city,node) -> {
			this.getAllBySource(city).forEach(routeView -> {
				node.addDestination(nodes.get(routeView.getDestination()), routeView.getPrice());
			});
			//add built node to graph
			graph.addNode(city,node);
		});		

	*/	
		
		Graph calcGraph = calculateCheapestPathFromSource(graph.getGraph(), graph.getNodes().get(source));
		BigDecimal totalPrice = calcGraph.getNodes().get(destination).getPrice();

		System.out.println("calcGraph:\n" + calcGraph);
		System.out.println("totalPrice:\n" + totalPrice);
		
		//TODO: calculate length
		
		List<RoutesView> routes = (List<RoutesView>) routesViewRepo.findAll();
		return routes;
	}
	
	public List<RoutesView> getAllRoutes(){
		return (List<RoutesView>) routesViewRepo.findAll();
	}
	
	public List<RoutesView> getAllBySource(String source){
		return routesViewRepo.findBySource(source);
	}
}
