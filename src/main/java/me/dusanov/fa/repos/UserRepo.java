package me.dusanov.fa.repos;

import org.springframework.data.repository.CrudRepository;

import me.dusanov.fa.domains.User;

public interface UserRepo extends CrudRepository<User, String> {

	//User findByUsername(String username);
	
}
