package me.dusanov.fa.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
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
	@Transient
	private List<Comment> comments = new ArrayList<Comment>();
}
