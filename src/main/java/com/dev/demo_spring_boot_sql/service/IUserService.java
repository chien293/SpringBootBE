package com.dev.demo_spring_boot_sql.service;

import com.dev.demo_spring_boot_sql.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User getUser(String email);
}
