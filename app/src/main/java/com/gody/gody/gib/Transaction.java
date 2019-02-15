package com.gody.gody.gib;

public class Transaction
{
    private int sender;
    private int receiver;
    private double amount;
    private String date;

    public Transaction(int sender, int receiver, double amount, String date)
    {
        this.sender     = sender;
        this.receiver   = receiver;
        this.amount     = amount;
        this.date       = date;
    }

    public Transaction()
    {}

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
