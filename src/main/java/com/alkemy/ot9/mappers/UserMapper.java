package com.alkemy.ot9.mappers;

import java.util.*;

import com.alkemy.ot9.entities.UserEntity;
import com.alkemy.ot9.models.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    private ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserEntity map(User user) {
        return modelMapper.map(user, UserEntity.class);
    }

    public User map(UserEntity UserEntity) {
        return modelMapper.map(UserEntity, User.class);
    }

    public List<User> map(List<UserEntity> userEntities) {
        List<User> users = new ArrayList<>();
        userEntities.stream().forEach(u -> {
            users.add(map(u));
        });
        return users;
    }

    public Optional<User> map(Optional<UserEntity> userEntity) {
        User user = modelMapper.map(userEntity, User.class);
        return (Optional.of(user));
    }

}
