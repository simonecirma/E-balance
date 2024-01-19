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
            <a href="DatiController?action=generaDashboard"><button class="section-button" onclick="closeSection(1)">Chiudi</button></a>
            <div class="content">
                <!-- Contenuto della sezione 4 -->
                <h3>Stato attuale batteria</h3>
                <%= percentualeBatterie%>
                <!-- <div class="programming-stats">
               <div class="chart-container">
                 <canvas class="my-chart"></canvas>
               </div>

               <div class="details">
                 <ul></ul>
               </div>
             </div>-->
            </div>
        </div>

        <div class="section" onclick="toggleExpansion(2)">
            <a href="DatiController?action=generaDashboard"><button class="section-button" onclick="closeSection(1)">Chiudi</button></a>
            <div class="content">
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
        </div>

        <div class="section" onclick="toggleExpansion(3)">
            <a href="DatiController?action=generaDashboard"><button class="section-button" onclick="closeSection(1)">Chiudi</button></a>
            <div class="content">
                <!-- Contenuto della sezione 3 -->
                <h3>Priorità sorgenti</h3>
                <table>
                    <tbody>
                    <tr>
                        <td>Priorità Sorgenti</td>
                        <td>
                            <table>
                                <% for (InteragisceBean par : parametriAttivi) {
                                    if (par.getFlagPreferenzaSorgente())
                                    {
                                %>
                                <thead><tr><th>1- <%= par.getTipoSorgente() %></th></thead>
                                <tbody><tr><td>2- <%= par.getTipoSorgente() %></td></tr></tbody>
                                <%
                                    }
                                %>
                            </table>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <%
                                }
                %>

            </div>
        </div>
        <div class="section" onclick="toggleExpansion(4)">
            <a href="DatiController?action=generaDashboard"><button class="section-button" onclick="closeSection(1)">Chiudi</button></a>
            <div class="content">
                <!-- Contenuto della sezione 2 -->
                <h3>Consumi attuale</h3>
                <%= consumoEdifici%>
            </div>
        </div>
        <div class="section" onclick="toggleExpansion(5)">
            <a href="DatiController?action=generaDashboard"><button class="section-button" onclick="closeSection(1)">Chiudi</button></a>
            <div class="content">
                <!-- Contenuto della sezione 5 -->
                <h3>Parametri IA</h3>
                <% if (parametriAttivi != null && !parametriAttivi.isEmpty()) {
                    for (InteragisceBean par : parametriAttivi) {
                        if (par.getFlagPreferenzaSorgente())
                        {
                %>
                Preferenza Sorgente: <%= par.getTipoSorgente() %> <br>
                <%
                    }
                %>
                Percentuale Utilizzo "<%= par.getTipoSorgente() %>": <%= par.getPercentualeUtilizzoSorgente()%><br>
                <%
                    }
                %>
                <%
                   }
                %>
            </div>
        </div>
        <div class="section" onclick="toggleExpansion(6)">
            <a href="DatiController?action=generaDashboard"><button class="section-button" onclick="closeSection(1)">Chiudi</button></a>
            <div class="content">
                <!-- Contenuto della sezione 6 -->
                <h3>Previsioni meteo</h3>
            </div>
        </div>
    </div>
<script>
    function toggleExpansion(index) {
        const sections = document.querySelectorAll('.dashboard .section');

        sections.forEach((section, i) => {
            if (i + 1 === index) {
                section.classList.toggle('section-expanded');
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
    const chartData = {
        labels: ["Python", "Java", "JavaScript", "C#", "Others"],
        data: [30, 17, 10, 7, 36],
    };

    const myChart = document.querySelector(".my-chart");
    const ul = document.querySelector(".programming-stats .details ul");

    new Chart(myChart, {
        type: "doughnut",
        data: {
            labels: chartData.labels,
            datasets: [
                {
                    label: "Language Popularity",
                    data: chartData.data,
                },
            ],
        },
        options: {
            borderWidth: 10,
            borderRadius: 2,
            hoverBorderWidth: 0,
            plugins: {
                legend: {
                    display: false,
                },
            },
        },
    });

    const populateUl = () => {
        chartData.labels.forEach((l, i) => {
            let li = document.createElement("li");
            li.innerHTML = `${l}: <span class='percentage'>${chartData.data[i]}%</span>`;
            ul.appendChild(li);
        });
    };

    populateUl();

</script>

    </script>
</body>
</html>