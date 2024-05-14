package org.bilko.educationalprogram.repository;

import org.bilko.educationalprogram.model.Organization;
import org.bilko.educationalprogram.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    @Query("SELECT o FROM Organization o " +
            "WHERE o.id = :id")
    Optional<Organization> findById(Long id);
}
