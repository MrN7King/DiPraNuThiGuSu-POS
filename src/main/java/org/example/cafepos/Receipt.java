package org.example.cafepos;

import java.sql.Date;
import java.time.LocalDate;

public class Receipt {
    private int id;
    private int customerId;
    private double total;
    private LocalDate date;
    private String employeeUsername;

    public Receipt(int id, int customerId, double total, LocalDate date, String employeeUsername) {
        this.id = id;
        this.customerId = customerId;
        this.total = total;
        this.date = date;
        this.employeeUsername = employeeUsername;
    }

    // Getters
    public int getId() { return id; }
    public int getCustomerId() { return customerId; }
    public double getTotal() { return total; }
    public LocalDate getDate() { return date; }
    public String getEmployeeUsername() { return employeeUsername; }
}
