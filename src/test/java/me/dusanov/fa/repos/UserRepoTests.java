package me.dusanov.fa.repos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import me.dusanov.fa.domains.AppUser;

@SpringBootTest
public class UserRepoTests {

	@Autowired
	private UserRepo userRepo;
	
	@Test
	@Transactional
	public void testFindByUsername() {
		AppUser user = new AppUser(null,"admin","admin","admin","admin");
		userRepo.save(user);
		AppUser admin = userRepo.findByUsername("admin");
		assertNotNull(admin);
		assertEquals("admin", admin.getLastName());
	}
}
