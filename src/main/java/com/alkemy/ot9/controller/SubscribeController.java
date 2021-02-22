package com.alkemy.ot9.controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alkemy.ot9.exceptions.OrganizationNotFound;
import com.alkemy.ot9.models.Subscriber;
import com.alkemy.ot9.service.EmailService;
import com.alkemy.ot9.service.OrganizationService;
import com.alkemy.ot9.service.SubscriberService;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Controller
@RequestMapping("/subscribe")
public class SubscribeController {
	
	@Autowired
	private SubscriberService subscriberService;
	private OrganizationService organizationService;
	private EmailService emailService;
	
	public SubscribeController(SubscriberService subscriberService, OrganizationService organizationService,
			EmailService emailService) {
		super();
		this.subscriberService = subscriberService;
		this.organizationService = organizationService;
		this.emailService = emailService;
	}

	@PostMapping
	public String addSubscriber(@Valid Subscriber s,  BindingResult bindingResult,  Model model) throws MailException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, OrganizationNotFound, MessagingException, IOException, TemplateException{
		
		if (bindingResult.hasErrors()) {
			return "redirect:/";
		}
		
		if(!subscriberService.existEmail(s.getEmail()))
			subscriberService.addSubscriber(s);
		
		try {
			if (organizationService.emailIsConfig()) {
				this.emailService.getEmailConfiguration().send(this.emailService.createEmailMessage(s, model));
			}
			model.addAttribute("OK", true);
		} catch (Exception e) {
			model.addAttribute("error", "Te has subscripto al programa newsletter del Programa PAAS pero no hemos podido enviarte un correo de agradecimiento.");
		}
				
		return "subscriber/backoffice/message";
	}
	
	@GetMapping
	public String unsubscribe(Model model) {
		model.addAttribute("subscriber", new Subscriber());
		return "subscriber/site/unsubscribe";
	}
	
	@GetMapping("/unsubscribe")
	public String cancelSubscription(@RequestParam("email") String email, RedirectAttributes attribute) {
		
		if (subscriberService.existEmail(email)) {
			subscriberService.deleteSubscriber(email);
			attribute.addFlashAttribute("success", "Suscripción cancelada con éxito!");
		} else {
			attribute.addFlashAttribute("error", "El correo ingresado no se encuentra en la lista.");
		}
			
		
		
		return "redirect:/";
	}
	
}