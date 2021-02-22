package com.alkemy.ot9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alkemy.ot9.service.OrganizationService;



@Controller
@RequestMapping("/accesoprogramapaas")
public class AdminController {


@Autowired
private OrganizationService organizationService;


	
	
	@GetMapping
	public String adminPanel(Model model) {
	     model.addAttribute("organizations", organizationService.findAllOrganizations());

		return "viewAdmin/view-panelAdmin";

	}
}
