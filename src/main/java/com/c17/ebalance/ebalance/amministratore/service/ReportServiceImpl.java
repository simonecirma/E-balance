package com.c17.ebalance.ebalance.amministratore.service;

import com.c17.ebalance.ebalance.contratto.service.ContrattoService;
import com.c17.ebalance.ebalance.contratto.service.ContrattoServiceImpl;
import com.c17.ebalance.ebalance.dati.service.ProduzioneService;
import com.c17.ebalance.ebalance.dati.service.ProduzioneServiceImpl;
import com.c17.ebalance.ebalance.dati.service.ConsumoService;
import com.c17.ebalance.ebalance.dati.service.ConsumoServiceImpl;
import com.c17.ebalance.ebalance.model.DAO.ReportDAO;
import com.c17.ebalance.ebalance.model.DAO.ReportDAOImpl;
import com.c17.ebalance.ebalance.model.entity.ContrattoBean;
import com.c17.ebalance.ebalance.model.entity.ReportBean;
import com.c17.ebalance.ebalance.model.entity.VenditaBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    public VenditaService venditaService = new VenditaServiceImpl();
    public ConsumoService consumoService = new ConsumoServiceImpl();
    public ProduzioneService produzioneService = new ProduzioneServiceImpl();
    public ContrattoService contrattoService = new ContrattoServiceImpl();
    private ReportDAO reportDAO = new ReportDAOImpl();

    @Override
    public List<ReportBean> visualizzaReport() throws SQLException {
        return reportDAO.visualizzaReport();
    }

    @Override
    public int ultimoReport() throws SQLException {
        return reportDAO.ultimoReport();
    }

    @Override
    public void aggiungiReport(ReportBean report) throws SQLException {
        reportDAO.aggiungiReport(report);
    }

    @Override
    public ReportBean generaReport(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        ReportBean report = new ReportBean();

        Date dataInizio = Date.valueOf(request.getParameter("dataInizio"));
        Date dataFine = Date.valueOf(request.getParameter("dataFine"));
        List<VenditaBean> vendite = new ArrayList<>();
        vendite = venditaService.getVendite(dataInizio, dataFine);
        float ricavo = venditaService.getRicavoTotalePerData(dataInizio, dataFine);
        ContrattoBean bean = contrattoService.getContrattoAttivo(dataInizio, dataFine);
        float energiaConsumo = consumoService.getConsumoPerData(dataInizio, dataFine);
        float energiaRinnovabileProdotta = produzioneService.energiaRinnovabileProdottaPerData(dataInizio, dataFine);
        float costoMedioUnitario = bean.getCostoMedioUnitario();
        float correnteConsumata = energiaConsumo - energiaRinnovabileProdotta;
        float speseCorrente = (energiaConsumo - energiaRinnovabileProdotta) * costoMedioUnitario;
        float resocontoFinale = ricavo - speseCorrente;

        HttpSession session = request.getSession(false);
        String nome = "";
        String cognome = "";
        int id = 0;
        if (session != null && session.getAttribute("nome") != null && session.getAttribute("cognome") != null) {
            nome = (String) session.getAttribute("nome");
            cognome = (String) session.getAttribute("cognome");
            id = (int) session.getAttribute("idAmministratore");
        }
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        java.util.Date date = null;
        try {
            // Effettua il parsing della stringa nella data
            date = sdf.parse(formattedDate);

        } catch (ParseException e) {
            e.printStackTrace();
            // Gestisci eventuali eccezioni di parsing
        }

        String descrizione = "";
        String descrizione1 = "";
        int n = reportDAO.ultimoReport() + 1;
        String file = "Report" + n + ".pdf";

        try {
            String servletPath = request.getServletContext().getRealPath("");
            String filePath = servletPath + "report" + File.separator + "Report" + n + ".pdf";

            // Carica il template PDF esistente
            File templateFile = new File(servletPath + "report" + File.separator + "TemplateReport.pdf");
            File templateFile2 = new File(servletPath + "report" + File.separator + "TemplateReport2.pdf");

            // Posizione delle celle nel template da riempire

            // Posizione Corrente Elettrica Consumata
            float xCoordinate1 = 255;
            float yCoordinate1 = 446.5F;

            //Posizione Ricavo Totale
            float xCoordinate2 = 370;
            float yCoordinate2 = 192;

            // Posizione amministratore
            float xCoordinate3 = 450;
            float yCoordinate3 = 678;

            // Posizione numero report
            float xCoordinate4 = 130;
            float yCoordinate4 = 678;

            // Posizione data emissione
            float xCoordinate5 = 185;
            float yCoordinate5 = 655;

            // Posizione descrizione
            float xCoordinate10 = 50;
            float yCoordinate10 = 422.5F;

            // Posizione descrizione1
            float xCoordinate6 = 50;
            float yCoordinate6 = 446.5F;


            // Posizione data inizio
            float xCoordinate7 = 353;
            float yCoordinate7 = 655;

            // Posizione data fine
            float xCoordinate8 = 475;
            float yCoordinate8 = 655;

            // Posizione Energia Venduta
            float xCoordinate9 = 255;
            float yCoordinate9 = 422.5F;

            // Posizione Ricavo per ogni vendita
            float xCoordinate12 = 370;
            float yCoordinate12 = 422.5F;

            //Posizione Soldi Spesi Corrente
            float xCoordinate11 = 475;
            float yCoordinate11 = 446.5F;
            float xCoordinate13 = 475;
            float yCoordinate13 = 192;

            //Posizione Resoconto Finale
            float xCoordinate14 = 420;
            float yCoordinate14 = 160;

            //Posizione Nome Ente Contratto
            float xCoordinate15 = 304;
            float yCoordinate15 = 76;

            //Posizione Costo Medio Unitario
            float xCoordinate16 = 304;
            float yCoordinate16 = 62;

            //Posizione Prezzo Vendita
            float xCoordinate17 = 304;
            float yCoordinate17 = 48;

            int numVendite = vendite.size();
            if (numVendite <= 8) {
                PDDocument document = PDDocument.load(templateFile);
                // Ottieni la prima pagina del template
                PDPage page = document.getPage(0);

                // Apertura del flusso di contenuto per scrivere nel documento PDF
                PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

                // Scrivi i valori nel documento
                if (ricavo > 0) {
                    for (VenditaBean Vbean : vendite) {

                        descrizione = "VENDITA DEL ";

                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.newLineAtOffset(xCoordinate10, yCoordinate10);
                        contentStream.showText(descrizione + " " + Vbean.getDataVendita());
                        contentStream.endText();
                        yCoordinate10 -= 24;

                        contentStream.beginText();
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.newLineAtOffset(xCoordinate9, yCoordinate9);
                        contentStream.showText(String.valueOf(Vbean.getEnergiaVenduta()));
                        contentStream.endText();
                        yCoordinate9 -= 24;

                        contentStream.beginText();
                        contentStream.setNonStrokingColor(new Color(0, 128, 0)); // Verde scuro
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.newLineAtOffset(xCoordinate12, yCoordinate12);
                        contentStream.showText(String.valueOf("+" + Vbean.getRicavoTotale()));
                        contentStream.setNonStrokingColor(new Color(0, 0, 0)); // Nero
                        contentStream.endText();
                        yCoordinate12 -= 24;

                    }
                    contentStream.beginText();
                    contentStream.setNonStrokingColor(new Color(0, 128, 0)); // Verde scuro
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(xCoordinate2, yCoordinate2);
                    contentStream.showText(String.valueOf("+" + ricavo));
                    contentStream.setNonStrokingColor(new Color(0, 0, 0)); // Nero
                    contentStream.endText();
                }

                if (correnteConsumata > 0) {
                    descrizione1 = "SPESE CORRENTE";

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(xCoordinate6, yCoordinate6);
                    contentStream.showText(descrizione1);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(xCoordinate1, yCoordinate1);
                    contentStream.showText(String.valueOf(correnteConsumata));
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setNonStrokingColor(new Color(241, 5, 5)); // Rosso
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(xCoordinate11, yCoordinate11);
                    contentStream.showText(String.valueOf("-" + speseCorrente));
                    contentStream.setNonStrokingColor(new Color(0, 0, 0)); // Nero
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setNonStrokingColor(new Color(241, 5, 5)); // Rosso
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(xCoordinate13, yCoordinate13);
                    contentStream.showText(String.valueOf("-" + speseCorrente));
                    contentStream.setNonStrokingColor(new Color(0, 0, 0)); // Nero
                    contentStream.endText();
                }
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(xCoordinate14, yCoordinate14);
                if (resocontoFinale > 0) {
                    contentStream.setNonStrokingColor(new Color(0, 128, 0)); // Verde
                    contentStream.showText(String.valueOf("+" + resocontoFinale));
                    contentStream.endText();
                } else if (resocontoFinale < 0) {
                    contentStream.setNonStrokingColor(new Color(241, 5, 5)); // Rosso
                    contentStream.showText(String.valueOf(resocontoFinale));
                    contentStream.endText();
                } else {
                    contentStream.showText(String.valueOf(resocontoFinale));
                    contentStream.endText();
                }
                contentStream.setNonStrokingColor(new Color(0, 0, 0)); // Nero

                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(xCoordinate3, yCoordinate3);
                contentStream.showText(nome + " " + cognome);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(xCoordinate4, yCoordinate4);
                contentStream.showText(String.valueOf(n));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(xCoordinate5, yCoordinate5);
                contentStream.showText(formattedDate);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(xCoordinate7, yCoordinate7);
                contentStream.showText(String.valueOf(dataInizio));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(xCoordinate8, yCoordinate8);
                contentStream.showText(String.valueOf(dataFine));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setNonStrokingColor(new Color(47, 47, 47)); //Grigio
                contentStream.newLineAtOffset(xCoordinate15, yCoordinate15);
                contentStream.showText(bean.getNomeEnte());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate16, yCoordinate16);
                contentStream.showText("Costo Medio Unitario: " + bean.getCostoMedioUnitario());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate17, yCoordinate17);
                contentStream.showText("Prezzo Vendita Al kWH: " + bean.getPrezzoVendita());
                contentStream.endText();

                contentStream.close();

                document.save(filePath);
                document.close();
            } else {
                PDDocument document2 = PDDocument.load(templateFile2);
                // Ottieni la prima pagina del template
                PDPage page = document2.getPage(0);

                // Apertura del flusso di contenuto per scrivere nel documento PDF
                PDPageContentStream contentStream = new PDPageContentStream(document2, page, PDPageContentStream.AppendMode.APPEND, true, true);

                int maxElem = 9;
                int i = 0;

                if (correnteConsumata > 0) {
                    descrizione1 = "SPESE CORRENTE";

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(xCoordinate6, yCoordinate6);
                    contentStream.showText(descrizione1);
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(xCoordinate1, yCoordinate1);
                    contentStream.showText(String.valueOf(correnteConsumata));
                    contentStream.endText();

                    contentStream.beginText();
                    contentStream.setNonStrokingColor(new Color(241, 5, 5)); // Rosso
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(xCoordinate11, yCoordinate11);
                    contentStream.showText(String.valueOf("-" + speseCorrente));
                    contentStream.setNonStrokingColor(new Color(0, 0, 0)); // Nero
                    contentStream.endText();

                    i++;
                }

                int j = 0;
                while (i != maxElem) {
                    VenditaBean Vbean = vendite.get(j);
                    descrizione = "VENDITA DEL ";
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(xCoordinate10, yCoordinate10);
                    contentStream.showText(descrizione + " " + Vbean.getDataVendita());
                    contentStream.endText();
                    yCoordinate10 -= 24.5;

                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(xCoordinate9, yCoordinate9);
                    contentStream.showText(String.valueOf(Vbean.getEnergiaVenduta()));
                    contentStream.endText();
                    yCoordinate9 -= 24.5;

                    contentStream.beginText();
                    contentStream.setNonStrokingColor(new Color(0, 128, 0)); // Verde scuro
                    contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream.newLineAtOffset(xCoordinate12, yCoordinate12);
                    contentStream.showText(String.valueOf("+" + Vbean.getRicavoTotale()));
                    contentStream.setNonStrokingColor(new Color(0, 0, 0)); // Nero
                    contentStream.endText();
                    yCoordinate12 -= 24.5;

                    j++;
                    i++;
                    numVendite--;
                }
                yCoordinate12 += 24.5 * 9;
                i = 0;
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(xCoordinate3, yCoordinate3);
                contentStream.showText(nome + " " + cognome);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(xCoordinate4, yCoordinate4);
                contentStream.showText(String.valueOf(n));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(xCoordinate5, yCoordinate5);
                contentStream.showText(formattedDate);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(xCoordinate7, yCoordinate7);
                contentStream.showText(String.valueOf(dataInizio));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(xCoordinate8, yCoordinate8);
                contentStream.showText(String.valueOf(dataFine));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.setNonStrokingColor(new Color(47, 47, 47)); //Grigio
                contentStream.newLineAtOffset(xCoordinate15, yCoordinate15);
                contentStream.showText(bean.getNomeEnte());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate16, yCoordinate16);
                contentStream.showText("Costo Medio Unitario: " + bean.getCostoMedioUnitario());
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(xCoordinate17, yCoordinate17);
                contentStream.showText("Prezzo Vendita Al kWH: " + bean.getPrezzoVendita());
                contentStream.endText();

                contentStream.close();
                while (numVendite > 9) {
                    PDDocument document3 = PDDocument.load(templateFile2);
                    PDPage newPage = document3.getPage(0);
                    document2.addPage(newPage);

                    PDPageContentStream contentStream2 = new PDPageContentStream(document2, newPage, PDPageContentStream.AppendMode.APPEND, true, true);

                    while (i != maxElem) {
                        VenditaBean Vbean = vendite.get(j);
                        descrizione = "VENDITA DEL ";
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream2.newLineAtOffset(xCoordinate6, yCoordinate6);
                        contentStream2.showText(descrizione + " " + Vbean.getDataVendita());
                        contentStream2.endText();
                        yCoordinate6 -= 24.5;

                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream2.newLineAtOffset(xCoordinate1, yCoordinate1);
                        contentStream2.showText(String.valueOf(Vbean.getEnergiaVenduta()));
                        contentStream2.endText();
                        yCoordinate1 -= 24.5;

                        contentStream2.beginText();
                        contentStream2.setNonStrokingColor(new Color(0, 128, 0)); // Verde scuro
                        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream2.newLineAtOffset(xCoordinate12, yCoordinate12);
                        contentStream2.showText(String.valueOf("+" + Vbean.getRicavoTotale()));
                        contentStream2.setNonStrokingColor(new Color(0, 0, 0)); // Nero
                        contentStream2.endText();
                        yCoordinate12 -= 24.5;

                        j++;
                        i++;
                        numVendite--;
                    }
                    yCoordinate1 += 24.5 * 9;
                    yCoordinate6 += 24.5 * 9;
                    yCoordinate12 += 24.5 * 9;

                    i = 0;
                    contentStream2.setNonStrokingColor(Color.BLACK);
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream2.newLineAtOffset(xCoordinate3, yCoordinate3);
                    contentStream2.showText(nome + " " + cognome);
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream2.newLineAtOffset(xCoordinate4, yCoordinate4);
                    contentStream2.showText(String.valueOf(n));
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream2.newLineAtOffset(xCoordinate5, yCoordinate5);
                    contentStream2.showText(formattedDate);
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream2.newLineAtOffset(xCoordinate7, yCoordinate7);
                    contentStream2.showText(String.valueOf(dataInizio));
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream2.newLineAtOffset(xCoordinate8, yCoordinate8);
                    contentStream2.showText(String.valueOf(dataFine));
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.setNonStrokingColor(new Color(47, 47, 47)); //Grigio
                    contentStream2.newLineAtOffset(xCoordinate15, yCoordinate15);
                    contentStream2.showText(bean.getNomeEnte());
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate16, yCoordinate16);
                    contentStream2.showText("Costo Medio Unitario: " + bean.getCostoMedioUnitario());
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate17, yCoordinate17);
                    contentStream2.showText("Prezzo Vendita Al kWH: " + bean.getPrezzoVendita());
                    contentStream2.endText();

                    contentStream2.close();
                }
                if (numVendite <= 9) {
                    PDDocument document = PDDocument.load(templateFile);
                    PDPage newPage = document.getPage(0);
                    document2.addPage(newPage);
                    PDPageContentStream contentStream2 = new PDPageContentStream(document2, newPage, PDPageContentStream.AppendMode.APPEND, true, true);

                    while (i != maxElem && (numVendite != 0)) {
                        VenditaBean Vbean = vendite.get(j);
                        descrizione = "VENDITA DEL ";
                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream2.newLineAtOffset(xCoordinate6, yCoordinate6);
                        contentStream2.showText(descrizione + " " + Vbean.getDataVendita());
                        contentStream2.endText();
                        yCoordinate6 -= 24.5;

                        contentStream2.beginText();
                        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream2.newLineAtOffset(xCoordinate1, yCoordinate1);
                        contentStream2.showText(String.valueOf(Vbean.getEnergiaVenduta()));
                        contentStream2.endText();
                        yCoordinate1 -= 24.5;

                        contentStream2.beginText();
                        contentStream2.setNonStrokingColor(new Color(0, 128, 0)); // Verde scuro
                        contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream2.newLineAtOffset(xCoordinate12, yCoordinate12);
                        contentStream2.showText(String.valueOf("+" + Vbean.getRicavoTotale()));
                        contentStream2.setNonStrokingColor(new Color(0, 0, 0)); // Nero
                        contentStream2.endText();
                        yCoordinate12 -= 24.5;

                        j++;
                        i++;
                        numVendite--;
                    }
                    contentStream2.beginText();
                    contentStream2.setNonStrokingColor(new Color(0, 128, 0)); // Verde scuro
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream2.newLineAtOffset(xCoordinate2, yCoordinate2);
                    contentStream2.showText(String.valueOf("+" + ricavo));
                    contentStream2.setNonStrokingColor(new Color(0, 0, 0)); // Nero
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setNonStrokingColor(new Color(241, 5, 5)); // Rosso
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream2.newLineAtOffset(xCoordinate13, yCoordinate13);
                    contentStream2.showText(String.valueOf("-" + speseCorrente));
                    contentStream2.setNonStrokingColor(new Color(0, 0, 0)); // Nero
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 12);
                    contentStream2.newLineAtOffset(xCoordinate14, yCoordinate14);
                    if (resocontoFinale > 0) {
                        contentStream2.setNonStrokingColor(new Color(0, 128, 0)); // Verde
                        contentStream2.showText(String.valueOf("+" + resocontoFinale));
                        contentStream2.endText();
                    } else if (resocontoFinale < 0) {
                        contentStream2.setNonStrokingColor(new Color(241, 5, 5)); // Rosso
                        contentStream2.showText(String.valueOf(resocontoFinale));
                        contentStream2.endText();
                    } else {
                        contentStream2.showText(String.valueOf(resocontoFinale));
                        contentStream2.endText();
                    }
                    contentStream2.setNonStrokingColor(new Color(0, 0, 0)); // Nero

                    contentStream2.setNonStrokingColor(Color.BLACK);
                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream2.newLineAtOffset(xCoordinate3, yCoordinate3);
                    contentStream2.showText(nome + " " + cognome);
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream2.newLineAtOffset(xCoordinate4, yCoordinate4);
                    contentStream2.showText(String.valueOf(n));
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream2.newLineAtOffset(xCoordinate5, yCoordinate5);
                    contentStream2.showText(formattedDate);
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream2.newLineAtOffset(xCoordinate7, yCoordinate7);
                    contentStream2.showText(String.valueOf(dataInizio));
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA_BOLD, 16);
                    contentStream2.newLineAtOffset(xCoordinate8, yCoordinate8);
                    contentStream2.showText(String.valueOf(dataFine));
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.setNonStrokingColor(new Color(47, 47, 47)); //Grigio
                    contentStream2.newLineAtOffset(xCoordinate15, yCoordinate15);
                    contentStream2.showText(bean.getNomeEnte());
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate16, yCoordinate16);
                    contentStream2.showText("Costo Medio Unitario: " + bean.getCostoMedioUnitario());
                    contentStream2.endText();

                    contentStream2.beginText();
                    contentStream2.setFont(PDType1Font.HELVETICA, 12);
                    contentStream2.newLineAtOffset(xCoordinate17, yCoordinate17);
                    contentStream2.showText("Prezzo Vendita Al kWH: " + bean.getPrezzoVendita());
                    contentStream2.endText();

                    contentStream2.close();

                    document2.save(filePath);
                    document2.close();

                }
            }

            //Apertura PDF
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(new File(filePath));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        // Inserisci il nuovo report nel database
        report.setDataEmissione(date);
        report.setIdAmministratore(id);
        report.setNomeReport(file);

        return report;
    }

}
