package me.dusanov.fa.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class City {
	@Id @GeneratedValue
	private Long id;
	private String name;
	private String country;
	private String description;
	//@OneToMany(mappedBy="")
	//private List<Comment> comments;
}
