package com.alkemy.ot9.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alkemy.ot9.entities.ContributorEntity;
import com.alkemy.ot9.exceptions.ContributorNotFoundException;
import com.alkemy.ot9.interfaceService.IOrganizationService;
import com.alkemy.ot9.models.Contributor;
import com.alkemy.ot9.models.ContributorType;
import com.alkemy.ot9.models.PotentialContributor;
import com.alkemy.ot9.service.ContributorService;

@Controller
@RequestMapping("admin/aportantes")
public class ContributorController {

	@Autowired
	private ContributorService contributorService;
	@Autowired
	private IOrganizationService organizationService;

	@GetMapping
	public String getAllContributors(@RequestParam Map<String, Object> params, Model model) {

		int page = (int) (params.get("page") != null ? (Long.valueOf(params.get("page").toString()) - 1) : 0);
		PageRequest pageRequest = PageRequest.of(page, 8);

		Page<ContributorEntity> pageContributorEntity = contributorService.getAllContributors(pageRequest);

		int totalPage = pageContributorEntity.getTotalPages();

		if (totalPage > 0) {

			List<Long> pages = LongStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}

		List<ContributorEntity> listContributors = pageContributorEntity.getContent();

		String message;
		model.addAttribute("contributors", listContributors);
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("previous", page);
		model.addAttribute("last", totalPage);
		model.addAttribute("organizations", organizationService.findAllOrganizations());

		if (listContributors.isEmpty()) {

			message = "No hay contribuidores cargados al momento";
			model.addAttribute("message", message);
		}
		return "contributor/index";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("contributor", new Contributor());
		model.addAttribute("contributorType", ContributorType.values());
		model.addAttribute("potentialContributor", PotentialContributor.values());
		return "contributor/form";
	}

	@PostMapping
	public String createContributor(@Valid Contributor c, BindingResult bindingResult, Model model,
			RedirectAttributes attribute) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("contributorType", ContributorType.values());
			model.addAttribute("potentialContributor", PotentialContributor.values());
			return "contributor/form";
		}
		contributorService.createContributor(c);
		attribute.addFlashAttribute("success", "Aportante creado/editado con éxito!");
		return "redirect:/admin/aportantes";
	}

	@GetMapping("delete/{id}")
	public String deleteContributorById(@PathVariable("id") Long id, Model model, RedirectAttributes attribute)
			throws ContributorNotFoundException {
		contributorService.deleteContributorById(id);
		attribute.addFlashAttribute("warning", "Aportante eliminado con éxito!");
		return "redirect:/admin/aportantes";
	}

	@GetMapping("/contributor/{id}")
	public String updateContributor(@PathVariable Long id, Model model) throws ContributorNotFoundException {
		Contributor contributor = contributorService.getContributorById(id);
		model.addAttribute("contributor", contributor);
		model.addAttribute("contributorType", ContributorType.values());
		model.addAttribute("potentialContributor", PotentialContributor.values());
		return "contributor/form";
	}

	@GetMapping("/find/{id}")
	public String getContributorById(@PathVariable Long id, Model model) throws ContributorNotFoundException {
		Contributor contributor = contributorService.getContributorById(id);
		model.addAttribute("contributor", contributor);
		return "contributor/form";
	}
}