package com.example.validation.ui.home;

public class dataBase {
    private Integer expenses;
   private  String uid;
  private   Integer amount;
   private String date;
  private  String category;
   private int Balance;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public dataBase(Integer amount, Integer expenses, String category, String date, int Balance, String uid){
        this.amount = amount;
        this.Balance= Balance;
        this.date = date;
        this.category = category;
        this.expenses = expenses;
        this.uid = uid;



    }
public dataBase(){

}
//    public dataBase(String amount, String date, String category, String expenses){
//        // empty constructor
//    }
 // get method

    public void setExpenses(Integer expenses) {
        this.expenses = expenses;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBalance(int balance) {
        Balance = balance;
    }
    public Integer getAmount() {
        return amount;
    }
    public int getBalance() {
        return Balance;
    }
    public String getCategory() {
        return category;
    }
    public String getDate() {
        return date;
    }
    public Integer getExpenses() {
        return expenses;
    }







}
