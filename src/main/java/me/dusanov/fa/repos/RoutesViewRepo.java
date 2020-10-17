package me.dusanov.fa.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import me.dusanov.fa.domains.RoutesView;

@Repository
public interface RoutesViewRepo extends CrudRepository<RoutesView, Long> {}
