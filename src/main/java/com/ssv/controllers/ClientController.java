package com.ssv.controllers;

import com.ssv.services.ClientService;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ClientController implements IController {
    @Override
    public void process(IWebExchange webExchange, ITemplateEngine templateEngine, Writer writer) throws Exception {
        WebContext ctx = new WebContext(webExchange, webExchange.getLocale());
        ClientService clientService = new ClientService();
        ctx.setVariable("clients", clientService.getAllClients());
        templateEngine.process("clients/list", ctx, writer);
    }
}
