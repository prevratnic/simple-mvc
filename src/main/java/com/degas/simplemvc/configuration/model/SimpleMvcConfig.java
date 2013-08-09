package com.degas.simplemvc.configuration.model;

import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Set;

/**
 * Author: Ilya Varlamov aka privratnik (contact with me) degas.developer@gmail.com
 * Date: 16.07.13
 * Time: 13:25
 */
@XmlRootElement(name = "simple-mvc-config")
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleMvcConfig {

    @XmlElement(name = "controller-config")
    private Set<ControllerConfig> controllerConfig;

    public void setControllerConfig(Set<ControllerConfig> controllerConfig) {
        this.controllerConfig = controllerConfig;
    }

    @NotNull
    public Set<ControllerConfig> getControllerConfig() {
        return controllerConfig;
    }

}
