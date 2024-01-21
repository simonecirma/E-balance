package com.c17.ebalance.ebalance.dati.service;

import com.c17.ebalance.ebalance.amministratore.service.ReportService;
import com.c17.ebalance.ebalance.amministratore.service.ReportServiceImpl;
import com.c17.ebalance.ebalance.model.entity.ReportBean;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BilancioServiceImpl implements BilancioService{
    public VenditaService venditaService = new VenditaServiceImpl();

    public ReportService reportService = new ReportServiceImpl();
    @Override
    public ReportBean generaReport(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        ReportBean bean = new ReportBean();
        HttpSession session = request.getSession(false);
        String nome = "";
        String cognome = "";
        int id = 0;
        if(session != null && session.getAttribute("nome") != null && session.getAttribute("cognome") != null) {
             nome = (String) session.getAttribute("nome");
             cognome = (String) session.getAttribute("cognome");
             id = (int) session.getAttribute("idAmministratore");
        }

        Date dataInizio = java.sql.Date.valueOf(request.getParameter("dataInizio"));
        Date dataFine = java.sql.Date.valueOf(request.getParameter("dataFine"));
        float energia = venditaService.getEnergiaVendutaPerData((java.sql.Date) dataInizio, (java.sql.Date) dataFine);
        float ricavo = venditaService.getRicavoTotalePerData((java.sql.Date) dataInizio, (java.sql.Date) dataFine);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = sdf.format(new Date());
        String formattedDate1 = sdf.format(new Date());

        Date date = null;
        try {
            date = sdf.parse(formattedDate1);
            // Ora dateObject è un oggetto Date rappresentante la stessa data che era stata formattata
        } catch (ParseException e) {
            e.printStackTrace();
            // Gestisci eventuali eccezioni di parsing
        }

        String descrizione = "";
        int n = reportService.ultimoReport() + 1;
        String file = "Report"+n+".pdf";

        String servletPath = request.getServletContext().getRealPath("");

        try {
            // Carica il template PDF esistente
            File templateFile = new File(servletPath + File.separator  + "TemplateReport.pdf");
            PDDocument document = PDDocument.load(templateFile);

            // Ottieni la prima pagina del template
            PDPage page = document.getPage(0);

            // Apertura del flusso di contenuto per scrivere nel documento PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);

            // Posizione delle celle nel template da riempire
            float xCoordinate1 = 240;
            float yCoordinate1 = 551.5F;
            float xCoordinate2 = 400;
            float yCoordinate2 = 551.5F;

            float xCoordinate3 = 475.5F;
            float yCoordinate3 = 668;

            float xCoordinate4 = 475.5F;
            float yCoordinate4 = 711.5F;

            float xCoordinate5 = 475;
            float yCoordinate5 = 697.5F;

            float xCoordinate6 = 55;
            float yCoordinate6 = 551.5F;

            // Scrivi i valori nel documento
            if(ricavo>0){
                descrizione = "ENERGIA VENDUTA";

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(xCoordinate6, yCoordinate6);
                contentStream.showText(descrizione);
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(xCoordinate1, yCoordinate1);
                contentStream.showText(String.valueOf(energia));
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(xCoordinate2, yCoordinate2);
                contentStream.showText(String.valueOf(ricavo));
                contentStream.endText();
            }

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(xCoordinate3, yCoordinate3);
            contentStream.showText(nome + " " + cognome);
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(xCoordinate4, yCoordinate4);
            contentStream.showText(String.valueOf(n));
            contentStream.endText();

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(xCoordinate5, yCoordinate5);
            contentStream.showText(formattedDate);
            contentStream.endText();

            // Chiudi il flusso di contenuto
            contentStream.close();

            // Invia il documento come risposta HTTP
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Report" + n +".pdf");

            // Salva il documento modificato e chiudi correttamente le risorse
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            document.close();

            // Scrivi il contenuto nella risposta HTTP
            response.getOutputStream().write(byteArrayOutputStream.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        bean.setDataEmissione(date);
        bean.setIdAmministratore(id);
        bean.setNomeReport(file);
        reportService.aggiungiReport(bean);

        return bean;
    }

}
