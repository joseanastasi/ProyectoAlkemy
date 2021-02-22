package com.alkemy.ot9.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alkemy.ot9.entities.NewsEntity;
import com.alkemy.ot9.entities.UserCommentaryEntity;
import com.alkemy.ot9.exceptions.MaxSizeImageException;
import com.alkemy.ot9.exceptions.NewsModelNotFoundException;
import com.alkemy.ot9.interfaceService.INewsService;
import com.alkemy.ot9.interfaceService.IUserCommentsService;
import com.alkemy.ot9.models.Subscriber;
import com.alkemy.ot9.service.OrganizationService;
import com.alkemy.ot9.util.FileUtil;

@Controller
@RequestMapping("/news")
public class FrontNewsController {

	@Autowired
	private INewsService newsService;
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private IUserCommentsService userCommentsService;

	@Autowired
	private FileUtil fileUtil;

	@GetMapping("/readNews/{id}")
	public String viewNews(@PathVariable("id") Long idNews, Model model, RedirectAttributes attribute)
			throws NewsModelNotFoundException {

		NewsEntity newsEntity = null;
		try {
			newsEntity = newsService.getNewsById(idNews);

		} catch (NewsModelNotFoundException e) {
			attribute.addFlashAttribute("error", e.getMessage());

		}

		if (newsEntity != null) {

			List<UserCommentaryEntity> listUserCommentaryEntity = userCommentsService
					.listUserCommentaryEntityNews(idNews).stream().filter(e -> e.isEnabled() == true)
					.collect(Collectors.toList());

			UserCommentaryEntity userCommentaryEntity = new UserCommentaryEntity();
			userCommentaryEntity.setNews(newsEntity);

			model.addAttribute("subscriber", new Subscriber());
			model.addAttribute("listComments", listUserCommentaryEntity);
			model.addAttribute("numberComments", listUserCommentaryEntity.size());
			model.addAttribute("userCommentary", userCommentaryEntity);
			model.addAttribute("news", newsEntity);
			model.addAttribute("organizations", organizationService.findAllOrganizations());
			return "/frontNews/read-news";
		}
		return "redirect:/news/listNews";
	}

	@PostMapping(value = "/saveComments/")
	public String saveComments(@Valid @ModelAttribute UserCommentaryEntity userCommentaryEntity, BindingResult result,
			@RequestParam("file") MultipartFile multipartFile, Model model, RedirectAttributes attribute) {

		if (result.hasErrors()) {
			attribute.addFlashAttribute("warning", "Error al enviar el comentario!");
			return "redirect:/news/readNews/" + userCommentaryEntity.getNews().getId().intValue();
		}

		if (!fileUtil.isEmpty(multipartFile)) {

			long MAX_SIZE_IMAGE = 4194304;

			if (multipartFile.getSize() > MAX_SIZE_IMAGE) {
				try {
					throw new MaxSizeImageException("El tamaño máximo permitido de la imagen es 4MB.");

				} catch (MaxSizeImageException e) {

					attribute.addFlashAttribute("error", e.getMessage());
					return "redirect:/news/readNews/" + userCommentaryEntity.getNews().getId().intValue();
				}

			}

			try {
				userCommentaryEntity.setPhoto(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
			} catch (IOException e) {
				attribute.addFlashAttribute("error", "Error al elegir la imagen");
				return "redirect:/news/readNews/" + userCommentaryEntity.getNews().getId().intValue();
			}
		}
		userCommentaryEntity.setPublicationDate(new Date());
		userCommentsService.createCommentary(userCommentaryEntity);
		attribute.addFlashAttribute("success",
				"Tu comentario está pendiente de moderación, estará visible cuando se apruebe.");
		return "redirect:/news/readNews/" + userCommentaryEntity.getNews().getId().intValue();

	}

	@GetMapping
	public String listNewsPagination(@RequestParam Map<String, Object> params, Model model) {
		int page = (int) (params.get("page") != null ? (Long.valueOf(params.get("page").toString()) - 1) : 0);
		PageRequest pageRequest = PageRequest.of(page, 6);

		Page<NewsEntity> pageNewsEntity = newsService.getListNewsToEnable(true, pageRequest);

		int totalPage = pageNewsEntity.getTotalPages();

		if (totalPage > 0) {

			List<Long> pages = LongStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}

		List<NewsEntity> listNews = pageNewsEntity.getContent();

		model.addAttribute("subscriber", new Subscriber());
		model.addAttribute("organizations", organizationService.findAllOrganizations());
		model.addAttribute("list", listNews);
		model.addAttribute("titleTable", "Novedades");
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("previous", page);
		model.addAttribute("last", totalPage);

		return "frontNews/blogNews";
	}

}
