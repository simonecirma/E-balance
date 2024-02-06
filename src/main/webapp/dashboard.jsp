<%@ page import="java.util.List" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    float percentualeBatterie = (float) request.getAttribute("percentualeBatterie");
    float consumoEdifici[] = (float[]) request.getAttribute("consumoEdifici");
    List<ArchivioConsumoBean> archivioConsumo = (List<ArchivioConsumoBean>) request.getAttribute("archivioConsumo");
    List<MeteoBean> condizioni = (List<MeteoBean>) request.getAttribute("condizioniMeteo");
    List<MeteoBean> settimana = (List<MeteoBean>) request.getAttribute("condizioniSettimanali");
    float produzioneSorgente[] = (float[]) request.getAttribute("produzioneSorgente");
    float produzioneSEN = (float) request.getAttribute("produzioneSEN");
    List<ParametriIABean> parametriIA = (List<ParametriIABean>) request.getAttribute("parametriIA");
    List<InteragisceBean> interazioneParametri = (List<InteragisceBean>) request.getAttribute("interazioneParametri");
    List<InteragisceBean> parametriAttivi = (List<InteragisceBean>) request.getAttribute("parametriAttivi");
    List<TipoSorgenteBean> tipoSorgente = (List<TipoSorgenteBean>) request.getAttribute("tipoSorgente");
    String result = (String) request.getAttribute("result");
    synchronized(session) {
        session = request.getSession();
        idAmministratore = (int) session.getAttribute("idAmministratore");
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link href="css/dashboard.css" rel="stylesheet" type="text/css">

    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
    <div class="section" id="section1" onclick="toggleExpansion(1)">
        <a href="DatiController?action=generaDashboard">
            <img src="img/indietro.png" id="img"></a>
        <div class="contentEnergia">
            <div class="initial-content-energia">
                <!-- Contenuto della sezione 1 -->
                <h3>Energia prodotta</h3>
                <div id="curveChart_sommaProduzione1"></div>
                <div id = "sommaProduzione" name = "sommaProduzione">
                    Energia prodotta:
                    <% if (produzioneSorgente != null) {
                        for (int i=0;i<produzioneSorgente.length;i++) {
                    %>
                    <%= produzioneSorgente[i] %>
                    <%
                            }
                        }
                    %>
                </div>
            </div>
            <div class="expanded-content-energia">
                <div class="chart-container-consumi">
                    <div id="curveChart_sommaProduzione2"></div>
                </div>
                <div class="elettrico-nazionale" id="elettrico-nazionale">
                    Energia da Servizio Elettrico Nazionale: <%=produzioneSEN%> kWh
                </div>
            </div>
        </div>
    </div>
    <div class="section" id="section2" onclick="toggleExpansion(2)">
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
                <div id="curve_chart_ConsumiEdificio1"></div>
            </div>
            <div class="expanded-content-consumiAttuali">
                <div class="chart-container-consumi">
                    <div id="curve_chart_ConsumiEdificio2"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="section" id="section3" onclick="toggleExpansion(3)">
        <a href="DatiController?action=generaDashboard">
            <img src="img/indietro.png" id="img"></a>
        <div class="content">
            <div class="initial-content">
                <!-- Contenuto della sezione 4 -->
                <h3>Batteria</h3>
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
                <div id = "venditaDiv" name = "venditaDiv">
                    <%
                        if (percentualeBatterie >= 80) {
                    %>
                    <br>
                    <h5>C'è una nuova possibilità di vendita</h5>
                    <br><br>
                    <%
                        }
                        if (percentualeBatterie <= 5) {
                    %>
                    <br>
                    <h5>Batteria scarica, protocollo emergenza attivo!</h5>
                    <br><br>
                    <%
                        }
                    %>
                    <%
                        if (percentualeBatterie >= 80) {
                    %>
                    <h1> Hai la possibilità di vendere. Vuoi vendere? </h1>
                    <form id="vendita" action="AmministratoreController?action=vendita" method="POST">
                        <input type="hidden" name="idAmministratore" value="<%=idAmministratore%>">
                        <button class="button17">
                            <svg class="svgIcon" viewBox="0 0 384 512">
                                <path
                                        d="M214.6 41.4c-12.5-12.5-32.8-12.5-45.3 0l-160 160c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L160 141.2V448c0 17.7 14.3 32 32 32s32-14.3 32-32V141.2L329.4 246.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3l-160-160z"
                                ></path>
                            </svg>
                        </button>
                    </form>
                    <%
                        }
                    %>
                </div>

            </div>
            <div class="expanded-content">

            </div>
        </div>
    </div>
    <div class="section" onclick="toggleExpansion(4)">
        <a href="DatiController?action=generaDashboard">
            <img src="img/indietro.png" id="img"></a>
        <div class="contentConsumi">
            <div class="initial-content-consumi">
                <h3>Archivio Consumi</h3>
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
                        <%
                            if (archivioConsumo != null && !archivioConsumo.isEmpty()) {
                        %>
                    <div id="archivioConsumi" name="archivioConsumi">
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
                                <td> <%= archivio.getConsumoGiornaliero() %>  kWh</td>
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

                </div>
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
                <div id="parametriIA" name="parametriIA">
                    <%
                        if (parametriIA != null && !parametriAttivi.isEmpty()) {
                    %>
                    <div class="visTab">
                  <table id="vistabIA">
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
                    <table id="vistab1IA">
                        <tbody>
                        <%
                            for (InteragisceBean par : parametriAttivi) {
                        %>
                        <tr>
                            <td>Percentuale Utilizzo "<%= par.getTipoSorgente() %>":</td>
                            <td><%= par.getPercentualeUtilizzoSorgente()%> %</td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                    <table id="vistab1IA">
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
                </div>
                </div>
            </div>
            <div class="expanded-content">
                <div class="expandendIA">
                    <div class="tabelle">
                        <div class="tipo">
                            <div class="title">Piano per la salvaguardia ambientale</div>
                        </div>
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
                                <td><%= inter.getPercentualeUtilizzoSorgente()%> %</td>
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
                        <a href="DatiController?action=selezionaPiano&piano=SalvaguardiaAmbientale"><input type="submit" class="btnIA" value="Seleziona"></a>
                    </div>
                    <div class="tabelle2">
                        <div class="tipo">
                            <div class="title">Piano per l'efficienza economica</div>
                        </div>
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
                                <td><%= inter.getPercentualeUtilizzoSorgente()%> %</td>
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
                        <a href="DatiController?action=selezionaPiano&piano=EfficienzaEconomica"><input type="submit" class="btnIA" value="Seleziona"></a>
                    </div>
                    <div class="tabelle3">
                        <div class="tipo">
                            <div class="title">Piano personalizzato</div>
                        </div>
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
                            <div class="contIA">
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
                                                    }
                                                }
                                            }
                                        }
                                    }
                                %>
                                <% if (parametriIA != null && !parametriIA.isEmpty()) {
                                    for (ParametriIABean parametri : parametriIA) {
                                        if(parametri.getPiano().equalsIgnoreCase("Personalizzato"))
                                        {
                                            for (InteragisceBean inter : interazioneParametri) {
                                                if(parametri.getIdParametro() == inter.getIdParametro()) {
                                %>
                                <tr>
                                    <td>
                                        <label for="percentualeUtilizzo1">Percentuale Utilizzo "<%= inter.getTipoSorgente() %>":</label>
                                        <br>
                                        <input type="range" id="percentualeUtilizzo1" name="<%= inter.getTipoSorgente() %>" min="1" max="100" value="<%= inter.getPercentualeUtilizzoSorgente() %>" oninput="updateSliderValue(this, '<%= inter.getTipoSorgente() %>')">
                                        <span id="<%= inter.getTipoSorgente() %>Value"><%= inter.getPercentualeUtilizzoSorgente() %></span>%
                                    </td>
                                </tr>
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
                                            <%
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            %>
                                        </ul>
                                    </td>
                                </tr>
                            </table>
                        </div>
                            <input type="hidden" name="sortableListData" id="sortableListData">
                            <input type="submit" class="btnIA" value="Invia">

                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class="section" onclick="toggleExpansion(6)">
        <a href="DatiController?action=generaDashboard">
            <img src="img/indietro.png" id="img"></a>
        <div class="contentMeteo">
            <div class="initial-content-meteo">
                <!-- Contenuto della sezione 6 -->
                <h3>Previsioni meteo</h3>
                <!--<ul id="listaCondizioni"></ul>-->
                <div id="meteo" name="meteo">
                    <%
                        if (settimana != null && !settimana.isEmpty()) {
                    %>
                    <div class="contentMeteoTab">
                        <table id="meteoTable">
                            <thead>

                            <tr>
                                <th>Data</th>
                                <th style="display: none">Ora</th>
                                <th colspan="2">Previsione</th>
                                <th>Pioggia</th>
                                <th>Vento</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                for (MeteoBean bean : settimana) {
                            %>
                            <tr>
                                <td><%= bean.getDataRilevazione() %></td>
                                <td style="display: none"> <%= bean.getOraRilevazione() %></td>
                                <td></td>
                                <td id="previsione<%= bean.getIdMeteo() %>"> <%= bean.getCondizioniMetereologiche()%></td>
                                <td> <%= bean.getProbabilitaPioggia()%>%</td>
                                <td> <%= bean.getVelocitaVento()%>km/h</td>
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
                </div>
            </div>
            <div class="expanded-content-meteo">
                <div id="meteo1" name="meteo1">
                    <%
                        if (condizioni != null && !condizioni.isEmpty()) {
                    %>
                    <div class="contentMeteoTab">
                        <table id="meteoTable1">
                            <thead>
                            <tr>
                                <th>Data</th>
                                <th>Ora</th>
                                <th colspan="2">Previsione</th>
                                <th>Pioggia</th>
                                <th>Vento</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                for (MeteoBean bean : condizioni) {
                            %>
                            <tr>
                                <td> <%= bean.getDataRilevazione() %></td>
                                <td> <%= bean.getOraRilevazione() %></td>
                                <td></td>
                                <td id="previsione<%= bean.getIdMeteo() %>"> <%= bean.getCondizioniMetereologiche()%></td>
                                <td> <%= bean.getProbabilitaPioggia()%>%</td>
                                <td> <%= bean.getVelocitaVento()%>km/h</td>
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
                </div>
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


    function updateSliderValue(input, tipoSorgente) {
        // Ottieni l'elemento span che mostra il valore
        var valueSpan = document.getElementById(tipoSorgente + "Value");

        // Aggiorna il valore nel tuo elemento span
        valueSpan.textContent = input.value;
    }


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

    var myChart;
    var myChart2;

    function AggiornaArchivioConsumi() {
        AggiornaArchivioConsumi1();
        AggiornaArchivioConsumi2();
    }

    function AggiornaArchivioConsumi1() {
        var tabella = document.getElementById("consumi");
        var date = [];
        var consumi = [];
        var colors = [];

        for (var i = 1; i < tabella.rows.length; i++) {
            date.push(tabella.rows[i].cells[0].innerText);
            consumi.push(parseInt(tabella.rows[i].cells[1].innerText));

            var randomColor = 'rgba(91, 143, 248, 0.4)';
            colors.push(randomColor);
        }

        var ctx = document.getElementById('istogramma').getContext('2d');
        if (myChart) {
            myChart.destroy(); // Distrugge il grafico esistente
        }
        myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: date,
                datasets: [{
                    label: 'Consumo Giornaliero (kWh)',
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
    }

    function AggiornaArchivioConsumi2() {
        var tabella = document.getElementById("consumi");
        var date = [];
        var consumi = [];
        var colors = [];

        for (var i = 1; i < tabella.rows.length; i++) {
            date.push(tabella.rows[i].cells[0].innerText);
            consumi.push(parseInt(tabella.rows[i].cells[1].innerText));

            var randomColor = 'rgba(91, 143, 248, 0.4)';
            colors.push(randomColor);
        }

        var ctx2 = document.getElementById('istogramma2').getContext('2d');
        if (myChart2) {
            myChart2.destroy(); // Distrugge il grafico esistente
        }
        myChart2 = new Chart(ctx2, {
            type: 'bar',
            data: {
                labels: date,
                datasets: [{
                    label: 'Consumo Giornaliero (kWh)',
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
            span.style.backgroundColor = '#28293f';
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

    function drawChartsConsumiEdificio() {
        drawChartConsumiEdificio();
        drawChartConsumiEdificio2();
    }

    function drawChartConsumiEdificio() {
        var consumoEdificiDiv = document.getElementById('consumoEdifici');
        var dataArray = consumoEdificiDiv.innerText.match(/\d+(\.\d+)?/g);

        // Crea un DataTable con i dati estratti
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Label');
        data.addColumn('number', 'Consumo (kWh)');

        for (var i = 0; i < dataArray.length; i++) {
            var label = "Edificio " + (i + 1);
            var consumo = parseFloat(dataArray[i]);
            data.addRow([label, consumo]);
        }

        var options = {
            curveType: 'function',
            legend: {
                position: 'bottom',

                textStyle: {
                    color: '#fff' // Cambia il colore del testo della legenda
                }
            },
            chartArea: {height: '50px'},
            backgroundColor: 'transparent', // Imposta il colore dello sfondo del grafico
            colors: ['#f84444'], // Imposta il colore della linea del grafico
            hAxis: {
                textStyle: {color: '#fff'} // Cambia il colore delle etichette dell'asse x
            },
            vAxis: {
                textStyle: {color: '#fff'} // Cambia il colore delle etichette dell'asse y
            } // Imposta l'altezza desiderata qui
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart_ConsumiEdificio1'));

        chart.draw(data, options);
    }

    function drawChartConsumiEdificio2() {
        var consumoEdificiDiv = document.getElementById('consumoEdifici');
        var dataArray = consumoEdificiDiv.innerText.match(/\d+(\.\d+)?/g);

        // Crea un DataTable con i dati estratti
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Label');
        data.addColumn('number', 'Consumo (kWh)');

        for (var i = 0; i < dataArray.length; i++) {
            var label = "Edificio " + (i + 1);
            var consumo = parseFloat(dataArray[i]);
            data.addRow([label, consumo]);
        }

        var options = {
            curveType: 'function',
            legend: {
                position: 'bottom',

                textStyle: {
                    color: '#fff' // Cambia il colore del testo della legenda
                }
            },
            chartArea: {height: '50px'},
            backgroundColor: 'transparent', // Imposta il colore dello sfondo del grafico
            colors: ['#f84444'], // Imposta il colore della linea del grafico
            hAxis: {
                textStyle: {color: '#fff'} // Cambia il colore delle etichette dell'asse x
            },
            vAxis: {
                textStyle: {color: '#fff'} // Cambia il colore delle etichette dell'asse y
            }// Imposta l'altezza desiderata qui
        };

        var chart1 = new google.visualization.LineChart(document.getElementById('curve_chart_ConsumiEdificio2'));

        chart1.draw(data, options);
    }

    function drawChartProduzione() {
        drawChartProduzione1();
        drawChartProduzione2();
    }

    function drawChartProduzione1() {
        var sommaProduzioneDiv = document.getElementById('sommaProduzione');
        var dataArray = sommaProduzioneDiv.innerText.match(/\d+(\.\d+)?/g);

        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Sorg');
        data.addColumn('number', 'Produzione (kWh)');

        for (var i = 0; i < dataArray.length; i++) {
            var sorg = "Valore " + (i + 1);
            var prod = parseFloat(dataArray[i]);
            data.addRow([sorg, prod]);
        }

        var options = {
            curveType: 'function',
            legend: {
                position: 'bottom',

                textStyle: {
                    color: '#fff' // Cambia il colore del testo della legenda
                }
            },

        backgroundColor: 'transparent', // Imposta il colore dello sfondo del grafico
            colors: ['#00fa9a'], // Imposta il colore della linea del grafico
            hAxis: {
                textStyle: {color: '#fff'} // Cambia il colore delle etichette dell'asse x
            },
            vAxis: {
                textStyle: {color: '#fff'} // Cambia il colore delle etichette dell'asse y
            }
        };

        var chart = new google.visualization.LineChart(document.getElementById('curveChart_sommaProduzione1'));

        chart.draw(data, options);
    }


    function drawChartProduzione2() {
        var sommaProduzioneDiv = document.getElementById('sommaProduzione');
        var dataArray = sommaProduzioneDiv.innerText.match(/\d+(\.\d+)?/g);

        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Sorg');
        data.addColumn('number', 'Produzione (kWh)');

        for (var i = 0; i < dataArray.length; i++) {
            var sorg = "Valore " + (i + 1);
            var prod = parseFloat(dataArray[i]);
            data.addRow([sorg, prod]);
        }

        var options = {
            curveType: 'function',
            legend: {
                position: 'bottom',

                textStyle: {
                    color: '#fff' // Cambia il colore del testo della legenda
                }
            },
            backgroundColor: 'transparent', // Imposta il colore dello sfondo del grafico
            colors: ['#00fa9a'], // Imposta il colore della linea del grafico
            hAxis: {
                textStyle: {color: '#fff'} // Cambia il colore delle etichette dell'asse x
            },
            vAxis: {
                textStyle: {color: '#fff'} // Cambia il colore delle etichette dell'asse y
            }

        };

        var chart = new google.visualization.LineChart(document.getElementById('curveChart_sommaProduzione2'));

        chart.draw(data, options);
    }
    function aggiungiIconaPrevisioneIniziale(tableId) {
        aggiungiIconaPrevisione(tableId); // Chiamata alla funzione per aggiungere le icone previsione
    }

    // Chiamata alla funzione per aggiungere le icone previsione quando il documento è completamente caricato
    $(document).ready(function() {
        aggiungiIconaPrevisioneIniziale("meteoTable"); // Chiamata alla funzione per aggiungere le icone previsione inizialmente per la tabella meteoTable
        aggiungiIconaPrevisioneIniziale("meteoTable1"); // Chiamata alla funzione per aggiungere le icone previsione inizialmente per la tabella meteoTable1
        Observer(); // Avvio dell'observer
    });

    function Observer() {
        $.get("DatiController?action=dashboardObserver", function (data) {
            if (data.percentualeBatteriaUpdate) {
                $("#percentualeBatterie").load(window.location.href + " #percentualeBatterie");
                updateBattery();
                $("#venditaDiv").load(window.location.href + " #venditaDiv");
            }
            if (data.produzioneAttualeUpdate) {
                $("#sommaProduzione").load(window.location.href + " #sommaProduzione");
                $("#elettrico-nazionale").load(window.location.href + " #elettrico-nazionale");
                google.charts.load('current', {'packages': ['corechart']});
                google.charts.setOnLoadCallback(drawChartProduzione);
            }
            if (data.consumoAttualeUpdate) {
                $("#consumoEdifici").load(window.location.href + " #consumoEdifici");
                google.charts.load('current', {'packages': ['corechart']});
                google.charts.setOnLoadCallback(drawChartsConsumiEdificio);
            }
            if (data.archivioConsumiUpdate) {
                $("#archivioConsumi").load(window.location.href + " #archivioConsumi");
                AggiornaArchivioConsumi();
            }
            if (data.meteoUpdate) {
                $("#meteo").load(window.location.href + " #meteo", function() {
                    aggiungiIconaPrevisione("meteoTable");
                });

                $("#meteo1").load(window.location.href + " #meteo1", function() {
                    aggiungiIconaPrevisione("meteoTable1");
                });
            }

            if (data.parametriAttiviUpdate) {
                $("#parametriIA").load(window.location.href + " #parametriIA");
            }

            setTimeout(Observer, 10000);
        });
    }



        // Ottieni il pulsante
        var button = document.querySelector('.btnIA');

        // Aggiungi un listener al click
        button.addEventListener('click', function() {
        // Rimuovi la classe 'selected' da tutti i bottoni
        document.querySelectorAll('.btnIA').forEach(function(btn) {
            btn.classList.remove('selected');
            btn.value = 'Seleziona';
        });
        // Aggiungi la classe 'selected' solo al bottone cliccato
        this.classList.add('selected');
        this.value = 'Selezionato';
    });


    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChartsConsumiEdificio);
    google.charts.load('current', {'packages': ['corechart']});
    google.charts.setOnLoadCallback(drawChartProduzione);
    AggiornaArchivioConsumi();
    updateBattery();
    Observer();

    // Funzione per aggiungere le icone previsione
    function aggiungiIconaPrevisione(tableId) {
        var tabella = document.getElementById(tableId);
        var righe = tabella.getElementsByTagName("tr");

        // Per ogni riga della tabella, tranne l'intestazione
        for (var i = 1; i < righe.length; i++) {
            var cellaIcona = righe[i].getElementsByTagName("td")[2]; // Cell contenente l'icona previsione
            var cellaPrevisione = righe[i].getElementsByTagName("td")[3]; // Cell contenente la previsione
            var previsione = cellaPrevisione.textContent.trim().toLowerCase(); // Testo della previsione

            // Rimuove eventuali icone già presenti nella cella dell'icona previsione
            while (cellaIcona.firstChild) {
                cellaIcona.removeChild(cellaIcona.firstChild);
            }

            // Determina l'icona corrispondente alla previsione
            var icona = determinaIconaPrevisione(previsione);

            // Aggiunge l'immagine dell'icona alla cella dell'icona previsione
            if (icona !== "") {
                var img = document.createElement("img");
                img.src = icona;
                img.alt = previsione;
                cellaIcona.appendChild(img);
            }
        }
    }


    // Funzione per determinare l'icona corrispondente alla previsione
    function determinaIconaPrevisione(previsione) {
        // In questo esempio, vengono forniti solo alcuni tipi di previsione con le relative immagini
        // Aggiungi più casi se necessario
        switch (previsione) {
            case "sereno":
                return "img/sole.png";
            case "ventilato":
                return "img/vento.png";
            case "nuvoloso":
                return "img/nuvole.png";
            case "nevoso":
                return "img/neve.png";
            case "soleggiato":
                return "img/sole.png";
            case "piovoso":
                return "img/pioggia.png";
            default:
                return "";
        }
    }

    // Chiamata alla funzione per aggiungere le icone previsione quando il documento è completamente caricato
    window.onload = function() {
        aggiungiIconaPrevisione("meteoTable");
        aggiungiIconaPrevisione("meteoTable1");
    };

    function aggiornaTabella() {
        // Codice per aggiornare la tabella con i nuovi dati (utilizzando l'observer)

        // Dopo aver aggiornato i dati, riaggiungi le icone delle previsioni
        aggiungiIconaPrevisione();
    }



    // Definisci l'observer per rilevare le modifiche alla tabella
    var observer = new MutationObserver(function(mutations) {
        mutations.forEach(function(mutation) {
            // Controlla se ci sono cambiamenti nella tabella meteoTable
            if (mutation.type === 'childList' && mutation.target.id === 'meteoTable') {
                // Se ci sono cambiamenti nella tabella, aggiorna la tabella meteoTable
                aggiornaTabella("meteoTable");
            }
            // Controlla se ci sono cambiamenti nella tabella meteoTable1
            if (mutation.type === 'childList' && mutation.target.id === 'meteoTable1') {
                // Se ci sono cambiamenti nella tabella, aggiorna la tabella meteoTable1
                aggiornaTabella("meteoTable1");
            }
        });
    });

    // Configura e avvia l'observer per entrambe le tabelle
    var config = { childList: true };

    // Aggiunge l'observer per la tabella meteoTable
    observer.observe(document.getElementById("meteo"), config);

    // Aggiunge l'observer per la tabella meteoTable1
    observer.observe(document.getElementById("meteo1"), config);


</script>

</body>
</html>