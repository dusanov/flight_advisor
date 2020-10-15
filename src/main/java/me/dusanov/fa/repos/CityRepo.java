package me.dusanov.fa.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import me.dusanov.fa.domains.City;

@Repository
public interface CityRepo extends CrudRepository<City, Long> {
	List<City> findByNameContaining(String name);
}
