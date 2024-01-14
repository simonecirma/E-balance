<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
	Boolean tipo;
	String email = "";
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
		tipo = (Boolean) session.getAttribute("flagTipo");
		idAmministratore = (int) session.getAttribute("idAmministratore");
		cognome = (String) session.getAttribute("cognome");
		nome = (String) session.getAttribute("nome");
		password = (String) session.getAttribute("password");
		email = (String) session.getAttribute("email");
		dataNascita = (Date) session.getAttribute("dataNascita");
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
			<a href="dashboard.jsp">
				<img src="img/Logo.png" alt="Home" width="90" height="80">
			</a>
			<div class="icone">
				<a href="profilo.jsp">
					<button type="button">Profilo</button>
				</a>
				<%
					if(email != null)
				  	{
				%>
						<a href="logoutController">
							<button type="button">Logout</button>
						</a>
				<%
				  	}
					else
					{
				%>
						<a href="login.jsp">
							<button type="button">Login</button>
						</a>
				<%
					}
				%>
			</div>
			
		</div>
	</body>
</html>
