<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.AmministratoreBean" %>
<%@ page import="java.util.List" %>
<html class="amministratori">
<head>
    <title>Registra Super Admin</title>
    <link href="css/superAdmin.css" rel="stylesheet" type="text/css">
</head>
<body style="background-image: url('img/wp1.png'); background-color: #1d1f2f;">
<%@include file="navBar.jsp" %>
<br>

    <form id="aggiungiAmministratoreForm" action="AmministratoreController?action=aggiungiAmministratore"
          method="post" onsubmit="return validateForm()">
        <br>
        <h1 align="center" style="color: black">Registra i tuoi dati personali</h1>
        <br>
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
            <input type="date" id="dataNascita" name="dataNascita" placeholder="Dammi la data di nascita"
                   onchange="maxDataSelection()" required><br>
        </div>
        <input type="submit" class="bottone1" value="Conferma">
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

        var isEmailValid = false;

        $.ajax({
            url: "AmministratoreController?action=verificaPresenzaEmail",
            method: "POST",
            data: {email: email},
            async: false,
            dataType: 'json',
            success: function (response) {
                if (response.flagPresenza) {
                    alert("Questa email è già registrata. Scegli un'altra email.");
                } else {
                    isEmailValid = true;
                }
            },
            error: function () {
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