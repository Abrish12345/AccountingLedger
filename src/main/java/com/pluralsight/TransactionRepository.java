package com.pluralsight;

import java.awt.im.InputMethodHighlight;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


/**
 * cashFlowCommander is the main utility class for managing financial transactions.
 * it handles reading from and writing to CSV file, and provides methodes to:
 * -Save transactions
 * -Display all transactions, deposite, and payments
 * -Filter transactions by date (month to date, pervious month,year to date, previous year)
 * -search transactions by vendor
 **/


public class TransactionRepository {

//path to csv file where the transaction data will be saved

    /**
     *This method is responsible for saving a single transaction to CSV
     * Uses a BufferedWriter wrapped around a filewriter to write to the file
     **/

    private static final String finalPath = "src/main/resources/transaction.csv";

    /*
    *Appends a single transaction to the csv file for permanent storage.
    *each tramsaction is recorded on a new line in the following format
    * formateddate,formatedtime,description,vendor,amount
    * it uses a bufferWriter in append mode to keep existing data and add the new transaction at the end
    * transaction is added to the end of the file
     */
    public static void recordTransaction(Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(finalPath, true))) {

                //write the transaction details to the file
            writer.write(
                    transaction.getDate() + "|" +
                    transaction.getTime().withNano(0) + "|" +
                    transaction.getDescription() + "|" +
                    transaction.getVendor() + "|" +
                    transaction.getAmount() + "\n");

        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    //Method that reads transactions from the CSV file and returns them as a list
    public static ArrayList<Transaction> getTransaction() {

        //create an empity list to store transactions objects
        ArrayList<Transaction> transactions = new ArrayList<>();


        try {

            //initialize buffered reader to read file contents line by line.
            BufferedReader reader = new BufferedReader(new FileReader(finalPath));

            String line;

            //read the file line by line.
            while ((line = reader.readLine()) != null) {

                //split the line by '|' delimiter
                String[] parts = line.split("\\|");

                //check if the line has all required 5 Parts
                if (parts.length == 5) {

                    //parse each parts into the appropriate data type
                    LocalDate date = LocalDate.parse(parts[0]);                          //transaction date
                    LocalTime time = LocalTime.parse(parts[1]).withNano(0);//transaction time
                    String description = parts[2];                            //description of the transaction
                    String vendor = parts[3];                                //vendor name
                    double amount = Double.parseDouble(parts[4]);           //transaction amount

                    //Create a transaction object using parsed values
                    Transaction transaction = new Transaction(date, time, description, vendor, amount);

                    // print out the transaction details in a readable format
                    // System.out.println(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);

                    //add the transaction to the list
                    transactions.add(transaction);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //sort transactions first by date in decending order.
        // if two transactions have the same date, sort them by time in decending order.
        transactions.sort((t1,t2) -> {
            if (t1.getDate().equals(t2.getDate())){
                return t2.getTime().compareTo(t1.getTime());
            }else {
                return t2.getDate().compareTo(t1.getDate());
            }
        });
        //return the list of transactions read from the csv
        return transactions;
    }

    /**
     * display all transactions stored in the CSV file.
     * it reads the transactions from the file and prints each one to the console.
     **/

    public static void showAllTransaction() {
        //Dispaly meassage indicating that all transactions will be shown
        System.out.println("Displaying all transactions...");
        try {

            //Retrieve the list of transactions by calling the getTransaction method
            ArrayList<Transaction> transactions = getTransaction();

            //Iterate through each transaction in the list
            for (int i = 0; i < transactions.size(); i++) {

                //get the transaction at the current index in the list
                Transaction transaction = transactions.get(i);

                // print the transaction to the console
                System.out.println(transaction);
            }

        } catch (Exception e) {

            //Handle any errors that occur during file reading
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    /*
     * Display all deposite transactions from the CVS file.
     * A deposite is considerd any transaction with a positive amount
     * uses the getTransaction() method to retrive all transactions.
     * then filters and prints only those with an amount greater than zero
     */

    public static void showDeposits() {
        System.out.println("List of deposits only...");

        try {
            //Retrieve the list of transactions by calling the getTransaction method
            ArrayList<Transaction> transactions = getTransaction();

            //Iterate through each transaction in the list
            for (int i = 0; i < transactions.size(); i++) {

                //get the transaction at the current index in the list
                Transaction transaction = transactions.get(i);

                //check if the amount is positive deposit

                if (transaction.getAmount() > 0) {
                    System.out.println(transaction);

                }

            }


        } catch (Exception e) {

            //Handle any errors that occur during file reading
            System.out.println("Error reading the file: " + e.getMessage());

        }

    }
    /*
     * this method filters and displays only the payment transactions
     * A payment is identified by a nrgative amount in the transaction
     * it retrives the list of transactions from the CSV file using the getTransaction() method
     * it loops through each transaction and check if the amount is less than 0.
     * if the condition is true, it prints that transaction to the console.
    */
    public static void showPayment() {
        //inform the user that payment transaction are being displayed
        System.out.println("List of payments only...");

        try {

            //Retrieve the list of transactions by calling the getTransaction method
            ArrayList<Transaction> transactions = getTransaction();

            //Iterate through each transaction in the list
            for (int i = 0; i < transactions.size(); i++) {

                //get the transaction at the current index in the list
                Transaction transaction = transactions.get(i);

                //check if the amount is negative deposit

                if (transaction.getAmount() < 0) {
                    System.out.println(transaction);

                }

            }


        } catch (Exception e) {

            //Handle any errors that occur during file reading
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    /*
     * this method displays all transactions that occurred from the beginning of the current month up to today.
     * it gets todays date
     * it calculates the first day of the current month
     * the method retrives all transactions and loops through them.
     * it checks if the transaction date is on or after the first day of this month and before or on today.
     */

    public static void monthToDate() {
        System.out.println("Transactions from this month");

        //Retrieve the list of transactions by calling the getTransaction method
        ArrayList<Transaction> transactions = getTransaction();


        LocalDate today = LocalDate.now();    //get todays date
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);   //get the first day of the current month

        //Iterate through each transaction in the list
        for (int i = 0; i < transactions.size(); i++) {

            //get the transaction at the current index in the list
            Transaction transaction = transactions.get(i);

            //get the date of the transaction
            LocalDate date = transaction.getDate();

            //check if the date is withn the current month
            if ((!date.isBefore(firstDayOfMonth)  &&
                    !date.isAfter(today))) {

                System.out.println(transaction);
            }

        }
    }
/**
 * this method displays all transactions that happend during the previous month
 * it calculates the first and last days of the previous month
 * the method retrives all transactions and loops through them.
 * it checks whether each transactions date falls within that range
**/
    public static void previousMonth() {

        //Retrieve the list of transactions by calling the getTransaction method
        ArrayList<Transaction> transactions = getTransaction();


        LocalDate today = LocalDate.now();  // get todays date

        //get the first and last day of the pervious month
        LocalDate firstDayOfLastMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfLastMonth = today.withDayOfMonth(1).minusDays(1);


        //Iterate through each transaction in the list
        for (int i = 0; i < transactions.size(); i++) {

            //get the transaction at the current index in the list
            Transaction transaction = transactions.get(i);

            LocalDate date = transaction.getDate();
            //check if the date is within the previous month
            if (!date.isBefore(firstDayOfLastMonth)  &&
                    !date.isAfter(lastDayOfLastMonth)) {

                //create a transaction object and print it

                System.out.println(transaction);

            }


        }

    }

    /**
     *This method displays all transactions form the entier prrevious year.
     * It finds the first day and last day of the previous year
     * It reads all transactions using getTransations() method
     * it checks whether the date falls withn that one year period.
    **/


    public static void previousYear() {

        System.out.println("Display all transactions from the previous year: ");


        //Retrieve the list of transactions by calling the getTransaction method
        ArrayList<Transaction> transactions = getTransaction();

        LocalDate today = LocalDate.now();  // get todays date

        //get the first and last day of the pervious month
        LocalDate firstDayOfLastYear = today.minusYears(1).withDayOfYear(1);
        LocalDate lastDayOfLastYear = today.withDayOfYear(1).minusDays(1);

        //Iterate through each transaction in the list
        for (int i = 0; i < transactions.size(); i++) {

            //get the transaction at the current index in the list
            Transaction transaction = transactions.get(i);

            LocalDate date = transaction.getDate();

            //check if date is between first and last day of last year

            if ((date.isEqual(firstDayOfLastYear) ||  date.isAfter(firstDayOfLastYear)) &&
            (date.isEqual(lastDayOfLastYear) || date.isBefore(lastDayOfLastYear))){


                System.out.println(transaction);
            }

        }

    }

    /**
     * This method lets the user search for transactions by entering a specific vendors name.
     * retrives all transactions
     * it goes through each transaction and compares the vendor field
     **/

    public static void byVendor() {
        System.out.println("Enter the vendor name to search: ");


        String vInput = AccountLedgerApp.myScanner.nextLine().trim().toLowerCase();

        System.out.println("**************************************");
        System.out.println("Results for vendor: " + vInput);
        System.out.println("**************************************");


        //Retrieve the list of transactions by calling the getTransaction method
        ArrayList<Transaction> transactions = getTransaction();

        //Iterate through each transaction in the list
        for (int i = 0; i < transactions.size(); i++) {

            //get the transaction at the current index in the list
            Transaction transaction = transactions.get(i);

            if (transaction.getVendor().equalsIgnoreCase(vInput)) {
                System.out.println(transaction);

            }
        }
    }

    /**
     *This method displays all transactions that occured from the beginning of the current year up to today.
     * It gets todays date and the first day of the current year.
     * It retrives all transactions, it loops through each one.
     * It checkes whether the date is on or after jan 1st and on or before today.
     * If the date falls within this range, the transaction will be printed.
     **/

    public static void yearToDate() {
        System.out.println("**************************************");
        System.out.println("YEAR TO DATE OVERVIEW ");
        System.out.println("**************************************");


        //Retrieve the list of transactions by calling the getTransaction method
        ArrayList<Transaction> transactions = getTransaction();


        LocalDate today = LocalDate.now();  // get todays date

        //get the first and last day of the pervious month
        LocalDate firstDayOfYear = today.withDayOfYear(1);


        //Read the file line by line
        //Iterate through each transaction in the list
        for (int i = 0; i < transactions.size(); i++) {

            //get the transaction at the current index in the list
            Transaction transaction = transactions.get(i);

            LocalDate date = transaction.getDate();

            //check if date is between first and last day of last year

            if ((date.isEqual(firstDayOfYear) || date.isAfter(firstDayOfYear)) &&
                   !date.isAfter(today)) {


                System.out.println(transaction);
            }

        }

    }
  public static void showTransactionByAmount(){
        try {

            //prompts the user for minimum amount
            System.out.println("Enter the Minimum amount: ");
            String minAmountStr = AccountLedgerApp.myScanner.nextLine();

            //prompts the user for maximum amounts
            System.out.println("Enter the Maximum amount: ");
            String maxAmountStr = AccountLedgerApp.myScanner.nextLine();

            //Convert the entered amounts to double
            double minAmount = Double.parseDouble(minAmountStr);
            double maxAmount = Double.parseDouble(maxAmountStr);

            //retrive the list of transaction
            ArrayList<Transaction> transactions = getTransaction();

            System.out.println("Transaction between $" + minAmount + "and $" + maxAmount + ":");

             //Iterate through each transaction in the list
            for (int i = 0; i < transactions.size(); i++) {

                //get the transaction at the current index in the list
                Transaction transaction = transactions.get(i);

                //comparse the transactions amount with the user specified range
                if (transaction.getAmount() >= minAmount && transaction.getAmount() <= maxAmount) {

                    System.out.println(transaction);

                }
            }
        }catch (NumberFormatException e){

            //handles invalid inpute if the user enters non numeric values for amounts
            System.out.println("Invalid input!");

        }catch (Exception e) {
            //catches other errors (e.g file reading issues) and prints an error message
            System.out.println("Error reading the file: " + e.getMessage());
        }
  }
}

















