package com.c17.ebalance.ebalance.dati.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

public class BilancioServiceImpl implements BilancioService{
    public VenditaService venditaService = new VenditaServiceImpl();
    @Override
    public void generaReport(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        Date dataInizio = java.sql.Date.valueOf(request.getParameter("dataInizio"));
        Date dataFine = java.sql.Date.valueOf(request.getParameter("dataFine"));
        float energia = venditaService.getEnergiaVendutaPerData((java.sql.Date) dataInizio, (java.sql.Date) dataFine);
        float ricavo = venditaService.getRicavoTotalePerData((java.sql.Date) dataInizio, (java.sql.Date) dataFine);

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
            float xCoordinate1 = 240;  // Sostituisci con la coordinata X effettiva
            float yCoordinate1 = 551.5F;  // Sostituisci con la coordinata Y effettiva
            float xCoordinate2 = 400;  // Sostituisci con la coordinata X effettiva
            float yCoordinate2 = 551.5F;  // Sostituisci con la coordinata Y effettiva

            // Scrivi i valori nel documento
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

            // Chiudi il flusso di contenuto
            contentStream.close();

            // Invia il documento come risposta HTTP
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=output.pdf");

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
    }
}
