
package me.dusanov.fa.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SearchResult {
	
	private String description;
	private double totalLength;
	private double totalPrice;
	private List<SearchResultItem> routes = new ArrayList<SearchResultItem>();
	private Map<String,Object> geoJson;
	
	public SearchResult() {}
	public SearchResult(String description) {this.description = description;}
	
	public void addRoute(SearchResultItem item) { routes.add(item); }

}