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
	
	public City addCity(@Valid City city) {
		return cityRepo.save(city);
	}
	
	public List<City> getAll(){
		return (List<City>) cityRepo.findAll();
	}
	
	public List<City> searchByCityName(String cityName){
		return cityRepo.findByNameContaining(cityName);
	}

}
