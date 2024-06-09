package org.bilko.educationalprogram.repository;

import org.bilko.educationalprogram.model.Module;
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
public interface ModuleRepository extends JpaRepository<Module, Long> {
    @Query("SELECT m FROM Module m " +
        "LEFT JOIN FETCH m.course c " +
        "LEFT JOIN FETCH c.students s " +
        "LEFT JOIN FETCH s.organization o " +
        "LEFT JOIN FETCH s.courses cs")
    Page<Module> findAll(Pageable pageable);

    @Query("SELECT m FROM Module m " +
            "LEFT JOIN FETCH m.course c " +
            "LEFT JOIN FETCH c.students s " +
            "LEFT JOIN FETCH s.organization o " +
            "LEFT JOIN FETCH s.courses cs " +
            "WHERE m.id = :id")
    Optional<Module> findById(Long id);
}
