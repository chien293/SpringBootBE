package com.dev.demo_spring_boot_sql.service;

import com.dev.demo_spring_boot_sql.exception.InternalServerException;
import com.dev.demo_spring_boot_sql.exception.RoleAlreadyExistException;
import com.dev.demo_spring_boot_sql.exception.UserAlreadyExistException;
import com.dev.demo_spring_boot_sql.model.Role;
import com.dev.demo_spring_boot_sql.model.User;
import com.dev.demo_spring_boot_sql.repository.RoleRepository;
import com.dev.demo_spring_boot_sql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_" + theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if (roleRepository.existsByName(role)) {
            throw new RoleAlreadyExistException(theRole.getName() + " role already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }

    @Override
    public User removeUserFromRole(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (user.isPresent() && role.isPresent() && role.get().getUsers().contains(user.get())) {
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public User assignRoleToUser(Long userId, Long roleId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);

        if (user.isPresent() && role.isPresent() && user.get().getRoles().contains(role.get())) {
            throw new UserAlreadyExistException(user.get().getFirstName() + " is already assign to the" + role.get().getName() + " role");
        }

        if (user.isPresent() && role.isPresent()) {
            role.get().assignRoleToUser(user.get());
            roleRepository.save(role.get());
        }

        return user.get();
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);

        if(role.isPresent()){
            role.ifPresent(Role::removeAllUsersFromRole);
            return roleRepository.save(role.get());
        }

        throw new InternalServerException("Internal Server Error");
    }
}
