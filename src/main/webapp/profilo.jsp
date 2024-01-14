<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.amministratoreBean" %>
<%@ page import="java.util.List" %>
<% List<amministratoreBean> amministratori = (List<amministratoreBean>) request.getAttribute("amministratori");
    if(amministratori == null)
    {
        response.sendRedirect("amministratoreController");
        return;
    }
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%@include file="navBar.jsp" %>
    <form action="amministratoreController?action=modificaAmministratore" method="post">
        <label>Nome:</label>
        <input type="text" name="nome" value="<%=session.getAttribute("nome") %>"><br>
        <label>Cognome:</label>
        <input type="text" name="cognome" value="<%= session.getAttribute("cognome") %>"><br>
        <label>Email:</label>
        <input type="text" name="email" value="<%=session.getAttribute("email") %>"><br>
        <label>Password:</label>
        <input type="password" name="password" value="<%=session.getAttribute("password") %>"><br>

        <input type="submit" value="Salva modifiche">
    </form>
    <br>
    <table>
        <thead>
        <tr>
            <th>Email</th>
            <th>Nome</th>
            <th>Cognome</th>
        </tr>
        </thead>
        <tbody>
        <% if (amministratori != null && !amministratori.isEmpty()) {
            for (amministratoreBean admin : amministratori) { %>
        <tr>
            <td><%= admin.getEmail() %></td>
            <td><%= admin.getNome() %></td>
            <td><%= admin.getCognome() %></td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="3">Nessun dato disponibile.</td>
        </tr>
        <%= "Numero di amministratori: " + (amministratori == null ? "null" : amministratori.size()) %>
        <% } %>
        </tbody>
    </table>
</body>
</html>
