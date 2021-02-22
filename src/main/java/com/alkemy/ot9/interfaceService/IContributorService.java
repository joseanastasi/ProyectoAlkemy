package com.alkemy.ot9.interfaceService;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alkemy.ot9.entities.ContributorEntity;
import com.alkemy.ot9.exceptions.ContributorNotFoundException;
import com.alkemy.ot9.models.Contributor;

public interface IContributorService {

	Long createContributor(Contributor contributor);

	void deleteContributorById(Long id) throws ContributorNotFoundException;

	List<Contributor> getAllContributors();

	Contributor getContributorById(Long id) throws ContributorNotFoundException;

	boolean existEmail(String email);

	Page<ContributorEntity> getAllContributors(Pageable pageable);
}