package org.example.cafepos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class MainFormController implements Initializable{

    // Admin Form Components - Nuha
    @FXML private TableView<EmployeeData> admin_tableView;
    @FXML private TableColumn<EmployeeData, Integer> admin_col_id;
    @FXML private TableColumn<EmployeeData, String> admin_col_username;
    @FXML private TableColumn<EmployeeData, String> admin_col_role;
    @FXML private TextField admin_username;
    @FXML private PasswordField admin_password;
    @FXML private ComboBox<String> admin_role;
    @FXML private ComboBox<String> admin_question;
    @FXML private TextField admin_answer;
    @FXML private Button admin_addBtn;
    @FXML private Button admin_updateBtn;
    @FXML private Button admin_deleteBtn;
    @FXML private Button admin_printBtn;
    private ObservableList<EmployeeData> adminUserList;
    private int adminSelectedId = -1;
    @FXML private AnchorPane admin_form;
    @FXML private Button admin_btn;
    @FXML private Button logout_btn;


    // Dashboard - thivaker
    @FXML  private Label dashboard_NC;
    @FXML  private Label dashboard_NSP;
    @FXML  private Label dashboard_TI;
    @FXML  private Label dashboard_TotalI;
    @FXML  private Button dashboard_btn;
    @FXML  private AnchorPane dashboard_form;
    @FXML  private AreaChart<String, Number> dashboard_incomeChart;
    @FXML  private BarChart<String, Number> dashboard_CustomerChart;
    // Dashboard Transaction Modal Controls Admin only
    @FXML private AnchorPane dashboardAdmin_form;
    @FXML private StackPane transactionModal;
    @FXML private StackPane dayIncomeForm;
    @FXML private TextField transactionID;
    @FXML private ComboBox<Receipt> transactionSelectionCombo;
    @FXML private DatePicker transactionDatePicker;
    @FXML private TextField transactionAmountField;
    @FXML private ComboBox<Integer> transactionCustomerCombo;
    @FXML private Button addTransactionBtn;
    @FXML private Button updateTransactionBtn;
    @FXML private Button deleteTransactionBtn;
    @FXML private Button cancelTransactionBtn;
    // duplicated variables
    @FXML private BarChart<String, Number> dashboard_CustomerChart1;
    @FXML private Label dashboard_NC1;
    @FXML private Label dashboard_NSP1;
    @FXML private Label dashboard_TI1;
    @FXML private Label dashboard_TotalI1;
    @FXML private AreaChart<String, Number> dashboard_incomeChart1;
    @FXML private StackPane customerChartForm;
    @FXML private Button dashboardPrintBtn;



    // expense -- sumry
    @FXML private AnchorPane expenseTracking_form;
    @FXML private Button expenses_btn;
    @FXML private TableView<ExpenseData> expenseTableView;
    @FXML private TableColumn<ExpenseData, String> Expenses_col_expenseID;
    @FXML private TableColumn<ExpenseData, String> Expenses_col_type;
    @FXML private TableColumn<ExpenseData, String> Expenses_col_description;
    @FXML private TableColumn<ExpenseData, Double> Expenses_col_amount;
    @FXML private TableColumn<ExpenseData, String> Expenses_col_date;
    @FXML private TableColumn<ExpenseData, String> Expenses_col_recordedBy;
    @FXML private TextField expenseID_txtField;
    @FXML private ComboBox<String> expenseType_combo;
    @FXML private TextArea expenseDescription_txtField;
    @FXML private TextField expenseAmount_txtField;
    @FXML private TextField expenseRecordedBy_txtField;
    @FXML private Button expenseAddBtn;
    @FXML private Button expenseUpdateBtn;
    @FXML private Button expenseDeleteBtn;
    @FXML private Button expensePrintBtn;

    //Inventory -- Pranal
    @FXML private Button inventory_addbtn;
    @FXML private Button inventory_btn;
    @FXML private Button inventory_clearbtn;
    @FXML private TableView<ProductData> inventory_tableView;
    @FXML private TableColumn<ProductData, String> inventory_col_productID;
    @FXML private TableColumn<ProductData, String> inventory_col_productName;
    @FXML private TableColumn<ProductData, String> inventory_col_type;
    @FXML private TableColumn<ProductData, String> inventory_col_stock;
    @FXML private TableColumn<ProductData, String> inventory_col_price;
    @FXML private TableColumn<ProductData, String> inventory_col_status;
    @FXML private TableColumn<ProductData, String> inventory_col_date;
    @FXML private Button inventory_deletebtn;
    @FXML private AnchorPane inventory_form;
    @FXML private ImageView inventory_imageView;
    @FXML private Button inventory_importbtn;
    @FXML private TextField inventory_price;
    @FXML private TextField inventory_productID;
    @FXML private TextField inventory_productName;
    @FXML private ComboBox<?> inventory_status;
    @FXML private TextField inventory_stock;
    @FXML private ComboBox<?> inventory_type;
    @FXML private Button inventory_updatebtn;


    //menu -- Dilaksan
    @FXML private AnchorPane main_form;
    @FXML private Button menu_btn;
    @FXML private Label username;
    @FXML private TextField menu_amount;
    @FXML private Label menu_change;
    @FXML private TableView<ProductData> menu_tableView;
    @FXML private TableColumn<ProductData, String> menu_col_price;
    @FXML private TableColumn<ProductData, String> menu_col_productName;
    @FXML private TableColumn<ProductData, String> menu_col_quantity;
    @FXML private AnchorPane menu_form;
    @FXML private GridPane menu_gridPane;
    @FXML private Button menu_payBtn;
    @FXML private Button menu_receiptBtn;
    @FXML private Button menu_removeBtn;
    @FXML private ScrollPane menu_scrollPane;
    @FXML private Label menu_total;
    @FXML private TextField menuProductNametxtFiled;
    @FXML private TextField menuPricetxtField;
    @FXML private Spinner<Integer> menuQuantitySpinner;
    @FXML private Button menu_updateBtn;

    //uber -- Guru
    @FXML private Button uber_btn;



    private Alert alert;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private Image image;

    private ObservableList<ProductData> cardListData = FXCollections.observableArrayList();


    // ----------------------------------------------------------------------
    //dashboard thivaker
    public void dashboardDisplayNC(Label ncLabel) {
        String sql = "SELECT COUNT(DISTINCT customer_id) FROM receipt";
        connect = Database.connectDB();

        try {
            int nc = 0;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                nc = result.getInt(1);
            }
            ncLabel.setText(String.valueOf(nc)); // Use the parameter instead of dashboard_NC
        } catch (Exception e) {
            e.printStackTrace();
            ncLabel.setText("0"); // Use the parameter here too
        } finally {
            closeResources();
        }
    }

    public void dashboardDisplayTI(Label tiLabel) {
        LocalDate today = LocalDate.now();
        String sql = "SELECT COALESCE(SUM(total), 0) FROM receipt WHERE date = ?";
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setDate(1, Date.valueOf(today));
            result = prepare.executeQuery();

            double ti = 0;
            if (result.next()) {
                ti = result.getDouble(1);
            }
            tiLabel.setText(String.format("LKR %.2f", ti)); // Use parameter
        } catch (Exception e) {
            e.printStackTrace();
            tiLabel.setText("LKR 0.00"); // Use parameter
        } finally {
            closeResources();
        }
    }

    public void dashboardTotalI(Label totalTiLabel) {
        String sql = "SELECT COALESCE(SUM(total), 0) FROM receipt";
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            double ti = 0;
            if (result.next()) {
                ti = result.getDouble(1);
            }
            totalTiLabel.setText(String.format("LKR %.2f", ti)); // Use parameter
        } catch (Exception e) {
            e.printStackTrace();
            totalTiLabel.setText("LKR 0.00"); // Use parameter
        } finally {
            closeResources();
        }
    }

    public void dashboardNSP(Label nspLabel) {
        String sql = "SELECT COALESCE(SUM(quantity), 0) FROM customer";
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            int q = 0;
            if (result.next()) {
                q = result.getInt(1);
            }
            nspLabel.setText(String.valueOf(q)); // Use parameter
        } catch (Exception e) {
            e.printStackTrace();
            nspLabel.setText("0"); // Use parameter
        } finally {
            closeResources();
        }
    }

    public void dashboardIncomeChart(AreaChart<String, Number> chart) {
        if (chart == null) return;

        chart.getData().clear();
        String sql = "SELECT date, COALESCE(SUM(total), 0) FROM receipt GROUP BY date ORDER BY date";

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Database.connectDB();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Daily Income");

            while (rs.next()) {
                String date = rs.getString(1);
                Number total = rs.getDouble(2);
                XYChart.Data<String, Number> data = new XYChart.Data<>(date, total);

                // Make data points clickable by adding a node
                Node node = data.getNode();
                if (node == null) {
                    node = new StackPane();
                    data.setNode(node);
                }

                series.getData().add(data);
            }

            chart.getData().add(series);
            chart.setLegendVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void dashboardCustomerChart(BarChart<String, Number> chart) {
        chart.getData().clear();
        String sql = "SELECT date, COUNT(DISTINCT customer_id) FROM receipt GROUP BY date ORDER BY date";

        try (Connection conn = Database.connectDB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Daily Customers");

            while (rs.next()) {
                String date = rs.getString(1);
                Number count = rs.getInt(2);
                series.getData().add(new XYChart.Data<>(date, count));
            }

            chart.getData().add(series);
            chart.setLegendVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeResources() {
        try {
            if (result != null) result.close();
            if (prepare != null) prepare.close();
            if (connect != null) connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //admin Dashboard
    // Initialize transaction modal components
    private void initializeTransactionModal() {
        transactionModal.setVisible(false);
        updateTransactionBtn.setDisable(true);
        deleteTransactionBtn.setDisable(true);

        // Load customer IDs and transactions
        loadCustomerIDs();
        loadTransactions();

        // Set up transaction selection listener
        transactionSelectionCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                showEditTransactionModal(newVal);
            }
        });
    }

    private void loadCustomerIDs() {
        try {
            connect = Database.connectDB();
            String sql = "SELECT DISTINCT customer_id FROM customer ORDER BY customer_id";
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList<Integer> customerIDs = FXCollections.observableArrayList();
            while (result.next()) {
                customerIDs.add(result.getInt("customer_id"));
            }
            transactionCustomerCombo.setItems(customerIDs);
        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load customer IDs");
            alert.showAndWait();
        } finally {
            closeResources();
        }
    }

    private void loadTransactions() {
        try {
            connect = Database.connectDB();
            String sql = "SELECT * FROM receipt ORDER BY date DESC";
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ObservableList<Receipt> transactions = FXCollections.observableArrayList();
            while (result.next()) {
                transactions.add(new Receipt(
                        result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date").toLocalDate(),
                        result.getString("em_username")
                ));
            }
            transactionSelectionCombo.setItems(transactions);

            // Set custom cell factory to display meaningful text
            transactionSelectionCombo.setCellFactory(param -> new ListCell<Receipt>() {
                @Override
                protected void updateItem(Receipt item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(String.format("ID: %d - Date: %s - Amount: LKR %.2f",
                                item.getId(),
                                item.getDate().toString(),
                                item.getTotal()));
                    }
                }
            });

            transactionSelectionCombo.setButtonCell(new ListCell<Receipt>() {
                @Override
                protected void updateItem(Receipt item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText("Select a transaction");
                    } else {
                        setText(String.format("ID: %d - Date: %s - Amount: LKR %.2f",
                                item.getId(),
                                item.getDate().toString(),
                                item.getTotal()));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load transactions");
            alert.showAndWait();
        } finally {
            closeResources();
        }
    }

    public void showAddTransactionModal() {
        // Clear selection first
        transactionSelectionCombo.getSelectionModel().clearSelection();

        // Generate a new transaction ID
        try {
            connect = Database.connectDB();
            String sql = "SELECT MAX(id) FROM receipt";
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            int newId = 1;
            if (result.next()) {
                newId = result.getInt(1) + 1;
            }
            transactionID.setText(String.valueOf(newId));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        transactionDatePicker.setValue(LocalDate.now());
        transactionAmountField.clear();
        transactionID.clear();
        transactionCustomerCombo.getSelectionModel().clearSelection();

        updateTransactionBtn.setDisable(true);
        deleteTransactionBtn.setDisable(true);

        customerChartForm.setVisible(false);
        transactionModal.setVisible(true);
    }

    public void showEditTransactionModal(Receipt receipt) {
        try {
            transactionID.setText(String.valueOf(receipt.getId()));
            transactionDatePicker.setValue(receipt.getDate());
            transactionAmountField.setText(String.format("%.2f", receipt.getTotal()));

            if (!transactionCustomerCombo.getItems().contains(receipt.getCustomerId())) {
                loadCustomerIDs();
            }
            transactionCustomerCombo.getSelectionModel().select((Integer)receipt.getCustomerId());

            updateTransactionBtn.setDisable(false);
            deleteTransactionBtn.setDisable(false);

            // Hide customer chart and show transaction form
            customerChartForm.setVisible(false);
            transactionModal.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load transaction data");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancelTransaction() {
        // Show customer chart and hide transaction form
        transactionModal.setVisible(false);
        customerChartForm.setVisible(true);
    }

    @FXML
    private void handleAddTransaction() {
        if (validateTransactionInput()) {
            try {
                connect = Database.connectDB();
                String sql = "INSERT INTO receipt (id, customer_id, total, date, em_username) VALUES (?,?,?,?,?)";
                prepare = connect.prepareStatement(sql);

                prepare.setInt(1, Integer.parseInt(transactionID.getText()));
                prepare.setInt(2, transactionCustomerCombo.getValue());
                prepare.setDouble(3, Double.parseDouble(transactionAmountField.getText()));
                prepare.setDate(4, Date.valueOf(transactionDatePicker.getValue()));
                prepare.setString(5, data.username);

                prepare.executeUpdate();

                // Refresh data and close modal
                loadTransactions();
                refreshBothDashboards();
                transactionModal.setVisible(false);
                customerChartForm.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add transaction");
                alert.showAndWait();
            } finally {
                closeResources();
            }
        }
    }

    @FXML
    private void handleUpdateTransaction() {
        if (!validateTransactionInput()) return;

        try {
            connect = Database.connectDB();
            String sql = "UPDATE receipt SET customer_id=?, total=?, date=? WHERE id=?";
            prepare = connect.prepareStatement(sql);

            prepare.setInt(1, transactionCustomerCombo.getValue());
            prepare.setDouble(2, Double.parseDouble(transactionAmountField.getText()));
            prepare.setDate(3, Date.valueOf(transactionDatePicker.getValue()));
            prepare.setInt(4, Integer.parseInt(transactionID.getText()));

            int affectedRows = prepare.executeUpdate();

            if (affectedRows > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Transaction updated successfully");
                alert.showAndWait();

                // Refresh the transaction list
                loadTransactions();
                refreshBothDashboards();
                transactionModal.setVisible(false);
                customerChartForm.setVisible(true);
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No transaction was updated");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update transaction: " + e.getMessage());
            alert.showAndWait();
        } finally {
            closeResources();
        }
    }

    @FXML
    private void handleDeleteTransaction() {
        try {
            String transactionId = transactionID.getText();
            if (transactionId.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No transaction selected");
                alert.showAndWait();
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Deletion");
            confirm.setHeaderText("Delete Transaction #" + transactionId);
            confirm.setContentText("Are you sure you want to delete this transaction?");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                connect = Database.connectDB();
                String sql = "DELETE FROM receipt WHERE id=?";
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, Integer.parseInt(transactionId));

                int affectedRows = prepare.executeUpdate();

                if (affectedRows > 0) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Transaction deleted successfully");
                    alert.showAndWait();

                    // Refresh the transaction list
                    loadTransactions();
                    refreshBothDashboards();
                    transactionModal.setVisible(false);
                    customerChartForm.setVisible(true);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("No transaction was deleted");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to delete transaction: " + e.getMessage());
            alert.showAndWait();
        } finally {
            closeResources();
        }
    }

    // Validate transaction input
    private boolean validateTransactionInput() {
        try {
            if (transactionCustomerCombo.getValue() == null) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a customer");
                alert.showAndWait();
                return false;
            }
            if (transactionDatePicker.getValue() == null) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a date");
                alert.showAndWait();
                return false;
            }
            Double.parseDouble(transactionAmountField.getText());
            return true;
        } catch (NumberFormatException e) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid amount");
            alert.showAndWait();
            return false;
        }
    }

    // Refresh dashboard data
    private void refreshBothDashboards() {
        // Always refresh admin dashboard data (even if not visible)
        dashboardDisplayNC(dashboard_NC1);
        dashboardDisplayTI(dashboard_TI1);
        dashboardTotalI(dashboard_TotalI1);
        dashboardNSP(dashboard_NSP1);
        dashboardIncomeChart(dashboard_incomeChart1);
        dashboardCustomerChart(dashboard_CustomerChart1);

        // Always refresh regular dashboard data (even if not visible)
        dashboardDisplayNC(dashboard_NC);
        dashboardDisplayTI(dashboard_TI);
        dashboardTotalI(dashboard_TotalI);
        dashboardNSP(dashboard_NSP);
        dashboardIncomeChart(dashboard_incomeChart);
        dashboardCustomerChart(dashboard_CustomerChart);
    }

    // Find receipt in database
    private Receipt findReceiptByDateAndAmount(String dateStr, double amount) {
        try {
            connect = Database.connectDB();
            String sql = "SELECT * FROM receipt WHERE date = ? AND total = ?";
            prepare = connect.prepareStatement(sql);
            prepare.setDate(1, Date.valueOf(dateStr));
            prepare.setDouble(2, amount);

            result = prepare.executeQuery();
            if (result.next()) {
                return new Receipt(
                        result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getDouble("total"),
                        result.getDate("date").toLocalDate(),
                        result.getString("em_username")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    @FXML
    private void handleDashboardPrint() {
        try {
            // Create file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Dashboard Report");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));

            // Set default filename
            fileChooser.setInitialFileName("cafe_dashboard_" +
                    LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".pdf");

            // Show save dialog
            File file = fileChooser.showSaveDialog(main_form.getScene().getWindow());

            if (file != null) {
                // Get current dashboard values
                int totalCustomers = Integer.parseInt(dashboard_NC.getText());
                double todayIncome = Double.parseDouble(dashboard_TI.getText().replace("LKR", "").trim());
                double totalIncome = Double.parseDouble(dashboard_TotalI.getText().replace("LKR", "").trim());
                int totalSales = Integer.parseInt(dashboard_NSP.getText());

                // Generate PDF
                DashboardPDFGenerator.generateDashboardReport(
                        totalCustomers,
                        todayIncome,
                        totalIncome,
                        totalSales,
                        file.getAbsolutePath());

                // Show success message
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Dashboard report saved successfully!");
                alert.showAndWait();

                // Open the PDF if desktop is supported
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to generate report: " + e.getMessage());
            alert.showAndWait();
        }
    }


    // ----------------------------------------------------------------------
    // inventory Pranal
    //Add btn
    public void inventoryAddBtn() {

        if (inventory_productID.getText().isEmpty()
                || inventory_productName.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.path == null)
        {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            // CHECK PRODUCT ID
            String checkProdID = "SELECT prod_id FROM product WHERE prod_id = '"
                    + inventory_productID.getText() + "'";

            connect = Database.connectDB();

            try {

                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(inventory_productID.getText() + " is already taken");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO product "
                            + "(prod_id, prod_name, type, stock, price, status, image, date) "
                            + "VALUES(?,?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, inventory_productID.getText());
                    prepare.setString(2, inventory_productName.getText());
                    prepare.setString(3, (String) inventory_type.getSelectionModel().getSelectedItem());
                    prepare.setString(4, inventory_stock.getText());
                    prepare.setString(5, inventory_price.getText());
                    prepare.setString(6, (String) inventory_status.getSelectionModel().getSelectedItem());

                    String path = data.path;
                    path = path.replace("\\", "\\\\");

                    prepare.setString(7, path);

                    // TO GET CURRENT DATE
                    //not sure with the below 3 lines
                    LocalDate localDate = LocalDate.now();
                    Date sqlDate = Date.valueOf(localDate);
                    prepare.setDate(8, sqlDate);


                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    inventoryShowData();
                    inventoryClearBtn();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
      }
    }

    public void inventoryUpdateBtn() {

        if (inventory_productID.getText().isEmpty()
                || inventory_productName.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.path == null || data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            String path = data.path;
            path = path.replace("\\", "\\\\");

            String updateData = "UPDATE product SET "
                    + "prod_id = '" + inventory_productID.getText() + "', prod_name = '"
                    + inventory_productName.getText() + "', type = '"
                    + inventory_type.getSelectionModel().getSelectedItem() + "', stock = '"
                    + inventory_stock.getText() + "', price = '"
                    + inventory_price.getText() + "', status = '"
                    + inventory_status.getSelectionModel().getSelectedItem() + "', image = '"
                    + path + "', date = '"
                    + data.date + "' WHERE id = " + data.id;

            connect = Database.connectDB();

            try {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE PRoduct ID: " + inventory_productID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void inventoryDeleteBtn() {
        if (data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Product ID: " + inventory_productID.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM product WHERE id = " + data.id;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryShowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }
    }

    public void inventoryClearBtn() {

        inventory_productID.setText("");
        inventory_productName.setText("");
        inventory_type.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        inventory_imageView.setImage(null);

    }

    // BEHAVIOR FOR IMPORT BTN
    public void inventoryImportBtn(){
        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 180, 180, false, true);

            inventory_imageView.setImage(image);
        }
    }

    public void inventoryPrintBtn() {
        try {
            // Generate the PDF report
            InventoryPDFGenerator.generateInventoryReport(inventoryListData, data.username);

            // Show success message
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Inventory report generated successfully!");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to generate report: " + e.getMessage());
            alert.showAndWait();
        }
    }

    // MERGE ALL DATAS
    public ObservableList<ProductData> inventoryDataList() {

        ObservableList<ProductData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product";

        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData prodData;

            while (result.next()) {

                prodData = new ProductData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(prodData);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    // to show all data to table
    private ObservableList<ProductData> inventoryListData;
    public void inventoryShowData() {
        inventoryListData = inventoryDataList();

        inventory_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_tableView.setItems(inventoryListData);

    }

    public void inventorySelectData() {

        ProductData prodData = inventory_tableView.getSelectionModel().getSelectedItem();
        int num = inventory_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        inventory_productID.setText(prodData.getProductId());
        inventory_productName.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));

        data.path = prodData.getImage();

        String path = "File:" + prodData.getImage();
        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();

        image = new Image(path, 180, 180, false, true);
        inventory_imageView.setImage(image);
    }

    //to display type list in dropmenu
    private String[] typeList = {"Meals", "Drinks"};
    public void inventoryTypeList() {

        List<String> typeL = new ArrayList<>();

        for (String data : typeList) {
            typeL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(typeL);
        inventory_type.setItems(listData);
    }

    //to display status list in dropmenu
    private String[] statusList = {"Available", "Unavailable"};
    public void inventoryStatusList() {

        List<String> statusL = new ArrayList<>();

        for (String data : statusList) {
            statusL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(statusL);
        inventory_status.setItems(listData);
    }



    // ----------------------------------------------------------------------
    // Menu Dilaksan
    public ObservableList<ProductData> menuGetData() {

        String sql = "SELECT * FROM product";

        ObservableList<ProductData> listData = FXCollections.observableArrayList();
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData prod;

            while (result.next()) {
                prod = new ProductData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public void menuDisplayCard() {

        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;

        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        for (int q = 0; q < cardListData.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("cardProduct.fxml"));
                AnchorPane pane = load.load();
                cardProductController cardC = load.getController();
                cardC.setData(cardListData.get(q));
                cardC.setMainFormController(this);

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                menu_gridPane.add(pane, column++, row);

                GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<ProductData> menuGetOrder() {
        customerID();
        ObservableList<ProductData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customer WHERE customer_id = " + cID;

        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ProductData prod;

            while (result.next()) {
                prod = new ProductData(result.getInt("id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private int getid;

    public void menuSelectOrder() {
        ProductData prod = menu_tableView.getSelectionModel().getSelectedItem();
        int num = menu_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        // TO GET THE ID PER ORDER
        getid = prod.getId();

        // Populate the fields with selected product data
        menuProductNametxtFiled.setText(prod.getProductName());
        menuPricetxtField.setText(String.format("LKR %.2f", prod.getPrice()));

        // Set spinner value to current quantity
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, prod.getQuantity());
        menuQuantitySpinner.setValueFactory(valueFactory);
    }

    private double totalP;
    public void menuGetTotal() {
        customerID();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + cID;

        connect = Database.connectDB();

        try {

            prepare = connect.prepareStatement(total);
            result = prepare.executeQuery();

            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText("LKR " + totalP);
    }

    private ObservableList<ProductData> menuOrderListData;
    public void menuShowOrderData() {
        menuOrderListData = menuGetOrder();

        menu_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        menu_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        menu_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        menu_tableView.setItems(menuOrderListData);
    }

    private double amount;
    private double change;

    public void menuAmount() {
        menuGetTotal();
        if (menu_amount.getText().isEmpty() || totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid :3");
            alert.showAndWait();
        } else {
            amount = Double.parseDouble(menu_amount.getText());
            if (amount < totalP) {
                menu_amount.setText("");
            } else {
                change = (amount - totalP);
                menu_change.setText("LKR " + change);
            }
        }
    }

    public void menuPayBtn() {

        if (totalP == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        } else {
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username) "
                    + "VALUES(?,?,?,?)";

            connect = Database.connectDB();

            try {

                if (amount == 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Messaged");
                    alert.setHeaderText(null);
                    alert.setContentText("Something wrong :3");
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure?");
                    Optional<ButtonType> option = alert.showAndWait();

                    if (option.get().equals(ButtonType.OK)) {
                        customerID();
                        menuGetTotal();
                        prepare = connect.prepareStatement(insertPay);
                        prepare.setString(1, String.valueOf(cID));
                        prepare.setString(2, String.valueOf(totalP));

                        LocalDate localDate = LocalDate.now();
                        Date sqlDate = Date.valueOf(localDate);

                        prepare.setDate(3, sqlDate);  // Use setDate instead of setString

//                        Date date = new Date();
//                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
//                        prepare.setString(3, String.valueOf(sqlDate));

                        prepare.setString(4, data.username);

                        prepare.executeUpdate();

                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successful.");
                        alert.showAndWait();


                        // Then reset the form
                        menuShowOrderData();


                    } else {
                        alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Infomation Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Cancelled.");
                        alert.showAndWait();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void menuRemoveBtn() {

        if (getid == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select the order you want to remove");
            alert.showAndWait();
        } else {
            String deleteData = "DELETE FROM customer WHERE id = " + getid;
            connect = Database.connectDB();
            try {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();
                }

                menuShowOrderData();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void menuReceiptBtn() {
        if (totalP == 0 || menu_amount.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Please complete an order first");
            alert.showAndWait();
        } else {
            try {
                // Prepare receipt data
                List<ProductData> orderItems = menu_tableView.getItems();
                double total = Double.parseDouble(menu_total.getText().replace("LKR", "").trim());
                double amountPaid = Double.parseDouble(menu_amount.getText());
                double change = Double.parseDouble(menu_change.getText().replace("LKR", "").trim());

                // Generate unique filename
                String receiptPath = "receipt_" + System.currentTimeMillis() + ".pdf";

                // Generate receipt
                ReceiptGenerator.generateReceipt(
                        orderItems,
                        total,
                        amountPaid,
                        change,
                        data.username,
                        receiptPath
                );

                // Open the receipt
                File file = new File(receiptPath);
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                }

                // Optional: Print the receipt directly
                // PrinterJob job = PrinterJob.createPrinterJob();
                // if (job != null && job.showPrintDialog(null)) {
                //     job.printPage(file);
                //     job.endJob();
                // }

            } catch (Exception e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setContentText("Failed to generate receipt: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }

    //new method to handle order updates
    public void menuUpdateOrder() {
        if (getid == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select an order to update");
            alert.showAndWait();
            return;
        }

        int newQuantity = menuQuantitySpinner.getValue();
        if (newQuantity <= 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Quantity must be at least 1");
            alert.showAndWait();
            return;
        }

        String updateData = "UPDATE customer SET quantity = ?, price = (SELECT price FROM product WHERE prod_id = (SELECT prod_id FROM customer WHERE id = ?)) * ? WHERE id = ?";
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(updateData);
            prepare.setInt(1, newQuantity);
            prepare.setInt(2, getid);
            prepare.setInt(3, newQuantity);
            prepare.setInt(4, getid);

            int affectedRows = prepare.executeUpdate();

            if (affectedRows > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Order updated successfully!");
                alert.showAndWait();

                // Refresh the order list and totals
                menuShowOrderData();
                menuDisplayTotal();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update order");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void confirmRestart() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm New Order");
        alert.setHeaderText("Start new order?");
        alert.setContentText("This will clear all current order items.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            menuRestart();
        }
    }

    public void menuRestart() {
        // First, delete all items from the customer table for this order
        String deleteOrderItems = "DELETE FROM customer WHERE customer_id = " + cID;
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(deleteOrderItems);
            prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Reset calculations
        totalP = 0;
        change = 0;
        amount = 0;

        // Reset UI displays
        menu_total.setText("LKR0.0");
        menu_amount.setText("");
        menu_change.setText("LKR0.0");

        // Clear the order update section
        menuProductNametxtFiled.clear();
        menuPricetxtField.clear();

        // Reset spinner to default value (1)
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        menuQuantitySpinner.setValueFactory(valueFactory);

        // Clear and refresh the table view
        if (menuOrderListData != null) {
            menuOrderListData.clear();
        }
        menu_tableView.getItems().clear();
        menu_tableView.refresh();

        // Generate a new customer ID for the new order
        customerID(); // This should generate a new cID
    }




    // ----------------------------------------------------------------------
    // sumry part below
    private int cID;
    public void customerID() {

        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                cID = result.getInt("MAX(customer_id)");
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }

            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }

            data.cID = cID;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private ObservableList<ExpenseData> expenseList = FXCollections.observableArrayList();

    public void expenseAdd() {
        String sql = "INSERT INTO expenses (expense_id, expense_type, description, amount, date, recorded_by) " +
                "VALUES(?,?,?,?,?,?)";

        connect = Database.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, expenseID_txtField.getText());
            prepare.setString(2, expenseType_combo.getValue());
            prepare.setString(3, expenseDescription_txtField.getText());
            prepare.setDouble(4, Double.parseDouble(expenseAmount_txtField.getText()));
            prepare.setDate(5, new java.sql.Date(System.currentTimeMillis()));
            prepare.setString(6, expenseRecordedBy_txtField.getText());

            prepare.executeUpdate();

            alertMessage("Successfully Added!");

            expenseShowListData();
            expenseClear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void expenseUpdate() {
        String sql = "UPDATE expenses SET expense_type = ?, description = ?, amount = ?, recorded_by = ? " +
                "WHERE expense_id = ?";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, expenseType_combo.getValue());
            prepare.setString(2, expenseDescription_txtField.getText());
            prepare.setDouble(3, Double.parseDouble(expenseAmount_txtField.getText()));
            prepare.setString(4, expenseRecordedBy_txtField.getText());
            prepare.setString(5, expenseID_txtField.getText());

            prepare.executeUpdate();

            alertMessage("Successfully Updated!");

            expenseShowListData();
            expenseClear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void expenseDelete() {
        String sql = "DELETE FROM expenses WHERE expense_id = ?";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, expenseID_txtField.getText());

            prepare.executeUpdate();

            alertMessage("Successfully Deleted!");

            expenseShowListData();
            expenseClear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void expenseClear() {
        expenseID_txtField.setText("");
        expenseType_combo.getSelectionModel().clearSelection();
        expenseDescription_txtField.setText("");
        expenseAmount_txtField.setText("");
        expenseRecordedBy_txtField.setText("");
    }

    public ObservableList<ExpenseData> expenseListData() {
        ObservableList<ExpenseData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM expenses";

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            ExpenseData expense;

            while (result.next()) {
                expense = new ExpenseData(result.getInt("id"),
                        result.getString("expense_id"),
                        result.getString("expense_type"),
                        result.getString("description"),
                        result.getDouble("amount"),
                        result.getDate("date"),
                        result.getString("recorded_by"));

                listData.add(expense);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void expenseShowListData() {
        expenseList = expenseListData();

        Expenses_col_expenseID.setCellValueFactory(new PropertyValueFactory<>("expenseId"));
        Expenses_col_type.setCellValueFactory(new PropertyValueFactory<>("expenseType"));
        Expenses_col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        Expenses_col_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        Expenses_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        Expenses_col_recordedBy.setCellValueFactory(new PropertyValueFactory<>("recordedBy"));

        expenseTableView.setItems(expenseList);
    }

    public void expenseSelect() {
        ExpenseData expense = expenseTableView.getSelectionModel().getSelectedItem();
        int num = expenseTableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        expenseID_txtField.setText(expense.getExpenseId());
        expenseType_combo.setValue(expense.getExpenseType());
        expenseDescription_txtField.setText(expense.getDescription());
        expenseAmount_txtField.setText(String.valueOf(expense.getAmount()));
        expenseRecordedBy_txtField.setText(expense.getRecordedBy());
    }

    public void expenseTypeList() {
        List<String> typeList = new ArrayList<>();

        typeList.add("Rent");
        typeList.add("Salaries");
        typeList.add("Supplies");
        typeList.add("Utilities");
        typeList.add("Maintenance");
        typeList.add("Other");

        ObservableList<String> listData = FXCollections.observableArrayList(typeList);
        expenseType_combo.setItems(listData);
    }

    @FXML
    private void handleExpensePrintButton() {
        try {
            // Create a file chooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Expense Report");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

            // Use java.util.Date for filename
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            fileChooser.setInitialFileName("ExpenseReport_" + dateFormat.format(new java.util.Date()) + ".pdf");

            // Show save dialog
            File file = fileChooser.showSaveDialog(expensePrintBtn.getScene().getWindow());

            if (file != null) {
                // Generate the PDF
                ExpensePDFGenerator.generateExpenseReport(expenseList, file.getAbsolutePath());

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Expense report saved successfully to:\n" + file.getAbsolutePath());
                alert.showAndWait();

                // Optionally open the PDF
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to generate PDF: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void alertMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    // ----------------------------------------------------------------------
    // Done by Nuha
    // ADMIN Section below
    private void setupAdminSection() {
        // Initialize combo boxes
        admin_role.setItems(FXCollections.observableArrayList("admin", "cashier"));
        admin_question.setItems(FXCollections.observableArrayList(
                "Your secret that no one knows ?",
                "Your favourite food ?",
                "Your pets name ?"
        ));

        // Configure table columns
        admin_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        admin_col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        admin_col_role.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Load initial data
        adminRefreshData();

        // Set up table selection listener
        admin_tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                adminSelectUser(newSelection);
            }
        });
    }

    private void adminRefreshData() {
        String sql = "SELECT id, username, role, question, answer FROM employee";
        adminUserList = FXCollections.observableArrayList();

        connect = Database.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                adminUserList.add(new EmployeeData(
                        result.getInt("id"),
                        result.getString("username"),
                        result.getString("role"),
                        result.getString("question"),
                        result.getString("answer")
                ));
            }

            admin_tableView.setItems(adminUserList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void adminSelectUser(EmployeeData user) {
        adminSelectedId = user.getId();
        admin_username.setText(user.getUsername());
        admin_role.setValue(user.getRole());
        admin_question.setValue(user.getQuestion());
        admin_answer.setText(user.getAnswer());
        admin_password.setText(""); // Clear password field for security
    }

    @FXML
    private void adminAddUserBtn() {
        if (admin_username.getText().isEmpty() || admin_password.getText().isEmpty() ||
                admin_role.getValue() == null || admin_question.getValue() == null ||
                admin_answer.getText().isEmpty()) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields");
            alert.showAndWait();
            return;
        }

        String sql = "INSERT INTO employee (username, password, role, question, answer) VALUES (?,?,?,?,?)";
        connect = Database.connectDB();

        try {
            // Check if username already exists
            String checkUser = "SELECT username FROM employee WHERE username = ?";
            prepare = connect.prepareStatement(checkUser);
            prepare.setString(1, admin_username.getText());
            result = prepare.executeQuery();

            if (result.next()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Username already exists");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, admin_username.getText());
                prepare.setString(2, admin_password.getText()); // Note: In production, hash this password
                prepare.setString(3, admin_role.getValue());
                prepare.setString(4, admin_question.getValue());
                prepare.setString(5, admin_answer.getText());

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("User added successfully");
                alert.showAndWait();

                adminRefreshData();
                adminClearFields();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void adminUpdateUserBtn() {
        if (adminSelectedId == -1) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to update");
            alert.showAndWait();
            return;
        }

        String sql = "UPDATE employee SET username=?, role=?, question=?, answer=?" +
                (admin_password.getText().isEmpty() ? "" : ", password=?") +
                " WHERE id=?";

        connect = Database.connectDB();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, admin_username.getText());
            prepare.setString(2, admin_role.getValue());
            prepare.setString(3, admin_question.getValue());
            prepare.setString(4, admin_answer.getText());

            if (admin_password.getText().isEmpty()) {
                prepare.setInt(5, adminSelectedId);
            } else {
                prepare.setString(5, admin_password.getText()); // Note: Hash this in production
                prepare.setInt(6, adminSelectedId);
            }

            prepare.executeUpdate();

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("User updated successfully");
            alert.showAndWait();

            adminRefreshData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void adminDeleteUserBtn() {
        if (adminSelectedId == -1) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete");
            alert.showAndWait();
            return;
        }

        // Prevent self-deletion
        if (data.username.equals(admin_username.getText())) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("You cannot delete your own account");
            alert.showAndWait();
            return;
        }

        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this user?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            String sql = "DELETE FROM employee WHERE id = ?";
            connect = Database.connectDB();
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, adminSelectedId);
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("User deleted successfully");
                alert.showAndWait();

                adminRefreshData();
                adminClearFields();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void adminClearFields() {
        adminSelectedId = -1;
        admin_username.setText("");
        admin_password.setText("");
        admin_role.getSelectionModel().clearSelection();
        admin_question.getSelectionModel().clearSelection();
        admin_answer.setText("");
    }

    @FXML
    private void userDataPrintButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save User Report");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));

        fileChooser.setInitialFileName(
                "cafe_users_" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".pdf");

        File file = fileChooser.showSaveDialog(admin_form.getScene().getWindow());

        if (file != null) {
            try {
                PDFGeneratorUserManagement.generateUserReport(
                        admin_tableView.getItems(),
                        file.getAbsolutePath()
                );

                // Open the generated PDF
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                }

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Report Generated, User report saved successfully");
                alert.showAndWait();

            } catch (Exception e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Generation Failed, Could not generate report: " + e.getMessage());
                alert.showAndWait();

                e.printStackTrace();
            }
        }
    }

    private void switchToAdminDashboard() {
        dashboardAdmin_form.setVisible(true);
        dashboard_form.setVisible(false);
        dayIncomeForm.setVisible(true);
        customerChartForm.setVisible(true);
        transactionModal.setVisible(false);

        inventory_form.setVisible(false);
        menu_form.setVisible(false);
        expenseTracking_form.setVisible(false);
        admin_form.setVisible(false);
    }

    private void switchToCashierDashboard() {
        dashboardAdmin_form.setVisible(false);
        dashboard_form.setVisible(true);

        inventory_form.setVisible(false);
        menu_form.setVisible(false);
        expenseTracking_form.setVisible(false);
        admin_form.setVisible(false);
    }


    // ----------------------------------------------------------------------
    // General Codes
    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            if ("admin".equals(data.role)) {
                switchToAdminDashboard();
            } else {
                switchToCashierDashboard();
            }
            refreshBothDashboards();
        }
        else if (event.getSource() == admin_btn && "admin".equals(data.role)) {
            // Admin management form
            dashboard_form.setVisible(false);
            dashboardAdmin_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            expenseTracking_form.setVisible(false);
            admin_form.setVisible(true);

            adminRefreshData();
        }
        else if (event.getSource() == inventory_btn) {
            dashboard_form.setVisible(false);
            dashboardAdmin_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            expenseTracking_form.setVisible(false);
            admin_form.setVisible(false);

            inventoryTypeList();
            inventoryStatusList();
            inventoryShowData();
        }
        else if (event.getSource() == menu_btn) {
            dashboard_form.setVisible(false);
            dashboardAdmin_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
            expenseTracking_form.setVisible(false);
            admin_form.setVisible(false);

            menuDisplayCard();
            menuDisplayTotal();
            menuShowOrderData();
        }
        else if (event.getSource() == expenses_btn) {
            dashboard_form.setVisible(false);
            dashboardAdmin_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            expenseTracking_form.setVisible(true);
            admin_form.setVisible(false);

        }
        else if (event.getSource() == uber_btn) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Uber.fxml"));
                Parent root = loader.load();

                // Optional: If you want to replace the whole scene
                Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logout() {

        try {

            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                // TO HIDE MAIN FORM
                logout_btn.getScene().getWindow().hide();

                // LINK YOUR LOGIN FORM AND SHOW IT
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.setTitle("Cafe Shop Management System");

                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void displayUsername() {

        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);

        username.setText(user);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Display username in the UI
        displayUsername();

        // Initialize common components
        initializeTransactionModal();
        refreshBothDashboards();
        inventoryTypeList();
        inventoryStatusList();
        inventoryShowData();
        menuDisplayCard();
        menuGetOrder();
        menuShowOrderData();
        menuDisplayTotal();

        expenseTypeList();
        expenseShowListData();

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        menuQuantitySpinner.setValueFactory(valueFactory);

        // Make product name and price fields non-editable
        menuProductNametxtFiled.setEditable(false);
        menuPricetxtField.setEditable(false);

        // Set up admin section if user is admin
        if ("admin".equals(data.role)) {
            setupAdminSection();
            addTransactionBtn.setVisible(true);
            admin_btn.setVisible(true);

            // Show admin dashboard by default
            switchToAdminDashboard();
        } else {
            // Hide admin-only elements for regular users
            admin_form.setVisible(false);
            admin_btn.setVisible(false);
            addTransactionBtn.setVisible(false);

            // Show regular dashboard
            switchToCashierDashboard();
        }
    }
}