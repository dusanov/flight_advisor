package me.dusanov.fa.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.domains.Airport;
import me.dusanov.fa.domains.Route;
import me.dusanov.fa.dtos.ImportResult;
import me.dusanov.fa.repos.RouteRepo;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class RouteService {
	
	private final CityService cityService;
	private final AirportService airportService;
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
	
	public ImportResult<Route> importRoutes(List<Route> routes, boolean validateCity) {
		
		int count = 0;
		List<Route> failed = new ArrayList<Route>();
		
		for (Route route : routes) {
			if (validateCity) {
				//Try to find the source airport first
				Airport srcAirport = airportService.searchByIata(route.getSourceAirport());
				if (srcAirport==null) srcAirport = airportService.searchByIcao(route.getSourceAirport());
				if (srcAirport==null) {
					failed.add(route);
					continue;
				} else {				
					//source airport found, check if the city exists
					if (!srcAirport.getCity().equals("") && cityService.searchByCityName(srcAirport.getCity()).size() > 0) {
						//source airport and the city are ok, let's check destination
						Airport destAirport = airportService.searchByIata(route.getDestinationAirport());
						if (destAirport==null) destAirport = airportService.searchByIcao(route.getDestinationAirport());
						if (destAirport==null) {
							failed.add(route);
							continue;
						} else {
							//destination airport found, check the city
							if (!destAirport.getCity().equals("") && cityService.searchByCityName(destAirport.getCity()).size() > 0) {
								//TODO: generate price
								routeRepo.save(route);
								count++;
							} else {
								failed.add(route);
							}
						}
						
					} else {
						failed.add(route);
						continue;
					}
				}
			} else {
				//TODO: generate price
				routeRepo.save(route);
				count++;
			}
		}

		return new ImportResult<Route>(count,failed);		
		
	}	
}