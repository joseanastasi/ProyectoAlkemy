package com.alkemy.ot9.service;

import java.util.List;
import java.util.Optional;

import com.alkemy.ot9.entities.RoleEntity;
import com.alkemy.ot9.exceptions.RoleNotFound;
import com.alkemy.ot9.interfaceService.IRoleService;
import com.alkemy.ot9.mappers.RoleMapper;
import com.alkemy.ot9.models.Role;
import com.alkemy.ot9.models.RoleEnum;
import com.alkemy.ot9.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleMapper roleMapper;

    public RoleService(RoleMapper roleMapper, RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public RoleService() {
    }

    public Role existsByRoleNombre(RoleEnum roleName) {
        RoleEntity r = roleRepository.findByName(roleName).get();
        return (r != null ? roleMapper.map(r) : null);
    }

    @Override
    public List<Role> list() {
        return roleMapper.map(roleRepository.findAll());
    }

    @Override
    public Role save(RoleEntity role) {
        roleRepository.save(role);
        return roleMapper.map(role);
    }

    @Override
    public Role getById(Long id) throws RoleNotFound {
        Optional<RoleEntity> r = roleRepository.findById(id);

        if (r.isEmpty()) {
            throw new RoleNotFound();
        }
        return roleMapper.map(r.get());
    }

    public Role saved(Role role) {
        return roleMapper.map(roleRepository.save(roleMapper.map(role)));

    }

    public RoleRepository getRoleRepository() {
        return roleRepository;
    }

    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleMapper getRoleMapper() {
        return roleMapper;
    }

    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }
}