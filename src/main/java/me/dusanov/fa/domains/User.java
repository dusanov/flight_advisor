package me.dusanov.fa.domains;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class User {
	@Id /*@GeneratedValue
	private Long id;*/ // we want username to be unique
	private String username;
	private String firstName;
	private String lastName;
	private String hashpwd;
	private boolean isAdmin;
	/*
	@OneToMany(mappedBy="")
	private List<Comment> comments;
	*/
}
