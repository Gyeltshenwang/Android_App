package com.example.validation.ui.TotalExpenseTable;

public class graphModel {
    int balance;
    long time;
    public graphModel() {
    }

    public graphModel(int balance, long time) {
        this.balance = balance;
        this.time = time;
    }
    public int getBalance() {
        return balance;
    }
    public long getTime() {
        return time;
    }

}
