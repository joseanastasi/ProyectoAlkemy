package com.alkemy.ot9.service;

import com.alkemy.ot9.entities.OrganizationEntity;
import com.alkemy.ot9.exceptions.OrganizationNotFound;
import com.alkemy.ot9.interfaceService.IOrganizationService;
import com.alkemy.ot9.mappers.OrganizationMapper;
import com.alkemy.ot9.models.Organization;
import com.alkemy.ot9.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationService implements IOrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationMapper organizationMapper, OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
    }

    public Long createOrganization(Organization organization) {
        OrganizationEntity organizationEntity = organizationRepository.save(organizationMapper.map(organization));
        return organizationEntity.getId();
    }

    public List<Organization> findAllOrganizations() {
        return organizationMapper.map((List<OrganizationEntity>) organizationRepository.findAll());
    }

    public Organization getOrganizationById(Long id) throws OrganizationNotFound {
        Optional<OrganizationEntity> organizationEntity = organizationRepository.findById(id);
        if (organizationEntity.isEmpty()) {
            throw new OrganizationNotFound();
        }
        return organizationMapper.map(organizationEntity.get());
    }

    public void deleteOrganizationById(Long id) throws OrganizationNotFound {
        Optional<OrganizationEntity> organizationEntity = organizationRepository.findById(id);
        if (organizationEntity.isEmpty()) {
            throw new OrganizationNotFound();
        }
        organizationRepository.deleteById(id);
    }
    
    public Organization getActiveOrganization() throws OrganizationNotFound {
        Optional<OrganizationEntity> organizationEntity = organizationRepository.getActiveOrganization();
        if (organizationEntity.isEmpty()) {
            throw new OrganizationNotFound();
        }
        return organizationMapper.map(organizationEntity.get());
    }
    
    public boolean emailIsConfig() {
    	
    	if (this.findAllOrganizations().size()!=0) {
    		Organization organization = organizationMapper.map(organizationRepository.getActiveOrganization().get());
        	if(organization.getEmailServer().isEmpty() || organization.getEmailServerPort().isEmpty() || organization.getEmailUser().isEmpty() || organization.getEmailPassword().isEmpty()) {
        		return false;
        	}else {
        		return true;
        	}
    	} else {
    		return false;
    	}    	    	
    }
}
