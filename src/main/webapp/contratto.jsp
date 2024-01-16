<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.ContrattoBean" %>
<%@ page import="java.util.List" %>
<%
    ContrattoBean contratto = (ContrattoBean) request.getAttribute("contratto");
    List<ContrattoBean> contratti = (List<ContrattoBean>) request.getAttribute("contratti");
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
        for (ContrattoBean contr : contratti) { %>

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
    <tr>
        <th>Nome Ente</th>
        <th>Consumo Medio Annuale</th>
        <th>Costo Medio Unitario</th>
    </tr>
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



<button onclick="toggleFormVisibility()">Mostra/Nascondi Form</button>

<script>
    window.onload = function() {
        toggleFormVisibility();
    };
</script>

<form id="aggiungiContrattoForm" action="ContrattoController?action=aggiungiContratto" method="post" style="display: none;">
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



<form id="aggiornaContrattoForm" action="ContrattoController?action=aggiornaContratto" method="post">
    <label>Ente:</label>
    <input type="text" name="nomeEnte" value="<%=contratto != null ? contratto.getNomeEnte() : null%>"><br>
    <label>Consumo Annuale:</label>
    <input type="text" name="consumoMedioAnnuale" value="<%=contratto != null ? contratto.getConsumoMedioAnnuale() : 0%>"><br>
    <label>Costo Unitario:</label>
    <input type="text" name="costoMedioUnitario" value="<%=contratto != null ? contratto.getCostoMedioUnitario() : 0%>"><br>
    <label>Data Sottoscrizione:</label>
    <input type="date" name="dataSottoscrizione" value="<%=contratto != null ? contratto.getDataSottoscrizione() : null%>"><br>
    <label>Durata:</label>
    <input type="text" name="durata" value="<%=contratto != null ? contratto.getDurata() : 0%>"><br>
    <label>Prezzo:</label>
    <input type="text" name="prezzoVendita" value="<%=contratto != null ? contratto.getPrezzoVendita() : 0%>"><br>
    <input type="hidden" name="idContratto" value="<%=contratto != null ? contratto.getIdContratto() : 0%>">
    <input type="hidden" name="idAmministratore" value="<%=idAmministratore%>">
    <input type="submit" value="Conferma modifica">
</form>

</body>
</html>