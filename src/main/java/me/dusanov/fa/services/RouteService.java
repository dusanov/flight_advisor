package me.dusanov.fa.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.domains.Route;
import me.dusanov.fa.repos.RouteRepo;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class RouteService {
	
	private final RouteRepo routeRepo;
	
	public Route addRoute(@Valid Route route) {
		return routeRepo.save(route);
	}
	
	public List<Route> getAll(){
		return (List<Route>) routeRepo.findAll();
	}
	
	public List<Route> search(String source, String destination){
		//TODO
		return null;
	}
	
	public void importRoutes(String routesFilePath) {}
	
}
