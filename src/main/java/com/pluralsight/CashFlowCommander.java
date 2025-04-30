package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class CashFlowCommander {

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

        }

    }

    public static void showAllTransaction() {
        System.out.println("Displaying all transactions...");
        try {

            //create a file object using the path to the transaction CSV file.
            File file = new File(finalPath);

            //initialize buffered reader to read file contents.
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            //read the file line by line.
            while ((line = reader.readLine()) != null) {

                //split the line by '|' delimiter
                String[] parts = line.split("\\|");

                //print out the transaction details in a readable format
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    //Create a transaction object using parts

                    Transaction transaction = new Transaction(date, time, description, vendor, amount);
                    // print out the transaction details in a readable format
                    // System.out.println(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);

                    System.out.println(transaction);
                }

            }

            //close the file reader
            reader.close();
        } catch (Exception e) {

            //Handle any errors that occur during file reading
            System.out.println("Error reading transaction");
        }
    }

    public static void showDeposits() {
        System.out.println("List of deposits only...");

        try {
            //create a file object using the path to the transaction CSV file.
            File file = new File(finalPath);

            //initialize buffered reader to read file contents.
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            //read the file line by line.
            while ((line = reader.readLine()) != null) {

                //split the line by '|' delimiter
                String[] parts = line.split("\\|");

                //print out the transaction details in a readable format
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    //Create a transaction object using parts

                    Transaction transaction = new Transaction(date, time, description, vendor, amount);

                    if (amount > 0) {


                        System.out.println(transaction);
                    }
                }

            }


        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    public static void showPayment() {
        //inform the user that payment transaction are being displayed
        System.out.println("List of payments only...");

        try {

            // create a file object pointing to the csv file path
            File file = new File(finalPath);

            //initialize bufferedreder to read file contents
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            //Read the file line by line
            while ((line = reader.readLine()) != null) {

                //split the line into parts using '|' as the delimiter
                String parts[] = line.split("\\|");

                //ensure there are 5 parts in total
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    //Create a transaction object using parts

                    Transaction transaction = new Transaction(date, time, description, vendor, amount);
                    //check if the amount is negative
                    if (amount < 0) {
                        System.out.println(transaction);
                    }

                }

            }
            //close the buffered reader after processing is Complete.
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void monthToDate() {
        System.out.println("Transactions from this month");

        try (BufferedReader reader = new BufferedReader(new FileReader(finalPath))) {

            String line;

            LocalDate today = LocalDate.now();
            LocalDate firstDayOfMonth = today.withDayOfMonth(1);

            //Read the file line by line
            while ((line = reader.readLine()) != null) {

                //split the line into parts using '|' as the delimiter
                String parts[] = line.split("\\|");

                //ensure there are 5 parts in totall
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    if ((date.isEqual(firstDayOfMonth) || date.isAfter(firstDayOfMonth)) && date.isBefore(today.plusDays(1))) {
                        Transaction transaction = new Transaction(date, time, description, vendor, amount);
                        System.out.println(transaction);
                    }

                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void previousMonth() {

        try (BufferedReader reader = new BufferedReader(new FileReader(finalPath))) {

            String line;

            LocalDate today = LocalDate.now();
            LocalDate firstDayOfLastMonth = today.withDayOfMonth(1).minusDays(1);

            //Read the file line by line
            while ((line = reader.readLine()) != null) {

                //split the line into parts using '|' as the delimiter
                String parts[] = line.split("\\|");

                //ensure there are 5 parts in total
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    if ((date.isBefore(firstDayOfLastMonth) || date.isAfter(firstDayOfLastMonth)) && (date.isEqual(firstDayOfLastMonth) || date.isBefore(firstDayOfLastMonth))) {
                        Transaction transaction = new Transaction(date, time, description, vendor, amount);
                        System.out.println(transaction);

                    }


                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
