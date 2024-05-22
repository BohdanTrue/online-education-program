package org.bilko.educationalprogram.repository;

import org.bilko.educationalprogram.model.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    @EntityGraph(attributePaths = {"organization", "courses"})
    Page<Program> findAll(Pageable pageable);

    @Query("SELECT p FROM Program p " +
            "LEFT JOIN FETCH p.courses c " +
            "LEFT JOIN FETCH p.organization o " +
            "WHERE p.id = :id")
    Optional<Program> findById(Long id);
}
