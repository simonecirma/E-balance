<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.c17.ebalance.ebalance.model.entity.AmministratoreBean" %>
<%@ page import="java.util.List" %>
<%
    List<AmministratoreBean> amministratori = (List<AmministratoreBean>) request.getAttribute("amministratori");
    synchronized(session)
    {
        session = request.getSession();
        email = (String) session.getAttribute("email");
    }
%>
<html>
<head>
    <title>Gestione amministratori</title>
    <link href="css/amministratori.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body style="background-image: url('img/wp1.png'); background-repeat: no-repeat; background-size: cover; background-position: center 150px; background-color: #1d1f2f; background-attachment: fixed;">
<%@include file="navBar.jsp" %>
<br>
<%
    if(email!=null){
%>
<div class="container" id="container">
    <div class="card-container" id="cont">
        <div id="table-card" class="card">
            <table id="tab">
                <thead>
                <tr id="header">
                    <th>Email</th>
                    <th>Nome</th>
                    <th>Cognome</th>
                    <th>Elimina Admin</th>
                </tr>

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
                    <input type="date" id="dataNascita" name="dataNascita" placeholder="Dammi la data di nascita"  required ><br>
                </div>
                <input type="submit" class="btn1" value="Conferma">
            </form>
        </div>
    </div>
    <div class="btn-container">
        <button class="btn" onclick="toggleCard()">Aggiungi un nuovo amministratore</button>
    </div>
</div>

<script>
    var isTableVisible = true; // Stato iniziale della tabella

    function toggleCard() {
        var tableCard = document.getElementById('table-card');
        var formCard = document.getElementById('form-card');
        var button = document.querySelector('.btn');
        if (button.textContent === "Aggiungi un nuovo amministratore") {
            button.textContent = "Indietro";
        } else {
            button.textContent = "Aggiungi un nuovo amministratore";
        }
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

    function validateForm() {
        var nome = document.getElementById("nome").value;
        var cognome = document.getElementById("cognome").value;
        var email = document.getElementById("email").value;
        var password = document.getElementById("password").value;
        var dataNascita = document.getElementById("dataNascita").value;

        // Validazione della lunghezza del nome e cognome
        if (nome.length < 1 || nome.length > 255) {
            alert("Il nome deve avere una lunghezza compresa tra 1 e 255 caratteri.");
            return false;
        }

        if (cognome.length < 1 || cognome.length > 255) {
            alert("Il cognome deve avere una lunghezza compresa tra 1 e 255 caratteri.");
            return false;
        }

        // Validazione della data di nascita
        var today = new Date();
        var minDate = new Date(today.getFullYear() - 16, today.getMonth(), today.getDate()); // 16 anni fa
        var selectedDate = new Date(dataNascita);

        if (selectedDate >= today || selectedDate >= minDate) {
            alert("La data di nascita deve essere precedente a 16 anni fa.");
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
            async: false,
            dataType: 'json',
            success: function(response) {
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

        return true;

    }

    function aggiornaAltezzaContainer() {
        var tabella = document.getElementById('table-card');
        var container = document.getElementById('cont');

        if (tabella && container) {
            var altezzaTabella = tabella.clientHeight;
            container.style.height = altezzaTabella + 'px';
        }
    }

    document.addEventListener('DOMContentLoaded', function() {
        aggiornaAltezzaContainer();
    });


    window.addEventListener('resize', function() {
        aggiornaAltezzaContainer();
    });
    var maxMarginTop = 50;


    var container = document.getElementById('container');


    var windowHeight = window.innerHeight;
    var containerHeight = container.clientHeight;
    var marginTop = Math.min((windowHeight - containerHeight) / 2, maxMarginTop);

    container.style.marginTop = marginTop + 'px';
</script>

</body>
</html>