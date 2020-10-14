package me.dusanov.fa.domains;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Comment {
	@Id @GeneratedValue
	private Long id;
	/*
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	private User user;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	private City city;
	*/
	private String username;
	private Long cityId;
	private String comment;
	private LocalDate createdDate;
	private LocalDate modifiedDate;
}
