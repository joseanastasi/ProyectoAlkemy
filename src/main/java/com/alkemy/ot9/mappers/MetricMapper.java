package com.alkemy.ot9.mappers;

import java.util.Optional;

import com.alkemy.ot9.entities.MetricEntity;
import com.alkemy.ot9.models.Metric;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricMapper {

    private ModelMapper modelMapper;

    @Autowired
    public MetricMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MetricEntity map(Metric metric) {
        return modelMapper.map(metric, MetricEntity.class);
    }

    public Metric map(Optional<MetricEntity> MetricEntity) {
        return modelMapper.map(MetricEntity, Metric.class);
    }

    public Metric map(MetricEntity metricEntity) {
        return modelMapper.map(metricEntity, Metric.class);
    }

}
