package com.ssv.services.auth;

import com.ssv.models.auth.User;
import com.ssv.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.Objects;

import static com.ssv.services.auth.GuestSessionService.guestUsers;
import static com.ssv.services.cookie.CookieManager.*;
import static com.ssv.services.utils.security.JwtService.createJwtToken;
import static com.ssv.services.utils.security.JwtService.getUsernameFromJwtToken;
import static com.ssv.services.utils.security.PasswordUtils.checkPassword;

public class AuthenticateService {
    private final String AUTH_COOKIE = "auth_token";

    UserService userService = new UserService();
    GuestSessionService guestSessionService = new GuestSessionService();

    public User validateAndGetAuthUser(final HttpServletRequest request, final HttpServletResponse response) {
        var tokenFromCookie = getCookieValueFromRequest(request, AUTH_COOKIE);

        if (Objects.nonNull(tokenFromCookie)) {
            String username = getUsernameFromJwtToken(tokenFromCookie);
            if (Objects.nonNull(username)) {
                var result = userService.getUserByUsername(username);
                if (Objects.nonNull(result)) {
                    return result;
                } else if (Objects.nonNull(guestUsers.get(username))) {
                    return guestUsers.get(username);
                }
            }
        }

        var guestUser = guestSessionService.addGuestUser();

        addCookieToResponse(response, AUTH_COOKIE, guestSessionService.getGuestToken(guestUser));

        return guestUser;
    }

    public void loginUser(String username, String password, final HttpServletResponse response) {
        var user = userService.getUserByUsername(username);
        checkPassword(password, user.getPassword());
        long nowMillis = System.currentTimeMillis();
        Date expiryDate = new Date(nowMillis + 3600000);
        var token = createJwtToken(username, expiryDate);

        addCookieToResponse(response, AUTH_COOKIE, token);
    }

    public void logout(final HttpServletRequest request, final HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        clearCookie(response, AUTH_COOKIE);
    }
}
