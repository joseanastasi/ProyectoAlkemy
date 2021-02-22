package com.alkemy.ot9.service;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.alkemy.ot9.exceptions.OrganizationNotFound;
import com.alkemy.ot9.interfaceService.IEmailService;
import com.alkemy.ot9.models.Contact;
import com.alkemy.ot9.models.Newsletter;
import com.alkemy.ot9.models.Subscriber;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

@Service
public class EmailService implements IEmailService {
	
	@Autowired
	private OrganizationService organizationService;
	private SubscriberService subscriberService;
		
	@Autowired
	Configuration configuration;
	
	public EmailService(OrganizationService organizationService, SubscriberService subscriberService,
			Configuration configuration) {
		super();
		this.organizationService = organizationService;
		this.subscriberService = subscriberService;
		this.configuration = configuration;
	}

	@Override
	public JavaMailSenderImpl getEmailConfiguration() throws OrganizationNotFound {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost(this.organizationService.getActiveOrganization().getEmailServer());
		mailSender.setPort(Integer.parseInt(this.organizationService.getActiveOrganization().getEmailServerPort()));
		mailSender.setUsername(this.organizationService.getActiveOrganization().getEmailUser());
		mailSender.setPassword(this.organizationService.getActiveOrganization().getEmailPassword());
		
		Properties p = new Properties();
		p.setProperty("mail.transport.protocol", "smtp");
		p.setProperty("mail.smtp.auth", "true");
		p.setProperty("mail.smtp.starttls.enable", "true");
		
		mailSender.setJavaMailProperties(p);
		
		return mailSender;
	}

	@Override
	public SimpleMailMessage createEmailMessage(Contact c) throws OrganizationNotFound {
		
		String mailContent = "Nombre: " + c.getName() + ", " +c.getSurname() + "\n";
		mailContent += "Tipo de Contribuidor: " + c.getType() + "\n";
		mailContent += "Email: " + c.getEmail() + "\n";
		mailContent += "Telefono: " + c.getPhone() + "\n";
		mailContent += "Provincia: " + c.getProvince() + "\n";
		mailContent += "Localidad/Barrio: " + c.getLocation() + "\n";
		mailContent += "Mensaje: " + c.getMessage() + "\n";
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(c.getEmail());
		mailMessage.setTo(this.organizationService.getActiveOrganization().getEmail());
		mailMessage.setSubject("Nuevo contacto de: " + c.getName() + ", " + c.getSurname());
		mailMessage.setText(mailContent);
		
		return mailMessage;
	}
	
	@Override
	public MimeMessage createEmailMessage(Contact c, Model model) throws OrganizationNotFound, MessagingException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		MimeMessage message = new JavaMailSenderImpl().createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
				
		helper.setTo(c.getEmail());
		helper.setSubject("Programa PAAS");
		helper.setText(FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("contributor/site/email-template.ftl"), model), true);		
		
		return message;
	}
	
	@Override
	public MimeMessage createEmailMessage(Subscriber s, Model model) throws OrganizationNotFound, MessagingException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		MimeMessage message = new JavaMailSenderImpl().createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
				
		helper.setTo(s.getEmail());
		helper.setSubject("Programa PAAS");
		helper.setText(FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("subscriber/backoffice/email-template.ftl"), model), true);		
		
		return message;
	}
	
	@Override
	public MimeMessage createEmailMessage(Newsletter n, Model model) throws OrganizationNotFound, MessagingException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		MimeMessage message = new JavaMailSenderImpl().createMimeMessage();		
						
		model.asMap().put("message", n.getMessage());
		model.asMap().put("url", n.getUrl());
		
		String[] emails = new String[subscriberService.getAllSubscribers().size()];		
		List<Subscriber> subscribers = subscriberService.getAllSubscribers();		
		for (int i = 0; i < subscribers.size(); i++) {
			emails[i] = subscribers.get(i).getEmail();
		}
		
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
				
		helper.setTo(emails);
		helper.setSubject("Programa PAAS");
		helper.setText(FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("newsletter/email-template.ftl"), model), true);		
		
		return message;
	}
}