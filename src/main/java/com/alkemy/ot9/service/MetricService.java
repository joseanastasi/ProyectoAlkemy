package com.alkemy.ot9.service;

import java.util.*;

import com.alkemy.ot9.entities.MetricEntity;
import com.alkemy.ot9.exceptions.MetricNotFoundException;
import com.alkemy.ot9.interfaceService.IMetricService;
import com.alkemy.ot9.mappers.MetricMapper;
import com.alkemy.ot9.models.Metric;
import com.alkemy.ot9.repository.MetricRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricService implements IMetricService {

    @Autowired
    MetricRepository metricRepository;

    @Autowired
    MetricMapper metricMapper;

    public MetricService(MetricMapper metricMapper, MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
        this.metricMapper = metricMapper;
    }

    public Long createMetric(Metric metric) {
        MetricEntity mEntity = metricRepository.save(metricMapper.map(metric));
        return mEntity.getId();
    }

    public List<Metric> getAllMetrics() {
        List<Metric> metrics = new ArrayList<>();
        metricRepository.findAll().stream().forEach(m -> {
            metrics.add(metricMapper.map(m));
        });
        return metrics;
    }

    public boolean deleteMetric(Long id) throws MetricNotFoundException {
        Metric metric = getMetricById(id);
        if (metric != null) {
            metricRepository.delete(metricMapper.map(metric));
            return true;
        }
        return false;
    }

    public boolean updateMetric(Metric metric) {
        metricRepository.save(metricMapper.map(metric));
        return true;
    }

    public Metric getMetric(Long id) throws MetricNotFoundException {
        MetricEntity metric = metricRepository.findAll().stream().filter(m -> m.getId() == id).findFirst().get();
        return (metric != null ? metricMapper.map(metric) : null);
    }

    public Metric getMetricById(Long id) throws MetricNotFoundException {
        Optional<MetricEntity> r = metricRepository.findById(id);
        if (r.isEmpty()) {
            throw new MetricNotFoundException();
        }
        return metricMapper.map(r.get());
    }

}
