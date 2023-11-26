package com.ssv.controllers;

import com.ssv.services.TourAgentService;
import com.ssv.services.TravelAgencyService;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;

public class TourController implements IController {
    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        TravelAgencyService travelAgencyService = new TravelAgencyService();
        ctx.setVariable("tours", travelAgencyService.getTours());
        templateEngine.process("tours/list", ctx, writer);
    }
}
