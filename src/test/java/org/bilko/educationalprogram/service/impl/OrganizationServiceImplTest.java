package org.bilko.educationalprogram.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.bilko.educationalprogram.dto.organization.OrganizationRequestDto;
import org.bilko.educationalprogram.dto.organization.OrganizationResponseDto;
import org.bilko.educationalprogram.mapper.OrganizationMapper;
import org.bilko.educationalprogram.model.Organization;
import org.bilko.educationalprogram.repository.OrganizationRepository;
import org.bilko.educationalprogram.service.impl.OrganizationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrganizationServiceImplTest {
    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 100L;

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private OrganizationMapper organizationMapper;

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    @Test
    @DisplayName("Find organization by valid id")
    void findOrganizationByValidId_Success() {
        // Given
        Organization organization = new Organization();
        OrganizationResponseDto organizationResponseDto = new OrganizationResponseDto();

        // Mock behavior
        when(organizationRepository.findById(VALID_ID)).thenReturn(Optional.of(organization));
        when(organizationMapper.toDto(organization)).thenReturn(organizationResponseDto);

        // When
        OrganizationResponseDto result = organizationService.getById(VALID_ID);

        // Then
        assertNotNull(result);
        assertEquals(organizationResponseDto, result);
    }

    @Test
    @DisplayName("Find organization by invalid id")
    void findOrganizationByInvalidId_EntityNotFoundExceptionThrown() {
        // Given
        when(organizationRepository.findById(INVALID_ID)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(EntityNotFoundException.class, () -> organizationService.getById(INVALID_ID));
    }

    @Test
    @DisplayName("Update organization with valid id")
    void updateOrganizationWithValidId_Success() {
        // Given
        Long organizationId = 1L;
        OrganizationRequestDto requestDto = new OrganizationRequestDto();
        Organization organization = new Organization();
        organization.setId(organizationId);

        // Mock behavior
        when(organizationRepository.existsById(organizationId)).thenReturn(true);
        when(organizationMapper.toEntity(requestDto)).thenReturn(organization);
        when(organizationRepository.save(organization)).thenReturn(organization);
        when(organizationMapper.toDto(organization)).thenReturn(new OrganizationResponseDto());

        // When
        OrganizationResponseDto result = organizationService.update(organizationId, requestDto);

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("Update organization with invalid id")
    void updateOrganizationWithInvalidId_EntityNotFoundExceptionThrown() {
        // Given
        Long organizationId = 1L;
        OrganizationRequestDto requestDto = new OrganizationRequestDto();

        // Mock behavior
        when(organizationRepository.existsById(organizationId)).thenReturn(false);

        // When, Then
        assertThrows(EntityNotFoundException.class, () -> organizationService.update(organizationId, requestDto));
    }

    @Test
    @DisplayName("Remove organization by valid id")
    void removeOrganizationByValidId_Success() {
        // Given
        Long organizationId = 1L;

        // Mock behavior
        when(organizationRepository.existsById(organizationId)).thenReturn(true);

        // When
        organizationService.remove(organizationId);

        // Then: No exceptions should be thrown
    }

    @Test
    @DisplayName("Remove organization by invalid id")
    void removeOrganizationByInvalidId_EntityNotFoundExceptionThrown() {
        // Given
        Long organizationId = 1L;

        // Mock behavior
        when(organizationRepository.existsById(organizationId)).thenReturn(false);

        // When, Then
        assertThrows(EntityNotFoundException.class, () -> organizationService.remove(organizationId));
    }
}
