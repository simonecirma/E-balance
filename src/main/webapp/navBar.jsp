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
		session= request.getSession();
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
		<div class="navbar2">
			<%
				if(email != null)
				{
			%>
				<a href="dashboard.jsp">
					<img src="img/Logo.png" alt="Home" width="90" height="80">
				</a>
				<a href="profilo.jsp">
					<button type="button">Profilo</button>
				</a>
				<a href="contratto.jsp">
					<button type="button">Contratto</button>
				</a>
				<a href="logoutController">
					<button type="button">Logout</button>
				</a>
			<%
				if(tipo)
				{
			%>
				<a href="amministratoreController?action=gestisciAmministratori">
					<button type="button">Gestisci</button>
				</a>
			<%
					}
				}
				else
				{
			%>
				<a href="amministratori.jsp">
					<img src="img/Logo.png" alt="Home" width="90" height="80">
				</a>
			<%
				}
			%>
		</div>
	</body>
</html>
