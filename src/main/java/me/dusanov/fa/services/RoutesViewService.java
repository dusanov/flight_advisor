package me.dusanov.fa.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.domains.RoutesView;
import me.dusanov.fa.repos.RoutesViewRepo;

@Service
@RequiredArgsConstructor
public class RoutesViewService {
	
	private final RoutesViewRepo routesViewRepo;
	
	public List<RoutesView> findTheCheapestRoute(String source, String destination){
		
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
