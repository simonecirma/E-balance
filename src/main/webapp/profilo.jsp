<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.AmministratoreBean" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Profilo</title>
    <link href="css/profilo.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="navBar.jsp" %>
<form action="AmministratoreController?action=aggiornaAmministratore" method="post">
    <div class="avatar">
        <img src="img/avatar.png">
    </div>
    <div>
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="<%=nome%>"><br>
    </div>
    <div>
        <label for="cognome">Cognome:</label>
        <input type="text" id="cognome" name="cognome" value="<%=cognome%>"><br>
    </div>
    <div>
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" value="<%=email%>"><br>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value="<%=password%>"><br>
    </div>
    <div>
        <label for="dataNascita">Data Nascita:</label>
        <input type="date" id="dataNascita" name="dataNascita" value="<%=dataNascita%>"><br>
    </div>
    <input type="hidden" name="idAmministratore" value="<%=idAmministratore%>"><br>
    <input type="hidden" name="flagTipo" value="<%=tipo%>"><br>
    <input type="submit" value="Salva modifiche">
</form>
</body>

</html>