package me.dusanov.fa.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.domains.Airport;
import me.dusanov.fa.domains.Route;
import me.dusanov.fa.dtos.ImportResult;
import me.dusanov.fa.repos.RouteRepo;

import static me.dusanov.fa.services.RoutesViewService.computeDistance;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class RouteService {
	
	private final AirportService airportService;
	private final RouteRepo routeRepo;
	
	public Route addRoute(@Valid Route route) {
		return routeRepo.save(route);
	}
	
	public List<Route> getAll(){
		return (List<Route>) routeRepo.findAll();
	}
	
	public ImportResult<Route> importRoutes(List<Route> routes, 
											boolean validateAirport,
											boolean generatePrice) {
		
		//delete all routes first, routes do not have id
		routeRepo.deleteAll();
		
		int count = 0;
		List<Route> failed = new ArrayList<Route>();
		System.out.println("route count: " + routes.size());
		int printcounter = 0;
		int counter = 0;
		for (Route route : routes) {
			printcounter++;
			counter++;
			if (printcounter == 10000) {
				printcounter = 0;
				System.out.println("at: " + counter);
			}
			
			if (validateAirport) {
				//Try to find the source airport first
				if (route.getSourceAirportId() == null) {failed.add(route);continue;}
				if (route.getDestinationAirportId() == null) {failed.add(route);continue;}
				Optional<Airport> optionalSourceAirport = airportService.searchById(route.getSourceAirportId());
				Optional<Airport> optionalDestinationAirport = airportService.searchById(route.getDestinationAirportId());
				if (!optionalSourceAirport.isPresent()) {failed.add(route);continue;}
				if (!optionalDestinationAirport.isPresent()) {failed.add(route);continue;}
				
				//TODO: flag this
				if ((route.getPrice() == null)&&(generatePrice))
					route.setPrice(BigDecimal.valueOf(computeDistance(optionalSourceAirport.get(),optionalDestinationAirport.get())/10));
			} 
			routeRepo.save(route);
			count++;
		}
		
		return new ImportResult<Route>(count,failed);		
		
	}	
}