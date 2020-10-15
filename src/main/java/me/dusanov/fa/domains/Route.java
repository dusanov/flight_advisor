package me.dusanov.fa.domains;

import java.math.BigDecimal;

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
public class Route {

	/*
	Airline,	2-letter (IATA) or 3-letter (ICAO) code of the airline.
	Airline ID, Identifier for airline.
	Source airport, 3-letter (IATA) or 4-letter (ICAO) code of the source airport.
	Source airport ID, Identifier for source airport.
	Destination airport, 3-letter (IATA) or 4-letter (ICAO) code of the destination airport.
	Destination airport ID, Unique OpenFlights identifier for destination airport.
	Codeshare, "Y" if this flight is a codeshare, empty otherwise.
	Stops, Number of stops on this flight ("0" for direct).
	Equipment, 3-letter codes for plane type(s) generally used on this flight, separated by spaces.
	Price Flight cost.	
	*/
	
	@Id @GeneratedValue
	private Long id;
	private String airline;
	private Long airlineId;
	private String sourceAirport;
	private Long sourceAirportId;
	private String destinationAirport;
	private Long destinationAirportId;
	private char codeshare;
	private int stops;
	private String equipment;
	private BigDecimal price;
	
}
