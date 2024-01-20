package com.c17.ebalance.ebalance.dati.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class PdfGenerator {
    public static File generatePdf(float earningsStart, float earningsEnd, String filePath) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Setta il colore verde
            contentStream.setNonStrokingColor(Color.GREEN);

            // Imposta il font e la dimensione
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Scrivi il primo numero in verde
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Numero 1: " + earningsStart);
            contentStream.endText();

            // Scrivi il secondo numero in verde sotto il primo
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 680);
            contentStream.showText("Numero 2: " + earningsEnd);
            contentStream.endText();
        }

        File file = new File(filePath);
        document.save(file);
        document.close();

        return file;
    }
}
