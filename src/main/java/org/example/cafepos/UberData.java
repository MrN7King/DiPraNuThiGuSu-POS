package org.example.cafepos;

import java.sql.Timestamp;

public class UberData{
    private int id;
    private String tripId;
    private Timestamp reqTime;
    private Timestamp dropTime;
    private String dropAddr;
    private String custName;
    private double amount;
    private double commission;
    private String payMethod;

    public UberData(int id, String tripId, Timestamp reqTime, Timestamp dropTime, String dropAddr,
                     String custName, double amount, double commission, String payMethod) {
        this.id = id;
        this.tripId = tripId;
        this.reqTime = reqTime;
        this.dropTime = dropTime;
        this.dropAddr = dropAddr;
        this.custName = custName;
        this.amount = amount;
        this.commission = commission;
        this.payMethod = payMethod;
    }

    // Getters and Setters for TableView bindings
    public int getId() { return id; }
    public String getTripId() { return tripId; }
    public Timestamp getReqTime() { return reqTime; }
    public Timestamp getDropTime() { return dropTime; }
    public String getDropAddr() { return dropAddr; }
    public String getCustName() { return custName; }
    public double getAmount() { return amount; }
    public double getCommission() { return commission; }
    public String getPayMethod() { return payMethod; }

    public void setId(int id) { this.id = id; }
    public void setTripId(String tripId) { this.tripId = tripId; }
    public void setReqTime(Timestamp reqTime) { this.reqTime = reqTime; }
    public void setDropTime(Timestamp dropTime) { this.dropTime = dropTime; }
    public void setDropAddr(String dropAddr) { this.dropAddr = dropAddr; }
    public void setCustName(String custName) { this.custName = custName; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setCommission(double commission) { this.commission = commission; }
    public void setPayMethod(String payMethod) { this.payMethod = payMethod; }
}
