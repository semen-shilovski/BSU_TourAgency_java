package com.ssv.services;

import com.ssv.models.auth.User;
import com.ssv.models.auth.UserRole;
import com.ssv.services.dao.UserDao;

import static com.ssv.services.utils.security.PasswordUtils.hashPassword;

public class UserService {
    public UserDao userDao = new UserDao();

    public User getUserByUsername(String username) {
        return userDao.getByUsername(username).orElse(null);
    }

    public void createUser(String username, String password, UserRole userRole) {
        userDao.save(User.builder()
                .username(username)
                .password(hashPassword(password))
                .role(userRole)
                .build());
    }
}
