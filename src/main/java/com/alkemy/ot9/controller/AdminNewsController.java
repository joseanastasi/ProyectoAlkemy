package com.alkemy.ot9.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alkemy.ot9.entities.UserCommentaryEntity;
import com.alkemy.ot9.exceptions.UserCommentsNotFound;
import com.alkemy.ot9.interfaceService.IOrganizationService;
import com.alkemy.ot9.interfaceService.IUserCommentsService;
import com.alkemy.ot9.service.OrganizationService;

@Controller
@RequestMapping("/admin/commentarios")
public class AdminNewsController {

	@Autowired
	private IUserCommentsService userCommentsService;
	@Autowired
	private OrganizationService organizationService;

	@GetMapping
	public String viewOfCommentsToManage(@RequestParam Map<String, Object> params, Model model) {

		int page = (int) (params.get("page") != null ? (Long.valueOf(params.get("page").toString()) - 1) : 0);
		PageRequest pageRequest = PageRequest.of(page, 5);

		Page<UserCommentaryEntity> pageUserCommentaryEntity = userCommentsService.getListCommentsToEnable(false,
				pageRequest);

		int totalPage = pageUserCommentaryEntity.getTotalPages();

		if (totalPage > 0) {

			List<Long> pages = LongStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}

		List<UserCommentaryEntity> listComments = pageUserCommentaryEntity.getContent();

		model.addAttribute("listComments", listComments);
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("previous", page);
		model.addAttribute("last", totalPage);
		model.addAttribute("manage", "Administrar Comentarios");
		model.addAttribute("organizations", organizationService.findAllOrganizations());

		return "/commentsNews/adminCommentsNews";

	}

	@GetMapping("/delete/{id}")
	public String deleteCommentary(@PathVariable("id") Long id, RedirectAttributes attribute)
			throws UserCommentsNotFound {

		UserCommentaryEntity userCommentaryEntity = null;
		try {
			userCommentaryEntity = userCommentsService.findByIdUserCommentaryEntity(id);

		} catch (UserCommentsNotFound e) {
			attribute.addFlashAttribute("error", e.getMessage());

		}

		if (userCommentaryEntity != null) {
			userCommentsService.deleteUserCommentaryEntityById(id);
			attribute.addFlashAttribute("warning", "El comentario fue eliminado!");
			return "redirect:/admin/commentarios";
		}
		return "redirect:/admin/commentarios";
	}

	@GetMapping("/approve/{id}")
	public String approveCommentary(@PathVariable("id") Long id, RedirectAttributes attribute)
			throws UserCommentsNotFound {

		UserCommentaryEntity userCommentaryEntity = null;
		try {
			userCommentaryEntity = userCommentsService.findByIdUserCommentaryEntity(id);

		} catch (UserCommentsNotFound e) {
			attribute.addFlashAttribute("error", e.getMessage());

		}

		if (userCommentaryEntity != null) {
			userCommentaryEntity.setEnabled(true);
			userCommentsService.createCommentary(userCommentaryEntity);
			attribute.addFlashAttribute("success", "El comentario fue aprobado con éxito!");
			return "redirect:/admin/commentarios";
		}
		return "redirect:/admin/commentarios";
	}
}
