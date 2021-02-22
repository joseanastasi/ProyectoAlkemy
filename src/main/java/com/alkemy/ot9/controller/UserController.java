package com.alkemy.ot9.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.validation.Valid;

import com.alkemy.ot9.entities.UserEntity;
import com.alkemy.ot9.exceptions.UserNotFoundException;
import com.alkemy.ot9.models.RoleEnum;
import com.alkemy.ot9.models.User;
import com.alkemy.ot9.service.OrganizationService;
import com.alkemy.ot9.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin/usuarios")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	private OrganizationService organizationService;

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public String listUser(@RequestParam Map<String, Object> params, Model model) {

		int page = (int) (params.get("page") != null ? (Long.valueOf(params.get("page").toString()) - 1) : 0);
		PageRequest pageRequest = PageRequest.of(page, 9);

		Page<UserEntity> pageUserEntity = userService.getAllUser(pageRequest);

		int totalPage = pageUserEntity.getTotalPages();

		if (totalPage > 0) {

			List<Long> pages = LongStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}

		List<UserEntity> listUsers = pageUserEntity.getContent();

		model.addAttribute("users", listUsers);
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("previous", page);
		model.addAttribute("last", totalPage);
		model.addAttribute("organizations", organizationService.findAllOrganizations());

		String message;
		if (listUsers.isEmpty()) {
			message = "No hay usuarios registrados";
			model.addAttribute("message", message);
		}
		return "/users/user-index";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/new-user")
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "/users/sign-up";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/saved-user")
	public String newUser(@Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "users/sign-up";
		}
		userService.toCreate(user);
		model.addAttribute("users", userService.findAll());
		return "redirect:/admin/usuarios";
	}

	@GetMapping("/find-user/{id}")
	public String getPost(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
		model.addAttribute("user", userService.findById(id));
		return "user";
	}

	@GetMapping("/update-user/{id}")
	public String updatedUser(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
		User user = userService.findById(id);
		if (user != null)
			model.addAttribute("user", user);
		model.addAttribute("role", RoleEnum.values());
		return "/users/sign-up";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/update-user/{id}")
	public String postUpdate(@PathVariable("id") Long id, @Valid User user, BindingResult result, Model model) {
		try {
			userService.toUpdate(user);
			model.addAttribute("user", userService.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/admin/usuarios";

	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/delete-user/{id}")
	public String deleteUser(@PathVariable("id") Long id, Model model, RedirectAttributes attribute) throws UserNotFoundException {
		User user = userService.findById(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (user.getEmail().equals(((User)auth.getPrincipal()).getEmail())){
			model.addAttribute("users", userService.findAll());
			attribute.addFlashAttribute("error", "No se puede eliminar el usuario con el que ha iniciado la sesion.");
			return "redirect:/admin/usuarios";
		}
				
		if (user != null)
			userService.toDelete(id);
		model.addAttribute("users", userService.findAll());
		return "redirect:/admin/usuarios";

	}

}
