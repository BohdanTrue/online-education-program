package org.bilko.educationalprogram.repository;

import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.bilko.educationalprogram.model.Organization;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrganizationRepositoryTest {
    @Autowired
    private OrganizationRepository organizationRepository;

    private Organization epam;
    private Organization ajax;

    @BeforeEach
    void setUp() {
        epam = new Organization()
                .setId(1L)
                .setName("EPAM")
                .setDescription("Super outsource company");

        ajax = new Organization()
                .setId(2L)
                .setName("Ajax Systems")
                .setDescription("Super product company");
    }

    @DisplayName("""
            find all organizations
            """)
    @Sql(scripts = {
            "classpath:database/organization/add-organizations.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/organization/remove-organizations.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void findOrganizationByValidId_Success() {
        Organization expected = ajax;
        Organization actual = organizationRepository.findById(2L).orElseThrow(() -> new EntityNotFoundException("Cannot find organization by id: 2"));

        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        EqualsBuilder.reflectionEquals(expected, actual);
    }

    @DisplayName("""
            find organization by valid id
            """)
    @Sql(scripts = {
            "classpath:database/organization/add-organizations.sql"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/organization/remove-organizations.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    void findAllOrganizations_Success() {
        List<Organization> expected = List.of(epam, ajax);
        List<Organization> actual = organizationRepository.findAll();

        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        EqualsBuilder.reflectionEquals(expected, actual);
    }
}