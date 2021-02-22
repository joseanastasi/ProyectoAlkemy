package com.alkemy.ot9.service;

import static org.mockito.ArgumentMatchers.any;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.alkemy.ot9.entities.ContributorEntity;
import com.alkemy.ot9.exceptions.ContributorNotFoundException;
import com.alkemy.ot9.mappers.ContributorMapper;
import com.alkemy.ot9.models.Contributor;
import com.alkemy.ot9.repository.ContributorRepository;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ContributorServiceTest {

	@MockBean
	private ContributorRepository contributorRepository;
	
	@MockBean
	private ContributorMapper contributorMapper;
	
	@InjectMocks
	private ContributorService contributorService;
	
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        contributorMapper = new ContributorMapper(new ModelMapper());
        contributorService = new ContributorService(contributorRepository, contributorMapper);
    }
	
	@Test
	public void testCreateContributor () {
		Long id = 1L;
		
		Contributor contributor = new Contributor();
		contributor.setId(id);
		
		ContributorEntity contributorEntity = new ContributorEntity();
		contributorEntity.setId(id);
		
		when(contributorRepository.save(any())).thenReturn(contributorEntity);
		Long resultId = contributorService.createContributor(contributor);
		
		Assertions.assertEquals(id, resultId);
	}
	
	@Test
	public void testDeleteContributorById() throws ContributorNotFoundException {
		
		Long id = 1L;
		
		Contributor contributor = new Contributor();
		contributor.setId(id);
		
		ContributorEntity contributorEntity = new ContributorEntity();
		contributorEntity.setId(id);
		
		when(contributorRepository.findById(id)).thenReturn(java.util.Optional.of(contributorEntity));
		contributorService.deleteContributorById(id);
	}
	
	@Test
	public void testGetAllContributors () {
		
		Long id = 1L;
		
		Contributor contributor = new Contributor();
		contributor.setId(id);
		
		ContributorEntity contributorEntity = new ContributorEntity();
		contributorEntity.setId(id);
		
		List<ContributorEntity> contributorsEntity = new ArrayList<>();
		contributorsEntity.add(contributorEntity);
		
		List<Contributor> contributors = new ArrayList<>();
		contributors.add(contributor);
		
		when(contributorRepository.lastTen()).thenReturn(contributorsEntity);
		List<Contributor> result = contributorService.getAllContributors();
		
		Assertions.assertEquals(1, result.size());
		
	}
	
	@Test
	public void testGetContributorById() throws ContributorNotFoundException {
		
		Long id = 1L;
		
		Contributor contributor = new Contributor();
		contributor.setId(id);
		
		ContributorEntity contributorEntity = new ContributorEntity();
		contributorEntity.setId(id);
		
		when(contributorRepository.findById(id)).thenReturn(java.util.Optional.of(contributorEntity));
		
		Contributor result = contributorService.getContributorById(id);
		
		Assertions.assertEquals(id, result.getId());
		
	}
	
	@Test
	public void testGetContributorByIdException() {
		
		Long id= 1L;
		
		when(contributorRepository.findById(id)).thenReturn(Optional.empty());
		try {
			contributorService.getContributorById(id);
		} catch (ContributorNotFoundException e) {
			Assert.assertTrue(true);
			return;
		}
		
		Assert.fail();
	}
	
	@Test
	public void testDeleteContributorByIdException() {
		
		Long id= 1L;
		
		when(contributorRepository.findById(id)).thenReturn(Optional.empty());
		try {
			contributorService.deleteContributorById(id);
		} catch (ContributorNotFoundException e) {
			Assert.assertTrue(true);
			return;
		}
		
		Assert.fail();
	}
	
	
	
}
