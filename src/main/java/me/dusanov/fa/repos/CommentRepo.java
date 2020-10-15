package me.dusanov.fa.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import me.dusanov.fa.domains.Comment;

@Repository
public interface CommentRepo extends CrudRepository<Comment, Long> {

}
