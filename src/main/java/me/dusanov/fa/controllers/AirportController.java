package me.dusanov.fa.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.domains.Airport;
import me.dusanov.fa.dtos.ImportResult;
import me.dusanov.fa.services.AirportService;
import me.dusanov.fa.services.CsvMapperService;
import me.dusanov.fa.services.FileSystemStorageService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/airports")
public class AirportController {

	private final AirportService airportService;
	private final FileSystemStorageService storageService;
	private final CsvMapperService csvMapper;
	
	@PostMapping
	public ResponseEntity<Airport> addNewAirport(@Valid @RequestBody Airport airport) {
		return ResponseEntity.ok(airportService.addAirport(airport));
	}	
	
	@GetMapping
	public List<Airport> getAll(){
		return airportService.getAll();
	}
	
	@PostMapping("/import")
	public ImportResult<Airport> uploadFile(@RequestParam("file") MultipartFile file,
											@RequestParam(defaultValue = "false", required = false) boolean validateCity) 
	throws Exception {
		//TODO: validate!
		Resource fileResource = storageService.loadAsResource(storageService.store(file));
		List<Airport> airports = csvMapper.loadObjectList(Airport.class, fileResource);
	
		return airportService.importAirports(airports, validateCity);
	}	
	
	@GetMapping("/search/{airportName}")
	public List<Airport> search(@PathVariable String airportName){
		return airportService.searchByAirportName(airportName);
	}	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
								.body(e.getLocalizedMessage());
	}
}
