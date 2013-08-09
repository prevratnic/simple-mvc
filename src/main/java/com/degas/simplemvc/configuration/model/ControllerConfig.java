package com.degas.simplemvc.configuration.model;

import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import java.util.Set;

/**
 * Author: Ilya Varlamov aka privratnik (contact with me) degas.developer@gmail.com
 * Date: 16.07.13
 * Time: 13:26
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class ControllerConfig {

    @XmlElement(name = "url", required = true)
    private String url;

    @XmlElement(name = "type", required = true)
    private String type;

    @XmlElement(name = "forward")
    private Set<Forward> forward;

    @NotNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NotNull String url) {
        this.url = url;
    }

    @NotNull
    public String getType() {
        return type;
    }

    public void setType(@NotNull String type) {
        this.type = type;
    }

    @NotNull
    public Set<Forward> getForward() {
        return forward;
    }

    public void setForward(@NotNull Set<Forward> forward) {
        this.forward = forward;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ControllerConfig that = (ControllerConfig) o;

        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
