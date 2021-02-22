package com.alkemy.ot9.models;

import java.util.*;

public class Role {
    private Long id;
    private RoleEnum name;
    private Set<User> users = new HashSet<>();

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public RoleEnum getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(RoleEnum name) {
        this.name = name;
    }

    /**
     * @return the users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(Set<User> users) {
        this.users = users;

    }

    public void setRoleToUser(User user) {
        this.users.add(user);
        user.getRoles().add(this);
    }



}
