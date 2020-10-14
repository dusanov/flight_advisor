package me.dusanov.fa.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import me.dusanov.fa.domains.User;

@SpringBootTest
public class UserRepoTests {

	@Autowired
	private UserRepo userRepo;
	
	@Test
	public void testFindByUsername() {
		//User admin = userRepo.findByUsername("admin");
		Optional<User> admin = userRepo.findById("admin");
		assertEquals(true, admin.isPresent());
		assertEquals("admin", admin.get().getLastName());
		assertEquals(true, admin.get().isAdmin());
	}
}
