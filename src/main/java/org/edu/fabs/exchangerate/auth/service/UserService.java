package org.edu.fabs.exchangerate.auth.service;

import org.edu.fabs.exchangerate.auth.entity.Role;
import org.edu.fabs.exchangerate.auth.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    User getUser(String email);
    List<User> getUsers(); // page

}
