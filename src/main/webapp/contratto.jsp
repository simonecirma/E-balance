<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.contrattoBean" %>
<%@ page import="java.util.List" %>
<%
    contrattoBean contratto = (contrattoBean) request.getAttribute("contratto");
    List<contrattoBean> contratti = (List<contrattoBean>) request.getAttribute("contratti");
%>

<html>
<head>
    <title>Title</title>
    <script>
        function toggleFormVisibility() {
            var form = document.getElementById('aggiungiContrattoForm');
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
        <th>Nome Ente</th>
        <th>Consumo Medio Annuale</th>
        <th>Costo Medio Unitario</th>
    </tr>
    </thead>
    <tbody>
    <% if (contratti != null && !contratti.isEmpty()) {
        for (contrattoBean contr : contratti) { %>

    <tr>
        <td><%= contr.getNomeEnte() %></td>
        <td><%= contr.getConsumoMedioAnnuale() %></td>
        <td><%= contr.getCostoMedioUnitario()%></td>
    </tr>
    <%   }
    }
    }
    %>
    </tbody>

    <tbody>
    <% if(contratto!=null){%>

    <tr>
        <td><%= contratto.getNomeEnte() %></td>
        <td><%= contratto.getConsumoMedioAnnuale() %></td>
        <td><%= contratto.getCostoMedioUnitario()%></td>
    </tr>
    <%}
    %>
    </tbody>


</table>

<form id="redirectForm">
    <select id="actionSelector">
        <option value="vediContrattoCorrente">Contratto Corrente</option>
        <option value="vediStorico">Storico</option>
    </select>
    <button type="button" onclick="redirect()">Vai</button>
</form>

<script>
    function redirect() {
        var selectedAction = document.getElementById('actionSelector').value;
        if (selectedAction === 'vediContrattoCorrente' || selectedAction === 'vediStorico') {
            window.location.href = 'contrattoController?action=' + selectedAction;
        }
    }
</script>

<button onclick="toggleFormVisibility()">Mostra/Nascondi Form</button>

<script>
    window.onload = function() {
        toggleFormVisibility();
    };
</script>

<form id="aggiungiContrattoForm" action="contrattoController?action=aggiungiContratto" method="post" style="display: none;">
    <label>Ente:</label>
    <input type="text" name="nomeEnte" placeholder="Dammi il nome ente"><br>
    <label>Consumo Annuale:</label>
    <input type="text" name="consumoMedioAnnuale" placeholder="Dammi il consumo medio annuale"><br>
    <label>Costo Unitario:</label>
    <input type="text" name="costoMedioUnitario" placeholder="Dammi il costo medio unitario"><br>
    <label>Data Sottoscrizione:</label>
    <input type="date" name="dataSottoscrizione" placeholder="Dammi la data di sottoscrizione"><br>
    <label>Durata:</label>
    <input type="text" name="durata" placeholder="Dammi la durata del contratto"><br>
    <label>Prezzo:</label>
    <input type="text" name="prezzoVendita" placeholder="Dammi il prezzo di vendita"><br>
    <input type="hidden" name="idAmministratore" value="<%=idAmministratore%>">
    <input type="submit" value="Registra nuovo contratto">
</form>

<form id="aggiornaContrattoForm" action="contrattoController?action=aggiornaContratto" method="post" style="display: none;">
    <label>Ente:</label>
    <input type="text" name="nomeEnte" value="<%=contratto.getNomeEnte()%>"><br>
    <label>Consumo Annuale:</label>
    <input type="text" name="consumoMedioAnnuale" value="<%=contratto.getConsumoMedioAnnuale()%>"><br>
    <label>Costo Unitario:</label>
    <input type="text" name="costoMedioUnitario" value="<%=contratto.getCostoMedioUnitario()%>"><br>
    <label>Data Sottoscrizione:</label>
    <input type="date" name="dataSottoscrizione" value="<%=contratto.getDataSottoscrizione()%>"><br>
    <label>Durata:</label>
    <input type="text" name="durata" value="<%=contratto.getDurata()%>"><br>
    <label>Prezzo:</label>
    <input type="text" name="prezzoVendita" value="<%=contratto.getPrezzoVendita()%>"><br>
    <input type="hidden" name="idContratto" value="<%=contratto.getIdContratto()%>">
    <input type="hidden" name="idAmministratore" value="<%=idAmministratore%>">
    <input type="submit" value="Conferma modifica">
</form>

</body>
</html>