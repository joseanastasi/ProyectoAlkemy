package com.alkemy.ot9.service;

import java.util.*;

import com.alkemy.ot9.entities.UserEntity;
import com.alkemy.ot9.exceptions.UserNotFoundException;
import com.alkemy.ot9.interfaceService.IUserService;
import com.alkemy.ot9.mappers.RoleMapper;
import com.alkemy.ot9.mappers.UserMapper;
import com.alkemy.ot9.models.Role;
import com.alkemy.ot9.models.RoleEnum;
import com.alkemy.ot9.models.User;
import com.alkemy.ot9.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserMapper userMapper;

	@Autowired
	RoleService roleService;

	@Autowired
	RoleMapper roleMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	public UserService(UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder,
			RoleService roleService) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.roleService = roleService;
	}

	public UserService() {
	}

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public User toCreate(User user) {
		String passwordEnc = passwordEncoder.encode(user.getPassword());
		user.setPassword(passwordEnc);
		Role role = roleService.existsByRoleNombre(RoleEnum.ROLE_ADMIN);
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);
		role.setRoleToUser(user);
		UserEntity u = userMapper.map(user);
		userRepository.save(u);
		return user;
	}

	public User saved(User user) {
		UserEntity u = userMapper.map(user);
		userRepository.save(u);
		return user;
	}

	public List<User> findAll() {
		return userMapper.map(userRepository.findAll());
	}

	public Optional<User> findUserByEmail(String email) {
		Optional<UserEntity> user = userRepository.findByEmail(email);
		return (user != null ? userMapper.map(user) : null);
	}

	public boolean findUser(String email) {
		Optional<UserEntity> user = userRepository.findByEmail(email);
		return (user != null ? true : false);
	}

	public User findById(Long id) throws UserNotFoundException {
		Optional<UserEntity> r = userRepository.findById(id);

		if (r.isEmpty()) {
			throw new UserNotFoundException();
		}
		return userMapper.map(r.get());
	}

	public boolean toDelete(Long id) throws UserNotFoundException {
		UserEntity user = userMapper.map(findById(id));
		userRepository.delete(user);
		return true;
	}

	public boolean toUpdate(User user) throws UserNotFoundException {
		if (toCreate(user) != null) {
			return true;
		}
		return false;
	}

	public User update(User user) throws UserNotFoundException {
		return toCreate(user);
	}

	public boolean existsByName(String name) {
		UserEntity user = userRepository.findByName(name);
		return (user != null ? true : false);
	}

	public User findByEmail(String email) {
		Optional<UserEntity> user = userRepository.findByEmail(email);
		return (user != null ? userMapper.map(user.get()) : null);

	}

	public User login(String email, String password) {
		User u = findByEmail(email);
		if (u == null || !u.getPassword().equals(password)) {
			return null;
		}

		return u;
	}

	@Override
	public Optional<UserEntity> findEmail(String email) {
		Optional<UserEntity> user = userRepository.findByEmail(email);
		return (user != null ? user : null);
	}

	@Override
	public Page<UserEntity> getAllUser(Pageable pageable) {

		return userRepository.findAll(pageable);
	}

}
