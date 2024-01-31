<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.AmministratoreBean" %>
<%@ page import="java.util.List" %>
<%
    synchronized (session) {
        session = request.getSession();
        nome = (String) session.getAttribute("nome");
        cognome = (String) session.getAttribute("cognome");
        email = (String) session.getAttribute("email");
        password = (String) session.getAttribute("password");
        dataNascita = (Date) session.getAttribute("dataNascita");
        idAmministratore = (int) session.getAttribute("idAmministratore");
        tipo = (boolean) session.getAttribute("flagTipo");
    }
%>
<html>
<head>
    <title>Profilo</title>
    <link href="css/profilo.css" rel="stylesheet" type="text/css">
</head>
<body style="background-image: url('img/wp1.jpg'); background-attachment: fixed; background-repeat: no-repeat; background-size: cover; background-position: center 150px; background-color: #f6f6f6;">
<%@include file="navBar.jsp" %>
<form action="AmministratoreController?action=aggiornaAmministratore" method="post" onsubmit="return validateForm()">
    <div class="avatar">
        <img src="img/avatar.png">
    </div>
    <div>
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="<%=nome%>" required><br>
    </div>
    <div>
        <label for="cognome">Cognome:</label>
        <input type="text" id="cognome" name="cognome" value="<%=cognome%>" required><br>
    </div>
    <div>
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" value="<%=email%>" required><br>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value="<%=password%>" required><br>
    </div>
    <div>
        <label for="dataNascita">Data Nascita:</label>
        <input type="date" id="dataNascita" name="dataNascita" value="<%=dataNascita%>" onchange="maxDataSelection()"
               required><br>
    </div>
    <input type="hidden" name="idAmministratore" value="<%=idAmministratore%>" required><br>
    <input type="hidden" name="flagTipo" value="<%=tipo%>"><br>
    <input type="submit" value="Salva modifiche">
</form>
<script>
    function validateForm() {
        var nome = document.getElementById("nome").value;
        var cognome = document.getElementById("cognome").value;
        var email = document.getElementById("email").value;
        var password = document.getElementById("password").value;

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

        // Validazione della password
        var passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;
        if (!passwordRegex.test(password)) {
            alert("La password deve essere di almeno 8 caratteri e contenere almeno una minuscola, una maiuscola, un numero e un carattere speciale.");
            return false;
        }


        return true;

    }

    function maxDataSelection() {
        // Ottieni la data corrente
        var today = new Date();

        // Estrai l'anno, il mese e il giorno
        var year = today.getFullYear();
        // Aggiunge uno zero se il mese è inferiore a 10
        var month = String(today.getMonth() + 1).padStart(2, '0');
        // Aggiunge uno zero se il giorno è inferiore a 10
        var day = String(today.getDate()).padStart(2, '0');

        // Crea la stringa della data nel formato "YYYY-MM-DD"
        var maxDate = year + '-' + month + '-' + day;

        // Ottieni l'elemento input di tipo data
        var datePicker = document.getElementById("dataNascita");

        // Imposta la data massima consentita per la selezione
        datePicker.max = maxDate;

    }
</script>
</body>

</html>