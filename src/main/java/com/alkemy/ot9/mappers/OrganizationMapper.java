package com.alkemy.ot9.mappers;

import com.alkemy.ot9.entities.OrganizationEntity;
import com.alkemy.ot9.models.Organization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationMapper {


    private ModelMapper modelMapper;

    @Autowired
    public OrganizationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrganizationEntity map(Organization organization) {
        return modelMapper.map(organization, OrganizationEntity.class);
    }

    public Organization map(OrganizationEntity organizationEntity) {
        return modelMapper.map(organizationEntity, Organization.class);
    }

    public List<Organization> map(List<OrganizationEntity> organizationEntities) {
        List<Organization> organizations = new ArrayList<>();
        organizationEntities.stream().forEach(organizationEntity -> {
            organizations.add(map(organizationEntity));
        });
        return organizations;
    }
}
