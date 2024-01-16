<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.AmministratoreBean" %>
<%@ page import="java.util.List" %>
<%
    List<AmministratoreBean> amministratori = (List<AmministratoreBean>) request.getAttribute("amministratori");
%>
<html>
<head>
    <title>Title</title>
    <link href="css/amministratori.css" rel="stylesheet" type="text/css">
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
        <th>Elimina Admin</th>
    </tr>
    </thead>
    <tbody>
    <% if (amministratori != null && !amministratori.isEmpty()) {
        for (AmministratoreBean admin : amministratori) { %>
        <tr>
            <td><%= admin.getEmail() %></td>
            <td><%= admin.getNome() %></td>
            <td><%= admin.getCognome() %></td>
            <td>Elimina</td>
        </tr>
    <% }
    }
    %>
    </tbody>
</table>
<div class="btn-container">
    <button class="btn" onclick="toggleFormVisibility()">Mostra/Nascondi Form</button>
</div>
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
    <div>
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" placeholder="Dammi il nome"><br>
    </div>
    <div>
        <label for="cognome">Cognome:</label>
        <input type="text" id="cognome" name="cognome" placeholder="Dammi il cognome"><br>
    </div>
    <div>
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" placeholder="Dammi l'email"><br>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" placeholder="Dammi la password"><br>
    </div>
    <div>
        <label for="dataNascita">Data Nascita:</label>
        <input type="date" id="dataNascita" name="dataNascita" placeholder="Dammi la data di nascita"><br>
    </div>
    <input type="submit" class="btn1" value="Registra nuovo amministratore">
</form>
</body>
</html>

