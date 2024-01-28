<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.c17.ebalance.ebalance.model.entity.AmministratoreBean" %>
<%@ page import="java.util.List" %>
<%
    List<AmministratoreBean> amministratori = (List<AmministratoreBean>) request.getAttribute("amministratori");
    synchronized(session)
    {
        session = request.getSession();
        email = (String)session.getAttribute("email");
    }
%>
<html>
<head>
    <title>Gestione amministratori</title>
    <link href="css/amministratori.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body style="background-image: url('img/wp1.jpg'); background-color: #f6f6f6;">
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
    <h1 align="center">Aggiungi i tuoi dati personali</h1>
    <%
        }
    %>
    <div id="form-card" class="card">
<form id="aggiungiAmministratoreForm" action="AmministratoreController?action=aggiungiAmministratore" method="post" onsubmit="return validateForm()">
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
<script>
    function validateForm() {
        var nome = document.getElementById("nome").value;
        var cognome = document.getElementById("cognome").value;
        var email = document.getElementById("email").value;
        var password = document.getElementById("password").value;
        var dataNascita = document.getElementById("dataNascita").value;

        // Validazione del nome e cognome (solo caratteri alfabetici)
        var nameRegex = /^[a-zA-Z\s]+$/;
        if (!nameRegex.test(nome)) {
            alert("Il nome deve contenere solo caratteri alfabetici.");
            return false;
        }

        if (!nameRegex.test(cognome)) {
            alert("Il cognome deve contenere solo caratteri alfabetici.");
            return false;
        }

        // Validazione dell'email
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email)) {
            alert("Inserisci un indirizzo email valido.");
            return false;
        }

        var isEmailValid = false;

        $.ajax({
            url: "AmministratoreController?action=verificaPresenzaEmail",
            method: "POST",
            data: { email: email },
            dataType: 'json',
            success: function(response) {
                console.log("in SUCCESSO");
                if (response.flagPresenza) {
                    alert("Questa email è già registrata. Scegli un'altra email.");
                } else {
                    isEmailValid = true;
                }
            },
            error: function() {
                alert("Si è verificato un errore nella verifica dell'email.");
            }
        });

        if (!isEmailValid) {
            return false;
        }

        // Validazione della password
        var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;
        if (!passwordRegex.test(password)) {
            alert("La password deve essere di almeno 8 caratteri e contenere almeno una minuscola, una maiuscola, un numero e un carattere speciale.");
            return false;
        }

        // Validazione della data di nascita
        var currentDate = new Date();
        var selectedDate = new Date(dataNascita);

        if (selectedDate >= currentDate) {
            alert("La data di nascita deve essere antecedente alla data odierna.");
            return false;
        }

        return true;
    }
</script>

</body>
</html>

