<%@ page import="java.util.List" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //List<BatteriaBean> batterie = (List<BatteriaBean>) request.getAttribute("batteria");
    float percentualeBatterie = (float) request.getAttribute("percentualeBatterie");
    List<ConsumoEdificioBean> consumi = (List<ConsumoEdificioBean>) request.getAttribute("consumoEdificio");
    //List<SorgenteBean> sorgenti = (List<SorgenteBean>) request.getAttribute("sorgente");
    float sommaProduzione[] = (float[]) request.getAttribute("sommaProduzione");
    List<ParametriIABean> parametriIA = (List<ParametriIABean>) request.getAttribute("parametriIA");
    List<InteragisceBean> interazioneParametri = (List<InteragisceBean>) request.getAttribute("interazioneParametri");
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
        </tr>
        </thead>
        <tbody>
        <% if (sommaProduzione != null) {
            for(int i=0;i<sommaProduzione.length;i++) { %>
        <tr>
            <td><%= sommaProduzione[i] %></td>
        </tr>
        <% }
        }
        %>
        </tbody>
    </table><br><br>
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>tipo</th>
            <th>prefe</th>
            <th>perce</th>
            <th>prior</th>
        </tr>
        </thead>
        <tbody>
        <% if (parametriIA != null && !parametriIA.isEmpty()) {
            for (ParametriIABean par : parametriIA) { %>
        <tr>
            <% if (par.getFlagAttivazioneParametro()) {
                if (interazioneParametri != null && !interazioneParametri.isEmpty()) {
            for (InteragisceBean inter : interazioneParametri) {
                if(par.getIdAmministratore()==inter.getIdParametro()) {
            %>
            <td><%= inter.getIdParametro() %></td>
            <td><%= inter.getTipoSorgente() %></td>
            <td><%= inter.getFlagPreferenzaSorgente()%></td>
            <td><%= inter.getPercentualeUtilizzoSorgente()%></td>
            <td><%= inter.getPrioritaSorgente()%></td>
        </tr>
        <%          }
                    }
                    }
                }
            }
        }
        %>
        </tbody>
    </table><br><br>
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Nome Edificio</th>
            <th>Consumo Attuale</th>
        </tr>
        </thead>
        <tbody>
        <% if (consumi != null && !consumi.isEmpty()) {
            for (ConsumoEdificioBean consumo : consumi) { %>
        <tr>
            <td><%= consumo.getIdEdificio() %></td>
            <td><%= consumo.getNomeEdificio() %></td>
            <td><%= consumo.getConsumoAttuale()%></td>
        </tr>
        <% }
        }
        %>
        </tbody>
    </table>
</body>
</html>