package com.ssv.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.web.IWebExchange;

import java.io.Writer;

public interface IController {
    public void process(final IWebExchange webExchange, final ITemplateEngine templateEngine, final Writer writer, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception;

}
