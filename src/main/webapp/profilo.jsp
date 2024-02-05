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
<html class="profilo">
<head>
    <title>Profilo</title>
    <link href="css/profilo.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body style="background-image: url('img/wp1.png'); background-attachment: fixed; background-repeat: no-repeat; background-size: cover; background-position: center 150px; background-color: #1d1f2f;">
<%@include file="navBar.jsp" %>
<div id="notification" class="notification">Profilo aggiornato correttamente!</div>
<form id="aggiornaAmministratoreForm" action="AmministratoreController?action=aggiornaAmministratore" method="post" onsubmit="return validateForm(event)">
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
    function validateForm(event) {
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

        if ((nameRegex.test(nome)) && (nameRegex.test(cognome)) && (emailRegex.test(email)) && (passwordRegex.test(password)) ) {
            mostraNotificaProfiloAggiornato(event); // Chiamata alla funzione di notifica
            return true; // Per inviare il modulo
        }

        return false;

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

    // Aggiungi questa funzione per gestire la visualizzazione del div di notifica
    function mostraNotificaProfiloAggiornato(event) {
        event = event || window.event; // Aggiunge questa linea per gestire eventi cross-browser
        if (event) {
            event.preventDefault();
            // Ottieni i dati dalla form
            var formData = $('#aggiornaAmministratoreForm').serialize();

            // Esegui la chiamata AJAX
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/ebalance_war_exploded/AmministratoreController?action=aggiornaAmministratore',
                data: formData,
                success: function (response) {
                    // Dopo aver inviato i dati con successo, mostra il div di notifica.
                    document.getElementById('notification').style.display = 'block';
                    setTimeout(function () {
                        window.location.href = 'http://localhost:8080/ebalance_war_exploded/profilo.jsp';
                    }, 1000);
                },
                error: function (error) {
                    // Gestisci eventuali errori qui, se necessario.
                    console.error('Errore durante l\'invio dei dati:', error);
                }
            });
        }
    }
</script>
</body>

</html>