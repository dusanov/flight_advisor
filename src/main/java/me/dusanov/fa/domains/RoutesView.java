package me.dusanov.fa.domains;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Immutable
@Subselect(
"select route.id id, route.airline airline, source.city source, destination.city destination, route.stops stops, route.price price, "+ 
"source.latitude source_latitude, source.longitude source_longitude, destination.latitude destination_latitude, destination.longitude destination_longitude " + 
"	from route route " + 
"	  inner join airport source on source.airport_id = route.source_airport_id " + 
"	  inner join airport destination on destination.airport_id = route.destination_airport_id"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoutesView {
	
	@Id
	private Long id;
	private String airline;
	private String source;
	private double sourceLatitude;
	private double sourceLongitude;
	private String destination;
	private double destinationLatitude;
	private double destinationLongitude;
	private int stops;
	private BigDecimal price;

}
