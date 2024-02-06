<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.ReportBean" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.AmministratoreBean" %>
<%@ page import="java.util.List" %>
<%
    List<ReportBean> listReport = (List<ReportBean>) request.getAttribute("listReport");
    List<AmministratoreBean> amministratori = (List<AmministratoreBean>) request.getAttribute("amministratori");
%>
<html class="report">
<head>
    <title>Report</title>
    <link href="css/report.css" rel="stylesheet" type="text/css">
</head>
<body style="background-image: url('img/wp1.png'); background-attachment: fixed; background-repeat: no-repeat; background-size: cover; background-position: center 150px; background-color: #1d1f2f;">
<%@include file="navBar.jsp" %>
<br>
<%
    if (email != null) {
%>
<div class="principale">
    <div class="container">
        <div class="icon-container">
            <div class="icon">
                <img src="img/report.png">
            </div>
        </div>
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
                <td><%= rep.getDataEmissione() %>
                </td>
                <%
                    if (bean.getNome() == null) {
                %>
                <td>Amministratore licenziato</td>
                <%
                } else {
                %>

                <td><%= bean.getNome() %> <%=bean.getCognome()%> </td>
                <%
                    }
                %>
                <td><a href="report\<%=rep.getNomeReport()%>" target="_blank">
                    <button class="button">Apri</button>
                </a></td>
            </tr>
            <% }
            }
            }
            %>
            </tbody>
        </table>

        <form id="generaPdf" action="AmministratoreController?action=generaReport" method="post" onsubmit="return validaDate()">
            <div>
                <label for="dataInizio">Data inizio:</label> <input type="date" id="dataInizio" name="dataInizio" required>
            </div>
            <div>
                <label for="dataFine">Data fine:</label> <input type="date" id="dataFine" name="dataFine" required>
            </div>
            <button class="Btn10">
                <svg class="svgIcon" viewBox="0 0 384 512" height="1em" xmlns="http://www.w3.org/2000/svg"><path d="M169.4 470.6c12.5 12.5 32.8 12.5 45.3 0l160-160c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L224 370.8 224 64c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 306.7L54.6 265.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3l160 160z"></path></svg>
                <span class="icon2"></span>
                <span class="tooltip">Download</span>
            </button>
        </form>
    </div>
</div>

<script>
    // Funzione per controllare che la data sia compresa tra oggi e il 01-01-2021
    function validaDate() {
        var dataInizio = new Date(document.getElementById("dataInizio").value);
        var dataFine = new Date(document.getElementById("dataFine").value);
        var oggi = new Date();
        var limite = new Date("2021-01-01");

        if (dataInizio > oggi || dataInizio < limite || dataInizio > dataFine || dataFine > oggi || dataFine < limite || dataFine < dataInizio) {
            alert("Si prega di inserire date valide comprese tra oggi e il 01-01-2021, con la data di inizio non successiva alla data di fine.");
            return false;
        }
        return true;
    }

    // Aggiungi un listener per la validazione quando viene inviato il modulo
    document.getElementById("generaPdf").addEventListener("submit", function(event) {
        if (!validaDate()) {
            event.preventDefault(); // Impedisce l'invio del modulo se le date non sono valide
        }
    });
</script>

</body>
</html>
