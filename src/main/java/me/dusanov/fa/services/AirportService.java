package me.dusanov.fa.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.GraphComponent;
import me.dusanov.fa.domains.Airport;
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
	
	public ImportResult<Airport> importAirports(List<Airport> airports, boolean validateCity){
		
		// we wont be deleting airoprts first, since dataset has id and H2 is smart enough
		
		int count = 0;
		List<Airport> failed = new ArrayList<Airport>();
		
		for (Airport airport : airports) {
			if (validateCity) {
				if ( !airport.getCity().equals("") && cityService.searchByCityName(airport.getCity()).size() > 0) {
					airportRepo.save(airport);
					count++;
				} else 
					failed.add(airport);
			} else {
				airportRepo.save(airport);
				count++;
			}
		}
		
		//reload nodes
		graph.loadNodes();
		
		return new ImportResult<Airport>(count,failed);
		
	}

}