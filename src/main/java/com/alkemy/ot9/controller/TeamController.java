package com.alkemy.ot9.controller;

import com.alkemy.ot9.entities.TeamEntity;
import com.alkemy.ot9.exceptions.MaxSizeImageException;
import com.alkemy.ot9.exceptions.NotFoundOrgActive;
import com.alkemy.ot9.exceptions.TeamNotFound;
import com.alkemy.ot9.interfaceService.IOrganizationService;
import com.alkemy.ot9.models.*;
import com.alkemy.ot9.service.TeamService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.alkemy.ot9.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("admin/equipo")
public class TeamController {

	@Autowired
	private FileUtil fileUtil;

	@Autowired
	private TeamService teamService;

	@Autowired
	private IOrganizationService organizationService;

	@RequestMapping
	public String index(@RequestParam Map<String, Object> params, Model model) {

		int page = (int) (params.get("page") != null ? (Long.valueOf(params.get("page").toString()) - 1) : 0);
		PageRequest pageRequest = PageRequest.of(page, 7);

		Page<TeamEntity> pageTeamEntity = teamService.getAllTeams(pageRequest);

		int totalPage = pageTeamEntity.getTotalPages();

		if (totalPage > 0) {

			List<Long> pages = LongStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}

		List<TeamEntity> listTeam = pageTeamEntity.getContent();

		model.addAttribute("teams", listTeam);
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("previous", page);
		model.addAttribute("last", totalPage);
		model.addAttribute("organizations", organizationService.findAllOrganizations());

		return "team/team-index";
	}

	@GetMapping("/save/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) throws TeamNotFound {
		if (id != null && id != 0) {
			model.addAttribute("team", teamService.getTeamById(id));
		} else {
			model.addAttribute("team", new TeamEntity());
		}
		model.addAttribute("teamType", TeamType.values());
		return "team/team-save";
	}

	@PostMapping("/save")
	public String createTeam(@Valid Team team, BindingResult bindingResult,
			@RequestParam("file") MultipartFile multipartFile, Model model, RedirectAttributes attribute)
			throws NotFoundOrgActive, MaxSizeImageException {

		if (bindingResult.hasErrors()) {
			model.addAttribute("teamType", TeamType.values());
			return "team/team-save";
		}

		List<Organization> listOrganization = organizationService.findAllOrganizations();
		Predicate<Organization> active = organization -> organization.isActive();
		Optional<Organization> organizationActive = listOrganization.stream().filter(active).findFirst();

		if (organizationActive.isEmpty()) {
			try {
				throw new NotFoundOrgActive("No se encontraron organizaciones activas");
			} catch (NotFoundOrgActive e) {
				attribute.addFlashAttribute("error", e.getMessage());
				return "redirect:/admin/equipo";
			}
		}

		if (!fileUtil.isEmpty(multipartFile)) {

			long MAX_SIZE_IMAGE = 4194304;

			if (multipartFile.getSize() > MAX_SIZE_IMAGE) {
				try {
					throw new MaxSizeImageException("El tamaño máximo permitido de la imagen es 4MB.");

				} catch (MaxSizeImageException e) {
					attribute.addFlashAttribute("error", "Error al elegir la imagen");
					model.addAttribute("team", team);
					model.addAttribute("teamType", TeamType.values());
					return "team/team-save";
				}
			}
			if (!multipartFile.isEmpty()) {
				try {
					team.setImage(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
				} catch (IOException e) {
					attribute.addFlashAttribute("error", "Error al elegir la imagen");
					model.addAttribute("team", team);
					model.addAttribute("teamType", TeamType.values());
					return "team/team-save";
				}
			}
		}

		team.setOrganization(organizationActive.get());
		teamService.createTeam(team);
		attribute.addFlashAttribute("success", "Equipo creado/editado con éxito!");
		return "redirect:/admin/equipo";
	}

	@GetMapping("/delete/{id}")
	public String deleteTeamById(@PathVariable Long id, Model model, RedirectAttributes attribute) throws TeamNotFound {
		teamService.deleteTeamById(id);
		attribute.addFlashAttribute("warning", "Equipo eliminado con éxito!");
		return "redirect:/admin/equipo";
	}

	@GetMapping("/find-by-id/{id}")
	public String getTeamById(@PathVariable Long id, Model model) throws TeamNotFound {
		model.addAttribute("team", teamService.getTeamById(id));
		return "team/team-by-id";
	}

}