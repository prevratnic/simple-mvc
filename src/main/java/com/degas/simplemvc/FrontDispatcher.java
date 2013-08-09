package com.degas.simplemvc;

import com.degas.simplemvc.configuration.LoadXmlConfiguration;
import com.degas.simplemvc.controllers.Controller;
import com.degas.simplemvc.controllers.DefaultController;
import com.degas.simplemvc.configuration.model.ControllerConfig;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Ilya Varlamov aka privratnik (contact with me) degas.developer@gmail.com
 * Date: 16.07.13
 * Time: 0:29
 */

public class FrontDispatcher extends DispatcherService {

    private static final Logger LOGGER = LogManager.getLogger(FrontDispatcher.class);

    @Override
    protected void process(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {

        final LoadXmlConfiguration modelAction = getModelController();
        final ControllerConfig controllerConfig;
        final Controller controller;
        String controllerType, forwardView, process;

        controllerConfig = modelAction.getControllerConfig(getRequestUrl(req));

        if(controllerConfig == null){
            sendErrorPageNotFound(resp);
            LOGGER.debug("Url address not found");
            return;
        }

        controllerType = modelAction.getTypeController(controllerConfig);
        controller = getRealController(controllerType);
        process = controller.processRequest(req, resp);

        forwardView = modelAction.getForwardPage(controllerConfig, process);

        if(forwardView == null){
            sendErrorPageNotFound(resp);
            LOGGER.error("View not found");
            return;
        }

        forward(req, resp, forwardView);

    }

    @NotNull
    private Controller getRealController(@NotNull String controllerType){
        try {
            return (Controller) Class.forName(controllerType).newInstance();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return new DefaultController();
        }
    }

    private void forward(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp, @NotNull String path) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(path);
        dispatcher.forward(req, resp);
    }

    /**
     * Method removes the context path
     * @return String URL
     * */
    @NotNull
    private String getRequestUrl(@NotNull HttpServletRequest req) throws ServletException, IOException{
        final String url = req.getRequestURI(),
        context = req.getContextPath();

        return url.substring(context.length());
    }

    private void sendErrorPageNotFound(@NotNull HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

}
