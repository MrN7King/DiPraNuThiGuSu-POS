package org.example.cafepos;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.*;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReceiptGenerator {
    private static final float MARGIN = 20f;
    private static final float ROW_HEIGHT = 15f;
    private static final PDType1Font TITLE_FONT = PDType1Font.HELVETICA_BOLD;
    private static final PDType1Font HEADER_FONT = PDType1Font.HELVETICA_BOLD;
    private static final PDType1Font BODY_FONT = PDType1Font.HELVETICA;

    public static void generateReceipt(List<ProductData> items,
                                       double total,
                                       double amountPaid,
                                       double change,
                                       String cashierName,
                                       String filePath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A6);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            try {
                float yPosition = page.getMediaBox().getHeight() - MARGIN;

                // Add header
                yPosition = addHeader(contentStream, page, yPosition);

                // Add receipt info
                yPosition = addReceiptInfo(contentStream, page, yPosition, cashierName);

                // Add items table (pass the document reference)
                yPosition = addItemsTable(document, contentStream, page, yPosition, items, cashierName);

                // Add payment summary
                yPosition = addPaymentSummary(contentStream, page, yPosition, total, amountPaid, change);

                // Add footer
                addFooter(contentStream, page, yPosition);
            } finally {
                contentStream.close();
            }

            document.save(filePath);
        }
    }

    private static float addHeader(PDPageContentStream contentStream, PDPage page, float y)
            throws IOException {
        // Cafe name
        contentStream.beginText();
        contentStream.setFont(TITLE_FONT, 14);
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("CAFE POS SYSTEM");
        contentStream.endText();
        y -= 20;

        // Address
        contentStream.beginText();
        contentStream.setFont(BODY_FONT, 10);
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("123 Cafe Street, Colombo, Sri Lanka");
        contentStream.endText();
        y -= 15;

        // Phone
        contentStream.beginText();
        contentStream.setFont(BODY_FONT, 10);
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("Tel: +94 11 2345678");
        contentStream.endText();
        y -= 20;

        return y;
    }

    private static float addReceiptInfo(PDPageContentStream contentStream, PDPage page, float y,
                                        String cashierName) throws IOException {
        // Receipt title
        contentStream.beginText();
        contentStream.setFont(HEADER_FONT, 12);
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("RECEIPT");
        contentStream.endText();
        y -= 15;

        // Date/time
        String dateTime = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        contentStream.beginText();
        contentStream.setFont(BODY_FONT, 10);
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("Date: " + dateTime);
        contentStream.endText();
        y -= 15;

        // Cashier
        contentStream.beginText();
        contentStream.setFont(BODY_FONT, 10);
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("Cashier: " + cashierName);
        contentStream.endText();
        y -= 20;

        return y;
    }

    private static float addItemsTable(PDDocument document,
                                       PDPageContentStream contentStream,
                                       PDPage page,
                                       float y,
                                       List<ProductData> items,
                                       String cashierName) throws IOException {
        float tableWidth = page.getMediaBox().getWidth() - 2 * MARGIN;
        float[] columnWidths = {tableWidth * 0.4f, tableWidth * 0.2f, tableWidth * 0.2f, tableWidth * 0.2f};

        // Table header
        contentStream.beginText();
        contentStream.setFont(HEADER_FONT, 10);
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("Item");
        contentStream.newLineAtOffset(columnWidths[0], 0);
        contentStream.showText("Qty");
        contentStream.newLineAtOffset(columnWidths[1], 0);
        contentStream.showText("Price");
        contentStream.newLineAtOffset(columnWidths[2], 0);
        contentStream.showText("Total");
        contentStream.endText();
        y -= ROW_HEIGHT;

        // Table rows
        contentStream.setFont(BODY_FONT, 10);
        for (ProductData item : items) {
            if (y < MARGIN + 50) { // Check if we need a new page
                contentStream.close();
                PDPage newPage = new PDPage(PDRectangle.A6);
                document.addPage(newPage);
                contentStream = new PDPageContentStream(document, newPage);
                y = newPage.getMediaBox().getHeight() - MARGIN;
                // Redraw header on new page
                y = addHeader(contentStream, newPage, y);
                y = addReceiptInfo(contentStream, newPage, y, cashierName);

                // Redraw table header
                contentStream.beginText();
                contentStream.setFont(HEADER_FONT, 10);
                contentStream.newLineAtOffset(MARGIN, y);
                contentStream.showText("Item");
                contentStream.newLineAtOffset(columnWidths[0], 0);
                contentStream.showText("Qty");
                contentStream.newLineAtOffset(columnWidths[1], 0);
                contentStream.showText("Price");
                contentStream.newLineAtOffset(columnWidths[2], 0);
                contentStream.showText("Total");
                contentStream.endText();
                y -= ROW_HEIGHT;
            }

            contentStream.beginText();
            contentStream.newLineAtOffset(MARGIN, y);
            contentStream.showText(item.getProductName());
            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText(String.valueOf(item.getQuantity()));
            contentStream.newLineAtOffset(columnWidths[1], 0);
            contentStream.showText(String.format("LKR %.2f", item.getPrice()));
            contentStream.newLineAtOffset(columnWidths[2], 0);
            contentStream.showText(String.format("LKR %.2f", item.getPrice() * item.getQuantity()));
            contentStream.endText();
            y -= ROW_HEIGHT;
        }

        return y;
    }

    private static float addPaymentSummary(PDPageContentStream contentStream, PDPage page,
                                           float y, double total, double amountPaid, double change) throws IOException {
        float tableWidth = page.getMediaBox().getWidth() - 2 * MARGIN;

        // Divider line
        contentStream.moveTo(MARGIN, y - 5);
        contentStream.lineTo(MARGIN + tableWidth, y - 5);
        contentStream.stroke();
        y -= 15;

        // Total
        contentStream.beginText();
        contentStream.setFont(HEADER_FONT, 10);
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("TOTAL:");
        contentStream.newLineAtOffset(tableWidth - 50, 0);
        contentStream.showText(String.format("LKR %.2f", total));
        contentStream.endText();
        y -= ROW_HEIGHT;

        // Amount Paid
        contentStream.beginText();
        contentStream.setFont(BODY_FONT, 10);
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("Amount Paid:");
        contentStream.newLineAtOffset(tableWidth - 50, 0);
        contentStream.showText(String.format("LKR %.2f", amountPaid));
        contentStream.endText();
        y -= ROW_HEIGHT;

        // Change
        contentStream.beginText();
        contentStream.setFont(BODY_FONT, 10);
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("Change:");
        contentStream.newLineAtOffset(tableWidth - 50, 0);
        contentStream.showText(String.format("LKR %.2f", change));
        contentStream.endText();
        y -= 20;

        return y;
    }

    private static void addFooter(PDPageContentStream contentStream, PDPage page, float y)
            throws IOException {
        contentStream.beginText();
        contentStream.setFont(BODY_FONT, 8);
        contentStream.newLineAtOffset(MARGIN, y);
        contentStream.showText("Thank you for your visit!");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(BODY_FONT, 8);
        contentStream.newLineAtOffset(MARGIN, y - 15);
        contentStream.showText("Generated by Café POS System");
        contentStream.endText();
    }
}