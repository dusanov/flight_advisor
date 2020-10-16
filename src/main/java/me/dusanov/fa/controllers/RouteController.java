package me.dusanov.fa.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.domains.Route;
import me.dusanov.fa.dtos.ImportResult;
import me.dusanov.fa.services.CsvMapperService;
import me.dusanov.fa.services.FileSystemStorageService;
import me.dusanov.fa.services.RouteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes")
public class RouteController {

	private final RouteService routeService;
	private final FileSystemStorageService storageService;
	private final CsvMapperService csvMapper;
	
	@PostMapping
	public ResponseEntity<Route> addNewRoute(@Valid @RequestBody Route route) {
		return ResponseEntity.ok(routeService.addRoute(route));
	}	
	
	@GetMapping
	public List<Route> getAll(){
		return routeService.getAll();
	}
	
	@PostMapping("/import")
	public ImportResult<Route> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam(defaultValue = "false", required = false) boolean validateCity) 
	throws Exception {
		
		Resource fileResource = storageService.loadAsResource(storageService.store(file));
		List<Route> routes = csvMapper.loadObjectList(Route.class, fileResource);
		
		return routeService.importRoutes(routes, validateCity);
	}	
	
	@GetMapping("/search")
	public List<Route> search(@RequestParam String src, @RequestParam String dest){
		return routeService.search(src,dest);
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
