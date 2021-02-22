package com.alkemy.ot9.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.alkemy.ot9.entities.RoleEntity;
import com.alkemy.ot9.exceptions.RoleNotFound;
import com.alkemy.ot9.mappers.RoleMapper;
import com.alkemy.ot9.models.Role;
import com.alkemy.ot9.models.RoleEnum;
import com.alkemy.ot9.repository.RoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoleServiceTest {

    @Mock
    RoleRepository roleRepository;
    @Mock
    RoleMapper roleMapper;
    @Mock
    RoleService roleService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        roleMapper = new RoleMapper(new ModelMapper());
        roleService = new RoleService(roleMapper, roleRepository);
    }

    @Test
    public void testCreateRole() {
        Long id = 1L;
        Role admin = new Role();
        Role user = new Role();
        admin.setId(id);
        admin.setName(RoleEnum.ROLE_ADMIN);
        user.setName(RoleEnum.ROLE_USER);
        user.setId(id);
        Set<Role> roles = new HashSet<>();
        roles.add(admin);
        roles.add(user);

        RoleEntity roleAdmin = new RoleEntity();
        RoleEntity roleUser = new RoleEntity();
        Set<RoleEntity> rolesE = new HashSet<>();

        roleAdmin.setName(RoleEnum.ROLE_ADMIN);
        roleUser.setName(RoleEnum.ROLE_USER);
        roleAdmin.setId(id);
        roleUser.setId(id);
        rolesE.add(roleAdmin);

        when(roleRepository.save(any())).thenReturn(roleAdmin);
        when(roleRepository.save(any())).thenReturn(roleUser);

        Role resultId = roleService.saved(admin);
        Role resultId2 = roleService.saved(user);

        Assertions.assertEquals(roleAdmin.getId(), resultId2.getId());

        Assertions.assertEquals(roleUser.getId(), resultId.getId());

    }

    @Test
    public void listRoles() {

        Long id = 1L;
        Role admin = new Role();

        admin.setId(id);
        admin.setName(RoleEnum.ROLE_ADMIN);
        List<Role> roles = new ArrayList<>();
        roles.add(admin);

        RoleEntity roleAdmin = new RoleEntity();
        List<RoleEntity> rolesE = new ArrayList<>();

        roleAdmin.setName(RoleEnum.ROLE_ADMIN);
        roleAdmin.setId(id);

        rolesE.add(roleAdmin);

        when(roleRepository.findAll()).thenReturn(rolesE);

        List<Role> results = roleService.list();

        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(roleAdmin.getId(), results.get(0).getId());
        Assertions.assertEquals(roleAdmin.getName(), results.get(0).getName());
    }

    @Test
    public void testRoleById() throws RoleNotFound {
        Long id = 1L;
        Role admin = new Role();
        Role user = new Role();
        admin.setId(id);
        admin.setName(RoleEnum.ROLE_ADMIN);
        user.setName(RoleEnum.ROLE_USER);
        user.setId(id);
        Set<Role> roles = new HashSet<>();
        roles.add(admin);
        roles.add(user);

        RoleEntity roleAdmin = new RoleEntity();
        RoleEntity roleUser = new RoleEntity();
        Set<RoleEntity> rolesE = new HashSet<>();

        roleAdmin.setName(RoleEnum.ROLE_ADMIN);
        roleUser.setName(RoleEnum.ROLE_USER);
        roleAdmin.setId(id);
        roleUser.setId(id);
        rolesE.add(roleAdmin);

        when(roleRepository.findById(id)).thenReturn(Optional.of(roleAdmin));
        Role result = roleService.getById(id);

        Assertions.assertEquals(roleAdmin.getId(), result.getId());

    }

    @Test
    public void testFindByName() throws RoleNotFound {
        Long id = 1L;
        Role admin = new Role();

        admin.setId(id);
        admin.setName(RoleEnum.ROLE_ADMIN);

        Set<Role> roles = new HashSet<>();
        roles.add(admin);

        RoleEntity roleAdmin = new RoleEntity();

        Set<RoleEntity> rolesE = new HashSet<>();

        roleAdmin.setName(RoleEnum.ROLE_ADMIN);
        roleAdmin.setId(id);
        rolesE.add(roleAdmin);

        when(roleRepository.findById(id)).thenReturn(Optional.of(roleAdmin));
        Role result = roleService.getById(id);

        Assertions.assertEquals(roleAdmin.getName(), result.getName());

    }

}
