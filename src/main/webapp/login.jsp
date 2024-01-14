<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    String email="";
%>

<%
    synchronized(session)
    {
        session = request.getSession();
        email=(String)session.getAttribute("email");
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ebalance Login</title>
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
        <h1>Login</h1>
        <form method="post" name="invio" action = "accessoController" onsubmit="return validate()">
            <label for="email">Email</label>
            <input type="email" name="email"  id="email"><br>
            <label for="password">Password</label>
            <input type="password" name="password" id="password">
            <input type="submit" id="submit" value="Submit">
        </form>


</body>
</html>