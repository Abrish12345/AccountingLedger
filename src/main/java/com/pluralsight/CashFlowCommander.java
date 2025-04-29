package com.pluralsight;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Scanner;

import static com.pluralsight.AccountingLedgerApp.saveTransactionToCsv;

public class CashFlowCommander {
    private static final String finalPath = "src/main/resources/transaction.csv";

    //Create a scanner object to read user input from the console
    static Scanner myScanner = new Scanner(System.in);
    public static void main(String[] args) {


        //Variable to control the loop. it will be set to true to exit the loop.
        boolean exit =false;

        //This loop will keep running until the user selects the 'x' option to exit
        while (!exit){
             //Display the main menu to the user
            displayMenu();

            //Read the user's choice and convert it to uppercase to handle both lowercase and uppercase.
            String choice = myScanner.nextLine().toUpperCase();

            //Use a switch statement to handle different user choices
            switch (choice){

                //user choice to make a payment
                case "D":
                    processDeposit();

                    break;

                    //user chose to make payment.
                case "P":
                    processPayment();

                    break;

                    //user chose to view the ledger
                case "L":
                    ledgerMenu();

                    break;

                    //User chose to exit
                case "X":
                    System.out.println("Goodbye. Your financial records are safe with us.");
                    exit = true;  //Set exit to true to break out of the loop

                    break;

                    //Handles invalid inputs (if the user types other than D,P,L, or X )
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }

        }

    }

    //Method to display the main menu options to the user.
    //This method will be called in the main loop of the program to
    //present the available actions such as adding a deposit
    //making a payments, viewing the ledger, or exiting the application
    public static void displayMenu(){

        System.out.println(" =====================================");
        System.out.println(" \n   *** CashFlowCommander ***");
        System.out.println(" Welcome to the CashFlowCommander App!");
        System.out.println(" =====================================");
        System.out.println(" Please choose an option:");
        System.out.println(" -------------------------------------");
        System.out.println(" D) Add Deposit!");
        System.out.println(" P) Make Payment (Debit)");
        System.out.println(" L) View Ledger");
        System.out.println(" X) Exit");
        System.out.println(" -------------------------------------");
        System.out.print(" Your choice");
    }

    public static void processDeposit(){
        System.out.println("You have selected to add a deposit transaction");

        //prompts the user for transaction detail.

        //Asks the user for product description and collect the input.
        System.out.println("Enter description: ");
        String description = myScanner.nextLine();

        //Ask the user to inter the vendor and collect the input.
        System.out.println("Enter vendor: ");
        String vendor= myScanner.nextLine();

        //Ask the user to inter the amount and collect the input
        System.out.println("Enter amount: ");
        Double amount= Double.parseDouble(myScanner.nextLine()); //parse amount to double

        System.out.println("you successfully deposited to your account.");

        //capture the current date and time
        LocalDate date= LocalDate.now();
        LocalTime time= LocalTime.now().withNano(0);

        Transaction transaction= new Transaction(date,time,description,vendor,amount);

        saveTransactionToCsv(transaction);

    }

    public static void processPayment (){

        //Ask the user to payment description.
        System.out.println("You chose to make a payment");

        System.out.println("Enter description: ");
        String paymentDescription = myScanner.nextLine();

        //Ask the user to inter the payment vendor and collect the input.
        System.out.println("Enter vendor: ");
        String paymentVendor= myScanner.nextLine();

        //Ask the user to inter the payment amount and collect the input
        System.out.println("Enter amount: ");
        Double paymentAmount= Double.parseDouble(myScanner.nextLine()); //parse amount to double


        //change the value in to negative
        paymentAmount=-paymentAmount;


        //capture the current date and time
        LocalDate paymentDate= LocalDate.now();
        LocalTime paymentTime= LocalTime.now().withNano(0);



        Transaction transaction1 = new Transaction(paymentDate,paymentTime,paymentDescription,paymentVendor,paymentAmount);


        saveTransactionToCsv(transaction1);

        System.out.println("you successfully made a payment.");

    }
    //Method to display the ledger menu and handle users sub-choice
    public static void ledgerMenu(){

        //inform the user they selected the ledger option
        System.out.println("You chose to view a ledger");
        System.out.println("===================================");

        //show sub-menu option related to the ledger
        System.out.println("Please chose an option");
        System.out.println("-----------------------------------");
        System.out.println("A) All");
        System.out.println("D) Deposits");
        System.out.println("P) Payments");
        System.out.println("R) Reports");
        System.out.println("H) Home");

        //Accept users input for ledger menu and convert to uppercase
        String ledgerChoise = myScanner.nextLine().toUpperCase();


        //Use a switch statement to handle different user choices
        switch(ledgerChoise){

            //case for showing all transaction.
            case"A":
                System.out.println("Displaying all transactions...");
                try{

                    //creat a file object using the path to the transaction CSV file.
                    File file = new File(finalPath);

                    //initialize bufferedreader to read file contents.
                    BufferedReader reader = new BufferedReader(new FileReader(file));

                    String line;

                    //read the file line by line.
                    while ((line=reader.readLine()) !=null){

                        //split the line by '|' delimiter
                        String[] parts =line.split("\\|");

                        //print out the transaction details in a readable format
                        if (parts.length==5){
                            String date = parts[0];
                            String time = parts[1];
                            String description = parts[2];
                            String vendor = parts[3];
                            String amount = parts[4];

                            // print out the transaction details in a readable format
                            System.out.println(date + "|" + time + "|" + description + "|" + vendor + "|" + amount);
                        }

                    }

                    //close the file reader
                    reader.close();
                }catch (Exception e){

                            //Handle any errors that occur during file reading
                             System.out.println("");
                }
                break;

            case"D":

                System.out.println("List of deposits only...");

                try{
                    //creat a file object using the path to the transaction CSV file.
                    File file = new File(finalPath);

                    //initialize bufferedreader to read file contents.
                    BufferedReader reader = new BufferedReader(new FileReader(file));

                    String line;

                    //read the file line by line.
                    while ((line=reader.readLine()) !=null){

                        //split the line by '|' delimiter
                        String[] parts =line.split("\\|");

                        //print out the transaction details in a readable format
                        if (parts.length==5){
                           double amount =Double.parseDouble(parts[4]);

                           if (amount > 0){

                               System.out.println(line);
                           }
                        }

                    }


                } catch (IOException e) {
                    throw new RuntimeException(e);

                }


                break;
            case "P":

                //inform the user that payment transaction are being displayed
                System.out.println("List of payments only...");

                try {

                    // create a file object pointing to the csv file path
                    File file = new File(finalPath);

                    //initialize bufferedreder to read file contents
                    BufferedReader reader= new BufferedReader(new FileReader(file));

                    String line;

                     //Read the file line by line
                    while ((line=reader.readLine()) !=null){

                        //split the line into parts using '|' as the delimiter
                        String parts[] =line.split("\\|");

                        //ensure there are 5 parts in totall
                        if(parts.length==5){

                            //parse the amount (fifith field) in to double
                            double amount =Double.parseDouble(parts[4]);

                            //check if the amount is negative
                            if (amount<0){
                                System.out.println(line);
                            }

                        }

                    }
                        //close the bufferedreader after proccessing is complite
                    reader.close();

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                break;
            case"R":
                System.out.println("Generating reports...");

                break;

            case "H":

                System.out.println("Returning to home menu...");

            default:
                System.out.println("Invalid choise. Please select A, D, P, R, or H.");
        }

    }

}




