package com.ssv.controllers;

import com.ssv.models.auth.UserRole;
import com.ssv.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;

public class RegisterController implements IController {
    UserService userService = new UserService();

    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        System.out.println(ctx.getExchange().getRequest().getMethod());
        if (ctx.getExchange().getRequest().getRequestPath().equals("/registerPage")) {
            templateEngine.process("auth/register", ctx, writer);
        } else if (ctx.getExchange().getRequest().getRequestPath().equals("/register") && ctx.getExchange().getRequest().getMethod().equals("POST")) {
            var username = webExchange.getRequest().getParameterValue("username");
            var pass = webExchange.getRequest().getParameterValue("password");
            userService.createUser(username, pass, UserRole.USER);
            response.sendRedirect("/register-success");
        } else if (ctx.getExchange().getRequest().getRequestPath().equals("/register-success") && ctx.getExchange().getRequest().getMethod().equals("GET")) {
            templateEngine.process("auth/register-success", ctx, writer);
        }
    }
}
