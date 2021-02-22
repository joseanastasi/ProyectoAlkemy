package com.alkemy.ot9.mappers;

import java.util.*;

import com.alkemy.ot9.entities.RoleEntity;
import com.alkemy.ot9.models.Role;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleMapper {

    private ModelMapper modelMapper;

    @Autowired
    public RoleMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Role map(RoleEntity roleEntity) {
        return modelMapper.map(roleEntity, Role.class);
    }

    public Role map(Optional<RoleEntity> roleEntity) {
        Role role = modelMapper.map(roleEntity, Role.class);
        return (role);
    }

    public List<Role> map(List<RoleEntity> RoleEntities) {
        List<Role> roles = new ArrayList<>();
        RoleEntities.stream().forEach(u -> {
            roles.add(map(u));
        });
        return roles;
    }

    public RoleEntity map(Role role) {
        return modelMapper.map(role, RoleEntity.class);
    }

}
