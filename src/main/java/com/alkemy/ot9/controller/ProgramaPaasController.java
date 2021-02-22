package com.alkemy.ot9.controller;

import com.alkemy.ot9.exceptions.OrganizationNotFound;
import com.alkemy.ot9.exceptions.TeamNotFound;
import com.alkemy.ot9.models.Subscriber;
import com.alkemy.ot9.service.OrganizationService;
import com.alkemy.ot9.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/programa_paas")
public class ProgramaPaasController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private OrganizationService organizationService;

    @GetMapping
    public String index(Model model) throws OrganizationNotFound {
    	model.addAttribute("subscriber", new Subscriber());
        model.addAttribute("organizations", organizationService.findAllOrganizations());
        model.addAttribute("teams", teamService.findAllTeams());
        return "programa_paas/programa-paas";
    }
    @GetMapping("/team/find-by-id/{id}")
    public String getTeamById(@PathVariable Long id, Model model) throws TeamNotFound {
        model.addAttribute("team", teamService.getTeamById(id));
        return "programa_paas/team-by-id";
    }

}
