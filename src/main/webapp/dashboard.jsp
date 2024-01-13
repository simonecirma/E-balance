<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>
<form>
    <label>Nome:</label>
    <input type="text" name="nome" value="<%=session.getAttribute("nome") %>"><br>
    <label>Cognome:</label>
    <input type="text" name="cognome" value="<%= session.getAttribute("cognome") %>"><br>
    <label>Email:</label>
    <input type="text" name="email" value="<%=session.getAttribute("email") %>"><br>
    <label>Password:</label>
    <input type="password" name="password" value="<%=session.getAttribute("password") %>"><br>

    <input type="submit" value="Salva modifiche">
</form>
<br>
<a href="logoutController">Effettua Logout</a>
</body>
</html>
