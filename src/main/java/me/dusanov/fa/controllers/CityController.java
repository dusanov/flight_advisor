package me.dusanov.fa.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.domains.City;
import me.dusanov.fa.services.CityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cities")
public class CityController {

	private final CityService cityService;
	
	@PostMapping
	public ResponseEntity<City> addNewCity(@Valid @RequestBody City city) {
		return ResponseEntity.ok(cityService.addCity(city));
	}	
	
	@GetMapping
	public List<City> getAll(){
		return cityService.getAll();
	}
	
	@GetMapping("/search/{cityName}")
	public List<City> search(@PathVariable String cityName){
		return cityService.searchByCityName(cityName);
	}	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
