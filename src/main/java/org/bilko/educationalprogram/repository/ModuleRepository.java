package org.bilko.educationalprogram.repository;

import org.bilko.educationalprogram.model.Module;
import org.bilko.educationalprogram.model.Program;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
    @EntityGraph(attributePaths = {"course"})
    List<Module> findAll();

    @Query("SELECT m FROM Module m " +
            "LEFT JOIN FETCH m.course c " +
            "WHERE m.id = :id")
    Optional<Module> findById(Long id);
}
