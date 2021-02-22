package com.alkemy.ot9.controller;

import com.alkemy.ot9.entities.VoluntaryEntity;
import com.alkemy.ot9.exceptions.VoluntaryNotFoundException;
import com.alkemy.ot9.models.Voluntary;
import com.alkemy.ot9.service.OrganizationService;
import com.alkemy.ot9.service.VoluntaryService;
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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import javax.validation.Valid;

@Controller
@RequestMapping("admin/voluntarios")
public class VoluntaryController {

    @Autowired
    VoluntaryService voluntaryService;

    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public String getAllVoluntaries(@RequestParam Map<String, Object> params, Model model) {
        int page = (int) (params.get("page") != null ? (Long.valueOf(params.get("page").toString()) - 1) : 0);
        PageRequest pageRequest = PageRequest.of(page, 7);

        Page<VoluntaryEntity> pageVoluntaryEntity = voluntaryService.getAllVoluntaries(pageRequest);

        int totalPage = pageVoluntaryEntity.getTotalPages();

        if (totalPage > 0) {

            List<Long> pages = LongStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages", pages);
        }

        List<VoluntaryEntity> listVoluntaries = pageVoluntaryEntity.getContent();

        model.addAttribute("voluntaries", listVoluntaries);
        model.addAttribute("current", page + 1);
        model.addAttribute("next", page + 2);
        model.addAttribute("previous", page);
        model.addAttribute("last", totalPage);
        model.addAttribute("organizations", organizationService.findAllOrganizations());
        return "voluntary/voluntary-index";
    }

    @GetMapping("/save/{id}")
    public String showSave(@PathVariable("id") Long id, Model model) throws VoluntaryNotFoundException {
        if (id != null && id != 0) {
            model.addAttribute("voluntary", voluntaryService.getVoluntaryById(id));
        } else {
            model.addAttribute("voluntary", new Voluntary());
        }
        return "voluntary/voluntary-save";
    }

    @PostMapping("/save")
    public String createVoluntary(@Valid Voluntary voluntary, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "voluntary/voluntary-save";
        }
        voluntaryService.createVoluntary(voluntary);
        return "redirect:/admin/voluntarios";
    }

    @GetMapping("/delete/{id}")
    public String deleteVoluntaryById(@PathVariable Long id, Model model) throws VoluntaryNotFoundException {
        voluntaryService.deleteVoluntaryById(id);
        return "redirect:/admin/voluntarios";
    }

    @GetMapping("/find-by-id/{id}")
    public String getVoluntaryById(@PathVariable Long id, Model model) throws VoluntaryNotFoundException {
        model.addAttribute("voluntary", voluntaryService.getVoluntaryById(id));
        return "voluntary/voluntary-by-id";
    }
}
