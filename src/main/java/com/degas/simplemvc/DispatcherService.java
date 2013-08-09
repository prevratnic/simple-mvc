package com.degas.simplemvc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.degas.simplemvc.configuration.LoadXmlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Author: Ilya Varlamov aka privratnik (contact with me) degas.developer@gmail.com
 * Date: 18.07.13
 * Time: 17:06
 */
public abstract class DispatcherService extends HttpServlet {

    private final static String CONF_CONTROLLER = "confController";

    //private static final Logger LOGGER = LogManager.getLogger(DispatcherService.class);
    private LoadXmlConfiguration loadXmlConfiguration;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        String paramName = config.getInitParameter(CONF_CONTROLLER);
        String fullPath = config.getServletContext().getRealPath(paramName);

        loadXmlConfiguration = LoadXmlConfiguration.getInstance(fullPath);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Template method accepting all requests
     * */
    protected abstract void process(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException;

    @NotNull
    public LoadXmlConfiguration getModelController() {
        return loadXmlConfiguration;
    }

}
