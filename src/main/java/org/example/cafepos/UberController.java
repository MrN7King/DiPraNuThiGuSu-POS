package org.example.cafepos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.sql.*;
import java.util.Stack;

public class UberController {
    @FXML private Label statusLabel;
    @FXML private TextField tripIdFilter, customerFilter;
    @FXML private ComboBox<String> paymentMethodFilter;
    @FXML private DatePicker datePicker;
    @FXML private TableView<UberData> dataTable;

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

    @FXML private void handleDeleteRecord(ActionEvent event) {
        UberData sel = dataTable.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showError("No Selection", "Select a row to delete.");
            return;
        }
        Alert c = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete selected record?", ButtonType.OK, ButtonType.CANCEL);
        c.setHeaderText(null);
        c.showAndWait().filter(b -> b == ButtonType.OK).ifPresent(b -> {
            String sql = "DELETE FROM uber WHERE id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, sel.getId());
                ps.executeUpdate();
                orders.remove(sel);
            } catch (SQLException ex) {
                ex.printStackTrace();
                showError("Delete Failed", ex.getMessage());
            }
        });
    }

    // TODO: Implement handleEditRecord in case you want a separate edit dialog

    @FXML private void handleClearFilters(ActionEvent event) {
        tripIdFilter.clear();
        customerFilter.clear();
        paymentMethodFilter.setValue("All");
        datePicker.setValue(null);
        loadOrdersFromDatabase();
    }

    @FXML private void handleUndo(ActionEvent event)   { /* ... */ }
    @FXML private void handleRedo(ActionEvent event)   { /* ... */ }
    @FXML private void handleExportData(ActionEvent event) { /* ... */ }
    @FXML private void handleChooseFile(ActionEvent event) { /* ... */ }
    @FXML private void handleUploadFile(ActionEvent event) { /* ... */ }
    @FXML private void handleEditRecord(ActionEvent event) { /* ... */ }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
