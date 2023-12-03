package com.ssv.controllers;

import com.ssv.services.auth.AuthenticateService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;

public class AuthController implements IController {
    AuthenticateService authenticateService = new AuthenticateService();

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        if (ctx.getExchange().getRequest().getRequestPath().equals("/authPage")) {
            templateEngine.process("auth/login", ctx, writer);
        } else if (ctx.getExchange().getRequest().getRequestPath().equals("/auth") && ctx.getExchange().getRequest().getMethod().equals("POST")) {
            var username = webExchange.getRequest().getParameterValue("username");
            var pass = webExchange.getRequest().getParameterValue("password");
            authenticateService.loginUser(username, pass, response);
            response.sendRedirect("/home");
        }
    }
}
