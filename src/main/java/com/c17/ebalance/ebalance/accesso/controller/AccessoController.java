package com.c17.ebalance.ebalance.accesso.controller;

import com.c17.ebalance.ebalance.accesso.service.AccessoService;
import com.c17.ebalance.ebalance.accesso.service.accessoServiceImpl;
import com.c17.ebalance.ebalance.model.entity.amministratoreBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "accessoController", value = "/accessoController")
public class AccessoController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AccessoService accessoService = new accessoServiceImpl();



    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        amministratoreBean admin = new amministratoreBean();
        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        try {
            admin = accessoService.login(email, pass);
            if (admin != null) {

                HttpSession session = request.getSession(true);
                session.setAttribute("email", admin.getEmail());
                session.setAttribute("password", admin.getPassword());
                session.setAttribute("nome", admin.getNome());
                session.setAttribute("cognome", admin.getCognome());
                session.setAttribute("flagTipo", admin.getFlagTipo());
                session.setAttribute("idAmministratore", admin.getIdAmministratore());
                session.setAttribute("dataNascita", admin.getDataNascita());
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dashboard.jsp");
                dispatcher.forward(request, response);
            } else {

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }


    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}
