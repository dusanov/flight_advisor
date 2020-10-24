package me.dusanov.fa.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultItem {

	private String source;
	private String destination;
	private double price;
	private double length;

}