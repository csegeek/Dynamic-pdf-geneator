package com.example.pdfgenerator.service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.pdfgenerator.entity.Invoice;
import com.example.pdfgenerator.entity.Item;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

@Service
public class PdfGeneratorService {

    // generte byte array of pdf file with the given data using itextpdf library.
    public ByteArrayOutputStream generateByteArray(Invoice invoice) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDoc = new PdfDocument(writer);
            // Setting font of the text PdfFont
            PdfFont font = PdfFontFactory.createFont(StandardFonts.COURIER_BOLD);
            Document doc = new Document(pdfDoc).setFont(font);
            float[] pointColumnWidths = { 280F, 280F };
            // creating cells for seller and buyer
            Table header = new Table(pointColumnWidths);
            String seller = "Seller:\n" + invoice.getSeller() + "\n" + invoice.getSellerAddress() + "\n GSTIN: "
                    + invoice.getSellerGstin();
            header.addCell(new Cell().add(new Paragraph(seller)).setPadding(30));
            String buyer = "Buyer:\n" + invoice.getBuyer() + "\n" + invoice.getBuyerAddress() + "\n GSTIN: "
                    + invoice.getBuyerGstin();
            header.addCell(new Cell().add(new Paragraph(buyer)).setPadding(30));
            // creating table for products
            float[] productInfoColumnWidhths = { 140, 140, 140, 140 };
            Table productinfoTable = new Table(productInfoColumnWidhths);
            productinfoTable.setTextAlignment(TextAlignment.CENTER);
            productinfoTable.addCell(new Cell().add(new Paragraph("Item")));
            productinfoTable.addCell(new Cell().add(new Paragraph("Quantity")));
            productinfoTable.addCell(new Cell().add(new Paragraph("Rate")));
            productinfoTable.addCell(new Cell().add(new Paragraph("Amount")));

            List<Item> items = invoice.getItems();
            for (Item item : items) {
                productinfoTable.addCell(new Cell().add(new Paragraph(item.getName())));
                productinfoTable.addCell(new Cell().add(new Paragraph(item.getQuantity())));
                productinfoTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getRate()))));
                productinfoTable.addCell(new Cell().add(new Paragraph(String.valueOf(item.getAmount()))));
            }
            doc.add(header);
            doc.add(productinfoTable);
            writer.close();
            pdfDoc.close();
            doc.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("pdf generated successfully..");
        return outputStream;
    }
}
