package org.bilko.educationalprogram.repository;

import org.bilko.educationalprogram.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"organization", "courses"})
    List<User> findAll();

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.organization o " +
            "WHERE u.email = :email")
    Optional<User> findFirstByEmail(String email);

//    @Query("SELECT u FROM User u " +
//            "LEFT JOIN FETCH u.organization o " +
//            "WHERE c.id = :courseId")
//    Optional<List<User>> findByCourseId(Long courseId);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.organization o " +
            "WHERE o.id = :organizationId")
    Optional<List<User>> findByOrganizationId(Long organizationId);
}
