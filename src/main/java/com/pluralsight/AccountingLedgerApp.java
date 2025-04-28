package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AccountingLedgerApp {

//path to csv file where the transaction data will be saved

    private static final String finalPath = "src/main/resources/transaction.csv";

    //method to append a transaction to a csv file
    public static void saveTransactionToCsv(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(finalPath, true))) {

            //create a line to write the transactions date in to csv format
            String csvLine = transaction.getDate() + "|" +
                    transaction.getTime() + "|" +
                    transaction.getDescription() + "|" +
                    transaction.getVendor() + "|" +
                    transaction.getAmount();


            //write the formatted line to the csv file and move to a new line
            writer.write(csvLine);
            writer.newLine();   //Adds a new line after each entry


        } catch (IOException e) {
            throw new RuntimeException(e);




    /*

    //method to handle making a payment (convert transaction amount to negative)
    public static void payment(Transaction transaction1){

        //get the current amount from the transaction (it should be positive initially)

        double paymentAmount =transaction1.getAmount();

        // if the amount is positive, make it negative to represent a payment
        if (paymentAmount > 0){

            paymentAmount= -Math.abs(paymentAmount);   //insure amount is negative
            transaction1.setAmount(paymentAmount);    // set the new amount on the transaction

        }
         */
        }


    }
}
