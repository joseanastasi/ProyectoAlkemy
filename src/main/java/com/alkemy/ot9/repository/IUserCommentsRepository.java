package com.alkemy.ot9.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alkemy.ot9.entities.UserCommentaryEntity;

@Repository
public interface IUserCommentsRepository extends JpaRepository<UserCommentaryEntity, Long> {

	@Query(value = "SELECT * FROM comments_people WHERE news_id = :idNews", nativeQuery = true)
	List<UserCommentaryEntity> findAllComments(@Param("idNews") Long idNews);
	
	@Query(value = "SELECT * FROM comments_people WHERE enabled = :enabled", nativeQuery = true)
	Page<UserCommentaryEntity> listCommentsToEnable(@Param("enabled") boolean enabled, Pageable pageable);
}
