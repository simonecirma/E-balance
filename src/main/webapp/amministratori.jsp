<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.AmministratoreBean" %>
<%@ page import="java.util.List" %>
<%
    List<AmministratoreBean> amministratori = (List<AmministratoreBean>) request.getAttribute("amministratori");
%>
<html>
<head>
    <title>Gestione amministratori</title>
    <link href="css/amministratori.css" rel="stylesheet" type="text/css">
    </script>
</head>
<body>
<%@include file="navBar.jsp" %>
<br>
<%
    if(email!=null){
%>
<div class="container" id="container">
<div class="card-container">
    <div id="table-card" class="card">
<table id="tab">
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
            <%if (admin.getFlagTipo())
            {%>
            <td></td>
            <%}
            else{%>
            <td><a href="AmministratoreController?action=rimuoviAmministratore&idAmministratore=<%= admin.getIdAmministratore() %>"><button class="button">Elimina</button></a></td>
            <%}%>
        </tr>
    <% }
    }
    %>
    </tbody>
</table>
    </div>
    <%
    }
    else {
    %>
    </script>
    <h1 align="center">Aggiungi i tuoi dati personali</h1>
    <%
        }
    %>
    <div id="form-card" class="card">
<form id="aggiungiAmministratoreForm" action="AmministratoreController?action=aggiungiAmministratore" method="post">
    <div>
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" placeholder="Dammi il nome" required><br>
    </div>
    <div>
        <label for="cognome">Cognome:</label>
        <input type="text" id="cognome" name="cognome" placeholder="Dammi il cognome" required><br>
    </div>
    <div>
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" placeholder="Dammi l'email" required><br>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" placeholder="Dammi la password" required><br>
    </div>
    <div>
        <label for="dataNascita">Data Nascita:</label>
        <input type="date" id="dataNascita" name="dataNascita" placeholder="Dammi la data di nascita" required><br>
    </div>
    <input type="submit" class="btn1" value="Conferma">
</form>
    </div>
</div>
</div>
<div class="btn-container">
    <button class="btn" onclick="toggleCard()">Aggiungi un nuovo amministratore</button>
</div>
<script>
    var isTableVisible = true; // Stato iniziale della tabella

    function toggleCard() {
        var tableCard = document.getElementById('table-card');
        var formCard = document.getElementById('form-card');

        if (isTableVisible) {
            // Ruota la tabella fuori dalla vista e mostra il form
            tableCard.style.transform = 'rotateY(180deg)';
            tableCard.style.opacity = '0'; // Rendi la tabella trasparente
            formCard.style.transform = 'rotateY(0deg)';
            formCard.style.opacity = '1'; // Rendi il form opaco
        } else {
            // Ruota il form fuori dalla vista e mostra la tabella
            formCard.style.transform = 'rotateY(-180deg)';
            formCard.style.opacity = '0'; // Rendi il form trasparente
            tableCard.style.transform = 'rotateY(0deg)';
            tableCard.style.opacity = '1'; // Rendi la tabella opaca
        }

        isTableVisible = !isTableVisible; // Inverti lo stato della variabile
    }
</script>

</body>
</html>

