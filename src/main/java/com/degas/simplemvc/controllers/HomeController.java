package com.degas.simplemvc.controllers;

import org.jetbrains.annotations.NotNull;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author: Ilya Varlamov aka privratnik (contact with me) degas.developer@gmail.com
 * Date: 16.07.13
 * Time: 0:37
 */
public class HomeController implements Controller{
    @Override
    public @NotNull String processRequest(@NotNull HttpServletRequest req, @NotNull HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("test", "TEST HomeController");
        return "success";
    }
}
