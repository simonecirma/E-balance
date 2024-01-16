<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
	Boolean tipo;
	String email = null;
	String nome = "";
	String cognome = "";
	Date dataNascita;
	String password = "";
	int idAmministratore;
%>

<%
	synchronized (session)
	{
		session = request.getSession();
		if(session.getAttribute("idAmministratore")!= null)
		{
			tipo = (Boolean) session.getAttribute("flagTipo");
			idAmministratore = (int) session.getAttribute("idAmministratore");
			cognome = (String) session.getAttribute("cognome");
			nome = (String) session.getAttribute("nome");
			password = (String) session.getAttribute("password");
			email = (String) session.getAttribute("email");
			dataNascita = (Date) session.getAttribute("dataNascita");
		}
	}
%>

<!DOCTYPE html>
<html lang="it">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>GameCenter</title>
	<link href="css/navBar.css" rel="stylesheet" type="text/css">
</head>
<body>
<nav class="navbar">
	<div class="navdiv">
		<% if (email != null) { %>
		<a href="dashboard.jsp">
			<img src="img/Logo.png" alt="Home" width="90" height="90">
		</a>
		<ul>
			<li><a href="profilo.jsp">
				<span>Profilo</span>
			</a></li>
			<li><a href="ContrattoController">
				<span>Contratto</span>
			</a></li>
			<li><a href="AmministratoreController?action=vediReport">
				<span>Report</span>
			</a></li>
			<% if (tipo) { %>
			<li><a href="AmministratoreController?action=gestisciAmministratori">
				<span>Gestisci</span>
			</a></li>
			<% } %>
			<li><a href="LogoutController">
				<span>Logout</span>
			</a></li>
		</ul>
	</div>

	<% } else { %>
	<a href="amministratori.jsp">
		<img src="img/Logo.png" alt="Home" width="90" height="80">
	</a>
	<% } %>
</nav>
</body>
</html>
