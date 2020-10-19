package me.dusanov.fa.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SearchResult {

	private String description;
	private double totalLength;
	private double totalPrice;
	private List<SearchResultItem> routes = new ArrayList<SearchResultItem>();
	
	public void addRoute(SearchResultItem item) {
		routes.add(item);
	}
	
	
}
