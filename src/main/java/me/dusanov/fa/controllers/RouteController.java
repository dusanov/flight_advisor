package me.dusanov.fa.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.core.io.Resource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.domains.Route;
import me.dusanov.fa.dtos.ImportResult;
import me.dusanov.fa.dtos.SearchResult;
import me.dusanov.fa.services.CsvMapperService;
import me.dusanov.fa.services.FileSystemStorageService;
import me.dusanov.fa.services.RouteService;
import me.dusanov.fa.services.RoutesViewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes")
public class RouteController {

	private final RouteService routeService;
	private final FileSystemStorageService storageService;
	private final CsvMapperService csvMapper;
	private final RoutesViewService routesViewService;
	
	@PostMapping
	@RolesAllowed("ROLE_ADMIN")
	public ResponseEntity<Route> addNewRoute(@Valid @RequestBody Route route) {
		return ResponseEntity.ok(routeService.addRoute(route));
	}	
	
	@GetMapping
	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	public List<Route> getAll(){
		return routeService.getAll();
	}
	
	@PostMapping("/import")
	@RolesAllowed("ROLE_ADMIN")
	public ImportResult<Route> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam(defaultValue = "true", required = false) boolean validateCity) 
	throws Exception {
		
		Resource fileResource = storageService.loadAsResource(storageService.store(file));
		List<Route> routes = csvMapper.loadObjectList(Route.class, fileResource);
		
		return routeService.importRoutes(routes, validateCity);
	}	
	
	@GetMapping("/search/{source}/{destination}")
	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	public SearchResult search(@PathVariable String source, @PathVariable String destination){
		return routesViewService.findTheCheapestRoute(source,destination);
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
