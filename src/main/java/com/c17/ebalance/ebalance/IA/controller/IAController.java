package com.c17.ebalance.ebalance.IA.controller;

import com.c17.ebalance.ebalance.IA.service.IAService;
import com.c17.ebalance.ebalance.IA.service.IAServiceImpl;
import com.c17.ebalance.ebalance.model.entity.InteragisceBean;
import com.c17.ebalance.ebalance.model.entity.ParametriIABean;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "IAController", value = "/IAController")
public class IAController extends HttpServlet {
    private IAService IAService = new IAServiceImpl();


    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        try {
            List<ParametriIABean> parametriIA = ottieniParametri();
            List<InteragisceBean> interazioneParametri = ottieniInterazioneParametri();
            List<InteragisceBean> parametriAttivi = ottieniInterazioneParametri();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public List<ParametriIABean> ottieniParametri() throws SQLException {
        return IAService.visualizzaParametri();
    }
    public List<InteragisceBean> ottieniInterazioneParametri() throws SQLException {
        return IAService.visualizzaInterazioneParametri();
    }

    public List<InteragisceBean> ottieniParametriAttivi() throws SQLException {
        return IAService.ottieniParametriAttivi();
    }
    public void destroy() {
    }
}