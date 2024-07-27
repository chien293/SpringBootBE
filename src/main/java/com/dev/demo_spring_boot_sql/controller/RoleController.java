package com.dev.demo_spring_boot_sql.controller;

import com.dev.demo_spring_boot_sql.exception.RoleAlreadyExistException;
import com.dev.demo_spring_boot_sql.model.Role;
import com.dev.demo_spring_boot_sql.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;

    @GetMapping("/all-roles")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<List<Role>>(roleService.getRoles(), HttpStatus.FOUND);
    }

    @PostMapping("/create-new-role")
    public ResponseEntity<String> createRole(@RequestBody Role theRole){
        try{
            roleService.createRole(theRole);
            return ResponseEntity.ok("New role created successfully");
        }catch (RoleAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable("roleId") Long roleId){
        roleService.deleteRole(roleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/remove-all-users-from-role/{roleId}")
    public ResponseEntity<?> removeAllUsersFromRole(@PathVariable("roleId") Long roleId){
        roleService.removeAllUsersFromRole(roleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/remove-user-from-role")
    public ResponseEntity<?> removeUserFromRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId){
        roleService.removeUserFromRole(userId, roleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/assign-user-to-role")
    public ResponseEntity<?> assignRoleToUser(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId){
        roleService.assignRoleToUser(userId, roleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
