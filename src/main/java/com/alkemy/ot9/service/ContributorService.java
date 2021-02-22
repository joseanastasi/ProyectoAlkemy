package com.alkemy.ot9.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alkemy.ot9.entities.ContributorEntity;
import com.alkemy.ot9.exceptions.ContributorNotFoundException;
import com.alkemy.ot9.interfaceService.IContributorService;
import com.alkemy.ot9.mappers.ContributorMapper;
import com.alkemy.ot9.models.Contributor;
import com.alkemy.ot9.repository.ContributorRepository;

@Service
public class ContributorService implements IContributorService {

	private ContributorRepository contributorRepository;
	private ContributorMapper contributorMapper;

	@Autowired
	public ContributorService(ContributorRepository contributorRepository, ContributorMapper contributorMapper) {
		this.contributorRepository = contributorRepository;
		this.contributorMapper = contributorMapper;
	}

	public Long createContributor(Contributor contributor) {
		ContributorEntity createdContributorEntity = contributorRepository.save(contributorMapper.map(contributor));
		return createdContributorEntity.getId();
	}

	public void deleteContributorById(Long id) throws ContributorNotFoundException {
		Optional<ContributorEntity> ContributorOpt = contributorRepository.findById(id);
		if (ContributorOpt.isEmpty()) {
			throw new ContributorNotFoundException();
		}
		contributorRepository.deleteById(id);
	}

	public List<Contributor> getAllContributors() {

		return contributorMapper.map((List<ContributorEntity>) contributorRepository.lastTen());
	}

	public Contributor getContributorById(Long id) throws ContributorNotFoundException {
		Optional<ContributorEntity> ContributorOpt = contributorRepository.findById(id);
		if (ContributorOpt.isEmpty()) {
			throw new ContributorNotFoundException();
		}
		return contributorMapper.map(ContributorOpt.get());
	}

	public boolean existEmail(String email) {
		return contributorRepository.findByEmail(email).size() != 0;
	}

	@Override
	public Page<ContributorEntity> getAllContributors(Pageable pageable) {

		return contributorRepository.findAll(pageable);
	}
}