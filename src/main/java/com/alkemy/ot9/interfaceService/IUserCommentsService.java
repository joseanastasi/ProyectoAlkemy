package com.alkemy.ot9.interfaceService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alkemy.ot9.entities.UserCommentaryEntity;
import com.alkemy.ot9.exceptions.UserCommentsNotFound;

public interface IUserCommentsService {

	public List<UserCommentaryEntity> listUserCommentaryEntity();

	public void createCommentary(UserCommentaryEntity userCommentaryEntity);

	public UserCommentaryEntity findByIdUserCommentaryEntity(Long id) throws UserCommentsNotFound;

	public void deleteUserCommentaryEntityById(Long id) throws UserCommentsNotFound;

	public List<UserCommentaryEntity> listUserCommentaryEntityNews(Long idNews);
	
	public Page<UserCommentaryEntity> getListCommentsToEnable(boolean enabled, Pageable pageable);
}
