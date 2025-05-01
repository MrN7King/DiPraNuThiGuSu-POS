package org.example.cafepos;
import java.sql.Date;

public class ExpenseData {
    private Integer id;
    private String expenseId;
    private String expenseType;
    private String description;
    private Double amount;
    private Date date;
    private String recordedBy;

    public ExpenseData(Integer id, String expenseId, String expenseType,
                       String description, Double amount, Date date, String recordedBy) {
        this.id = id;
        this.expenseId = expenseId;
        this.expenseType = expenseType;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.recordedBy = recordedBy;
    }

    // Getters - MAKE SURE THESE ARE CORRECT
    public Integer getId() { return id; }
    public String getExpenseId() { return expenseId; }
    public String getExpenseType() { return expenseType; }
    public String getDescription() { return description; }  // This was likely returning expenseType before
    public Double getAmount() { return amount; }
    public Date getDate() { return date; }
    public String getRecordedBy() { return recordedBy; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setExpenseId(String expenseId) { this.expenseId = expenseId; }
    public void setExpenseType(String expenseType) { this.expenseType = expenseType; }
    public void setDescription(String description) { this.description = description; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setDate(Date date) { this.date = date; }
    public void setRecordedBy(String recordedBy) { this.recordedBy = recordedBy; }
}