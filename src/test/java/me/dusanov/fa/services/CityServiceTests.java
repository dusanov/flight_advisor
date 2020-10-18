package me.dusanov.fa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import me.dusanov.fa.domains.City;

@SpringBootTest
public class CityServiceTests {
	
	@Autowired
	private CityService cityService;
	
	@Test
	@Transactional
	public void testAddingACity() {
		//this should actually fail the validation on empty description field (or any other)
		City city = cityService.addCity(new City(null,"city of angels","neverland","not empty desc"));
		assertNotNull(city);
		assertNotNull(city.getId());
	}
	
	@Test
	public void testGetAllCities() {
		assertEquals(7,cityService.getAll().size());
	}
	
	@Test
	public void testSearchForACityByName() {
		List<City> result = cityService.searchByCityName("sad");
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("novi sad", result.get(0).getName());
	}

}
