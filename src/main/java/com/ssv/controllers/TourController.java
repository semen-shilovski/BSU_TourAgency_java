package com.ssv.controllers;

import com.ssv.models.auth.User;
import com.ssv.models.auth.UserRole;
import com.ssv.services.TourAgentService;
import com.ssv.services.TravelAgencyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;

public class TourController implements IController {
    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        User u = (User) request.getSession(true).getAttribute("user");

        if (u.getRole().equals(UserRole.GUEST)) {
            response.sendRedirect("/home");
        }
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        TravelAgencyService travelAgencyService = new TravelAgencyService();
        ctx.setVariable("tours", travelAgencyService.getTours());
        templateEngine.process("tours/list", ctx, writer);
    }
}
