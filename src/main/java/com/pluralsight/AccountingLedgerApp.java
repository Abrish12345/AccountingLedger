package com.pluralsight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AccountingLedgerApp {

//path to csv file
private static final String finalPath="src/main/resources/transaction.csv";

//method to append a transaction to a csv file
    public static void saveTransactionToCsv(Transaction transaction){
try(BufferedWriter writer = new BufferedWriter(new FileWriter(finalPath, true))){
    String csvLine= transaction.getDate() + "|" +
            transaction.getTime() + "|" +
            transaction.getDescription() + "|" +
            transaction.getVendor() + "|" +
            transaction.getAmount();


    writer.write(csvLine);
    writer.newLine();   //Adds a new line after each entry


} catch (IOException e) {
    throw new RuntimeException(e);
}


    }

}
