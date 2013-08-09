package com.degas.simplemvc.configuration;

import com.degas.simplemvc.configuration.model.ControllerConfig;
import com.degas.simplemvc.configuration.model.Forward;
import com.degas.simplemvc.configuration.model.SimpleMvcConfig;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Author: Ilya Varlamov aka privratnik (contact with me) degas.developer@gmail.com
 * Date: 23.07.13
 * Time: 13:06
 */
public final class LoadXmlConfiguration {

    private static final Logger LOGGER = LogManager.getLogger(LoadXmlConfiguration.class);
    private static LoadXmlConfiguration configuration;
    private SimpleMvcConfig simpleMvcConfig;

    private LoadXmlConfiguration(@NotNull String path){
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(SimpleMvcConfig.class);
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            simpleMvcConfig = (SimpleMvcConfig)unmarshaller.unmarshal(new File(path));
        } catch (JAXBException e) {
            LOGGER.fatal(e.getMessage(), e);
        }
    }

    /**
     * Method init singleton object
     * @param path set path to configuration file, only xml format
     * */
    public static LoadXmlConfiguration getInstance(@NotNull String path){
        if(configuration == null)
            configuration = new LoadXmlConfiguration(path);
        return configuration;
    }


    /**
     * Method returns from collection of the controller
     * @param url address site for example /home or /contact/main, can not be null
     * @return ControllerConfig or null
     * */
    @Nullable
    public ControllerConfig getControllerConfig(@NotNull String url){
        for(ControllerConfig config : simpleMvcConfig.getControllerConfig()){
            if(config.getUrl().equals(url)){
                return config;
            }
        }
        return null;
    }

    /**
     * Method returns the path a view
     * @param config Controller config, can not be null
     * @param forwardName name forward section, can not be null
     * @return String or null
     * */
    @Nullable
    public String getForwardPage(@NotNull ControllerConfig config, @NotNull String forwardName){
        for(Forward forward : config.getForward()){
            if(forward.getName().equals(forwardName)){
                return forward.getPagesPath();
            }
        }
        return null;
    }

    @NotNull
    public String getTypeController(@NotNull ControllerConfig config){
        return config.getType();
    }

}
