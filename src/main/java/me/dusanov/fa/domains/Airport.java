package me.dusanov.fa.domains;

import java.time.ZoneId;
import java.time.ZoneOffset;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airport {
	/*
	Airport ID, Identifier for this airport.
	Name, Name of airport.
	City, Main city served by airport.
	Country, Country or territory where airport is located.
	IATA, 3-letter IATA code. Null if not assigned/unknown.
	ICAO, 4-letter ICAO code. Null if not assigned.
	Latitude, Decimal degrees, usually to six significant digits.
	Longitude, Decimal degrees, usually to six significant digits.
	Altitude, In feet.
	Timezone, Hours offset from UTC.
	DST, Daylight savings time. One of E (Europe), A (US/Canada), S (South America), O (Australia), Z (New Zealand), N (None) or U (Unknown).
	Tz database time zone, Timezone in "tz" (Olson) format, eg. "America/Los_Angeles".
	Type, Type of the airport.
	Source. Source of this data.
	*/
	@Id
	private Long airportId;
	private String name;
	//Don’t import airports and routes if the city doesn’t exist.
	private String city;
	private String country;
	private String iata;
	private String icao;
	private double latitude;
	private double longitude;
	private int altitude;
	private ZoneOffset timezone;
	private char dst;
	private ZoneId tz;
	private String type;
	private String source;
}
