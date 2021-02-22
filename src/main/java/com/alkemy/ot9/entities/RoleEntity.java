package com.alkemy.ot9.entities;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.alkemy.ot9.models.RoleEnum;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(unique = true)
    private RoleEnum name;
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();

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
    public Set<UserEntity> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public void setRoleToUser(UserEntity user) {
        this.users.add(user);
        user.getRoles().add(this);
    }
}