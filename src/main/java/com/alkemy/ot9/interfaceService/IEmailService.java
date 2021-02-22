package com.alkemy.ot9.interfaceService;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.Model;

import com.alkemy.ot9.exceptions.OrganizationNotFound;
import com.alkemy.ot9.models.Contact;
import com.alkemy.ot9.models.Newsletter;
import com.alkemy.ot9.models.Subscriber;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public interface IEmailService {
	
	JavaMailSenderImpl getEmailConfiguration() throws OrganizationNotFound;
	
	SimpleMailMessage createEmailMessage(Contact c) throws OrganizationNotFound;
	
	MimeMessage createEmailMessage(Contact c, Model model) throws OrganizationNotFound, MessagingException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;

	MimeMessage createEmailMessage(Subscriber s, Model model) throws OrganizationNotFound, MessagingException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;

	MimeMessage createEmailMessage(Newsletter n, Model model) throws OrganizationNotFound, MessagingException, TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException;

}
