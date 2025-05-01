package org.example.cafepos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Stack;

public class UberController {
    @FXML private Label statusLabel;
    @FXML private TextField tripIdFilter, customerFilter;
    @FXML private ComboBox<String> paymentMethodFilter;
    @FXML private DatePicker datePicker;
    @FXML private TableView<UberData> dataTable;
    @FXML private File selectedExcelFile;
    @FXML private TableColumn<UberData, Integer> idColumn;
    @FXML private TableColumn<UberData, String> tripIdColumn,
            dropoffAddressColumn,
            customerNameColumn,
            paymentMethodColumn;
    @FXML private TableColumn<UberData, Timestamp> requestTimeColumn,
            dropoffTimeColumn;
    @FXML private TableColumn<UberData, Double> transactionAmountColumn,
            commissionColumn;

    private final ObservableList<UberData> orders = FXCollections.observableArrayList();
    private final Stack<ObservableList<UberData>> undoStack = new Stack<>();
    private final Stack<ObservableList<UberData>> redoStack = new Stack<>();

    private final String DB_URL  = "jdbc:mysql://localhost:3306/cafe";
    private final String USER    = "root";
    private final String PASS    = "";

    public void initialize() {
        dataTable.setEditable(true);

        // bind columns to model
        idColumn                 .setCellValueFactory(new PropertyValueFactory<>("id"));
        tripIdColumn             .setCellValueFactory(new PropertyValueFactory<>("tripId"));
        requestTimeColumn        .setCellValueFactory(new PropertyValueFactory<>("reqTime"));
        dropoffTimeColumn        .setCellValueFactory(new PropertyValueFactory<>("dropTime"));
        dropoffAddressColumn     .setCellValueFactory(new PropertyValueFactory<>("dropAddr"));
        customerNameColumn       .setCellValueFactory(new PropertyValueFactory<>("custName"));
        transactionAmountColumn  .setCellValueFactory(new PropertyValueFactory<>("amount"));
        commissionColumn         .setCellValueFactory(new PropertyValueFactory<>("commission"));
        paymentMethodColumn      .setCellValueFactory(new PropertyValueFactory<>("payMethod"));

        makeColumnsEditable();
        loadOrdersFromDatabase();
        addEditCommitListeners();

        // always keep an empty row at the bottom for new entries
        dataTable.getItems().add(new UberData(0, "", null, null, "", "", 0.0, 0.0, ""));
    }

    //editable columns
    private void makeColumnsEditable() {
        // String columns
        tripIdColumn        .setCellFactory(TextFieldTableCell.forTableColumn());
        dropoffAddressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        customerNameColumn  .setCellFactory(TextFieldTableCell.forTableColumn());
        paymentMethodColumn .setCellFactory(TextFieldTableCell.forTableColumn());

        // Double columns
        transactionAmountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        commissionColumn       .setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        // Timestamp columns (format: yyyy-MM-dd HH:mm:ss)
        StringConverter<Timestamp> tsConverter = new StringConverter<>() {
            @Override public String toString(Timestamp obj) { return obj != null ? obj.toString() : ""; }
            @Override public Timestamp fromString(String str) {
                try {
                    return Timestamp.valueOf(str);
                } catch (IllegalArgumentException e) {
                    showError("Invalid Time", "Use format: yyyy-MM-dd HH:mm:ss");
                    return null;
                }
            }
        };
        requestTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(tsConverter));
        dropoffTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(tsConverter));
    }

    //listeners to save automatically
    private void addEditCommitListeners() {
        tripIdColumn            .setOnEditCommit(e -> { e.getRowValue().setTripId(e.getNewValue());        saveToDatabase(e, "trip_id"); });
        dropoffAddressColumn    .setOnEditCommit(e -> { e.getRowValue().setDropAddr(e.getNewValue());       saveToDatabase(e, "drop_addr"); });
        customerNameColumn      .setOnEditCommit(e -> { e.getRowValue().setCustName(e.getNewValue());       saveToDatabase(e, "cust_name"); });
        paymentMethodColumn     .setOnEditCommit(e -> { e.getRowValue().setPayMethod(e.getNewValue());      saveToDatabase(e, "pay_method"); });
        transactionAmountColumn .setOnEditCommit(e -> { e.getRowValue().setAmount(e.getNewValue());        saveToDatabase(e, "amount"); });
        commissionColumn        .setOnEditCommit(e -> { e.getRowValue().setCommission(e.getNewValue());    saveToDatabase(e, "commission"); });
        requestTimeColumn       .setOnEditCommit(e -> { e.getRowValue().setReqTime(e.getNewValue());       saveToDatabase(e, "req_time"); });
        dropoffTimeColumn       .setOnEditCommit(e -> { e.getRowValue().setDropTime(e.getNewValue());      saveToDatabase(e, "drop_time"); });
    }

    //load from database
    private void loadOrdersFromDatabase() {
        orders.clear();
        String sql = "SELECT * FROM uber";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                orders.add(new UberData(
                        rs.getInt("id"),
                        rs.getString("trip_id"),
                        rs.getTimestamp("req_time"),
                        rs.getTimestamp("drop_time"),
                        rs.getString("drop_addr"),
                        rs.getString("cust_name"),
                        rs.getDouble("amount"),
                        rs.getDouble("commission"),
                        rs.getString("pay_method")
                ));
            }
            dataTable.setItems(orders);
        } catch (SQLException e) {
            e.printStackTrace();
            statusLabel.setText("Error loading data.");
        }
    }

    //saving data to database
    private void saveToDatabase(TableColumn.CellEditEvent<UberData, ?> event, String dbColumn) {
        UberData row = event.getRowValue();
        if (row.getId() == 0) return;  // skip new unsaved row

        String sql = "UPDATE uber SET " + dbColumn + " = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            Object val = event.getNewValue();
            if (val instanceof String)    ps.setString(1, (String) val);
            else if (val instanceof Double) ps.setDouble(1, (Double) val);
            else if (val instanceof Timestamp) ps.setTimestamp(1, (Timestamp) val);
            else ps.setObject(1, val);

            ps.setInt(2, row.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            showError("Update Failed", "Could not update " + dbColumn);
        }
    }

    //add button
    @FXML
    private void handleAddNewRecord(ActionEvent event) {
        // Check if the last row is empty
        UberData newRow = dataTable.getItems().get(dataTable.getItems().size() - 1);

        // If the new row is empty (placeholding row), don't add it to the list.
        if (newRow.getTripId().isEmpty() && newRow.getCustName().isEmpty()) {
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            // SQL statement to insert the new record into the database
            String sql = "INSERT INTO uber (trip_id, req_time, drop_time, drop_addr, cust_name, amount, commission, pay_method) " +
                    "VALUES (?, NOW(), NOW(), ?, ?, ?, ?, ?)"; // Using NOW() for timestamps

            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Set the values from the new row
            stmt.setString(1, newRow.getTripId());
            stmt.setString(2, newRow.getDropAddr());
            stmt.setString(3, newRow.getCustName());
            stmt.setDouble(4, newRow.getAmount());
            stmt.setDouble(5, newRow.getCommission());
            stmt.setString(6, newRow.getPayMethod());

            // Execute the update and get the generated key (ID)
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // Set the ID and timestamps for the new row
                    newRow.setId(rs.getInt(1));  // Generated ID
                    newRow.setReqTime(new Timestamp(System.currentTimeMillis()));  // Set current timestamp for req_time
                    newRow.setDropTime(new Timestamp(System.currentTimeMillis()));  // Set current timestamp for drop_time

                    // Add the new row to the orders list
                    orders.add(newRow);

                    // Remove the last empty row and add a fresh empty one
                    dataTable.getItems().remove(newRow);
                    dataTable.getItems().add(new UberData(0, "", null, null, "", "", 0.0, 0.0, ""));

                    // Automatically select the newly added row
                    dataTable.getSelectionModel().select(newRow);

                    // Provide feedback to the user
                    statusLabel.setText("New record added.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Database Error", "Failed to insert record: " + e.getMessage());
        }
    }


    //delete button
    @FXML
    private void handleDeleteRecord(ActionEvent event) {
        UberData selectedRow = dataTable.getSelectionModel().getSelectedItem();

        if (selectedRow == null || selectedRow.getId() == 0) {
            showError("No Selection", "Please select a valid saved row to delete.");
            return;
        }

        // Confirm deletion
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this record?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);
        confirm.setTitle("Confirm Delete");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                     PreparedStatement ps = conn.prepareStatement("DELETE FROM uber WHERE id = ?")) {

                    ps.setInt(1, selectedRow.getId());
                    int affected = ps.executeUpdate();

                    if (affected > 0) {
                        // Remove from UI list
                        orders.remove(selectedRow);
                        statusLabel.setText("Record deleted successfully.");
                    } else {
                        showError("Delete Failed", "No record deleted. Check if ID is correct.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showError("Database Error", "Failed to delete record: " + e.getMessage());
                }
            }
        });
    }

    @FXML
    private void handleChooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Excel File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );

        File file = fileChooser.showOpenDialog(dataTable.getScene().getWindow());
        if (file != null) {
            statusLabel.setText("Selected: " + file.getName());
            selectedExcelFile = file;
        } else {
            statusLabel.setText("No file selected.");
        }
    }

    @FXML
    private void handleUploadFile(ActionEvent event) {
        if (selectedExcelFile == null || !selectedExcelFile.exists()) {
            showError("No File", "Please select a valid Excel file first.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(selectedExcelFile);
             Workbook workbook = new XSSFWorkbook(fis);
             Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            Sheet sheet = workbook.getSheetAt(0);
            String sql = "INSERT INTO uber (trip_id, req_time, drop_time, drop_addr, cust_name, amount, commission, pay_method) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            int count = 0;
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                stmt.setString(1, getStringCell(row, 0)); // trip_id
                stmt.setTimestamp(2, getTimestampCell(row, 1)); // req_time
                stmt.setTimestamp(3, getTimestampCell(row, 2)); // drop_time
                stmt.setString(4, getStringCell(row, 3)); // drop_addr
                stmt.setString(5, getStringCell(row, 4)); // cust_name
                stmt.setDouble(6, getDoubleCell(row, 5)); // amount
                stmt.setDouble(7, getDoubleCell(row, 6)); // commission
                stmt.setString(8, getStringCell(row, 7)); // pay_method

                stmt.addBatch();
                count++;
            }

            stmt.executeBatch();
            statusLabel.setText("Uploaded " + count + " records from Excel.");
            loadOrdersFromDatabase(); // Refresh TableView

        } catch (Exception e) {
            e.printStackTrace();
            showError("Upload Failed", "Could not process Excel file: " + e.getMessage());
        }
    }

    private String getStringCell(Row row, int col) {
        Cell cell = row.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        return cell.getCellType() == CellType.STRING ? cell.getStringCellValue() : cell.toString();
    }

    private double getDoubleCell(Row row, int col) {
        Cell cell = row.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        return cell.getCellType() == CellType.NUMERIC ? cell.getNumericCellValue() : 0.0;
    }

    private Timestamp getTimestampCell(Row row, int col) {
        try {
            Cell cell = row.getCell(col, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return new Timestamp(cell.getDateCellValue().getTime());
            } else if (cell.getCellType() == CellType.STRING) {
                return Timestamp.valueOf(cell.getStringCellValue());
            }
        } catch (Exception e) {
            // Fallback below
        }
        // Default to current timestamp if cell is invalid or empty
        return new Timestamp(System.currentTimeMillis());
    }



    @FXML
    private void handleExportData(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(dataTable.getScene().getWindow());

        if (file == null) return;

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA, 10);

            float margin = 40;
            float yStart = page.getMediaBox().getHeight() - margin;
            float y = yStart;
            float leading = 14f;
            float startX = margin;

            // Headers
            String[] headers = {
                    "ID", "Trip ID", "Req Time", "Drop Time", "Address",
                    "Customer", "Amount", "Commission", "Payment"
            };

            // Draw headers
            for (String header : headers) {
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, y);
                contentStream.showText(header);
                contentStream.endText();
                startX += 60;  // Spacing between columns
            }

            y -= leading;
            startX = margin;

            // Data rows
            for (UberData row : orders) {
                if (y < margin + leading) {  // Check if space is enough for another row
                    contentStream.close();  // Close the content stream
                    page = new PDPage(PDRectangle.A4);  // Create new page
                    document.addPage(page);  // Add new page to document
                    contentStream = new PDPageContentStream(document, page);  // New content stream
                    contentStream.setFont(PDType1Font.HELVETICA, 10);  // Reset font for new page
                    y = yStart;  // Reset Y position to top of the page

                    // Draw headers again on the new page
                    startX = margin;
                    for (String header : headers) {
                        contentStream.beginText();
                        contentStream.newLineAtOffset(startX, y);
                        contentStream.showText(header);
                        contentStream.endText();
                        startX += 60;
                    }
                    y -= leading;
                }

                String[] rowData = {
                        String.valueOf(row.getId()),
                        row.getTripId(),
                        row.getReqTime() != null ? row.getReqTime().toString() : "",
                        row.getDropTime() != null ? row.getDropTime().toString() : "",
                        row.getDropAddr(),
                        row.getCustName(),
                        String.format("%.2f", row.getAmount()),
                        String.format("%.2f", row.getCommission()),
                        row.getPayMethod()
                };

                startX = margin;  // Reset column X position

                // Draw the data row
                for (String cell : rowData) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(startX, y);
                    // Truncate text if too long (only show the first 10 characters)
                    contentStream.showText(cell.length() > 10 ? cell.substring(0, 10) + "…" : cell);
                    contentStream.endText();
                    startX += 60;  // Spacing between columns
                }

                y -= leading;  // Move Y position down for the next row
            }

            contentStream.close();
            document.save(file);  // Save the PDF to the file
            statusLabel.setText("Data exported to PDF.");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Export Failed", "Error writing PDF: " + e.getMessage());
        }
    }



    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    public void GoBack(ActionEvent actionEvent) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MainForm.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showError("Navigation Error", "Could not load MainForm.fxml");
            }
        }

    }
