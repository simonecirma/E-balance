<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.ContrattoBean" %>
<%@ page import="java.util.List" %>

<%
    Integer resultAttribute = (Integer) request.getAttribute("result");

    ContrattoBean contratto = (ContrattoBean) request.getAttribute("contratto");
    List<ContrattoBean> contratti = (List<ContrattoBean>) request.getAttribute("contratti");
    synchronized (session) {
        session = request.getSession();
        idAmministratore = (int) session.getAttribute("idAmministratore");
    }
%>

<html class="contratto">
<head>
    <title>Contratto</title>
    <link href="css/contratto.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script>
        function validateForm(formId) {
            var form = document.getElementById(formId);
            var nomeEnte = form.querySelector('input[name="nomeEnte"]').value.trim();
            var dataSottoscrizione = new Date(form.querySelector('input[name="dataSottoscrizione"]').value);
            var costoMedioUnitario = parseFloat(form.querySelector('input[name="costoMedioUnitario"]').value);
            var consumoMedioAnnuale = parseFloat(form.querySelector('input[name="consumoMedioAnnuale"]').value);
            var durata = parseFloat(form.querySelector('input[name="durata"]').value);

            // Controllo Nome Ente
            if (nomeEnte.length < 1 || nomeEnte.length > 244) {
                alert("Il nome dell'ente deve essere compreso tra 1 e 244 caratteri.");
                return false;
            }

            // Controllo Data Sottoscrizione
            var oggi = new Date();
            var domani = new Date(oggi);
            domani.setDate(oggi.getDate() + 1);
            today.setHours(0, 0, 0, 0);
            if (dataSottoscrizione > domani) {
                alert("La data di sottoscrizione non può essere successiva a quella odierna.");
                return false;
            }

            // Controllo Costo Medio Unitario
            if (costoMedioUnitario <= 0) {
                alert("Il costo medio unitario deve essere maggiore di zero.");
                return false;
            }

            // Controllo Consumo Medio Annuale
            if (consumoMedioAnnuale <= 0) {
                alert("Il consumo medio annuale deve essere maggiore di zero.");
                return false;
            }

            // Controllo Durata
            if (durata <= 0) {
                alert("La durata deve essere maggiore di zero.");
                return false;
            }

            if (formId === 'aggiornaContrattoForm')
            {
                gestisciAggiornaContratto(event);
            } else  if (formId === 'aggiungiContrattoForm') {
                gestisciAggiuntaContratto(event)
            }

            // Se tutti i controlli passano, restituisci true per consentire l'invio del modulo
            return true;
        }

        function toggleFormVisibility(formId) {
            var form = document.getElementById(formId);
            var otherFormId = formId === 'aggiornaContrattoForm' ? 'aggiungiContrattoForm' : 'aggiornaContrattoForm';
            var otherForm = document.getElementById(otherFormId);

            form.style.display = (form.style.display === 'none' || form.style.display === '') ? 'block' : 'none';
            otherForm.style.display = 'none'; // Nasconde l'altro form

            var container = document.querySelector('.container');
            var tables = document.querySelector('.tables');
            var btn = document.querySelector('.btn-container')
            if (form.style.display === 'block') {
                container.style.width = '1000px';
                tables.style.width = '50%';
                tables.style.marginTop = '-70px';
                btn.style.marginTop ='-30px';
            } else {
                container.style.width = '600px';
                tables.style.width = '100%';
                tables.style.marginTop = '';
                btn.style.marginTop ='';
            }
        }

        function gestisciAggiornaContratto(event) {
                event = event || window.event; // Aggiunge questa linea per gestire eventi cross-browser
                if (event) {
                    event.preventDefault();
                    // Ottieni i dati dalla form
                    var formData = $('#aggiornaContrattoForm').serialize();

                    // Esegui la chiamata AJAX
                    $.ajax({
                        type: 'POST',
                        url: 'ContrattoController?action=aggiornaContratto',
                        data: formData,
                        success: function (response) {
                            // Dopo aver inviato i dati con successo, mostra il div di notifica.
                            document.getElementById('notification').style.display = 'block';
                        },
                        error: function (error) {
                            // Gestisci eventuali errori qui, se necessario.
                            console.error('Errore durante l\'invio dei dati:', error);
                        }
                    });
                    setTimeout(function () {
                        location.reload(); // Aggiorna la pagina
                    }, 1000);
                }
        }

        function gestisciAggiuntaContratto(event) {
            event = event || window.event; // Aggiunge questa linea per gestire eventi cross-browser
            if (event) {
                event.preventDefault();
                // Ottieni i dati dalla form
                var formData = $('#aggiungiContrattoForm').serialize();

                // Esegui la chiamata AJAX
                $.ajax({
                    type: 'POST',
                    url: 'ContrattoController?action=aggiungiContratto',
                    data: formData,
                    success: function (response) {
                        // Dopo aver inviato i dati con successo, mostra il div di notifica.
                        document.getElementById('notification2').style.display = 'block';
                    },
                    error: function (error) {
                        // Gestisci eventuali errori qui, se necessario.
                        console.error('Errore durante l\'invio dei dati:', error);
                    }
                });
                setTimeout(function () {
                    location.reload(); // Aggiorna la pagina
                }, 1000);
            }
        }
    </script>
</head>
<body style="background-image: url('img/wp1.png'); background-repeat: no-repeat; background-size: cover; background-position: center 150px; background-color: #1d1f2f; background-attachment: fixed;">
<%@include file="navBar.jsp" %>
<div id="notification" class="notification">Contratto aggiornato correttamente!</div>
<div id="notification2" class="notification2">Contratto aggiunto correttamente!</div>
<br>
<%
    if (contratto != null) {
%>
<div class="principale">
    <div class="container">
        <div class="icon-container">
            <div class="icon">
                <img src="img/contratto.png">
            </div>
        </div>
        <div class="tables">
        <h1> Contratto attuale</h1>
        <br>
        <table>
            <thead>
            <tr>
                <th>Nome Ente</th>
                <th>Consumo Medio Annuale</th>
                <th>Costo Medio Unitario</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><%= contratto.getNomeEnte() %>
                </td>
                <td><%= contratto.getConsumoMedioAnnuale() %>
                </td>
                <td><%= contratto.getCostoMedioUnitario()%>
                </td>
            </tr>
            </tbody>
        </table>
        <br>
        <h1> Storico contratti passati</h1>
        <br>
        <div class="tabellaStorico">
            <table>
                <thead>
                <tr>
                    <th>Nome Ente</th>
                    <th>Consumo Medio Annuale</th>
                    <th>Costo Medio Unitario</th>
                </tr>
                </thead>
                <tbody>
                <% if (contratti != null && !contratti.isEmpty()) {
                    for (ContrattoBean contr : contratti) { %>
                <tr>
                    <td><%= contr.getNomeEnte() %>
                    </td>
                    <td><%= contr.getConsumoMedioAnnuale() %>
                    </td>
                    <td><%= contr.getCostoMedioUnitario()%>
                    </td>
                </tr>
                <% }
                } %>
                </tbody>
            </table>
        </div>
        </div>

        <form id="aggiornaContrattoForm" action="ContrattoController?action=aggiornaContratto" method="post"
              style="display: none;"  onsubmit="return validateForm('aggiornaContrattoForm')">
            <div>
                <label>Ente:</label>
                <input type="text" name="nomeEnte" value="<%=contratto != null ? contratto.getNomeEnte() : null%>"
                       required><br>
            </div>
            <div>
                <label>Consumo Annuale:</label>
                <input type="number" name="consumoMedioAnnuale" min=0 step="any"
                       value="<%=contratto != null ? contratto.getConsumoMedioAnnuale() : 0%>" required><br>
            </div>
            <div>
                <label>Costo Unitario:</label>
                <input type="number" name="costoMedioUnitario" min=0 step="any"
                       value="<%=contratto != null ? contratto.getCostoMedioUnitario() : 0%>" required><br>
            </div>
            <div>
                <label>Data Sottoscrizione:</label>
                <input type="date" name="dataSottoscrizione"
                       value="<%=contratto != null ? contratto.getDataSottoscrizione() : null%>" required><br>
            </div>
            <div>
                <label>Durata:</label>
                <input type="number" name="durata" min=0 step="any"
                       value="<%=contratto != null ? contratto.getDurata() : 0%>" required><br>
            </div>
            <div>
                <label>Prezzo Vendita:</label>
                <input type="number" name="prezzoVendita" min=0 step="any"
                       value="<%=contratto != null ? contratto.getPrezzoVendita() : 0%>" required><br>
            </div>
            <input type="hidden" name="idContratto" value="<%=contratto != null ? contratto.getIdContratto() : 0%>">
            <input type="hidden" name="idAmministratore" value="<%=idAmministratore%>">
            <div class="btnCont">
                <input type="submit" class="btn1" value="Conferma modifica" >
            </div>
        </form>

        <form id="aggiungiContrattoForm" action="ContrattoController?action=aggiungiContratto" method="post"
              style="display: none;" onsubmit="return validateForm('aggiungiContrattoForm')">
            <div>
                <label for="ente">Ente:</label>
                <input type="text" name="nomeEnte" id="ente" placeholder="Dammi il nome ente" required><br>
            </div>
            <div>
                <label for="consumo">Consumo Annuale:</label>
                <input type="number" name="consumoMedioAnnuale" min=0 step="any" id="consumo"
                       placeholder="Dammi il consumo annuale" required><br>
            </div>
            <div>
                <label for="costo">Costo Unitario:</label>
                <input type="number" name="costoMedioUnitario" min=0 step="any" id="costo"
                       placeholder="Dammi il costo medio unitario" required><br>
            </div>
            <div>
                <label for="data">Data Sottoscrizione:</label>
                <input type="date" name="dataSottoscrizione" id="data" placeholder="Dammi la data di sottoscrizione"
                       required><br>
            </div>
            <div>
                <label for="durata">Durata:</label>
                <input type="number" name="durata" min=0 step="any" id="durata"
                       placeholder="Dammi la durata del contratto" required><br>
            </div>
            <div>
                <label for="prezzo">Prezzo Vendita:</label>
                <input type="number" name="prezzoVendita" min=0 step="any" step="any" id="prezzo"
                       placeholder="Dammi il prezzo di vendita" required><br>
            </div>
            <input type="hidden" name="idAmministratore" value="<%=idAmministratore%>">
            <div class="btnCont">
                <input type="submit" class="btn1" value="Registra nuovo contratto">
            </div>
        </form>
        <div class="btn-container">
            <button class="btn" onclick="toggleFormVisibility('aggiornaContrattoForm')">Modifica contratto attuale
            </button>
            <button class="btn" onclick="toggleFormVisibility('aggiungiContrattoForm')">Aggiungi un nuovo contratto
            </button>
        </div>
    </div>
</div>
<br>
<%
}
%>

</body>
</html>