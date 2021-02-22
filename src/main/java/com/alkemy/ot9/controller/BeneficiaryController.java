package com.alkemy.ot9.controller;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alkemy.ot9.entities.BeneficiaryEntity;
import com.alkemy.ot9.exceptions.BeneficiaryNotFound;
import com.alkemy.ot9.exceptions.EventNotFound;
import com.alkemy.ot9.models.Beneficiary;
import com.alkemy.ot9.models.Event;
import com.alkemy.ot9.models.Subscriber;
import com.alkemy.ot9.repository.EventRepository;
import com.alkemy.ot9.service.BeneficiaryService;
import com.alkemy.ot9.service.EventService;
import com.alkemy.ot9.service.OrganizationService;

@Controller
public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private EventService eventService;

    
    @RequestMapping("/beneficiaries")
    public String addBeneficiary(Model model) {
    	model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("beneficiary", new Beneficiary());
        model.addAttribute("events", eventRepository.activeEvent());
        model.addAttribute("organizations", organizationService.findAllOrganizations());

        return "beneficiaries/beneficiaries";
    }

    @GetMapping("/new-beneficiary")
    public String getAllBeneficiary(Model model) {
        model.addAttribute("beneficiary", beneficiaryService.getAll());
        return "beneficiaries";
    }

    @GetMapping("/find-beneficiary-by-id/{id}")
    public String getBeneficiaryById(@PathVariable Long id, Model model) throws BeneficiaryNotFound {
        if (id != null) {
            model.addAttribute("beneficiary", beneficiaryService.getBeneficiaryById(id));
        }
        return "detailBeneficiary";
    }

    @PostMapping("/saved-beneficiary")
    public String newBeneficiary(@Valid Beneficiary beneficiary, BindingResult result, Model model, @RequestParam("eventId") String eventId) throws NumberFormatException, EventNotFound {
        if (result.hasErrors()) {
            model.addAttribute("subscriber", new Subscriber());
            model.addAttribute("events", eventRepository.activeEvent());
            model.addAttribute("organizations", organizationService.findAllOrganizations());
            return "beneficiaries/beneficiaries";
        }

        Event selectedEvent = eventService.getEventById(Long.valueOf(eventId));
        if (selectedEvent.getNumberOfBeneficiaries() > selectedEvent.getEnrolled()) {
            eventService.enrollBeneficiary(Long.valueOf(eventId));
        } else {
        	model.addAttribute("error", "El evento al cual desea inscribirse ya ha completado su cupo. Para mas informacion contacte a un representante del programa PAAS.");
        }
        beneficiaryService.createBeneficiary(beneficiary);
        return "beneficiaries/message";
    }

    @GetMapping("/delete-beneficiary/{id}")
    public String deleteBeneficiary(@PathVariable(value = "id") Long id, Model model, RedirectAttributes ra) throws BeneficiaryNotFound {
        Beneficiary beneficiary = null;
        try {
            beneficiary = beneficiaryService.getBeneficiaryById(id);
        } catch (BeneficiaryNotFound e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        if (beneficiary != null) {
            beneficiaryService.deleteBeneficiaryById(id);
            ra.addFlashAttribute("warning", "beneficiario eliminado con exito");
            return "redirect:/beneficiariesAll";

        }

        return "redirect:/beneficiariesAll";
    }
}
