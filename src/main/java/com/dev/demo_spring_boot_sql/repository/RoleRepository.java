package com.dev.demo_spring_boot_sql.repository;

import com.dev.demo_spring_boot_sql.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);

    boolean existsByName(Role role);
}
