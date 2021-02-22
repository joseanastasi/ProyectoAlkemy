package com.alkemy.ot9.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alkemy.ot9.entities.UserCommentaryEntity;
import com.alkemy.ot9.exceptions.UserCommentsNotFound;
import com.alkemy.ot9.interfaceService.IUserCommentsService;
import com.alkemy.ot9.repository.IUserCommentsRepository;

@Service
public class UserCommentsServiceImpl implements IUserCommentsService {

	@Autowired
	private IUserCommentsRepository userCommentsRepository;

	@Override
	public List<UserCommentaryEntity> listUserCommentaryEntity() {
		List<UserCommentaryEntity> listUserCommentary = userCommentsRepository.findAll();

		return listUserCommentary;
	}

	@Override
	public void createCommentary(UserCommentaryEntity userCommentaryEntity) {

		userCommentsRepository.save(userCommentaryEntity);
	}

	@Override
	public UserCommentaryEntity findByIdUserCommentaryEntity(Long id) throws UserCommentsNotFound {
		Optional<UserCommentaryEntity> userCommentaryEntity = userCommentsRepository.findById(id);

		if (userCommentaryEntity.isEmpty()) {

			throw new UserCommentsNotFound("No existe el comentario!");
		}

		return userCommentaryEntity.get();
	}

	@Override
	public List<UserCommentaryEntity> listUserCommentaryEntityNews(Long idNews) {
		List<UserCommentaryEntity> userCommentaryEntityNews = userCommentsRepository.findAllComments(idNews);
		return userCommentaryEntityNews;
	}

	@Override
	public void deleteUserCommentaryEntityById(Long id) throws UserCommentsNotFound {
		Optional<UserCommentaryEntity> userCommentaryEntity = userCommentsRepository.findById(id);

		if (userCommentaryEntity.isEmpty()) {

			throw new UserCommentsNotFound("No existe el comentario!");
		}
		userCommentsRepository.deleteById(id);
	}

	@Override
	public Page<UserCommentaryEntity> getListCommentsToEnable(boolean enabled, Pageable pageable) {

		return userCommentsRepository.listCommentsToEnable(enabled, pageable);
	}

}
