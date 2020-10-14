package me.dusanov.fa.repos;

import org.springframework.data.repository.CrudRepository;

import me.dusanov.fa.domains.Comment;

public interface CommentRepo extends CrudRepository<Comment, Long> {

}
