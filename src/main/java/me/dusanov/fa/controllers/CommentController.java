package me.dusanov.fa.controllers;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.domains.Comment;
import me.dusanov.fa.services.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

	private final CommentService commentService;
	
	@PostMapping
	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<Comment> addComment(@Valid @RequestBody Comment comment,
												Principal principal) {
		
		//don't allow creating someone else's comment (not even admin)
		comment.setUsername(principal.getName());
		
		return ResponseEntity.ok(commentService.addComment(comment));
	}
	
	@PatchMapping("/{commentId}")
	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, 
												 @Valid @RequestBody Comment comment,
												 Principal principal) throws Exception {
		
		//don't allow updating someone else's comment (except admin)
		if (!principal.getName().equalsIgnoreCase("admin") && 
			!comment.getUsername().equalsIgnoreCase(principal.getName()))
			throw new Exception("You are allowed to modify only your own comments");
		
		comment.setId(commentId);
		return ResponseEntity.ok(commentService.updateComment(comment));
	}
	
	@DeleteMapping("/{commentId}")
	@RolesAllowed({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<Comment> deleteComment(@Valid @RequestBody Comment comment,
												Principal principal) throws Exception {
		
		//don't allow deletion of someone else's comment (except admin)
		if (!principal.getName().equalsIgnoreCase("admin") && 
				!comment.getUsername().equalsIgnoreCase(principal.getName()))
				throw new Exception("You are allowed to delete only your own comments");
		
		return ResponseEntity.ok(commentService.deleteComment(comment));
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
