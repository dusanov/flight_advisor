package me.dusanov.fa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.GraphComponent;
import me.dusanov.fa.domains.Airport;
import me.dusanov.fa.domains.City;
import me.dusanov.fa.dtos.ImportResult;
import me.dusanov.fa.repos.AirportRepo;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class AirportService {
	
	private final CityService cityService;
	private final AirportRepo airportRepo;
	private final GraphComponent graph;
	
	public Airport addAirport(@Valid Airport airport) {
		return airportRepo.save(airport);
	}
	
	public List<Airport> getAll(){
		return (List<Airport>) airportRepo.findAll();
	}
	
	public List<Airport> searchByAirportName(String airportName){
		return airportRepo.findByNameContaining(airportName);
	}
	
	public Airport searchByIcao(String icao) {
		return airportRepo.findByIcao(icao);
	}
	
	public Airport searchByIata(String iata) {
		return airportRepo.findByIata(iata);
	}
	
	public Optional<Airport> searchById(Long id) {
		return airportRepo.findById(id);
	}
	
	public ImportResult<Airport> importAirports(List<Airport> airports, boolean validateCity){
		
		if (!validateCity) cityService.deleteAll();
		
		int count = 0;
		List<Airport> failed = new ArrayList<Airport>();
		
		for (Airport airport : airports) {
			if ( airport.getCity().equals("") ) { failed.add(airport); continue;}
			else {				
				if (validateCity) {
					List<City> cityRes = cityService.searchByCityNameAndCountry(airport.getCity(),airport.getCountry());
					if (cityRes.size() == 0){ failed.add(airport); continue;}
				} else {
					City newCity = new City(null,airport.getCity(),airport.getCountry(),"autogenerated city",null);
					cityService.addCity(newCity);
				}
				
				airportRepo.save(airport);
				count++;
			} 
		}
		
		System.out.println("== import of airports done, about to generate nodes");
		//reload nodes
		graph.loadNodes();
		
		return new ImportResult<Airport>(count,failed);
		
	}

}