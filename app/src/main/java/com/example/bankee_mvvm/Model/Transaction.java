package com.example.bankee_mvvm.Model;

public class Transaction {
    private String transactionId;
    private String senderId;
    private String receiverId;
    private double amount;
    private long timestamp;
    private String transactionType;

    // Default constructor (needed for Firebase)
    public Transaction() {
    }

    public Transaction(String transactionId, String senderId, String receiverId, double amount, long timestamp, String transactionType) {
        this.transactionId = transactionId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.transactionType = transactionType;
    }

    // Getters and setters
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
