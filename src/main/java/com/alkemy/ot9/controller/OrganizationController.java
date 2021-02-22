package com.alkemy.ot9.controller;

import com.alkemy.ot9.entities.OrganizationEntity;
import com.alkemy.ot9.exceptions.OrganizationNotFound;
import com.alkemy.ot9.models.Organization;
import com.alkemy.ot9.service.OrganizationService;

import java.io.IOException;
import java.util.Base64;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("admin/organizacion")
public class OrganizationController {

	@Autowired
	private OrganizationService organizationService;

	@GetMapping
	public String index(Model model) {
		model.addAttribute("organizations", organizationService.findAllOrganizations());
		return "organization/index";
	}

	@GetMapping("/email")
	public String indexEmail(Model model) {
		model.addAttribute("organizations", organizationService.findAllOrganizations());
		return "organization/email-index";
	}

	@PostMapping("/save")
	public String createOrganization(Organization organization, @RequestParam("file") MultipartFile multipartFile,
			Model model, RedirectAttributes attribute) {

		if (!multipartFile.isEmpty()) {

			try {
				organization.setLogo(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

		organizationService.createOrganization(organization);
		attribute.addFlashAttribute("success", "Organización creada/editada con éxito!");
		return "redirect:/admin/organizacion";
	}

	@GetMapping("/save/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) throws OrganizationNotFound {
		if (id != null && id != 0) {
			model.addAttribute("organization", organizationService.getOrganizationById(id));
		} else {
			model.addAttribute("organization", new OrganizationEntity());
		}
		return "organization/save";
	}

	@PostMapping("save/email")
	public String createOrganizationEmail(@Valid Organization organization, BindingResult bindingResult, Model model, RedirectAttributes attribute) {
		
		System.out.println(bindingResult);
		if(bindingResult.hasErrors()) {
			return "organization/email-edit";
		}
		
		organizationService.createOrganization(organization);
		attribute.addFlashAttribute("success", "Organización creada/editada con éxito!");
		return "redirect:/admin/organizacion/email";
	}

	@GetMapping("/save/email/{id}")
	public String showSaveEmail(@PathVariable("id") Long id, Model model) throws OrganizationNotFound {
		model.addAttribute("organization", organizationService.getOrganizationById(id));
		return "organization/email-edit";
	}

	@GetMapping("/find-by-id/{id}")
	public String getOrganizationById(@PathVariable Long id, Model model) throws OrganizationNotFound {
		model.addAttribute("organization", organizationService.getOrganizationById(id));
		return "organization/organization-by-id";
	}

	@GetMapping("/delete/{id}")
	public String deleteOrganizationById(@PathVariable Long id, Model model, RedirectAttributes attribute)
			throws OrganizationNotFound {
		organizationService.deleteOrganizationById(id);
		attribute.addFlashAttribute("warning", "Organización eliminada con éxito!");
		return "redirect:/admin/organizacion";
	}

}
