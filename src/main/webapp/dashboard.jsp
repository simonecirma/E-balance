<%@ page import="java.util.List" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //List<BatteriaBean> batterie = (List<BatteriaBean>) request.getAttribute("batteria");
    float percentualeBatterie = (float) request.getAttribute("percentualeBatterie");
    //List<ConsumoEdificioBean> consumi = (List<ConsumoEdificioBean>) request.getAttribute("consumoEdificio");
    float consumoEdifici = (float) request.getAttribute("consumoEdifici");
    //List<SorgenteBean> sorgenti = (List<SorgenteBean>) request.getAttribute("sorgente");
    String sommaProduzione[][] = (String[][]) request.getAttribute("sommaProduzione");
    //List<ParametriIABean> parametriIA = (List<ParametriIABean>) request.getAttribute("parametriIA");
    //List<InteragisceBean> interazioneParametri = (List<InteragisceBean>) request.getAttribute("interazioneParametri");
    List<InteragisceBean> parametriAttivi = (List<InteragisceBean>) request.getAttribute("parametriAttivi");
%>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
    <%@include file="navBar.jsp" %>
    <h1>
        Percentuale carica Batterie : <%= percentualeBatterie%>
    </h1>
    <br><br>
    <table>
        <thead>
        <tr>
            <th>Prod</th>
            <th>Sorg</th>
        </tr>
        </thead>
        <tbody>
        <% if (sommaProduzione != null) {
            for(int i=0;i<sommaProduzione.length;i++) { %>
        <tr>
            <td><%= sommaProduzione[i][0] %></td>
            <td><%= sommaProduzione[i][1] %></td>

        </tr>
        <% }
        }
        %>
        </tbody>
    </table><br><br>

        <% if (parametriAttivi != null && !parametriAttivi.isEmpty()) {
            for (InteragisceBean par : parametriAttivi) {
                if (par.getFlagPreferenzaSorgente())
                {
        %>
                Preferenza Sorgente: <%= par.getTipoSorgente() %> <br>
        <%
                }
        %>
                Percentuale Utilizzo "<%= par.getTipoSorgente() %>": <%= par.getPercentualeUtilizzoSorgente()%><br>
        <%
            }
        %>
                <table>
                    <tbody>
                    <tr>
                        <td>Priorità Sorgenti</td>
                        <td>
                            <table>
                                <% for (InteragisceBean par : parametriAttivi) {
                                        if (par.getFlagPreferenzaSorgente())
                                        {
                                %>
                                <thead><tr><th>1- <%= par.getTipoSorgente() %></th></thead>
                                <tbody><tr><td>2- <%= par.getTipoSorgente() %></td></tr></tbody>
                                <%
                                        }
                                %>
                            </table>
                        </td>
                    </tr>
                    </tbody>
                </table>
        <%          }
        }
        %>

    <h1>
        Consumo attuale edifici : <%= consumoEdifici%>
    </h1>
</body>
</html>