package com.ssv.services.auth;

import com.ssv.models.auth.User;
import com.ssv.models.auth.UserRole;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static com.ssv.services.utils.security.JwtService.createJwtToken;

public class GuestSessionService {
    public static ConcurrentHashMap<String, User> guestUsers = new ConcurrentHashMap<>();

    public User addGuestUser() {
        User user = new User();
        user.setUsername(UUID.randomUUID().toString());
        user.setPassword(null);
        user.setRole(UserRole.GUEST);
        guestUsers.put(user.getUsername(), user);
        return user;
    }

    public String getGuestToken(User user) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long oneYearMillis = 365L * 24 * 60 * 60 * 1000;
        Date expiryDate = new Date(nowMillis + oneYearMillis);
        return createJwtToken(user.getUsername(), expiryDate);
    }
}
