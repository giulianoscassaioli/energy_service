package com.energy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.energy.model.Role;
import com.energy.model.Roles;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<Role> findByRoleName(Roles role);
}
