package com.alkemy.ot9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.alkemy.ot9.service.EventService;
import com.alkemy.ot9.models.Subscriber;
import com.alkemy.ot9.service.MetricService;
import com.alkemy.ot9.service.OrganizationService;


@Controller
public class HomeController {

	 @Autowired
	 private EventService eventService;
	 @Autowired
	 private MetricService metricService;
	 @Autowired
	 private OrganizationService organizationService;
	 
	@GetMapping("/")
	public String home(Model model) {

		 model.addAttribute("carousel",eventService.lastThreeEventbyDate());
		 model.addAttribute("subscriber", new Subscriber());
	     model.addAttribute("metrics", metricService.getAllMetrics());
	     model.addAttribute("organizations", organizationService.findAllOrganizations());

		return "home";
	}

	@GetMapping("/loginprogramapaas")
	public String login() {
		return "login";
	}
}
