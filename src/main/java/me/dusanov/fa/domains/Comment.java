package me.dusanov.fa.domains;


import java.time.LocalDate;

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
public class Comment {
	@Id @GeneratedValue
	private Long id;
	private String username;
	private Long cityId;
	private String comment;
	private LocalDate createdDate;
	private LocalDate modifiedDate;
}
