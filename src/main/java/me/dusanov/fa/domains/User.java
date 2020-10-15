package me.dusanov.fa.domains;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id /*@GeneratedValue
	private Long id;*/ // we want username to be unique
	private String username;
	private String firstName;
	private String lastName;
	private String hashpwd;
	private String salt;
	private boolean isAdmin;
	/*
	@OneToMany(mappedBy="")
	private List<Comment> comments;
	*/
}
