package me.dusanov.fa.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.RequiredArgsConstructor;
import me.dusanov.fa.domains.Comment;
import me.dusanov.fa.repos.CommentRepo;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class CommentService {
	
	private final CommentRepo commentRepo;
	
	public Comment addComment(Comment comment) {
		comment.setCreatedDate(LocalDate.now());
		return commentRepo.save(comment);
	}
	
	public Comment updateComment(Comment comment) throws Exception {
		
		Optional<Comment> dbComment = commentRepo.findById(comment.getId());
		if (!dbComment.isPresent()) 
			throw new Exception(String.format("Comment id: %s not found", comment.getId()));
		dbComment.get().setComment(comment.getComment());
		dbComment.get().setModifiedDate(LocalDate.now());
		
		return commentRepo.save(dbComment.get());
	}
	
	public Comment deleteComment(Comment comment) {
		commentRepo.delete(comment);
		return comment;
	}
	
	public List<Comment> getComments(Long cityId, int limit/*, String username*/){
		//call repo with the limit
		if (limit > 0) return commentRepo.findByCityIdWithLimit(cityId, limit);
		//call repo without the limit
		else return commentRepo.findByCityId(cityId);
	}

}
