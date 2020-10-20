package me.dusanov.fa.repos;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import me.dusanov.fa.domains.Comment;

@Repository
public interface CommentRepo extends CrudRepository<Comment, Long> {
	
	List<Comment> findByCityId(Long cityId);
	
	@Query(value = "select * from comment c where c.city_id = ?1 limit ?2", nativeQuery=true)
	List<Comment> findByCityIdWithLimit(Long cityId, int limit);
}
