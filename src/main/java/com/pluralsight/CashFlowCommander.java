package com.pluralsight;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;


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

            LocalDate today = LocalDate.now();    //get todays date
            LocalDate firstDayOfMonth = today.withDayOfMonth(1);   //get the first day of the current month

            //Read the file line by line
            while ((line = reader.readLine()) != null) {

                //split the line into parts using '|' as the delimiter
                String parts[] = line.split("\\|");

                //make sure the line contains 5 parts: date, time, description, vendor, amount.
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    //check if the date is withn the current month
                    if ((date.isEqual(firstDayOfMonth) || date.isAfter(firstDayOfMonth)) && date.isBefore(today.plusDays(1))) {

                        //create a transaction object and print it
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

            LocalDate today = LocalDate.now();  // get todays date

            //get the first and last day of the pervious month
            LocalDate firstDayOfLastMonth = today.minusMonths(1).withDayOfMonth(1);
            LocalDate lastDayOfLastMonth = today.withDayOfMonth(1).minusDays(1);

            //Read the file line by line
            while ((line = reader.readLine()) != null) {

                //split the line into parts using '|' as the delimiter
                String parts[] = line.split("\\|");

                //make sure the line contains 5 parts: date, time, description, vendor, amount.
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    //check if the date is within the previous month
                    if ((date.isEqual(firstDayOfLastMonth) || date.isAfter(firstDayOfLastMonth)) &&
                            (date.isEqual(lastDayOfLastMonth) || date.isBefore(lastDayOfLastMonth))) {

                        //create a transaction object and print it
                        Transaction transaction = new Transaction(date, time, description, vendor, amount);
                        System.out.println(transaction);

                    }


                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void previousYear() {

        System.out.println("Display all transactions from the previous year: ");


        try (BufferedReader reader = new BufferedReader(new FileReader(finalPath))) {

            String line;

            LocalDate today = LocalDate.now();  // get todays date

            //get the first and last day of the pervious month
            LocalDate firstDayOfLastYear = today.minusYears(1).withDayOfYear(1);
            LocalDate lastDayOfLastYear = today.withDayOfYear(1).minusDays(1);

            //Read the file line by line
            while ((line = reader.readLine()) != null) {

                //split the line into parts using '|' as the delimiter
                String parts[] = line.split("\\|");

                //make sure the line contains 5 parts: date, time, description, vendor, amount.
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    //check if date is between first and last day of last year

                    if ((date.isEqual(firstDayOfLastYear) || date.isAfter(firstDayOfLastYear)) && (date.isEqual(lastDayOfLastYear) ||
                            date.isBefore(lastDayOfLastYear))) {

                        Transaction transaction = new Transaction(date, time, description, vendor, amount);
                        System.out.println(transaction);
                    }

                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public static void byVendor() {
        System.out.println("Enter the vendor name to search: " );


        String vInput = AccountLedgerApp.myScanner.nextLine().trim().toLowerCase();


        System.out.println("**************************************" );
        System.out.println("Results for vendor: " + vInput );
        System.out.println("**************************************" );



        try {

            //create a file object using the path to the transaction CSV file.
            File file = new File(finalPath);

            //initialize buffered reader to read file contents.
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;

            //read the file line by line.
            while ((line = reader.readLine()) != null ) {

                //split the line by '|' delimiter
                String[] parts = line.split("\\|");


                //print out the transaction details in a readable format
                if(parts[3].contains(vInput)) {
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

    public static void yearToDate() {
        System.out.println("**************************************");
        System.out.println("YEAR TO DATE OVERVIEW ");
        System.out.println("**************************************");

        try (BufferedReader reader = new BufferedReader(new FileReader(finalPath))) {

            String line;

            LocalDate today = LocalDate.now();  // get todays date

            //get the first and last day of the pervious month
            LocalDate firstDayOfYear = today.withDayOfYear(1);


            //Read the file line by line
            while ((line = reader.readLine()) != null) {

                //split the line into parts using '|' as the delimiter
                String parts[] = line.split("\\|");

                //make sure the line contains 5 parts: date, time, description, vendor, amount.
                if (parts.length == 5) {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    double amount = Double.parseDouble(parts[4]);

                    //check if date is between first and last day of last year

                    if ((date.isEqual(firstDayOfYear) || date.isAfter(firstDayOfYear)) &&
                            date.isBefore(today.plusDays(1))) {

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


