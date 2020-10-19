package me.dusanov.fa.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
	
	@Id @GeneratedValue
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	/*
	 * this field will contain hashed pwd + salt in following format:
	 *  
	 *	$2y$10$nOUIs5kJ7naTuTFkBy1veuK0kSxUFXfuaOKdOKf9xYT0KKIGSJwFa
	 *	 |  |  |                     |
	 *	 |  |  |                     hash-value = K0kSxUFXfuaOKdOKf9xYT0KKIGSJwFa
	 *	 |  |  |
	 *	 |  |  salt = nOUIs5kJ7naTuTFkBy1veu (22 characters)
	 *	 |  |
	 *	 |  cost-factor = 10 = 2^10 iterations
	 *	 |
	 *	 hash-algorithm = 2y = BCrypt
	 * */
	private String password;
	//private boolean isAdmin;
	/*
	@OneToMany(mappedBy="")
	private List<Comment> comments;
	*/
}
