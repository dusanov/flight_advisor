package me.dusanov.fa.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import me.dusanov.fa.domains.Route;

@Repository
public interface RouteRepo extends CrudRepository<Route, Long> {

}
