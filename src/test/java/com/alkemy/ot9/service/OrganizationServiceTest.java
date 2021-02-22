package com.alkemy.ot9.service;

import com.alkemy.ot9.entities.OrganizationEntity;
import com.alkemy.ot9.exceptions.OrganizationNotFound;
import com.alkemy.ot9.mappers.OrganizationMapper;
import com.alkemy.ot9.models.Organization;
import com.alkemy.ot9.repository.OrganizationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrganizationServiceTest {

    @Mock
    OrganizationRepository organizationRepository;
    OrganizationMapper organizationMapper;
    OrganizationService organizationService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        organizationMapper = new OrganizationMapper(new ModelMapper());
        organizationService = new OrganizationService(organizationMapper, organizationRepository);
    }

    @Test

    public void testCreateOrganization() {

        Long id = 1L;
        Organization organization = new Organization();
        organization.setId(id);

        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId(id);

        when(organizationRepository.save(any())).thenReturn(organizationEntity);

        Long resultId = organizationService.createOrganization(organization);

        Assertions.assertEquals(id, resultId);
    }

    @Test
    public void testFindAllOrganizations() {

        Long id = 1L;
        String name = "organization-test-name";

        Organization organization = new Organization();
        organization.setId(id);
        organization.setName(name);


        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId(id);
        organizationEntity.setName(name);


        List<OrganizationEntity> organizationEntityList = new ArrayList<>();
        organizationEntityList.add(organizationEntity);

        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);

        when(organizationRepository.findAll()).thenReturn(organizationEntityList);

        List<Organization> result = organizationService.findAllOrganizations();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(organization.getId(), result.get(0).getId());
        Assertions.assertEquals(organization.getName(), result.get(0).getName());

    }

    @Test
    public void testGetOrganizationById() throws OrganizationNotFound {

        Long id = 1L;
        String name = "organization-test-name";
        String surname = "organization-test-surname";

        Organization organization = new Organization();
        organization.setId(id);
        organization.setName(name);

        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId(id);
        organizationEntity.setName(name);

        when(organizationRepository.findById(id)).thenReturn(java.util.Optional.of(organizationEntity));

        Organization resultOrganization = organizationService.getOrganizationById(id);

        Assertions.assertEquals(id, resultOrganization.getId());
        Assertions.assertEquals(name, resultOrganization.getName());

    }

    @Test
    public void testDeleteOrganizationById() throws OrganizationNotFound {

        Long id = 1L;
        String name = "organization-test-name";

        Organization organization = new Organization();
        organization.setId(id);
        organization.setName(name);

        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setId(id);
        organizationEntity.setName(name);

        when(organizationRepository.findById(id)).thenReturn(java.util.Optional.of(organizationEntity));

        organizationService.deleteOrganizationById(1L);

    }

    @Test
    public void testGetOrganizationByIdException() {
        Long id = 1L;

        when(organizationRepository.findById(id)).thenReturn(Optional.empty());

        try {
            organizationService.getOrganizationById(id);
        } catch (OrganizationNotFound e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();

    }

    @Test
    public void testDeleteOrganizationByIdException() {
        Long id = 1L;

        when(organizationRepository.findById(id)).thenReturn(Optional.empty());

        try {
            organizationService.deleteOrganizationById(id);
        } catch (OrganizationNotFound e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();

    }
}
