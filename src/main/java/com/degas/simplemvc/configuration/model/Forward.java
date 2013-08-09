package com.degas.simplemvc.configuration.model;

import org.jetbrains.annotations.NotNull;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Author: Ilya Varlamov aka privratnik (contact with me) degas.developer@gmail.com
 * Date: 16.07.13
 * Time: 13:40
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Forward {

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "pagesPath")
    private String pagesPath;

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getPagesPath() {
        return pagesPath;
    }

    public void setPagesPath(@NotNull String pagesPath) {
        this.pagesPath = pagesPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Forward forward = (Forward) o;

        return name.equals(forward.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
