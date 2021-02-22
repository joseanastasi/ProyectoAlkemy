package com.alkemy.ot9.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import com.alkemy.ot9.mappers.RoleMapper;
import com.alkemy.ot9.models.Role;
import com.alkemy.ot9.models.RoleEnum;
import com.alkemy.ot9.models.User;
import com.alkemy.ot9.service.RoleService;
import com.alkemy.ot9.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserAuthController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMapper roleMapper;

    @GetMapping("/register")
    public String nuevo() {
        return "register";
    }

    @RequestMapping("/signin")
    public String displayLogin(Model model) {
        model.addAttribute("signin", model);
        return "login";
    }

    @PostMapping("/register")
    public ModelAndView registrar(@Valid User user) {
        ModelAndView mv = new ModelAndView();
        if (StringUtils.isBlank(user.getEmail())) {
            mv.addObject("error", "ese email no es válido");
            mv.setViewName("register");
            return mv;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            mv.addObject("error", "esa contraseña no es válida");
            mv.setViewName("register");
            return mv;
        }
        if (!userService.findUser(user.getEmail())) {
            mv.addObject("error", "ese email ya existe");
            mv.setViewName("register");
            return mv;
        }

        userService.toCreate(user);
        mv.addObject("registerOK", "  Usuario creada, ingresa tus credenciales para iniciar sesión");
        mv.setViewName("login");
        return mv;
    }
}