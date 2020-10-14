package me.dusanov.fa.repos;

import org.springframework.data.repository.CrudRepository;

import me.dusanov.fa.domains.Airport;

public interface AirportRepo extends CrudRepository<Airport, Long> {

}
