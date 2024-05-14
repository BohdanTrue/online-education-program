package org.bilko.educationalprogram.repository;

import org.bilko.educationalprogram.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRole(Role.RoleName roleName);
}
