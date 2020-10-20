package me.dusanov.fa.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CityRepoTests {
	
	@Autowired
	private CityRepo cityRepo;
	
	@Test
	public void testCityCount() {
		long numOfCities = cityRepo.count();
		assertEquals(8L,numOfCities);
	}
}
