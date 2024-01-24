<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.ReportBean" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.AmministratoreBean" %>
<%@ page import="java.util.List" %>
<%
    List<ReportBean> listReport = (List<ReportBean>) request.getAttribute("listReport");
    List<AmministratoreBean> amministratori = (List<AmministratoreBean>) request.getAttribute("amministratori");
%>
<html>
<head>
    <title>Report</title>
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
            <th>Data Emissione</th>
            <th>Amministratore</th>
            <th>Visualizza</th>
        </tr>
        </thead>
        <tbody>
        <% if (listReport != null && !listReport.isEmpty()) {
            for (int i = 0; i < listReport.size(); i++) { %>
                <% AmministratoreBean bean = new AmministratoreBean();
                   ReportBean rep = new ReportBean();
                   rep = listReport.get(i);
                   bean = amministratori.get(i);%>
        <tr>
            <td><%= rep.getDataEmissione() %></td>
            <td><%= bean.getNome() %> <%=bean.getCognome()%></td>
            <td><a href="report\<%=rep.getNomeReport()%>" target="_blank">Apri</a></td>
        </tr>
        <%   }
            }
        }
        %>
        </tbody>
    </table>

    <form id="generaPdf" action="AmministratoreController?action=generaReport" method="post">
        Data inizio: <input type="date" id="dataInizio" name="dataInizio" onchange="minDataSelection()"><br>
        Data fine: <input type="date" id="dataFine" name="dataFine" onchange="maxDataSelection()"><br>
        <input type="submit" value="Genera Pdf">
    </form>

    <script>
        function minDataSelection() {
            // Imposta la data minima consentita
            var minDate = "2021-01-01";

            // Ottieni l'elemento input di tipo data
            var datePicker = document.getElementById("dataInizio");

            // Imposta la data minima consentita per la selezione
            datePicker.min = minDate;
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
            var datePicker = document.getElementById("dataFine");

            // Imposta la data massima consentita per la selezione
            datePicker.max = maxDate;
        }
    </script>
</body>
</html>
