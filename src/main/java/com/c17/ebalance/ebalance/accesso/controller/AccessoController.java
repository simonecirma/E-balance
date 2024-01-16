package com.c17.ebalance.ebalance.accesso.controller;

import com.c17.ebalance.ebalance.accesso.service.AccessoService;
import com.c17.ebalance.ebalance.accesso.service.AccessoServiceImpl;
import com.c17.ebalance.ebalance.model.entity.AmministratoreBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "AccessoController", value = "/AccessoController")
public class AccessoController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static AccessoService accessoService = new AccessoServiceImpl();


    public void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {

        AmministratoreBean amministratore = new AmministratoreBean();
        String email = request.getParameter("email");
        String pass = request.getParameter("password");
        HttpSession session = request.getSession(true);
        try {
            amministratore = login(email, pass, session);
            if (amministratore != null) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dashboard.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("result", "Credenziali sbagliate, riprova!");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }


    }

    public void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    public static AmministratoreBean login(final String email, final String pass, final HttpSession session) throws SQLException {
        AmministratoreBean admin = new AmministratoreBean();
        admin = accessoService.login(email, pass);
        if (admin != null) {
            session.setAttribute("email", admin.getEmail());
            session.setAttribute("password", admin.getPassword());
            session.setAttribute("nome", admin.getNome());
            session.setAttribute("cognome", admin.getCognome());
            session.setAttribute("flagTipo", admin.getFlagTipo());
            session.setAttribute("idAmministratore", admin.getIdAmministratore());
            session.setAttribute("dataNascita", admin.getDataNascita());
        }
        return admin;
    }

    public void destroy() {
    }
}
