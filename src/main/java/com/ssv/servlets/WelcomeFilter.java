package com.ssv.servlets;

import com.ssv.services.auth.AuthenticateService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.IWebApplication;
import org.thymeleaf.web.IWebRequest;
import org.thymeleaf.web.servlet.IServletWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.io.Writer;

import static com.ssv.services.cookie.CookieManager.addCountOfVisitToCookie;
import static com.ssv.services.cookie.CookieManager.addLastVisitToCookie;
import static com.ssv.servlets.ControllerMappings.resolveControllerForRequest;

@WebFilter(urlPatterns = "/*")
public class WelcomeFilter implements Filter {
    private JakartaServletWebApplication application;
    private ITemplateEngine templateEngine;
    private AuthenticateService authenticateService;

    @Override
    public void init(FilterConfig filterConfig) {

        this.application =
                JakartaServletWebApplication.buildApplication(filterConfig.getServletContext());

        this.templateEngine = buildTemplateEngine(this.application);
        this.authenticateService = new AuthenticateService();
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!process((HttpServletRequest) request, (HttpServletResponse) response)) {
            chain.doFilter(request, response);
        }
    }

    private ITemplateEngine buildTemplateEngine(final IWebApplication application) {
        final WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(application);

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(3600000L);
        templateResolver.setCacheable(true);

        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    private boolean process(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException {
        if (isStaticResource(request.getRequestURI())) return false;
        var user = authenticateService.validateAndGetAuthUser(request, response);
        request.getSession(true).setAttribute("user", user);
        System.out.println(user.toString());
        response.setContentType("text/html;charset=UTF-8");
        addCountOfVisitToCookie(request, response);
        addLastVisitToCookie(request, response);
        try {
            final IServletWebExchange webExchange = this.application.buildExchange(request, response);
            final IWebRequest webRequest = webExchange.getRequest();
            final Writer writer = response.getWriter();
            var controller = resolveControllerForRequest(webRequest);

            controller.process(webExchange, templateEngine, writer, request, response);
            return true;
        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            } catch (final IOException ignored) {

            }
            throw new ServletException(e);
        }
    }

    private boolean isStaticResource(String path) {
        if (path.endsWith(".ico") || path.endsWith(".css") || path.endsWith(".js")) {
            return true;
        }
        return false;
    }

}
