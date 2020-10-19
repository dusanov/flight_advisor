package me.dusanov.fa.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import me.dusanov.fa.domains.AppUser;

@Repository
@Transactional
public interface UserRepo extends CrudRepository<AppUser, Long> {

	AppUser findByUsername(String username);
	
}
