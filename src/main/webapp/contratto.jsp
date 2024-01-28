<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.ContrattoBean" %>
<%@ page import="java.util.List" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.AmministratoreBean" %>
<%
    ContrattoBean contratto = (ContrattoBean) request.getAttribute("contratto");
    List<ContrattoBean> contratti = (List<ContrattoBean>) request.getAttribute("contratti");
%>

<html>
<head>
    <title>Contratto</title>
    <link href="css/contratto.css" rel="stylesheet" type="text/css">
    <script>
        function toggleFormVisibility(formId) {
            var form = document.getElementById(formId);
            form.style.display = (form.style.display === 'none' || form.style.display === '') ? 'block' : 'none';
        }
    </script>
</head>
<body>
<%@include file="navBar.jsp" %>
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
        <h1> Contratto attuale</h1>
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
                <td><%= contratto.getNomeEnte() %></td>
                <td><%= contratto.getConsumoMedioAnnuale() %></td>
                <td><%= contratto.getCostoMedioUnitario()%></td>
            </tr>
            </tbody>
        </table>

        <h1> Storico contratti passati</h1>
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
                <td><%= contr.getNomeEnte() %></td>
                <td><%= contr.getConsumoMedioAnnuale() %></td>
                <td><%= contr.getCostoMedioUnitario()%></td>
            </tr>
            <% }
            } %>
            </tbody>
        </table>

        <div class="btn-container">
            <button class="btn" onclick="toggleFormVisibility('aggiornaContrattoForm')">Modifica contratto attuale</button>
            <button class="btn" onclick="toggleFormVisibility('aggiungiContrattoForm')">Aggiungi un nuovo contratto</button>
        </div>


            <form id="aggiornaContrattoForm" action="ContrattoController?action=aggiornaContratto" method="post" style="display: none;">
                <div>
                    <label>Ente:</label>
                    <input type="text" name="nomeEnte" value="<%=contratto != null ? contratto.getNomeEnte() : null%>"><br>
                </div>
                <div>
                    <label>Consumo Annuale:</label>
                    <input type="text" name="consumoMedioAnnuale" value="<%=contratto != null ? contratto.getConsumoMedioAnnuale() : 0%>"><br>
                </div>
                <div>
                    <label>Costo Unitario:</label>
                    <input type="text" name="costoMedioUnitario" value="<%=contratto != null ? contratto.getCostoMedioUnitario() : 0%>"><br>
                </div>
                <div>
                    <label>Data Sottoscrizione:</label>
                    <input type="date" name="dataSottoscrizione" value="<%=contratto != null ? contratto.getDataSottoscrizione() : null%>"><br>
                </div>
                <div>
                    <label>Durata:</label>
                    <input type="text" name="durata" value="<%=contratto != null ? contratto.getDurata() : 0%>"><br>
                </div>
                <div>
                    <label>Prezzo:</label>
                    <input type="text" name="prezzoVendita" value="<%=contratto != null ? contratto.getPrezzoVendita() : 0%>"><br>
                </div>
                <input type="hidden" name="idContratto" value="<%=contratto != null ? contratto.getIdContratto() : 0%>">
                <input type="hidden" name="idAmministratore" value="<%=idAmministratore%>">
                <div class="btnCont">
                    <input type="submit" class="btn1" value="Conferma modifica">
                </div>
             </form>

        <form id="aggiungiContrattoForm" action="ContrattoController?action=aggiungiContratto" method="post" style="display: none;">
            <div>
                <label for="ente">Ente:</label>
                <input type="text" name="nomeEnte" id="ente" placeholder="Dammi il nome ente"><br>
            </div>
            <div>
                <label for="consumo">Consumo Annuale:</label>
                <input type="text" name="consumoMedioAnnuale" id ="consumo" placeholder="Dammi il consumo medio annuale"><br>
            </div>
            <div>
                <label for="costo">Costo Unitario:</label>
                <input type="text" name="costoMedioUnitario" id="costo" placeholder="Dammi il costo medio unitario"><br>
            </div>
            <div>
                <label for="data">Data Sottoscrizione:</label>
                <input type="date" name="dataSottoscrizione" id="data" placeholder="Dammi la data di sottoscrizione"><br>
            </div>
            <div>
                <label for="durata">Durata:</label>
                <input type="text" name="durata" id="durata" placeholder="Dammi la durata del contratto"><br>
            </div>
            <div>
                <label for="prezzo">Prezzo:</label>
                <input type="text" name="prezzoVendita" id="prezzo" placeholder="Dammi il prezzo di vendita"><br>
            </div>
            <input type="hidden" name="idAmministratore" value="<%=idAmministratore%>">
            <div class="btnCont">
                <input type="submit" class="btn1" value="Registra nuovo contratto">
            </div>
        </form>
    </div>
</div>
<br>
<%
} else {
%>
<script>
    window.onload = function() {
        toggleFormVisibility('aggiungiContrattoForm');
    };
</script>
<h1>Aggiungi i dati del contratto</h1>
<!-- Resto del codice per il form di aggiunta -->
<%
    }
%>

</body>
</html>
