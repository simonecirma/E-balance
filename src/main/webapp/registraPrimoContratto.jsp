<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.ContrattoBean" %>
<%@ page import="java.util.List" %>
<html class="contratto">
<head>
    <title>Contratto</title>
    <link href="css/contratto.css" rel="stylesheet" type="text/css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body style="background-image: url('img/wp1.png'); background-repeat: no-repeat; background-size: cover; background-position: center 150px; background-color: #1d1f2f; background-attachment: fixed;">
<%@include file="navBar.jsp" %>
    <form id="aggiungiContrattoForm" action="ContrattoController?action=aggiungiContratto" method="post">
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
<br>


</body>
</html>