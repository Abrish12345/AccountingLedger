package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {

        //class properties that describes a transaction
        private LocalDate date;
        private LocalTime time;
        private String description;
        private String vendor;
        private Double amount;

        //constructor to initialize all properties of a transaction

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, Double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }
    //Getter and setter method for each property

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
       //Builds a custom message for the transaction when printed.
      //This makes it easier to print and display transaction details in a readable format.
     // Instead of a weird memory address, it displays the transaction's details nicely.


    // I got this code from internet
   @Override
    public String toString(){
        return "Transaction{" +
                "date=" + date + "|" +
                " time=" + time + "|" +
                "description='" + description + '\'' +  "|" +
                "vendor='" + vendor + '\''+  "|" +
                " amount=" + amount +  "|" ;
    }



}







