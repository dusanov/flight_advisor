package me.dusanov.fa.repos;

import org.springframework.data.repository.CrudRepository;
import me.dusanov.fa.domains.City;

public interface CityRepo extends CrudRepository<City, Long> {}
