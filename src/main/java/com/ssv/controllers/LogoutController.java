package com.ssv.controllers;

import com.ssv.services.auth.AuthenticateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;

public class LogoutController implements IController {
    AuthenticateService authenticateService = new AuthenticateService();

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
        authenticateService.logout(request, response);
        response.sendRedirect("/home");
    }
}
