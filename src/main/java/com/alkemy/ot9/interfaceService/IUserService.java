package com.alkemy.ot9.interfaceService;

import com.alkemy.ot9.entities.UserEntity;
import com.alkemy.ot9.exceptions.UserNotFoundException;
import com.alkemy.ot9.models.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

	public User toCreate(User user);

	public List<User> findAll();

	public User findByEmail(String email);

	public Optional<User> findUserByEmail(String email);

	public Optional<UserEntity> findEmail(String email);

	public boolean existsByName(String name);

	public User findById(Long id) throws UserNotFoundException;

	public boolean toDelete(Long id) throws UserNotFoundException;

	public boolean toUpdate(User user) throws UserNotFoundException;

	public boolean findUser(String email);

	Page<UserEntity> getAllUser(Pageable pageable);
}
