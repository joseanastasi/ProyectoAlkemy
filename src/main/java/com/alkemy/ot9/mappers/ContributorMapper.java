package com.alkemy.ot9.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alkemy.ot9.entities.ContributorEntity;
import com.alkemy.ot9.models.Contributor;

@Service
public class ContributorMapper {

	private ModelMapper modelMapper;

	@Autowired
	public ContributorMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	public ContributorEntity map(Contributor contributor) {
		return modelMapper.map(contributor, ContributorEntity.class);
	}
	
	public Contributor map(Optional<ContributorEntity> contributorEntity) {		
		return modelMapper.map(contributorEntity, Contributor.class);
	}
	
	public Contributor map(ContributorEntity contributorEntity) {		
		return modelMapper.map(contributorEntity, Contributor.class);
	}
	
	public List<Contributor> map(List<ContributorEntity> ContributorsEntity) {
        List<Contributor> contributors = new ArrayList<>();
        ContributorsEntity.stream().forEach(c -> {
        	contributors.add(map(c));
        });
        return contributors;
    }
}