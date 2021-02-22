package com.alkemy.ot9.controller;

import java.io.IOException;
import java.util.NoSuchElementException;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alkemy.ot9.exceptions.OrganizationNotFound;
import com.alkemy.ot9.interfaceService.IApiServiceProxy;
import com.alkemy.ot9.mappers.LocationMapper;
import com.alkemy.ot9.mappers.ProvinceMapper;
import com.alkemy.ot9.models.Contact;
import com.alkemy.ot9.models.Contributor;
import com.alkemy.ot9.models.ContributorType;
import com.alkemy.ot9.models.PotentialContributor;
import com.alkemy.ot9.models.Provincia;
import com.alkemy.ot9.models.Subscriber;
import com.alkemy.ot9.service.ContributorService;
import com.alkemy.ot9.service.EmailService;
import com.alkemy.ot9.service.OrganizationService;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Controller
@RequestMapping("/contacts")
@EnableFeignClients(basePackageClasses = IApiServiceProxy.class)
public class ContactController {
	
	@Autowired
	private ContributorService contributorService;
	private IApiServiceProxy proxy;	
	private LocationMapper locationMapper;
	private ProvinceMapper provinceMapper;
	private EmailService emailService;
	private OrganizationService organizationService;

	public ContactController(ContributorService contributorService, IApiServiceProxy proxy,
			LocationMapper locationMapper, ProvinceMapper provinceMapper, EmailService emailService,
			OrganizationService organizationService) {
		super();
		this.contributorService = contributorService;
		this.proxy = proxy;
		this.locationMapper = locationMapper;
		this.provinceMapper = provinceMapper;
		this.emailService = emailService;
		this.organizationService = organizationService;
	}

	@GetMapping
	public String add(Model model) {
		model.addAttribute("subscriber", new Subscriber());
		model.addAttribute("contact", new Contact());
		model.addAttribute("contributorType", ContributorType.values());
		model.addAttribute("provinces", provinceMapper.map(proxy.getProvincia()));	
		model.addAttribute("organizations", organizationService.findAllOrganizations());

		return "contributor/site/form";
	}
	
	@ResponseBody
	@GetMapping("/location/{id}")
	public String loadLocation(Model model, @PathVariable("id") String id) {
		return locationMapper.map(proxy.getLocation(id));
	}
	
	@PostMapping
	public String createContact(@Valid Contact c,  BindingResult bindingResult, Model model) throws OrganizationNotFound, MailException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, MessagingException, IOException, TemplateException{
		
		for (Provincia provincia : provinceMapper.map(proxy.getProvincia())) {
			if(provincia.getId().equals(c.getProvince())) {
				c.setProvince(provincia.getName());
			}			
		}
		
		if(bindingResult.hasErrors()) {
			model.addAttribute("subscriber", new Subscriber());
			model.addAttribute("contributorType", ContributorType.values());
			model.addAttribute("provinces", provinceMapper.map(proxy.getProvincia()));
			model.addAttribute("organizations", organizationService.findAllOrganizations());
			return "contributor/site/form";
		}
		
		if (!contributorService.existEmail(c.getEmail())) {
			Contributor contributor = new Contributor(c.getName(), c.getSurname(), c.getType(), c.getEmail(), c.getPhone(), true, PotentialContributor.POTENTIAL, c.getProvince(), c.getLocation());
			contributorService.createContributor(contributor);
		}
		
		try {
			if (organizationService.emailIsConfig()) {
				this.emailService.getEmailConfiguration().send(this.emailService.createEmailMessage(c));
				this.emailService.getEmailConfiguration().send(this.emailService.createEmailMessage(c, model));
			}
			model.addAttribute("OK", true);
		} catch (Exception e) {
			model.addAttribute("error", "No hemos podido enviar el correo automatico. Por favor contáctese con un administrador.");
		}
				
		model.addAttribute("subscriber", new Subscriber());
		return "contributor/site/message";
	}
}