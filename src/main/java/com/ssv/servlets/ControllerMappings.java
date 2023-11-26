package com.ssv.servlets;

import com.ssv.controllers.*;
import org.thymeleaf.web.IWebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ControllerMappings {


    private static Map<String, IController> controllersByURL;


    static {
        controllersByURL = new HashMap<String, IController>();
        controllersByURL.put("/welcome", new HomeController());
        controllersByURL.put("/client/getAllClients", new ClientController());
        controllersByURL.put("/tours/getAllTours", new TourController());
        controllersByURL.put("/tourAgents/getAllTourAgents", new TourAgentController());
    }


    public static IController resolveControllerForRequest(final IWebRequest request) {
        final String path = request.getPathWithinApplication();
        var result = controllersByURL.entrySet().stream()
                .filter(x -> x.getKey().startsWith(path))
                .findFirst()
                .orElse(null);
        if (Objects.isNull(result) || path.equals("/")) return new HomeController();
        else {
            return result.getValue();
        }
    }

    private ControllerMappings() {
        super();
    }
}
