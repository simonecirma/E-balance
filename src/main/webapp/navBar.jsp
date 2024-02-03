<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    Boolean tipo;
    String email = null;
    String nome = "";
    String cognome = "";
    Date dataNascita;
    String password = "";
    int idAmministratore;
%>

<%
    synchronized (session) {
        session = request.getSession();
        if (session.getAttribute("idAmministratore") != null) {
            tipo = (Boolean) session.getAttribute("flagTipo");
            idAmministratore = (int) session.getAttribute("idAmministratore");
            cognome = (String) session.getAttribute("cognome");
            nome = (String) session.getAttribute("nome");
            password = (String) session.getAttribute("password");
            email = (String) session.getAttribute("email");
            dataNascita = (Date) session.getAttribute("dataNascita");
        }
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>GameCenter</title>
    <link href="css/navBar.css" rel="stylesheet" type="text/css">
</head>
<body>
<nav class="navbar">
    <div class="navdiv">
        <% if (email != null) { %>
        <a href="DatiController?action=generaDashboard">
            <img src="img/Logo.png" alt="Home" width="110" height="110" style="margin-left: 20px;">
        </a>
            <div class="nav-buttons">
        <ul>
            <li><a href="profilo.jsp">
                <button class="btn17"><p>Profilo</p></button>
            </a></li>
            <li><a href="ContrattoController">
                <button class="btn17"><p>Contratto</p></button>
            </a></li>
            <li><a href="AmministratoreController?action=vediReport">
                <button class="btn17"><p>Report</p></button>
            </a></li>
            <% if (tipo) { %>
            <li><a href="AmministratoreController?action=gestisciAmministratori">
                <button class="btn17"><p>Gestisci</p></button>
            </a></li>
            <% } %>
            <li>
            </div>
            <a href="LogoutController">
                <button class="btnLogout">
                    <div class="sign">
                        <svg viewBox="0 0 512 512">
                            <path d="M377.9 105.9L500.7 228.7c7.2 7.2 11.3 17.1 11.3 27.3s-4.1 20.1-11.3 27.3L377.9 406.1c-6.4 6.4-15 9.9-24 9.9c-18.7 0-33.9-15.2-33.9-33.9l0-62.1-128 0c-17.7 0-32-14.3-32-32l0-64c0-17.7 14.3-32 32-32l128 0 0-62.1c0-18.7 15.2-33.9 33.9-33.9c9 0 17.6 3.6 24 9.9zM160 96L96 96c-17.7 0-32 14.3-32 32l0 256c0 17.7 14.3 32 32 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32l-64 0c-53 0-96-43-96-96L0 128C0 75 43 32 96 32l64 0c17.7 0 32 14.3 32 32s-14.3 32-32 32z">
                            </path>
                        </svg>
                    </div>
                    <div class="text1">Logout</div>
                </button>
            </a>
            </li>
        </ul>

    </div>

    <% } else { %>
    <a href="amministratori.jsp">
        <img src="img/Logo.png" alt="Home" width="110" height="110">
    </a>
    <% } %>
</nav>
</body>
</html>
