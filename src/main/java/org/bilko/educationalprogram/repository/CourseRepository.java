package org.bilko.educationalprogram.repository;

import org.bilko.educationalprogram.model.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
//    @EntityGraph(attributePaths = {"program", "organization", "students", "course"})
    @Query("SELECT c FROM Course c " +
            "LEFT JOIN FETCH c.program p " +
            "LEFT JOIN FETCH p.organization")
    List<Course> findAll();

    @Query("SELECT c FROM Course c " +
            "LEFT JOIN FETCH c.program p " +
            "LEFT JOIN FETCH p.organization o " +
            "WHERE c.id = :id")
    Optional<Course> findById(Long id);
}
