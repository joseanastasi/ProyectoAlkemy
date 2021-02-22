package com.alkemy.ot9.controller;

import com.alkemy.ot9.exceptions.EventNotFound;
import com.alkemy.ot9.exceptions.MaxSizeImageException;
import com.alkemy.ot9.entities.EventEntity;
import com.alkemy.ot9.models.Event;
import com.alkemy.ot9.models.Organization;
import com.alkemy.ot9.service.EventService;
import com.alkemy.ot9.service.OrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.sql.Date;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Controller
@RequestMapping("admin/eventos")
public class EventController {

	@Autowired
	private EventService eventService;
	@Autowired
	private OrganizationService organizationService;

	@GetMapping

	public String getAllEvents(@RequestParam Map<String, Object> params, Model model) {

		int page = (int) (params.get("page") != null ? (Long.valueOf(params.get("page").toString()) - 1) : 0);
		PageRequest pageRequest = PageRequest.of(page, 7);

		Page<EventEntity> pageEventEntity = eventService.getAllEvent(pageRequest);

		int totalPage = pageEventEntity.getTotalPages();

		if (totalPage > 0) {

			List<Long> pages = LongStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}

		List<EventEntity> listEvent = pageEventEntity.getContent();

		model.addAttribute("events", listEvent);
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("previous", page);
		model.addAttribute("last", totalPage);
		model.addAttribute("organizations", organizationService.findAllOrganizations());

		return "/event/event";
	}

	@GetMapping("/find-by-id/{id}")
	public String getEventById(@PathVariable Long id, Model model) throws EventNotFound {
		if (id != null) {
			model.addAttribute("event", eventService.getEventById(id));
		}
		return "/event/detailEvent";
	}

	@GetMapping("/save/{id}")
	public String postEvent(@PathVariable Long id, Model model) throws EventNotFound {
		if (id != null && id != 0) {
			model.addAttribute("event", eventService.getEventById(id));
		} else {
			model.addAttribute("event", new EventEntity());
		}
		return "/event/formEvent";
	}

	@PostMapping("/save")
	public String createEvent(@RequestParam("id") Long id, @RequestParam("title") String title,
			@RequestParam("eventType") String eventType, @RequestParam("image") String image,
			@RequestParam("file") MultipartFile multipartFile, @RequestParam("content") String content,
			@RequestParam("address") String address, @RequestParam("startDate") Date startDate,
			@RequestParam("endDate") Date endDate, @RequestParam("numberOfBeneficiaries") int numberOfBeneficiaries,
			@RequestParam("active") boolean active, @RequestParam("enrolled") String enrolled, Model model,
			RedirectAttributes ra) throws IOException {

		Event event = new Event();

		event.setId(id);
		event.setTitle(title);
		event.setEventType(eventType);
		event.setContent(content);
		event.setAddress(address);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setNumberOfBeneficiaries(numberOfBeneficiaries);
		event.setActive(active);
		event.setEnrolled(Integer.valueOf(enrolled));

		if (!multipartFile.isEmpty()) {
			long MAX_SIZE_IMAGE = 4194304;

			if (multipartFile.getSize() > MAX_SIZE_IMAGE) {
				try {
					throw new MaxSizeImageException("El tamaño máximo permitido de la imagen es 4MB.");

				} catch (MaxSizeImageException e) {

					model.addAttribute("messageImageFile", e.getMessage());
					model.addAttribute("event", event);
					return "/event/formEvent";
				}

			}

			try {
				event.setImage(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
			} catch (IOException e) {
				model.addAttribute("messageImageFile", "Error con la imagen");
				model.addAttribute("event", event);
				return "/event/formEvent";
			}
		} else {
			event.setImage(image);
			if (event.getImage().isEmpty()) {
				Organization organization = organizationService.findAllOrganizations().stream().findFirst().get();
				event.setImage(organization.getLogo());
			}

		}

		eventService.createEvent(event);
		ra.addFlashAttribute("success", "Evento creado/editado con éxito!");
		return "redirect:/admin/eventos";

	}

	@GetMapping("/delete/{id}")
	public String deleteEvent(@PathVariable(value = "id") Long id, Model model, RedirectAttributes ra)
			throws EventNotFound {
		Event event = null;

		try {
			event = eventService.getEventById(id);
		} catch (EventNotFound e) {
			ra.addFlashAttribute("error", e.getMessage());
		}

		if (event != null) {
			eventService.deleteEventById(id);
			ra.addFlashAttribute("warning", "evento eliminado con exito");

		}

		return "redirect:/admin/eventos";
	}
}