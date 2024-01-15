<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.reportBean" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.amministratoreBean" %>
<%@ page import="java.util.List" %>
<%
    List<reportBean> report = (List<reportBean>) request.getAttribute("report");
    List<amministratoreBean> amm = (List<amministratoreBean>) request.getAttribute("amm");
%>
<html>
<head>
    <title>Title</title>
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
            <th>Nome Amministratore</th>
            <th>Cognome Amministratore</th>
        </tr>
        </thead>
        <tbody>
        <% if (report != null && !report.isEmpty()) {
            for (int i = 0; i < report.size(); i++) { %>
                <% amministratoreBean bean = new amministratoreBean();
                   reportBean rep = new reportBean();
                   rep = report.get(i);
                   bean = amm.get(i);%>
        <tr>
            <td><%= rep.getDataEmissione() %></td>
            <td><%= bean.getNome() %></td>
            <td><%= bean.getCognome()%></td>
        </tr>
        <%   }
            }
        }
        %>
        </tbody>
    </table>



</body>
</html>
