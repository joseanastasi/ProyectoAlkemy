package com.alkemy.ot9.interfaceService;

import java.util.*;

import com.alkemy.ot9.entities.RoleEntity;
import com.alkemy.ot9.exceptions.RoleNotFound;
import com.alkemy.ot9.models.Role;
import com.alkemy.ot9.models.RoleEnum;

public interface IRoleService {
    public Role existsByRoleNombre(RoleEnum roleUser);

    public List<Role> list();

    public Role save(RoleEntity role);

    public Role getById(Long id) throws RoleNotFound;;
}
