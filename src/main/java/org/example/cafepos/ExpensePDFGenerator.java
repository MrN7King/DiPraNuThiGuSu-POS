package org.example.cafepos;

import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpensePDFGenerator {

    // Page setup constants
    private static final PDRectangle PAGE_SIZE = PDRectangle.LETTER;
    private static final float PAGE_WIDTH = PAGE_SIZE.getWidth();
    private static final float MARGIN = 50;
    private static final float TABLE_WIDTH = PAGE_WIDTH - 2 * MARGIN;
    private static final float START_Y = 640;
    private static final float LINE_HEIGHT = 20;

    public static void generateExpenseReport(ObservableList<ExpenseData> expenses, String filePath) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PAGE_SIZE);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Title
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN, 700);
                contentStream.showText("CAFE EXPENSE REPORT");
                contentStream.endText();

                // Date
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN, 670);
                contentStream.showText("Generated on: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                contentStream.endText();

                // Table headers
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                drawTableHeader(contentStream);

                // Table content
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                drawExpenseData(contentStream, expenses);

                // Total
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(MARGIN, 100);
                contentStream.showText("Total Expenses: " + String.format("%.2f", calculateTotal(expenses)));
                contentStream.endText();
            }

            document.save(filePath);
        }
    }

    private static void drawTableHeader(PDPageContentStream contentStream) throws IOException {
        float[] columnWidths = {70, 70, 140, 70, 80, 82}; // Total = 512
        String[] headers = {"Expense ID", "Type", "Description", "Amount", "Date", "Recorded By"};

        float xPosition = MARGIN;
        float yPosition = START_Y;

        for (int i = 0; i < headers.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(xPosition, yPosition);
            contentStream.showText(headers[i]);
            contentStream.endText();
            xPosition += columnWidths[i];
        }

        contentStream.moveTo(MARGIN, yPosition - 5);
        contentStream.lineTo(MARGIN + TABLE_WIDTH, yPosition - 5);
        contentStream.stroke();
    }

    private static void drawExpenseData(PDPageContentStream contentStream, ObservableList<ExpenseData> expenses) throws IOException {
        float[] columnWidths = {70, 70, 140, 70, 80, 82};
        float yPosition = START_Y - 20;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (ExpenseData expense : expenses) {
            if (yPosition < 60) break; // basic bottom margin

            float xPosition = MARGIN;

            String[] rowData = {
                    expense.getExpenseId(),
                    expense.getExpenseType(),
                    expense.getDescription(),
                    String.format("%.2f", expense.getAmount()),
                    dateFormat.format(expense.getDate()),
                    expense.getRecordedBy()
            };

            for (int i = 0; i < rowData.length; i++) {
                contentStream.beginText();
                contentStream.newLineAtOffset(xPosition, yPosition);
                contentStream.showText(rowData[i]);
                contentStream.endText();
                xPosition += columnWidths[i];
            }

            yPosition -= LINE_HEIGHT;
        }
    }

    private static double calculateTotal(ObservableList<ExpenseData> expenses) {
        return expenses.stream().mapToDouble(ExpenseData::getAmount).sum();
    }
}
