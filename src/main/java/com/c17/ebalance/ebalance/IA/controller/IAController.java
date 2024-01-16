package com.c17.ebalance.ebalance.IA.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "datiController", value = "/datiController")
public class IAController extends HttpServlet {


    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
    }

    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}