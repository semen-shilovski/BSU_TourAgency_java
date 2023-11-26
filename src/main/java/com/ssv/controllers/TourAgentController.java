package com.ssv.controllers;

import com.ssv.services.TourAgentService;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;

public class TourAgentController implements IController {
    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        TourAgentService tourAgentService = new TourAgentService();
        ctx.setVariable("tourAgents", tourAgentService.findAll());
        templateEngine.process("tourAgents/list", ctx, writer);
    }
}
