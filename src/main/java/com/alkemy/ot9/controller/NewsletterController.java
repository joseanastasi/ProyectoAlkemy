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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alkemy.ot9.exceptions.OrganizationNotFound;
import com.alkemy.ot9.models.Newsletter;
import com.alkemy.ot9.service.EmailService;
import com.alkemy.ot9.service.OrganizationService;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Controller
@RequestMapping("admin/newsletter")
public class NewsletterController {
	
	@Autowired
	private EmailService emailService;
	private OrganizationService organizationService;
	
	
	public NewsletterController(EmailService emailService, OrganizationService organizationService) {
		super();
		this.emailService = emailService;
		this.organizationService = organizationService;
	}

	@GetMapping
	public String index(Model model) {
		model.addAttribute("newsletter", new Newsletter());	
	    model.addAttribute("organizations", organizationService.findAllOrganizations());

		return "newsletter/form";
	}
	
	@PostMapping
	public String sendNewsletter(@Valid Newsletter n, BindingResult bindingResult, Model model, RedirectAttributes attribute) throws MailException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, OrganizationNotFound, MessagingException, IOException, TemplateException {
		
		if(bindingResult.hasErrors()) {
			return "newsletter/form";
		}
		
		
		try {
			if (organizationService.emailIsConfig()) {
				this.emailService.getEmailConfiguration().send(this.emailService.createEmailMessage(n, model));
			}		
			attribute.addFlashAttribute("success", "Se ha enviado la comunicación de novedades de manera exitosa!");			
		} catch (Exception e) {
			attribute.addFlashAttribute("error", "No hemos podido enviar el newsletter. Por favor revisar la configuración de correo o en su defecto contáctese con un administrador.");
		}
		
		return "redirect:/admin";
	}

}
