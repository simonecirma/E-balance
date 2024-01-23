package com.c17.ebalance.ebalance.amministratore.service;
import com.c17.ebalance.ebalance.dati.service.ConsumoService;
import com.c17.ebalance.ebalance.dati.service.ConsumoServiceImpl;
import com.c17.ebalance.ebalance.model.DAO.ReportDAO;
import com.c17.ebalance.ebalance.model.DAO.ReportDAOImpl;
import com.c17.ebalance.ebalance.model.entity.ReportBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportServiceImpl implements ReportService {
    public VenditaService venditaService = new VenditaServiceImpl();
    public ConsumoService consumoService = new ConsumoServiceImpl();
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
        float energiaconsumo= consumoService.getConsumoPerData(dataInizio, dataFine);
        float energia = venditaService.getEnergiaVendutaPerData(dataInizio, dataFine);
        float ricavo = venditaService.getRicavoTotalePerData(dataInizio, dataFine);

        HttpSession session = request.getSession(false);
        String nome = "";
        String cognome = "";
        int id = 0;
        if(session != null && session.getAttribute("nome") != null && session.getAttribute("cognome") != null) {
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

            // Ora 'date' contiene la data parsata
            System.out.println("Data parsata: " + date);
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
            System.out.println(filePath);

            // Carica il template PDF esistente
            File templateFile = new File(servletPath + File.separator  + "TemplateReport.pdf");
            PDDocument document = PDDocument.load(templateFile);

            // Ottieni la prima pagina del template
            PDPage page = document.getPage(0);

            // Apertura del flusso di contenuto per scrivere nel documento PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            // Posizione delle celle nel template da riempire

            // Posizione produzione
            float xCoordinate1 = 240;
            float yCoordinate1 = 551.5F;
            float xCoordinate2 = 400;
            float yCoordinate2 = 551.5F;
            float xCoordinate9 = 240;
            float yCoordinate9 = 535.5F;

            // Posizione amministratore
            float xCoordinate3 = 475.5F;
            float yCoordinate3 = 668.5F;

            // Posizione numero report
            float xCoordinate4 = 475.5F;
            float yCoordinate4 = 712F;

            // Posizione data emissione
            float xCoordinate5 = 475;
            float yCoordinate5 = 698F;

            // Posizione descrizione
            float xCoordinate6 = 55;
            float yCoordinate6 = 551.5F;
            float xCoordinate10 = 55;
            float yCoordinate10 = 535.5F;


            // Posizione data inizio
            float xCoordinate7 = 412.5F;
            float yCoordinate7 = 654;

            // Posizione data fine
            float xCoordinate8 = 482.5F;
            float yCoordinate8 = 654;

            // Scrivi i valori nel documento
            if(ricavo > 0) {
                descrizione = "ENERGIA VENDUTA";
                descrizione1 = "CONSUMO ENERGIA";

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(xCoordinate6, yCoordinate6);
                contentStream.showText(descrizione);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(xCoordinate10, yCoordinate10);
                contentStream.showText(descrizione1);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(xCoordinate1, yCoordinate1);
                contentStream.showText(String.valueOf(energia));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setNonStrokingColor(new Color(0, 128, 0)); // Verde scuro
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(xCoordinate2, yCoordinate2);
                contentStream.showText("+"+String.valueOf(ricavo));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                contentStream.newLineAtOffset(xCoordinate9, yCoordinate9);
                contentStream.showText(String.valueOf(energiaconsumo));
                contentStream.endText();
            }

            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.newLineAtOffset(xCoordinate3, yCoordinate3);
            contentStream.showText(nome + " " + cognome);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.newLineAtOffset(xCoordinate4, yCoordinate4);
            contentStream.showText(String.valueOf(n));
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.newLineAtOffset(xCoordinate5, yCoordinate5);
            contentStream.showText(formattedDate);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.newLineAtOffset(xCoordinate7, yCoordinate7);
            contentStream.showText(String.valueOf(dataInizio));
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.newLineAtOffset(xCoordinate8, yCoordinate8);
            contentStream.showText(String.valueOf(dataFine));
            contentStream.endText();

            // Chiudi il flusso di contenuto
            contentStream.close();

            document.save(filePath);
            document.close();

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
