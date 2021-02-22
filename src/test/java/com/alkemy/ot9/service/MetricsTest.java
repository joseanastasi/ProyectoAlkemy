package com.alkemy.ot9.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import com.alkemy.ot9.controller.MetricController;
import com.alkemy.ot9.entities.MetricEntity;
import com.alkemy.ot9.exceptions.MetricNotFoundException;
import com.alkemy.ot9.mappers.MetricMapper;
import com.alkemy.ot9.models.Metric;

import com.alkemy.ot9.repository.MetricRepository;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class MetricsTest {
    @Mock
    MetricRepository metricRepository;

    @InjectMocks
    private MetricController metricController;

    @Mock
    MetricMapper metricMapper;

    @Mock
    MetricService metricService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        metricMapper = new MetricMapper(new ModelMapper());
        metricService = new MetricService(metricMapper, metricRepository);
    }

    @Test
    public void addNewMetric() {
        Long id = 1L;
        Metric metric = new Metric();
        MetricEntity metricEntity = new MetricEntity();
        metric.setId(id);
        metric.setMembers("miembros");
        metric.setPatients("pacientes");
        metric.setUsersActivities("usuariosActividades");
        Long numero = (long) 10000;
        metric.setCountMembers(numero);
        metric.setCountPatients(numero);
        metric.setCountUsersActivities(numero);
        metricEntity.setId(id);
        metricEntity.setMembers("miembros");
        metricEntity.setPatients("pacientes");
        metricEntity.setUsersActivities("usuariosActividades");

        metricEntity.setCountMembers(numero);
        metricEntity.setCountPatients(numero);
        metricEntity.setCountUsersActivities(numero);

        when(metricRepository.save(any())).thenReturn(metricEntity);

        Long resultId = metricService.createMetric(metric);

        Assertions.assertEquals(id, resultId);
    }

    @Test
    public void newMetric() {
        Long id = 1L;
        Metric metric = new Metric();
        MetricEntity metricEntity = new MetricEntity();
        metric.setId(id);
        metric.setMembers("miembros");
        metric.setPatients("pacientes");
        metric.setUsersActivities("usuariosActividades");
        Long numero = (long) 10000;
        metric.setCountMembers(numero);
        metric.setCountPatients(numero);
        metric.setCountUsersActivities(numero);

        metricEntity.setId(id);

        when(metricRepository.save(any())).thenReturn(metricEntity);

        Long resultId = metricService.createMetric(metric);

        Assertions.assertEquals(id, resultId);
    }

    @Test
    public void listMetrics() {

        Long id = 1L;
        Metric metric = new Metric();
        MetricEntity metricEntity = new MetricEntity();
        metric.setId(id);
        metric.setMembers("miembros");
        metric.setPatients("pacientes");
        metric.setUsersActivities("usuariosActividades");
        Long numero = (long) 10000;
        metric.setCountMembers(numero);
        metric.setCountPatients(numero);
        metric.setCountUsersActivities(numero);
        metricEntity.setId(id);
        metricEntity.setMembers("miembros");
        metricEntity.setPatients("pacientes");
        metricEntity.setUsersActivities("usuariosActividades");

        metricEntity.setCountMembers(numero);
        metricEntity.setCountPatients(numero);
        metricEntity.setCountUsersActivities(numero);

        List<MetricEntity> metricsEntities = new ArrayList<>();
        metricsEntities.add(metricEntity);

        List<Metric> metrics = new ArrayList<>();
        metrics.add(metric);

        when(metricRepository.findAll()).thenReturn(metricsEntities);

        List<Metric> results = metricService.getAllMetrics();

        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(metric.getId(), results.get(0).getId());
        Assertions.assertEquals(metric.getPatients(), results.get(0).getPatients());
        Assertions.assertEquals(metric.getCountPatients(), results.get(0).getCountPatients());
        Assertions.assertEquals(metric.getMembers(), results.get(0).getMembers());
        Assertions.assertEquals(metric.getCountMembers(), results.get(0).getCountMembers());
        Assertions.assertEquals(metric.getUsersActivities(), results.get(0).getUsersActivities());
        Assertions.assertEquals(metric.getCountUsersActivities(), results.get(0).getCountUsersActivities());
    }

    @Test
    public void testMetricById() throws MetricNotFoundException {
        Long id = 1L;
        Metric metric = new Metric();
        MetricEntity metricEntity = new MetricEntity();
        metric.setId(id);
        metric.setMembers("miembros");
        metric.setPatients("pacientes");
        metric.setUsersActivities("usuariosActividades");
        Long numero = (long) 10000;
        metric.setCountMembers(numero);
        metric.setCountPatients(numero);
        metric.setCountUsersActivities(numero);
        metricEntity.setId(id);
        metricEntity.setMembers("miembros");
        metricEntity.setPatients("pacientes");
        metricEntity.setUsersActivities("usuariosActividades");

        metricEntity.setCountMembers(numero);
        metricEntity.setCountPatients(numero);
        metricEntity.setCountUsersActivities(numero);

        when(metricRepository.findById(id)).thenReturn(Optional.of(metricEntity));
        Metric result = metricService.getMetricById(id);

        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals(metric.getId(), result.getId());
        Assertions.assertEquals(metric.getPatients(), result.getPatients());
        Assertions.assertEquals(metric.getCountPatients(), result.getCountPatients());
        Assertions.assertEquals(metric.getMembers(), result.getMembers());
        Assertions.assertEquals(metric.getCountMembers(), result.getCountMembers());
        Assertions.assertEquals(metric.getUsersActivities(), result.getUsersActivities());
        Assertions.assertEquals(metric.getCountUsersActivities(), result.getCountUsersActivities());
    }

    @Test
    public void testGetByIdException() {
        Long id = 1L;

        when(metricRepository.findById(id)).thenReturn(Optional.empty());
        try {
            metricService.getMetricById(id);
        } catch (MetricNotFoundException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test
    public void testDeleteByIdException() {
        Long id = 1L;

        when(metricRepository.findById(id)).thenReturn(Optional.empty());

        try {
            metricService.getMetricById(id);
        } catch (MetricNotFoundException e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test
    public void testDeleteMetric() throws MetricNotFoundException {
        Long id = 1L;
        Metric metric = new Metric();
        MetricEntity metricEntity = new MetricEntity();
        metric.setId(id);
        metric.setMembers("miembros");
        metric.setPatients("pacientes");
        metric.setUsersActivities("usuariosActividades");
        Long numero = (long) 10000;
        metric.setCountMembers(numero);
        metric.setCountPatients(numero);
        metric.setCountUsersActivities(numero);
        metricEntity.setId(id);
        metricEntity.setMembers("miembros");
        metricEntity.setPatients("pacientes");
        metricEntity.setUsersActivities("usuariosActividades");

        metricEntity.setCountMembers(numero);
        metricEntity.setCountPatients(numero);
        metricEntity.setCountUsersActivities(numero);

        when(metricRepository.findById(id)).thenReturn(java.util.Optional.of(metricEntity));

        metricService.deleteMetric(1L);

    }

}
