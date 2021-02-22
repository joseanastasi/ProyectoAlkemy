package com.alkemy.ot9.security.service;

import com.alkemy.ot9.entities.*;
import com.alkemy.ot9.mappers.*;
import com.alkemy.ot9.models.User;
import com.alkemy.ot9.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userService.findEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        return User.build(user);
    }

}
