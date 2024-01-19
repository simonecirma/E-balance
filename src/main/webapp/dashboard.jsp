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
    <link href="css/dashboard.css" rel="stylesheet" type="text/css">
</head>
<body>
    <script src="js/dashboard.js"></script>
    <%@include file="navBar.jsp" %>
    <div class="dashboard">
        <div class="section" onclick="toggleExpansion(1)">
            <a href="DatiController?action=generaDashboard"><button class="section-button">
                <img src="img/indietro1.png" id="img"></button></a>
            <div class="content">
                <div class="initial-content">
                <!-- Contenuto della sezione 4 -->
                    <h3>Stato attuale batteria</h3>
                    <div class="batt">
                        <div class="battery" id="battery">
                            <div class="box"></div>
                            <span id="s1"></span>
                            <span id="s2"></span>
                            <span id="s3"></span>
                            <span id="s4"></span>
                            <span id="s5"></span>
                        </div>
                    </div>
                    <input type="hidden" id="batteryInput" value="<%= percentualeBatterie%>" oninput="updateBattery()">
                    <div id="batteryText">0%</div>
                </div>
                <div class="expanded-content">
                </div>
            </div>
        </div>

        <div class="section" onclick="toggleExpansion(2)">
            <a href="DatiController?action=generaDashboard">
                <button class="section-button">
                <img src="img/indietro1.png" id="img"></button></a>
            <div class="content">
                <div class="initial-content">
                <!-- Contenuto della sezione 1 -->
                    <h3>Energia prodotta</h3>
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
                    </table>
                </div>
                <div class="expanded-content">

                </div>
            </div>
        </div>

        <div class="section" onclick="toggleExpansion(3)">
            <a href="DatiController?action=generaDashboard"><button class="section-button">
                <img src="img/indietro1.png" id="img"></button></a>
            <div class="content">
                <div class="initial-content">
                <!-- Contenuto della sezione 3 -->
                    <h3>Priorità sorgenti</h3>
                </div>
                <div class="expanded-content">
                </div>
            </div>
        </div>
        <div class="section" onclick="toggleExpansion(4)">
            <a href="DatiController?action=generaDashboard"><button class="section-button">
                <img src="img/indietro1.png" id="img"></button></a>
            <div class="content">
                <div class="initial-content">
                <!-- Contenuto della sezione 2 -->
                    <h3>Consumi attuali</h3>
                </div>
                <div class="expanded-content">
                    <%= consumoEdifici%>
                </div>
            </div>
        </div>
        <div class="section" onclick="toggleExpansion(5)">
            <a href="DatiController?action=generaDashboard"><button class="section-button">
                <img src="img/indietro1.png" id="img"></button></a>
            <div class="content">
                <div class="initial-content">
                <!-- Contenuto della sezione 5 -->
                    <h3>Parametri IA</h3>
                    <%
                        if (parametriAttivi != null && !parametriAttivi.isEmpty()) {
                    %>
                    <table id="tab">
                        <thead>

                        </thead>
                        <tbody>
                        <%
                            for (InteragisceBean par : parametriAttivi) {
                                if (par.getFlagPreferenzaSorgente()) {
                        %>
                        <tr>
                            <td>Preferenza Sorgente: <%= par.getTipoSorgente() %></td>
                            <td></td> <!-- Colonna vuota per allineare la tabella -->
                        </tr>
                        <%
                            }
                        %>
                        <tr>
                            <td>Percentuale Utilizzo "<%= par.getTipoSorgente() %>":</td>
                            <td><%= par.getPercentualeUtilizzoSorgente()%></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                    <%
                        }
                    %>
                    <table id="tab1">
                        <tbody>
                        <tr>
                            <td rowspan="2">Priorità Sorgenti</td>
                            <% for (InteragisceBean par : parametriAttivi) {
                                if (par.getFlagPreferenzaSorgente())
                                {
                            %>
                            <td>1- <%= par.getTipoSorgente() %></td>
                        </tr>
                        <tr>
                            <td>2- <%= par.getTipoSorgente() %></td>
                            <%
                                }
                            %>
                        </tr>
                        </tbody>
                    </table>
                    <%
                        }
                    %>
                </div>
                <div class="expanded-content">

            </div>
            </div>
        </div>
        <div class="section" onclick="toggleExpansion(6)">
            <a href="DatiController?action=generaDashboard"><button class="section-button">
                <img src="img/indietro1.png" id="img"></button></a>
            <div class="content">
                <div class="initial-content">
                <!-- Contenuto della sezione 6 -->
                    <h3>Previsioni meteo</h3>
                </div>
                <div class="expanded-content">
                </div>
            </div>
        </div>
    </div>
    <script>
        function toggleExpansion(index) {
            const sections = document.querySelectorAll('.dashboard .section');

            sections.forEach((section, i) => {
                if (i + 1 === index) {
                    section.classList.add('section-expanded');
                } else {
                    section.classList.remove('section-expanded');
                }
            });

            // Nasconde gli altri div quando uno è espanso
            document.querySelectorAll('.dashboard .section:not(.section-expanded)').forEach((section) => {
                section.style.display = 'none';
            });

            // Mostra tutti i div quando nessuno è espanso
            if (!document.querySelector('.dashboard .section-expanded')) {
                document.querySelectorAll('.dashboard .section').forEach((section) => {
                    section.style.display = 'block';
                });
            }
        }

        function updateBattery() {
            const input = document.getElementById('batteryInput');
            const battery = document.getElementById('battery');
            const batteryText = document.getElementById('batteryText');
            const spans = battery.querySelectorAll('span');
            let value = parseInt(input.value, 10);

            if (isNaN(value) || value < 0 || value > 100) {
                value = 0;
            }

            const numberOfColoredSpans = Math.max(1, Math.floor((value / 100) * spans.length));

            spans.forEach((span) => {
                span.style.backgroundColor = 'white';
            });

            for (let i = 0; i < numberOfColoredSpans; i++) {
                spans[i].style.backgroundColor = getBatteryColor(value);
            }

            batteryText.textContent = value + '%';
        }

        function getBatteryColor(value) {
            if (value > 74) {
                return 'limegreen';
            } else if (value > 49) {
                return 'yellow';
            } else if (value > 24) {
                return 'orange';
            } else {
                return 'red';
            }
        }

        updateBattery();
    </script>

</body>
</html>