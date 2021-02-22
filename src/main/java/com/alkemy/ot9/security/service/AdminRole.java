package com.alkemy.ot9.security.service;

import java.util.*;
import com.alkemy.ot9.mappers.RoleMapper;
import com.alkemy.ot9.models.Role;
import com.alkemy.ot9.models.RoleEnum;
import com.alkemy.ot9.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * @Service public class AdminRole implements CommandLineRunner {
 * 
 * @Autowired RoleService roleService;
 * 
 * @Autowired PasswordEncoder passwordEncoder;
 * 
 * @Autowired RoleMapper roleMapper;
 * 
 * @Override public void run(String... args) throws Exception { Role roleAdmin =
 * new Role(); roleAdmin.setName(RoleEnum.ROLE_ADMIN); Role roleUser = new
 * Role(); roleUser.setName(RoleEnum.ROLE_USER); Set<Role> roles = new
 * HashSet<>(); roles.add(roleAdmin); roles.add(roleUser); // insert
 * roleService.save(roleMapper.map(roleAdmin));
 * roleService.save(roleMapper.map(roleUser)); } }
 * 
 */
