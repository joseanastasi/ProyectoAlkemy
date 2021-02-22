package com.alkemy.ot9.interfaceService;

import com.alkemy.ot9.exceptions.MetricNotFoundException;
import com.alkemy.ot9.models.Metric;

import java.util.List;

public interface IMetricService {

    Long createMetric(Metric metric);

    List<Metric> getAllMetrics();

    boolean deleteMetric(Long id) throws MetricNotFoundException;

    boolean updateMetric(Metric metric);

    Metric getMetric(Long id) throws MetricNotFoundException;

}
