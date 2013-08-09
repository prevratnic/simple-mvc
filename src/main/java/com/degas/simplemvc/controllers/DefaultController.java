package com.degas.simplemvc.controllers;

import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Ilya Varlamov aka privratnik (contact with me) degas.developer@gmail.com
 * Date: 31.07.13
 * Time: 0:00
 */
public class DefaultController implements Controller {
    @Override
    public @NotNull String processRequest(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {
        return "empty";
    }
}
