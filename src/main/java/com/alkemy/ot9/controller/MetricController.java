package com.alkemy.ot9.controller;

import java.util.List;

import javax.validation.Valid;

import com.alkemy.ot9.exceptions.MetricNotFoundException;
import com.alkemy.ot9.models.Metric;
import com.alkemy.ot9.service.MetricService;
import com.alkemy.ot9.service.OrganizationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/metricas")
public class MetricController {
    @Autowired
    MetricService metricService;
    @Autowired
	 private OrganizationService organizationService;
    @GetMapping
    public String listMetrics(Model model) {
        List<Metric> metrics = metricService.getAllMetrics();
        model.addAttribute("metrics", metrics);
	    model.addAttribute("organizations", organizationService.findAllOrganizations());

        return "metrics/metric-index";
    }

    @GetMapping("/new-metric")
    public String addMetric(Model model) {
        model.addAttribute("metric", new Metric());
        return "metrics/new-metric";
    }

    @PostMapping("/saved-metric")
    public String newMetric(@Valid Metric metric, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "new-metric";
        }
        metricService.createMetric(metric);
        model.addAttribute("metrics", metricService.getAllMetrics());
        return "redirect:/admin/metricas";
    }

    @GetMapping("/edit-metric/{id}")
    public String updatedMetric(@PathVariable("id") Long id, Model model) throws MetricNotFoundException {
        Metric metric = metricService.getMetric(id);

        if (metric != null)
            model.addAttribute("metric", metric);
        return "metrics/edit-metric";
    }

    @PostMapping("/edit-metric/{id}")
    public String postUpdate(@PathVariable("id") Long id, Metric metric, Model model) {
        try {
            metricService.updateMetric(metric);
            model.addAttribute("metric", metricService.getAllMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/admin/metricas";

    }

    @GetMapping("/delete-metric/{id}")
    public String deleteMetric(@PathVariable("id") Long id, Model model) throws MetricNotFoundException {
        Metric metric = metricService.getMetric(id);
        if (metric != null)
            metricService.deleteMetric(id);
        model.addAttribute("metrics", metricService.getAllMetrics());
        return "redirect:/metrics";

    }
}
