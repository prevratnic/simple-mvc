package com.degas.simplemvc.controllers;

import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Ilya Varlamov aka privratnik (contact with me) degas.developer@gmail.com
 * Date: 18.07.13
 * Time: 23:29
 */
public interface Controller {
    public @NotNull String processRequest(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException;
}
