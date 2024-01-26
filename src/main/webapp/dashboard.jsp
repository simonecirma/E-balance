<%@ page import="java.util.List" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //List<BatteriaBean> batterie = (List<BatteriaBean>) request.getAttribute("batteria");
    float percentualeBatterie = (float) request.getAttribute("percentualeBatterie");
    //List<ConsumoEdificioBean> consumi = (List<ConsumoEdificioBean>) request.getAttribute("consumoEdificio");
    float consumoEdifici[] = (float[]) request.getAttribute("consumoEdifici");
    List<ArchivioConsumoBean> archivioConsumo = (List<ArchivioConsumoBean>) request.getAttribute("archivioConsumo");
    List<MeteoBean> condizioni = (List<MeteoBean>) request.getAttribute("condizioniMeteo");
    //List<SorgenteBean> sorgente = (List<SorgenteBean>) request.getAttribute("sorgente");
    float produzioneSorgente[] = (float[]) request.getAttribute("produzioneSorgente");
    float produzioneSEN = (float) request.getAttribute("produzioneSEN");
    List<ParametriIABean> parametriIA = (List<ParametriIABean>) request.getAttribute("parametriIA");
    List<InteragisceBean> interazioneParametri = (List<InteragisceBean>) request.getAttribute("interazioneParametri");
    List<InteragisceBean> parametriAttivi = (List<InteragisceBean>) request.getAttribute("parametriAttivi");
    List<TipoSorgenteBean> tipoSorgente = (List<TipoSorgenteBean>) request.getAttribute("tipoSorgente");
    String result = (String) request.getAttribute("result");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link href="css/dashboard.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="js/dashboard.js"></script>
</head>
<body>
    <%@include file="navBar.jsp" %>
    <%
        if(result != null)
        {
    %>
    <h3><%=result%></h3>
    <%
        }
    %>
    <div class="dashboard">
        <div class="section" onclick="toggleExpansion(1)">
            <a href="DatiController?action=generaDashboard">
                <img src="img/indietro.png" id="img"></a>
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
                    <div id="percentualeBatterie" name="percentualeBatterie">
                        <input type="hidden" id="batteryInput" value="<%= percentualeBatterie %>">
                    </div>
                    <div id="batteryText">0%</div>
                </div>
                <div class="expanded-content">
                </div>
            </div>
        </div>

        <div class="section" onclick="toggleExpansion(2)">
            <a href="DatiController?action=generaDashboard">
                <img src="img/indietro.png" id="img"></a>
            <div class="contentEnergia">
                <div class="initial-content-energia">
                <!-- Contenuto della sezione 1 -->
                    <h3>Energia prodotta</h3>
                    <div class="chart-container1">
                        <div class="doughnut1">
                            <canvas id="doughnut"></canvas>
                        </div>
                    </div>
                    <div id = "sommaProduzione" name = "sommaProduzione">
                        Energia prodotta:
                        <% if (produzioneSorgente != null) {
                            for(int i=0;i<produzioneSorgente.length;i++) {
                        %>
                        <%= produzioneSorgente[i] %>
                        <%
                                }
                            }
                        %>
                    </div>
                    <br>
                    Energia da Servizio Elettrico Nazionale: <%=produzioneSEN%>
                </div>
                <div class="expanded-content-energia">
                    <div class="expandendEnergia">
                        <div class="containerEnergia">
                            <div class="chart-container">
                                <div class="doughnut">
                                    <canvas id="doughnut1"></canvas>
                                </div>
                            </div>
                            <div class="chart-container">
                                <div class="doughnut">
                                    <canvas id="doughnut1"></canvas>
                                </div>
                            </div>
                            <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                            <script>
                                var sommaProduzioneDiv = document.getElementById("sommaProduzione");
                                var dataValues = [];
                                var backgroundColors = [];

                                // Estrai i valori numerici dall'array all'interno del div
                                var dataArray = sommaProduzioneDiv.innerText.match(/\d+(\.\d+)?/g);
                                for (var i = 0; i < dataArray.length; i++) {
                                    // Aggiungi il valore al dataset
                                    dataValues.push(parseFloat(dataArray[i]));
                                    // Genera un colore casuale per ogni valore
                                    var randomColor = 'rgba(' + Math.floor(Math.random() * 256) + ',' + Math.floor(Math.random() * 256) + ',' + Math.floor(Math.random() * 256) + ',0.5)';
                                    backgroundColors.push(randomColor);
                                }

                                // Costruisci il grafico a doughnut
                                var ctx = document.getElementById('doughnut').getContext('2d');
                                var myChart = new Chart(ctx, {
                                    type: 'doughnut',
                                    data: {
                                        labels: dataValues.map(value => value.toString()), // Utilizza i valori come etichette
                                        datasets: [{
                                            label: 'Consumo Giornaliero',
                                            data: dataValues,
                                            backgroundColor: backgroundColors,
                                            borderColor: 'rgba(75, 192, 192, 1)',
                                            borderWidth: 1
                                        }]
                                    },
                                    options: {
                                        plugins: {
                                            legend: {
                                                display: true,
                                                position: 'right', // Posizione della legenda
                                            }
                                        },
                                        scales: {
                                            x: {
                                                display: false // Nascondi l'asse X
                                            },
                                            y: {
                                                display: false // Nascondi l'asse Y
                                            }
                                        }
                                    }
                                });
                            </script>
                            <script>
                                var sommaProduzioneDiv = document.getElementById("sommaProduzione");
                                var dataValues = [];
                                var backgroundColors = [];

                                // Estrai i valori numerici dall'array all'interno del div
                                var dataArray = sommaProduzioneDiv.innerText.match(/\d+(\.\d+)?/g);
                                for (var i = 0; i < dataArray.length; i++) {
                                    // Aggiungi il valore al dataset
                                    dataValues.push(parseFloat(dataArray[i]));
                                    // Genera un colore casuale per ogni valore
                                    var randomColor = 'rgba(' + Math.floor(Math.random() * 256) + ',' + Math.floor(Math.random() * 256) + ',' + Math.floor(Math.random() * 256) + ',0.5)';
                                    backgroundColors.push(randomColor);
                                }

                                // Costruisci il grafico a doughnut
                                var ctx1 = document.getElementById('doughnut1').getContext('2d');
                                var myChart = new Chart(ctx1, {
                                    type: 'doughnut',
                                    data: {
                                        labels: dataValues.map(value => value.toString()), // Utilizza i valori come etichette
                                        datasets: [{
                                            label: 'Consumo Giornaliero',
                                            data: dataValues,
                                            backgroundColor: backgroundColors,
                                            borderColor: 'rgba(75, 192, 192, 1)',
                                            borderWidth: 1
                                        }]
                                    },
                                    options: {
                                        plugins: {
                                            legend: {
                                                display: true,
                                                position: 'right', // Posizione della legenda
                                            }
                                        },
                                        scales: {
                                            x: {
                                                display: false // Nascondi l'asse X
                                            },
                                            y: {
                                                display: false // Nascondi l'asse Y
                                            }
                                        }
                                    }
                                });
                            </script>
                        </div>
                        <div id="curve_chart"></div>
                        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
                        <script type="text/javascript">
                            google.charts.load('current', {'packages':['corechart']});
                            google.charts.setOnLoadCallback(drawChart);

                            function drawChart() {
                                // Assicurati che l'elemento con id "sommaProduzione" esista nell'HTML
                                var sommaProduzioneDiv = document.getElementById('sommaProduzione');

                                // Estrai i dati dall'elemento sommaProduzioneDiv
                                var dataArray = sommaProduzioneDiv.innerText.match(/\d+(\.\d+)?/g);

                                // Crea un nuovo DataTable di Google Charts
                                var data = new google.visualization.DataTable();
                                data.addColumn('string', 'Sorg');
                                data.addColumn('number', 'Prod');

                                // Popola il DataTable con i dati estratti
                                for (var i = 0; i < dataArray.length; i++) {
                                    // Supponendo che ci sia una fonte associata a ciascun dato,
                                    // possiamo utilizzare un'etichetta generica per la fonte
                                    var sorg = 'Fonte ' + (i + 1);
                                    var prod = parseFloat(dataArray[i]);
                                    data.addRow([sorg, prod]);
                                }

                                // Opzioni per il grafico
                                var options = {
                                    curveType: 'function',
                                    legend: { position: 'bottom' }
                                };

                                // Disegna il grafico utilizzando il DataTable e le opzioni
                                var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));
                                chart.draw(data, options);
                            }
                        </script>
                    </div>
                </div>
            </div>
        </div>

        <div class="section" onclick="toggleExpansion(3)">
            <a href="DatiController?action=generaDashboard">
                <img src="img/indietro.png" id="img"></a>
            <div class="contentConsumiAttuali">
                <div class="initial-content-consumiAttuali">
                <!-- Contenuto della sezione 3 -->
                    <h3>Consumi attuali</h3>
                    <div id="consumoEdifici" name="consumoEdifici">
                        <% if (consumoEdifici != null) {
                            for(int i=0;i<consumoEdifici.length;i++) {
                        %>
                                <%= consumoEdifici[i] %>
                        <%
                            }
                        }
                        %>
                    </div>
                        <div id="curve_chart1"></div>
                </div>
                <div class="expanded-content-consumiAttuali">
                    <div class="chart-container-consumi">
                      <div id="curve_chart2"></div>
                    </div>
                    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
                    <script>
                        google.charts.load('current', {'packages':['corechart']});
                        google.charts.setOnLoadCallback(drawCharts);

                        function drawCharts() {
                            drawChart();
                            drawChart1();
                        }

                        function drawChart() {
                            var consumoEdificiDiv = document.getElementById('consumoEdifici');
                            var dataArray = consumoEdificiDiv.innerText.match(/\d+(\.\d+)?/g);

                            // Crea un DataTable con i dati estratti
                            var data = new google.visualization.DataTable();
                            data.addColumn('string', 'Label');
                            data.addColumn('number', 'Consumo');

                            for (var i = 0; i < dataArray.length; i++) {
                                var label = "Edificio " + (i + 1);
                                var consumo = parseFloat(dataArray[i]);
                                data.addRow([label, consumo]);
                            }

                            var options = {
                                curveType: 'function',
                                legend: { position: 'bottom' },
                                chartArea: { height: '50px' } // Imposta l'altezza desiderata qui
                            };

                            var chart = new google.visualization.LineChart(document.getElementById('curve_chart1'));

                            chart.draw(data, options);
                        }

                        function drawChart1() {
                            var consumoEdificiDiv = document.getElementById('consumoEdifici');
                            var dataArray = consumoEdificiDiv.innerText.match(/\d+(\.\d+)?/g);

                            // Crea un DataTable con i dati estratti
                            var data = new google.visualization.DataTable();
                            data.addColumn('string', 'Label');
                            data.addColumn('number', 'Consumo');

                            for (var i = 0; i < dataArray.length; i++) {
                                var label = "Edificio " + (i + 1);
                                var consumo = parseFloat(dataArray[i]);
                                data.addRow([label, consumo]);
                            }

                            var options = {
                                curveType: 'function',
                                legend: { position: 'bottom' },
                                chartArea: { height: '50px' } // Imposta l'altezza desiderata qui
                            };

                            var chart1 = new google.visualization.LineChart(document.getElementById('curve_chart2'));

                            chart1.draw(data, options);
                        }
                    </script>
                </div>
            </div>
        </div>
        <div class="section" onclick="toggleExpansion(4)">
            <a href="DatiController?action=generaDashboard">
                <img src="img/indietro.png" id="img"></a>
            <div class="contentConsumi">
                <div class="initial-content-consumi">
                    <%
                        if (archivioConsumo != null && !archivioConsumo.isEmpty()) {
                    %>
                    <div class="text">Archivio Consumi</div>
                <!-- Contenuto della sezione 2 -->
                    <div class="chart1">
                        <canvas id="istogramma"></canvas>
                    </div>
                </div>
                <div class="expandedConsumi">
                    <h3>Archivio Consumi</h3>
                    <div class="containerChart">
                    <div class="chart">
                        <canvas id="istogramma2"></canvas>
                    </div>
                    <table id="consumi">
                        <thread>
                            <tr>
                                <th>Data consumo:</th>
                                <th>Consumo giornaliero:</th>
                            </tr>
                        </thread>
                        <tbody>
                        <%
                            for (ArchivioConsumoBean archivio : archivioConsumo) {
                        %>
                        <tr>
                            <td> <%= archivio.getDataConsumo() %></td>
                            <td> <%= archivio.getConsumoGiornaliero() %></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                    <%
                        }
                    %>
                    </div>
                    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                    <script>
                        var tabella = document.getElementById("consumi");
                        var date = [];
                        var consumi = [];
                        var colors = [];

                        for (var i = 1; i < tabella.rows.length; i++) {
                            date.push(tabella.rows[i].cells[0].innerText);
                            consumi.push(parseInt(tabella.rows[i].cells[1].innerText));

                            var randomColor = 'rgba(' + Math.floor(Math.random()*256) + ',' + Math.floor(Math.random()*256) + ',' + Math.floor(Math.random()*256) + ',0.5)';
                            colors.push(randomColor);
                        }

                        var ctx = document.getElementById('istogramma').getContext('2d');
                        var myChart = new Chart(ctx, {
                            type: 'bar',
                            data: {
                                labels: date,
                                datasets: [{
                                    label: 'Consumo Giornaliero',
                                    data: consumi,
                                    backgroundColor: colors,
                                    borderColor: 'rgba(75, 192, 192, 1)',
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                scales: {
                                    y: {
                                        beginAtZero: true
                                    }
                                },
                                plugins: {
                                    legend: {
                                        display: false
                                    }
                                }
                            }
                        });
                    </script>
                    <script>
                        var tabella = document.getElementById("consumi");
                        var date = [];
                        var consumi = [];
                        var colors = [];

                        for (var i = 1; i < tabella.rows.length; i++) {
                            date.push(tabella.rows[i].cells[0].innerText);
                            consumi.push(parseInt(tabella.rows[i].cells[1].innerText));

                            var randomColor = 'rgba(' + Math.floor(Math.random()*256) + ',' + Math.floor(Math.random()*256) + ',' + Math.floor(Math.random()*256) + ',0.5)';
                            colors.push(randomColor);
                        }

                        var ctx2 = document.getElementById('istogramma2').getContext('2d');
                        var myChart = new Chart(ctx2, {
                            type: 'bar',
                            data: {
                                labels: date,
                                datasets: [{
                                    label: 'Consumo Giornaliero',
                                    data: consumi,
                                    backgroundColor: colors,
                                    borderColor: 'rgba(75, 192, 192, 1)',
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                scales: {
                                    y: {
                                        beginAtZero: true
                                    }
                                },
                                plugins: {
                                    legend: {
                                        display: false
                                    }
                                }
                            }
                        });
                    </script>
                </div>
            </div>
        </div>
        <div class="section" onclick="toggleExpansion(5)">
            <a href="DatiController?action=generaDashboard">
                <img src="img/indietro.png" id="img"></a>
            <div class="contentIA">
                <div class="initial-content-IA">
                <!-- Contenuto della sezione 5 -->
                    <h3>Parametri IA</h3>
                    <!--
                    <%
                        if (parametriIA != null && !parametriAttivi.isEmpty()) {
                    %>
                    <table id="tabIA">
                        <tbody>
                        <%
                            for (InteragisceBean par : parametriAttivi) {
                                if (par.getFlagPreferenzaSorgente()) {
                        %>
                        <tr>
                            <td>Preferenza Sorgente:</td><td> <%= par.getTipoSorgente() %></td>
                                <%
                            }
                                }
                        %>
                        </tbody>
                    </table>
                    <table id="tab1IA">
                        <tbody>
                        <%
                            for (InteragisceBean par : parametriAttivi) {
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
                    <table id="tab1IA">
                        <tbody>
                        <tr>
                            <td rowspan="<%= parametriAttivi.size() + 1 %>">Priorità Sorgenti</td>
                        </tr>
                        <%
                            int i = 1;
                            for (InteragisceBean par : parametriAttivi) {%>
                        <tr>
                            <td><%=i%>- <%= par.getTipoSorgente() %></td>
                        </tr>
                        <%
                                i++;
                            }
                        %>
                        </tbody>
                    </table>
                    <%
                        }
                    %>
                    -->
                </div>
                <div class="expanded-content">
                    <div class="expandendIA">
                        <div class="tabelle">
                            <h1>Piano per la salvaguardia ambientale</h1>
                            <%
                                if (parametriIA != null && !parametriIA.isEmpty()) {
                                    for (ParametriIABean parametri : parametriIA) {
                                        if(parametri.getPiano().equalsIgnoreCase("Salvaguardia Ambientale"))
                                        {
                            %>
                            <table id="tabIA">
                                <tbody>
                                <%
                                        for (InteragisceBean inter : interazioneParametri) {
                                            if(parametri.getIdParametro() == inter.getIdParametro()){
                                                if (inter.getFlagPreferenzaSorgente()) {
                                %>
                                <tr>
                                    <td>Preferenza Sorgente:</td><td> <%= inter.getTipoSorgente() %></td>
                                <%
                                                }
                                            }
                                        }
                                %>
                                </tbody>
                            </table>
                            <table id="tab1IA">
                                <tbody>
                                <%
                                    for (InteragisceBean inter : interazioneParametri) {
                                        if(parametri.getIdParametro() == inter.getIdParametro()){
                                %>
                                <tr>
                                    <td>Percentuale Utilizzo "<%= inter.getTipoSorgente() %>":</td>
                                    <td><%= inter.getPercentualeUtilizzoSorgente()%></td>
                                </tr>
                                <%
                                        }
                                    }
                                %>
                                </tbody>
                            </table>
                            <table id="tab1IA">
                                <tbody>
                                <tr>
                                    <td rowspan="<%= interazioneParametri.size() + 1 %>">Priorità Sorgenti</td>
                                </tr>
                                <%
                                    int i = 1;
                                    for (InteragisceBean inter : interazioneParametri) {
                                        if(parametri.getIdParametro() == inter.getIdParametro()){
                                %>
                                <tr>
                                    <td><%=i%>- <%= inter.getTipoSorgente() %></td>
                                </tr>
                                <%
                                        i++;
                                        }
                                    }
                                %>
                                </tbody>
                            </table>
                            <%
                                        }
                                    }
                                }
                            %>
                            <a href="DatiController?action=selezionaPiano&piano=SalvaguardiaAmbientale"><input type="submit" value="Seleziona piano di salvaguardia ambientale"></a>
                        </div>
                        <div class="tabelle2">
                            <h1>Piano per l'efficienza economica</h1>
                            <%
                                if (parametriIA != null && !parametriIA.isEmpty()) {
                                    for (ParametriIABean parametri : parametriIA) {
                                        if(parametri.getPiano().equalsIgnoreCase("Efficienza Economica"))
                                        {
                            %>
                            <table id="tabIA">
                                <tbody>
                                <%
                                    for (InteragisceBean inter : interazioneParametri) {
                                        if(parametri.getIdParametro() == inter.getIdParametro()){
                                            if (inter.getFlagPreferenzaSorgente()) {
                                %>
                                <tr>
                                    <td>Preferenza Sorgente:</td><td> <%= inter.getTipoSorgente() %></td>
                                        <%
                                                }
                                            }
                                        }
                                %>
                                </tbody>
                            </table>
                            <table id="tab1IA">
                                <tbody>
                                <%
                                    for (InteragisceBean inter : interazioneParametri) {
                                        if(parametri.getIdParametro() == inter.getIdParametro()){
                                %>
                                <tr>
                                    <td>Percentuale Utilizzo "<%= inter.getTipoSorgente() %>":</td>
                                    <td><%= inter.getPercentualeUtilizzoSorgente()%></td>
                                </tr>
                                <%
                                        }
                                    }
                                %>
                                </tbody>
                            </table>
                            <table id="tab1IA">
                                <tbody>
                                <tr>
                                    <td rowspan="<%= interazioneParametri.size() + 1 %>">Priorità Sorgenti</td>
                                </tr>
                                <%
                                    int i = 1;
                                    for (InteragisceBean inter : interazioneParametri) {
                                        if(parametri.getIdParametro() == inter.getIdParametro()){
                                %>
                                <tr>
                                    <td><%=i%>- <%= inter.getTipoSorgente() %></td>
                                </tr>
                                <%
                                            i++;
                                        }
                                    }
                                %>
                                </tbody>
                            </table>
                            <%
                                        }
                                    }
                                }
                            %>
                            <a href="DatiController?action=selezionaPiano&piano=EfficienzaEconomica"><input type="submit" value="Seleziona piano per l'efficienza economica"></a>
                        </div>
                        <div class="tabelle3">

                                <h1>Piano personalizzato</h1>
                                <%
                                    if (parametriIA != null && !parametriIA.isEmpty()) {
                                        for (ParametriIABean parametri : parametriIA) {
                                            if(parametri.getPiano().equalsIgnoreCase("Personalizzato"))
                                            {
                                                for (InteragisceBean inter : interazioneParametri) {
                                                    if(parametri.getIdParametro() == inter.getIdParametro()){
                                                        if (inter.getFlagPreferenzaSorgente()) {
                                %>
                                <form id="priority-form-1" style="margin-top: -10px;" action="DatiController?action=selezionaPiano&piano=Personalizzato" method="post">
                                    <%
                                        if (tipoSorgente != null && !tipoSorgente.isEmpty()) {
                                            Iterator<?> it = tipoSorgente.iterator();
                                    %>
                                    <table class="IATab">
                                        <tr>
                                            <td>
                                                <label for="preferenzaSorgente">Seleziona la sorgente preferita</label>
                                                <br>
                                                <select name="preferenzaSorgente" id="preferenzaSorgente">
                                                    <option selected="selected" value="<%=inter.getTipoSorgente()%>"><%=inter.getTipoSorgente()%></option>
                                                        <%
                                while (it.hasNext())
                                {
                                    TipoSorgenteBean bean = (TipoSorgenteBean) it.next();
                            %>
                                                    <option value="<%=bean.getTipo() %>"><%=bean.getTipo()%></option>
                                                        <%
                                }
                            %>
                                            </td>
                                        </tr>
                                        <%
                                                }
                                            }
                                        %>
                                        <tr>
                                            <td>
                                                <label for="percentualeUtilizzo1">Percentuale Utilizzo "<%= inter.getTipoSorgente() %>":</label>
                                                <br>
                                                <input type="range" id="percentualeUtilizzo1" name="<%= inter.getTipoSorgente() %>" min="0" max="100" value="<%= inter.getPercentualeUtilizzoSorgente() %>" oninput="updateSliderValue(this, '<%= inter.getTipoSorgente() %>')">
                                                <span id="<%= inter.getTipoSorgente() %>Value"><%= inter.getPercentualeUtilizzoSorgente() %></span>%
                                            </td>
                                        </tr>
                                        <script>
                                            function updateSliderValue(input, tipoSorgente) {
                                                // Ottieni l'elemento span che mostra il valore
                                                var valueSpan = document.getElementById(tipoSorgente + "Value");

                                                // Aggiorna il valore nel tuo elemento span
                                                valueSpan.textContent = input.value;
                                            }
                                        </script>
                                        <%
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        %>
                                        <tr>
                                            <td class="sortable-list-container">
                                                <ul id="sortable-list">
                                                    <% if (parametriIA != null && !parametriIA.isEmpty()) {
                                                        for (ParametriIABean parametri : parametriIA) {
                                                            if(parametri.getPiano().equalsIgnoreCase("Personalizzato"))
                                                            {
                                                                for (InteragisceBean inter : interazioneParametri) {
                                                                    if(parametri.getIdParametro() == inter.getIdParametro()) {
                                                    %>
                                                    <li class="ui-state-default"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span><%=inter.getTipoSorgente()%></li>

                                                    <% }
                                                    }
                                                    }
                                                    }
                                                    }
                                                    %>
                                                </ul>
                                            </td>
                                        </tr>
                                    </table>
                                    <input type="hidden" name="sortableListData" id="sortableListData">
                                    <input type="submit" value="Invia">
                                    <script>
                                        $(function() {
                                            $("#sortable-list").sortable();
                                            $("#sortable-list").disableSelection();

                                            $("#priority-form-1").submit(function(event) {
                                                event.preventDefault();
                                                var priorities = [];

                                                $("#sortable-list li").each(function(index) {
                                                    var itemText = $(this).text().trim();
                                                    var tipoSorgente = $(this).data("tipo-sorgente");

                                                    priorities.push({ index: index, text: itemText, tipoSorgente: tipoSorgente });
                                                });

                                                // Imposta i dati della lista nell'input hidden prima di inviare il modulo
                                                $("#sortableListData").val(JSON.stringify(priorities));

                                                // Ora puoi inviare il modulo al server con i dati della lista
                                                this.submit();
                                            });

                                            // Aggiungi il gestore degli eventi al clic su un elemento <li>
                                            $("#sortable-list li").on("click", function() {
                                                var tipoSorgenteValue = $(this).text().trim();
                                                alert("Valore di tipoSorgente: " + tipoSorgenteValue);
                                                // Esegui altre azioni o invia il valore alla servlet come necessario
                                            });
                                        });

                                    </script>
                                </form>
                            </div>
                        </div>

                </div>
            </div>
        </div>
        <div class="section" onclick="toggleExpansion(6)">
            <a href="DatiController?action=generaDashboard">
                <img src="img/indietro.png" id="img"></a>
            <div class="content">
                <div class="initial-content">
                <!-- Contenuto della sezione 6 -->
                    <h3>Previsioni meteo</h3>
                    <table>
                        <thead>
                        <%
                            if (condizioni != null && !condizioni.isEmpty()) {
                        %>
                        <div class="text">Condizioni Metereologiche</div>
                        <tr>
                            <th>Data:</th>
                            <th>Ora:</th>
                            <th>Previsione:</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            for (MeteoBean bean : condizioni) {
                        %>
                        <tr>
                            <td> <%= bean.getDataRilevazione() %></td>
                            <td> <%= bean.getOraRilevazione() %></td>
                            <td> <%= bean.getCondizioniMetereologiche()%></td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                    <%
                        }
                    %>
                    </tbody>
                    </table>
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

        function Observer() {
            $.get("DatiController?action=dashboardObserver", function(data)  {
                console.log(data.nomeMetodo);
                if(data.nomeMetodo === "aggiornaPoi" || data.nomeMetodo === "aggiornaPoi" ) {
                    $("#percentualeBatterie").load(window.location.href + " #percentualeBatterie");
                    updateBattery();
                } else if(data.nomeMetodo === "aggiornaPoi" || data.nomeMetodo === "aggiornaPoi" ) {
                    $("#consumoEdifici").load(window.location.href + " #consumoEdifici");
                } else if(data.nomeMetodo === "setConsumoGiornaliero" || data.nomeMetodo === "setDataConsumo" ) {
                    $("#sommaProduzione").load(window.location.href + " #sommaProduzione");
                }

                setTimeout(Observer, 10000);
            });
        }

        function updateBattery() {
            // Ottieni il nuovo valore della batteria dal campo nascosto
            var batteryValue = document.getElementById("batteryInput").value;

            // Esegui le azioni necessarie con il nuovo valore della batteria
            // Aggiorna la visualizzazione della batteria, come hai già fatto nella funzione originale
            const battery = document.getElementById('battery');
            const batteryText = document.getElementById('batteryText');
            const spans = battery.querySelectorAll('span');
            let value = parseInt(batteryValue, 10);

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
        Observer();


    </script>

</body>
</html>