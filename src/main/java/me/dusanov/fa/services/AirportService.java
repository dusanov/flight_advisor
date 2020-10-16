package me.dusanov.fa.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;
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
		
		int count = 0;
		List<Airport> failed = new ArrayList<Airport>();
		
		for (Airport airport : airports) {
			if (validateCity) {
				if (cityService.searchByCityName(airport.getCity()).size() > 0) {
					airportRepo.save(airport);
					count++;
				} else 
					failed.add(airport);
			} else {
				airportRepo.save(airport);
				count++;
			}
		}

		return new ImportResult<Airport>(count,failed);
		
	}

}