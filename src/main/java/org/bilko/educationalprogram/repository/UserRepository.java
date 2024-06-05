package org.bilko.educationalprogram.repository;

import org.bilko.educationalprogram.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"organization", "courses"})
    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.organization o " +
            "LEFT JOIN FETCH u.courses c " +
            "LEFT JOIN FETCH u.roles r " +
            "WHERE u.email = :email")
    Optional<User> findFirstByEmail(String email);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.organization o " +
            "LEFT JOIN FETCH u.courses c " +
            "LEFT JOIN FETCH u.roles r " +
            "WHERE o.id = :organizationId")
    Optional<List<User>> findByOrganizationId(Long organizationId);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.organization o " +
            "LEFT JOIN FETCH u.courses c " +
            "LEFT JOIN FETCH u.roles r " +
            "WHERE c.id = :courseId")
    Optional<List<User>> findByCourseId(Long courseId);
}
