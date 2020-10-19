package me.dusanov.fa.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResultItem {

	private String source;
	private String destination;
	private double price;
	private double length;

}