<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    String email = "";
    String result = "";
%>
<%
    synchronized(session)
    {
        session = request.getSession();
        email=(String)session.getAttribute("email");
    }

    result = (String) request.getAttribute("result");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ebalance Login</title>
    <link href="css/login.css" rel="stylesheet" type="text/css">
    <!-- <link rel="shortcut icon" href="img/favicon.ico" /> -->
</head>
<body>
<%//@include file="navBar.jsp" %>
<%
    if(email != null)
    {
        response.sendRedirect("./dashboard.jsp");
    }
%>
<div class="header-container">
    <h1>E-Balance</h1>
    <img src="img/Logo2.jpg" class="background-image">
</div>
<br>
<hr>
<div class="container">
        <h1 id="log">Login</h1>
        <%
            if(result != null)
            {
        %>
        <h3><%=result%></h3>
        <%
            }
        %>
        <form method="post" name="invio" action="AccessoController" onsubmit="return validate()">
                <div class="cont1">
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email">
                </div>
            <br>
            <div class="cont2">
                <label>Password</label>
                <input type="password" name="password" id="password">
            </div>
        <input type="submit" id="submit" value="Accedi">
    </form>
</div>

</body>
</html>
