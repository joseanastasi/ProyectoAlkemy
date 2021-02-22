package com.alkemy.ot9.interfaceService;

import com.alkemy.ot9.exceptions.OrganizationNotFound;
import com.alkemy.ot9.models.Organization;

import java.util.List;

public interface IOrganizationService {

    Long createOrganization(Organization organization);

    List<Organization> findAllOrganizations();

    Organization getOrganizationById(Long id) throws OrganizationNotFound;

    void deleteOrganizationById(Long id) throws OrganizationNotFound;
}
