package com.ssv.servlets;

import com.ssv.controllers.HomeController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
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

import java.io.Writer;
import java.util.Objects;

import static com.ssv.services.cookie.CookieManager.addCountOfVisitToCookie;
import static com.ssv.services.cookie.CookieManager.addLastVisitToCookie;
import static com.ssv.servlets.ControllerMappings.resolveControllerForRequest;

@WebServlet(urlPatterns = "/*")
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private JakartaServletWebApplication application;
    private ITemplateEngine templateEngine;


    @Override
    public void init() {
        this.application =
                JakartaServletWebApplication.buildApplication(getServletContext());

        this.templateEngine = buildTemplateEngine(this.application);
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

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        if (isStaticResource(request.getRequestURI())) return;
        response.setContentType("text/html;charset=UTF-8");
        addCountOfVisitToCookie(request, response);
        addLastVisitToCookie(request, response);
        try {
            final IServletWebExchange webExchange = this.application.buildExchange(request, response);
            final IWebRequest webRequest = webExchange.getRequest();
            final Writer writer = response.getWriter();
            var controller = resolveControllerForRequest(webRequest);
            if (Objects.isNull(controller)) {
                controller = new HomeController();
            }
            controller.process(webExchange, templateEngine, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isStaticResource(String path) {
        if (path.endsWith(".ico") || path.endsWith(".css") || path.endsWith(".js")) {
            return true;
        }
        return false;
    }
}
  