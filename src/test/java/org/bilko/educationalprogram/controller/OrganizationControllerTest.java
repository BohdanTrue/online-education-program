package org.bilko.educationalprogram.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.bilko.educationalprogram.dto.organization.OrganizationRequestDto;
import org.bilko.educationalprogram.dto.organization.OrganizationResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrganizationControllerTest {
    protected static MockMvc mockMvc;
    private static final String ORGANIZATIONS_API = "/organizations";
    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 100L;
    private static final String ORGANIZATION_ID_PATH = "/{id}";
    private static List<OrganizationResponseDto> organizationList;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        OrganizationResponseDto epamDto = new OrganizationResponseDto()
                .setId(1L)
                .setName("EPAM")
                .setDescription("Super outsource company");
        OrganizationResponseDto ajaxDto = new OrganizationResponseDto()
                .setId(2L)
                .setName("Ajax Systems")
                .setDescription("Super product company");
        organizationList = List.of(epamDto, ajaxDto);
    }

    @BeforeEach
    void setUp(@Autowired DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/organization/add-organizations.sql")
            );
        }
    }

    @AfterEach
    void teardown(@Autowired DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(
                    connection,
                    new ClassPathResource("database/organization/remove-organizations.sql")
            );
        }
    }

    @Test
    @WithMockUser(username = "user", roles = {"STUDENT", "ADMIN"})
    @DisplayName("Get all organizations")
    void getAllOrganizations_ReturnsListOfOrganizations() throws Exception {
        // when
        MvcResult mvcResult = mockMvc.perform(get(ORGANIZATIONS_API)).andReturn();

        // then
        List<OrganizationResponseDto> actualList =
                objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                        new TypeReference<List<OrganizationResponseDto>>() {});
        assertNotNull(actualList);
        assertEquals(organizationList.size(), actualList.size());
        assertEquals(organizationList, actualList);
    }

    @Test
    @WithMockUser(username = "user", roles = {"STUDENT", "ADMIN"})
    @DisplayName("Get organization by valid id")
    void getOrganizationById_ValidId_ReturnsOrganizationDto() throws Exception {
        // when
        MvcResult mvcResult = mockMvc.perform(get(ORGANIZATIONS_API + ORGANIZATION_ID_PATH, VALID_ID))
                .andExpect(status().isOk())
                .andReturn();

        // then
        OrganizationResponseDto expectedDto = organizationList.get(0); // assuming EPAM is the first in the list
        OrganizationResponseDto actualDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                OrganizationResponseDto.class);
        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Update organization with valid request")
    void updateOrganization_ValidRequest_ReturnsUpdatedOrganizationDto() throws Exception {
        // given
        OrganizationRequestDto requestDto = new OrganizationRequestDto()
                .setName("Updated Organization")
                .setDescription("Updated Description");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        // when
        MvcResult mvcResult = mockMvc.perform(put(ORGANIZATIONS_API + ORGANIZATION_ID_PATH, VALID_ID)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        OrganizationResponseDto expectedDto = new OrganizationResponseDto()
                .setId(VALID_ID)
                .setName(requestDto.getName())
                .setDescription(requestDto.getDescription());
        OrganizationResponseDto actualDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                OrganizationResponseDto.class);
        assertNotNull(actualDto);
        assertEquals(expectedDto, actualDto);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Delete organization with valid id")
    void deleteOrganization_ValidId_Success() throws Exception {
        // when
        mockMvc.perform(delete(ORGANIZATIONS_API + ORGANIZATION_ID_PATH, VALID_ID))
                .andExpect(status().isNoContent());

        // then: Verify the organization is no longer available
        mockMvc.perform(get(ORGANIZATIONS_API + ORGANIZATION_ID_PATH, VALID_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Get organization by invalid id")
    void getOrganizationById_InvalidId_ReturnsNotFound() throws Exception {
        // when, then
        mockMvc.perform(get(ORGANIZATIONS_API + ORGANIZATION_ID_PATH, INVALID_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Update organization with invalid id")
    void updateOrganization_InvalidId_ReturnsNotFound() throws Exception {
        // given
        OrganizationRequestDto requestDto = new OrganizationRequestDto()
                .setName("Non-existent Organization")
                .setDescription("Non-existent Description");
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        // when, then
        mockMvc.perform(put(ORGANIZATIONS_API + ORGANIZATION_ID_PATH, INVALID_ID)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("Delete organization with invalid id")
    void deleteOrganization_InvalidId_ReturnsNotFound() throws Exception {
        // when, then
        mockMvc.perform(delete(ORGANIZATIONS_API + ORGANIZATION_ID_PATH, INVALID_ID))
                .andExpect(status().isNotFound());
    }
}
