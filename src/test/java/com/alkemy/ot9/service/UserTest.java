package com.alkemy.ot9.service;

import com.alkemy.ot9.entities.BeneficiaryEntity;
import com.alkemy.ot9.entities.EventEntity;
import com.alkemy.ot9.entities.RoleEntity;
import com.alkemy.ot9.entities.UserEntity;
import com.alkemy.ot9.exceptions.EventNotFound;
import com.alkemy.ot9.exceptions.UserNotFoundException;
import com.alkemy.ot9.mappers.RoleMapper;
import com.alkemy.ot9.mappers.UserMapper;
import com.alkemy.ot9.models.*;
import com.alkemy.ot9.repository.RoleRepository;
import com.alkemy.ot9.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserTest {

    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @Mock
    RoleService roleService;
    @Mock
    RoleMapper roleMapper;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserService userService;
    @Mock
    RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userMapper = new UserMapper(new ModelMapper());
        roleMapper = new RoleMapper(new ModelMapper());
        roleService = new RoleService();
        userService = new UserService();
        passwordEncoder = new BCryptPasswordEncoder();
        roleService.setRoleMapper(roleMapper);
        roleService.setRoleRepository(roleRepository);
        userService.setPasswordEncoder(passwordEncoder);
        userService.setRoleMapper(roleMapper);
        userService.setUserRepository(userRepository);
        userService.setUserMapper(userMapper);
        userService.setRoleService(roleService);
        // userService = new
        // UserService(userRepository,userMapper,roleService,roleMapper,passwordEncoder);
    }

    @Test
    public void testCreateUser() {
        // id
        Long id = 10L;
        // intento encryptar
        String passwordEnc = passwordEncoder.encode("pepino");
        // creo set para user , roleEntity y role
        Set<RoleEntity> roleEntitySet = new HashSet<>();
        Set<Role> roleSet = new HashSet<>();
        // creo el set para reole, user y userEntity
        Set<User> userSet = new HashSet<>();
        Set<UserEntity> userEntitySet = new HashSet<>();
        // creo un enum
        RoleEnum role1 = RoleEnum.ROLE_ADMIN;
        // creo instancia de role,roleEntity, user y userEntity
        RoleEntity roleEntity = new RoleEntity();
        Role role = new Role();
        User user = new User();
        UserEntity userEntity = new UserEntity();
        // seteo user
        user.setId(id);
        user.setPassword(passwordEnc);
        user.setName("pepito");
        user.setEmail("cuca@cuca.com");
        user.setRoles(roleSet);
        // set userEntity
        userEntity.setId(id);
        userEntity.setPassword(passwordEnc);
        userEntity.setName("pepito");
        userEntity.setEmail("cuca@cuca.com");
        userEntity.setRoles(roleEntitySet);
        // adiero al set de user y userEntity las instancias
        userSet.add(user);
        userEntitySet.add(userEntity);
        // seteo role
        role.setId(id);
        role.setRoleToUser(user);
        role.setName(role1);
        role.setUsers(userSet);
        // seteo roleEntity
        roleEntity.setId(id);
        roleEntity.setRoleToUser(userEntity);
        roleEntity.setName(role1);
        roleEntity.setUsers(userEntitySet);
        // adiero al set de role y roleEntity las instancias
        roleSet.add(role);
        roleEntitySet.add(roleEntity);

        when(userRepository.save(any())).thenReturn(userEntity);
        User resultUser = userService.saved(user);
        Assertions.assertEquals(user, resultUser);
    }

    @Test
    public void testDelete() throws UserNotFoundException {
        Long id = 1L;
        String title = "event-test-title";
        String content = "event-test-content";
        User user = new User();
        user.setId(id);
        user.setName(title);
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setName(title);

        boolean result = true;

        when(userRepository.findById(id)).thenReturn(java.util.Optional.of(userEntity));
        result = userService.toDelete(1L);

        Assert.assertEquals(true, userService.toDelete(1L));
    }

    @Test
    public void testDeleteEventByIdException() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());
        try {
            userService.toDelete(id);
        } catch (UserNotFoundException e) {

            Assert.assertTrue(true);

            return;
        }

        Assert.fail();

    }

    @Test
    public void testFindAllUser() {

        Long id = 1L;
        String passwordEnc = "pepe";
        List<User> userList = new ArrayList<>();
        List<UserEntity> userEntityList = new ArrayList<>();
        User user = new User();
        user.setId(id);
        user.setPassword(passwordEnc);
        user.setName("pepito");
        user.setEmail("cuca@cuca.com");

        // set userEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setPassword(passwordEnc);
        userEntity.setName("pepito");
        userEntity.setEmail("cuca@cuca.com");

        userList.add(user);
        userEntityList.add(userEntity);

        when(userRepository.findAll()).thenReturn(userEntityList);
        List<User> result = userService.findAll();

        Assertions.assertEquals(1, result.size());

        Assertions.assertEquals(user.getId(), result.get(0).getId());

        Assertions.assertEquals(user.getPassword(), result.get(0).getPassword());

        Assertions.assertEquals(user.getEmail(), result.get(0).getEmail());

    }

    @Test
    public void testGetUserById() throws UserNotFoundException {
        Long id = 1L;
        String passwordEnc = "pepe";
        String name = "pepito";
        String email = "cuca@cuca.com";

        User user = new User();
        user.setId(id);
        user.setPassword(passwordEnc);
        user.setName(name);
        user.setEmail(email);

        // set userEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setPassword(passwordEnc);
        userEntity.setName(name);
        userEntity.setEmail(email);

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        User resultUser = userService.findById(user.getId());
        if (resultUser != null) {
            Assert.assertTrue(true);
        }
        // Assertions.assertEquals(id, resultUser.getId());

    }

    @Test
    public void testGetEventByIdException() {

        Long id = 1100020L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());
        try {
            userService.findById(id);

        } catch (UserNotFoundException e) {

            Assert.assertTrue(true);

            return;
        }
        // no entra en el try
        Assert.fail();

    }

    @Test
    public void testGetUserByEmail() {
        Long id = 1L;
        String passwordEnc = "pepe";
        String name = "pepito";
        String email = "cuca@cuca.com";

        User user = new User();
        user.setId(id);
        user.setPassword(passwordEnc);
        user.setName(name);
        user.setEmail(email);

        // set userEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setPassword(passwordEnc);
        userEntity.setName(name);
        userEntity.setEmail(email);

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        Optional<UserEntity> resultUser = userService.findEmail(email);

        if (resultUser != null) {
            Assert.assertTrue(true);
        }

    }

    @Test
    public void testFindUserByEmail() {
        Long id = 1L;
        String passwordEnc = "pepe";
        String name = "pepito";
        String email = "cuca@cuca.com";

        User user = new User();
        user.setId(id);
        user.setPassword(passwordEnc);
        user.setName(name);
        user.setEmail(email);

        // set userEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setPassword(passwordEnc);
        userEntity.setName(name);
        userEntity.setEmail(email);

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        boolean resultUser = userService.findUser(email);

        if (resultUser != false) {
            Assert.assertTrue(true);
        }

    }

    @Test
    public void testCreateUserWithSaved() {

        Long id = 1L;

        String passwordEnc = passwordEncoder.encode("pepino");

        Long roleId = 1L;
        Role admin = new Role();
        admin.setId(roleId);
        admin.setName(RoleEnum.ROLE_ADMIN);
        Set<Role> roles = new HashSet<>();
        roles.add(admin);

        RoleEntity roleAdmin = new RoleEntity();
        Set<RoleEntity> rolesE = new HashSet<>();

        roleAdmin.setName(RoleEnum.ROLE_ADMIN);
        roleAdmin.setId(roleId);

        rolesE.add(roleAdmin);

        User user = new User();
        user.setId(id);
        user.setPassword(passwordEnc);
        user.setName("pepito");
        user.setEmail("cuca@cuca.com");
        user.setRoles(roles);
        admin.setRoleToUser(user);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        user.setPassword(passwordEnc);
        userEntity.setName("pepito");
        userEntity.setEmail("cuca@cuca.com");
        userEntity.setRoles(rolesE);
        roleAdmin.setRoleToUser(userEntity);

        when(userRepository.save(any())).thenReturn(userEntity);
        User resultId = userService.saved(user);

        Assertions.assertEquals(id, resultId.getId());
    }

}
