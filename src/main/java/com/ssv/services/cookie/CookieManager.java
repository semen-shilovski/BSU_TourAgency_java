package com.ssv.services.cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

public class CookieManager {
    public static void addLastVisitToCookie(HttpServletRequest request, HttpServletResponse response) {
        var session = request.getSession(true);
        System.out.println("Last visit time in this session : " + session.getAttribute("lastAccessTime"));
        var lastAccessTime = System.currentTimeMillis();
        Cookie lastAccessTimeCookie = new Cookie("lastAccessTime", String.valueOf(lastAccessTime));
        lastAccessTimeCookie.setMaxAge(60 * 60 * 24 * 365); // 365 days
        response.addCookie(lastAccessTimeCookie);
        session.setAttribute("lastAccessTime", lastAccessTime);
    }

    public static void addCountOfVisitToCookie(HttpServletRequest request, HttpServletResponse response) {
        var session = request.getSession(true);
        Integer visitCount = (Integer) session.getAttribute("visitCount");
        System.out.println("Last count of visit in this session : " + visitCount);
        if (visitCount == null) {
            visitCount = 1;
        } else {
            visitCount++;
        }
        Cookie visitCountCookie = new Cookie("visitCount", String.valueOf(visitCount));
        visitCountCookie.setMaxAge(60 * 60 * 24 * 365); // 365 days
        response.addCookie(visitCountCookie);
        session.setAttribute("visitCount", visitCount);
    }

    public static void addCookieToResponse(HttpServletResponse response, String cookieName, String cookieValue) {
        Cookie newCookie = new Cookie(cookieName, cookieValue);
        response.addCookie(newCookie);
    }

    public static void clearCookie(HttpServletResponse response, String cookieName) {
        Cookie newCookie = new Cookie(cookieName, null);
        response.addCookie(newCookie);
    }

    public static String getCookieValueFromRequest(HttpServletRequest request, String cookieName) {
        var cookieValue = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
        return cookieValue;
    }
}
