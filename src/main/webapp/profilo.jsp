<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.amministratoreBean" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%
    List<amministratoreBean> amministratori = (List<amministratoreBean>) request.getAttribute("amministratori");
    if(amministratori == null)
    {
        response.sendRedirect("amministratoreController");
        return;
    }
%>
<html>
<head>
    <title>Profilo</title>
</head>
<body>
    <%@include file="navBar.jsp" %>
    <form action="amministratoreController?action=aggiornaAmministratore" method="post">
        <label>Nome:</label>
        <input type="text" name="nome" value=<%=nome%>"><br>
        <label>Cognome:</label>
        <input type="text" name="cognome" value="<%=cognome%>"><br>
        <label>Email:</label>
        <input type="text" name="email" value="<%=email%>"><br>
        <label>Password:</label>
        <input type="password" name="password" value="<%=password%>"><br>
        <label>Data Nascita:</label>
        <input type="date" name="dataNascita" value="<%=dataNascita%>"><br>
        <input type="hidden" name="idAmministratore" value="<%=idAmministratore%>"><br>
        <input type="hidden" name="flagTipo" value="<%=tipo%>"><br>
        <input type="submit" value="Salva modifiche">
    </form>

    <br>
    <%
        if(tipo!=null){
            if(tipo){
    %>
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
        <%
                }
            }
        }
        %>
        </tbody>
    </table>
</body>
</html>
