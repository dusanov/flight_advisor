package me.dusanov.fa.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.domains.City;
import me.dusanov.fa.repos.CityRepo;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class CityService {
	
	private final CityRepo cityRepo;
	private final CommentService commentService;
	
	public City addCity(@Valid City city) {
		return cityRepo.save(city);
	}
	
	public void deleteAll() {cityRepo.deleteAll();}
	
	public List<City> getAll(int limitComments){
		
		List<City> cities = (List<City>) cityRepo.findAll();
		for (City city : cities)
				city.setComments(commentService.getComments(city.getId(), limitComments));
		
		return cities;
	}
	
	public List<City> searchByCityName(String cityName, int limitComments){
		
		List<City> cities = cityRepo.findByNameContaining(cityName);
		for (City city : cities)
			city.setComments(commentService.getComments(city.getId(), limitComments));
		
		return cities; 
	}
	
	public List<City> searchByCityNameAndCountry(String cityName, String countryName){
		return cityRepo.findByNameAndCountry(cityName, countryName);
	}

}
