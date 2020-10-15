package me.dusanov.fa.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import me.dusanov.fa.domains.User;

@Repository
public interface UserRepo extends CrudRepository<User, String> {

	//User findByUsername(String username);
	
}
