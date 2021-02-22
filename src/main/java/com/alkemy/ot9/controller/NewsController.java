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
import com.alkemy.ot9.exceptions.MaxSizeImageException;
import com.alkemy.ot9.exceptions.NewsModelNotFoundException;
import com.alkemy.ot9.interfaceService.INewsService;
import com.alkemy.ot9.interfaceService.IOrganizationService;
import com.alkemy.ot9.models.Organization;
import com.alkemy.ot9.util.FileUtil;

@Controller
@RequestMapping("admin/novedades")
public class NewsController {

	@Autowired
	private INewsService newsService;

	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private FileUtil fileUtil;

	@GetMapping
	public String listNews(@RequestParam Map<String, Object> params, Model model) {

		int page = (int) (params.get("page") != null ? (Long.valueOf(params.get("page").toString()) - 1) : 0);
		PageRequest pageRequest = PageRequest.of(page, 7);

		Page<NewsEntity> pageNewsEntity = newsService.getAllNews(pageRequest);

		int totalPage = pageNewsEntity.getTotalPages();

		if (totalPage > 0) {

			List<Long> pages = LongStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}

		List<NewsEntity> listNews = pageNewsEntity.getContent();

		model.addAttribute("list", listNews);
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("previous", page);
		model.addAttribute("last", totalPage);
		model.addAttribute("organizations", organizationService.findAllOrganizations());

		return "/news/list-news";
	}

	@GetMapping("/createNews")
	public String createNews(Model model) {

		NewsEntity newsEntity = new NewsEntity();
		model.addAttribute("news", newsEntity);
		model.addAttribute("action", "CREATE");
		model.addAttribute("titleTable", "AGREGAR NOVEDAD");

		return "/news/create-news";
	}

	@PostMapping("/saveNews")
	public String saveNews(@Valid @ModelAttribute("news") NewsEntity newsEntity, BindingResult result, Model model,
			@RequestParam(value = "file", required = false) MultipartFile multipartFile, RedirectAttributes attribute)
			throws MaxSizeImageException, IOException {

		if (result.hasErrors()) {
			if (newsEntity.getId() == null) {
				model.addAttribute("titleTable", "AGREGAR NOVEDAD");
				model.addAttribute("action", "CREATE");
			} else {
				model.addAttribute("titleTable", "EDITAR NOVEDAD");
			}

			model.addAttribute("news", newsEntity);
			return "/news/create-news";

		} else {

			if (!fileUtil.isEmpty(multipartFile)) {

				long MAX_SIZE_IMAGE = 4194304;

				if (multipartFile.getSize() > MAX_SIZE_IMAGE) {
					try {
						throw new MaxSizeImageException("El tamaño máximo permitido de la imagen es 4MB.");

					} catch (MaxSizeImageException e) {
						if (newsEntity.getId() == null) {
							model.addAttribute("titleTable", "AGREGAR NOVEDAD");
							model.addAttribute("action", "CREATE");
						} else {
							model.addAttribute("titleTable", "EDITAR NOVEDAD");
						}
						model.addAttribute("messageImageFile", e.getMessage());
						model.addAttribute("news", newsEntity);
						return "/news/create-news";
					}

				}

				try {
					newsEntity.setImage(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
				} catch (IOException e) {

					if (newsEntity.getId() == null) {
						model.addAttribute("titleTable", "AGREGAR NOVEDAD");
						model.addAttribute("action", "CREATE");
					} else {
						model.addAttribute("titleTable", "EDITAR NOVEDAD");
					}
					model.addAttribute("messageImageFile", "Error con la imagen");
					model.addAttribute("news", newsEntity);
					return "/news/create-news";
				}
			} else {

				if (newsEntity.getImage().isEmpty()) {
					Organization organization = organizationService.findAllOrganizations().stream().findFirst().get();
					newsEntity.setImage(organization.getLogo());
				}
			}

			if (newsEntity.getShortContent().length() > 150) {
				newsEntity.setShortContent(newsEntity.getShortContent().substring(0, 149) + "...");
			} else {
				newsEntity.setShortContent(newsEntity.getShortContent() + "...");
			}

			if (newsEntity.isEnabled() && newsEntity.getDate() == null) {
				newsEntity.setDate(new Date());
			}
			newsService.saveNews(newsEntity);
			attribute.addFlashAttribute("success", "Novedad/agenda guardada con éxito!");
			return "redirect:/admin/novedades";
		}
	}

	@GetMapping("/viewNews/{id}")
	public String viewNews(@PathVariable("id") Long idNews, Model model, RedirectAttributes attribute)
			throws NewsModelNotFoundException {

		NewsEntity newsEntity = null;
		try {
			newsEntity = newsService.getNewsById(idNews);

		} catch (NewsModelNotFoundException e) {
			attribute.addFlashAttribute("error", e.getMessage());

		}

		if (newsEntity != null) {
			model.addAttribute("news", newsEntity);
			return "/news/see-news";
		}
		return "redirect:/admin/novedades";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long idNews, Model model, RedirectAttributes attribute)
			throws NewsModelNotFoundException {

		NewsEntity newsEntity = null;
		try {
			newsEntity = newsService.getNewsById(idNews);

		} catch (NewsModelNotFoundException e) {
			attribute.addFlashAttribute("error", e.getMessage());

		}

		if (newsEntity != null) {

			model.addAttribute("titleTable", "EDITAR NOVEDAD");
			model.addAttribute("news", newsEntity);
			return "/news/create-news";
		}
		return "redirect:/admin/novedades";

	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long idNews, RedirectAttributes attribute)
			throws NewsModelNotFoundException {

		NewsEntity newsEntity = null;
		try {
			newsEntity = newsService.getNewsById(idNews);

		} catch (NewsModelNotFoundException e) {
			attribute.addFlashAttribute("error", e.getMessage());

		}

		if (newsEntity != null) {
			newsService.deleteNewsById(idNews);
			attribute.addFlashAttribute("warning", "Novedad/agenda eliminada con éxito!");
			return "redirect:/admin/novedades";
		}
		return "redirect:/admin/novedades";
	}

}
