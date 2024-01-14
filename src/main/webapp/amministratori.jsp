<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.amministratoreBean" %>
<%@ page import="java.util.List" %>
<%
    List<amministratoreBean> amministratori = (List<amministratoreBean>) request.getAttribute("amministratori");
%>
<html>
<head>
    <title>Title</title>
    <script>
        function toggleFormVisibility() {
            var form = document.getElementById('aggiungiAmministratoreForm');
            form.style.display = (form.style.display === 'none' || form.style.display === '') ? 'block' : 'none';
        }
    </script>
</head>
<body>
<%@include file="navBar.jsp" %>
<br>
<%
    if(email!=null){
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
    }
    %>
    </tbody>
</table>
<button onclick="toggleFormVisibility()">Mostra/Nascondi Form</button>

    <%
    }
    else {
    %>
    <script>
        window.onload = function() {
            toggleFormVisibility();
        };
    </script>
    <%
        }
    %>

<form id="aggiungiAmministratoreForm" action="amministratoreController?action=aggiungiAmministratore" method="post" style="display: none;">
    <label>Nome:</label>
    <input type="text" name="nome" placeholder="Dammi il nome"><br>
    <label>Cognome:</label>
    <input type="text" name="cognome" placeholder="Dammi il cognome"><br>
    <label>Email:</label>
    <input type="text" name="email" placeholder="Dammi l'email"><br>
    <label>Password:</label>
    <input type="password" name="password" placeholder="Dammi la password"><br>
    <label>Data Nascita:</label>
    <input type="date" name="dataNascita" placeholder="Dammi la data di nascita"><br>
    <input type="submit" value="Registra nuovo amministratore">
</form>
</body>
</html>

