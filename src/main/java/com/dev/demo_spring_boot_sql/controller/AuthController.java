package com.dev.demo_spring_boot_sql.controller;

import com.dev.demo_spring_boot_sql.exception.UserAlreadyExistException;
import com.dev.demo_spring_boot_sql.model.Role;
import com.dev.demo_spring_boot_sql.model.User;
import com.dev.demo_spring_boot_sql.request.LoginRequest;
import com.dev.demo_spring_boot_sql.response.JwtResoponse;
import com.dev.demo_spring_boot_sql.security.jwt.JwtUtils;
import com.dev.demo_spring_boot_sql.security.user.UserDetail;
import com.dev.demo_spring_boot_sql.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IUserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("Registration successful");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request) {
        Authentication authentication =
                authenticationManager
                        .authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()
                                ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtTokenForUser(authentication);
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        List<String> roles = userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return ResponseEntity.ok(new JwtResoponse(
                userDetail.getId(),
                userDetail.getEmail(),
                jwt,
                roles
        ));
    }
}
