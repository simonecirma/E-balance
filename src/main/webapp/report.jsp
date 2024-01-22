<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.ReportBean" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.AmministratoreBean" %>
<%@ page import="java.util.List" %>
<%
    List<ReportBean> report = (List<ReportBean>) request.getAttribute("report");
    List<AmministratoreBean> amm = (List<AmministratoreBean>) request.getAttribute("amm");
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
        <% if (report != null && !report.isEmpty()) {
            for (int i = 0; i < report.size(); i++) { %>
                <% AmministratoreBean bean = new AmministratoreBean();
                   ReportBean rep = new ReportBean();
                   rep = report.get(i);
                   bean = amm.get(i);%>
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

    <form id="generaPdf" action="DatiController?action=bilancioTotale" method="post">
        Data inizio: <input type="date" name="dataInizio"><br>
        Data fine: <input type="date" name="dataFine"><br>
        <input type="submit" value="Genera Pdf">
    </form>

</body>
</html>
