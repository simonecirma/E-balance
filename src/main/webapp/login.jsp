<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%!
    String email = "";
    String result = "";
%>
<%
    synchronized (session) {
        session = request.getSession();
        email = (String) session.getAttribute("email");
    }

    result = (String) request.getAttribute("result");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link href="css/login.css" rel="stylesheet" type="text/css">
    <!-- <link rel="shortcut icon" href="img/favicon.ico" /> -->
</head>
<body>
<script src="js/Login.js"></script>
<%
    if (email != null) {
        response.sendRedirect("DatiController?action=generaDashboard");
    }
%>
<form method="post" name="invio" action="AccessoController" onsubmit="return validate()">
    <div class="container">
        <div class="left">
            <img src="img/Logo.png" alt="Logo">
        </div>
        <div class="right">
            <h1>LOGIN</h1>
            <input type="email" name="email" placeholder="Email">
            <input type="password" name="password" placeholder="Password">
            <%
                if (result != null) {
            %>
            <h3><%=result%>
            </h3>
            <%
                }
            %>
            <!--<input type="submit" id="submit" value="Accedi">-->
            <button class="cssbuttons-io-button">
                Accedi
                <div class="icon">
                    <svg
                            height="24"
                            width="24"
                            viewBox="0 0 24 24"
                            xmlns="http://www.w3.org/2000/svg"
                    >
                        <path d="M0 0h24v24H0z" fill="none"></path>
                        <path
                                d="M16.172 11l-5.364-5.364 1.414-1.414L20 12l-7.778 7.778-1.414-1.414L16.172 13H4v-2z"
                                fill="currentColor"
                        ></path>
                    </svg>
                </div>
            </button>
        </div>
    </div>
</form>
</body>
</html>
