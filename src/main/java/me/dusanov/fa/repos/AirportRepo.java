package me.dusanov.fa.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import me.dusanov.fa.domains.Airport;

@Repository
public interface AirportRepo extends CrudRepository<Airport, Long> {
	List<Airport> findByNameContaining(String name);
}
