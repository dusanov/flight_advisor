package me.dusanov.fa.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
	
	@Id @GeneratedValue
	private Long id;
	
	@NotBlank(message="City name can not be empty")
	private String name;
	
	@NotBlank(message="City country can not be empty")
	private String country;
	
	@NotBlank(message="City description can not be empty")
	private String description;
	//@OneToMany(mappedBy="")
	//private List<Comment> comments;
}
