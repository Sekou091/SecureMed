package com.sekou.securemed.repositories;

import com.sekou.securemed.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    Roles findRoleByRoleName(String roleName);
}
