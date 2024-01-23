<%@ page import="java.util.List" %>
<%@ page import="com.c17.ebalance.ebalance.model.entity.*" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //List<BatteriaBean> batterie = (List<BatteriaBean>) request.getAttribute("batteria");
    float percentualeBatterie = (float) request.getAttribute("percentualeBatterie");
    //List<ConsumoEdificioBean> consumi = (List<ConsumoEdificioBean>) request.getAttribute("consumoEdificio");
    float consumoEdifici = (float) request.getAttribute("consumoEdifici");
    List<ArchivioConsumoBean> archivioConsumo = (List<ArchivioConsumoBean>) request.getAttribute("archivioConsumo");
    //List<SorgenteBean> sorgenti = (List<SorgenteBean>) request.getAttribute("sorgente");
    String sommaProduzione[][] = (String[][]) request.getAttribute("sommaProduzione");
    List<ParametriIABean> parametriIA = (List<ParametriIABean>) request.getAttribute("parametriIA");
    List<InteragisceBean> interazioneParametri = (List<InteragisceBean>) request.getAttribute("interazioneParametri");
    List<InteragisceBean> parametriAttivi = (List<InteragisceBean>) request.getAttribute("parametriAttivi");
    List<TipoSorgenteBean> tipoSorgente = (List<TipoSorgenteBean>) request.getAttribute("tipoSorgente");
    String result = (String) request.getAttribute("result");
%>
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
    <script>
        function Observer() {
            $.ajax({
                url: "DatiController?action=dashboardObserver",
                method: "POST",
                dataType: "json",
                success: function (response) {
                    var updatePage = response.updatePage;
                    console.log(updatePage);

                    if (updatePage === true) {
                        window.location.reload()
                    }
                    Observer();
                },
                error: function () {
                    console.error("Errore nella richiesta AJAX");
                }
            });
        }

        // Chiamare immediatamente la funzione all'avvio della pagina
        Observer();
    </script>
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
                    <input type="hidden" id="batteryInput" value="<%= percentualeBatterie%>" oninput="updateBattery()">
                    <div id="batteryText">0%</div>
                </div>
                <div class="expanded-content">
                </div>
            </div>
        </div>

        <div class="section" onclick="toggleExpansion(2)">
            <a href="DatiController?action=generaDashboard">
                <img src="img/indietro.png" id="img"></a>
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
            <a href="DatiController?action=generaDashboard">
                <img src="img/indietro.png" id="img"></a>
            <div class="content">
                <div class="initial-content">
                <!-- Contenuto della sezione 3 -->
                    <h3>Consumi attuali</h3>
                    <%=consumoEdifici%>
                </div>
                <div class="expanded-content">
                    <%=consumoEdifici%>
                </div>
            </div>
        </div>
        <div class="section" onclick="toggleExpansion(4)">
            <a href="DatiController?action=generaDashboard">
                <img src="img/indietro.png" id="img"></a>
            <div class="content">
                <div class="initial-content">
                <!-- Contenuto della sezione 2 -->
                    <h3>Archivio Consumi</h3>
                    <%
                        if (archivioConsumo != null && !archivioConsumo.isEmpty()) {
                    %>
                    <table >
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
                <div class="expanded-content">

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