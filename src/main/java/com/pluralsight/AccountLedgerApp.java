package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import static com.pluralsight.CashFlowCommander.*;

public class AccountLedgerApp {
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
        System.out.print(" Your choice: ");
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

                showAllTransaction();
                break;

            case"D":
                showDeposits();

                break;
            case "P":
                showPayment();

                break;
            case"R":
                System.out.println("Generating reports...");
                reportsMenu();

                break;

            case "H":

                System.out.println("Returning to home menu...");



                break;

            default:
                System.out.println("Invalid choose. Please select A, D, P, R, or H.");
        }

    }
    public static void reportsMenu(){
        System.out.println("You chose to view reports");
        System.out.println("===================================");

        //show sub-menu option related to the reports
        System.out.println("Please chose a report option");
        System.out.println("-----------------------------------");
        System.out.println("1) Month To Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year To Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search By Vendor");
        System.out.println("0) Back");

        String reportChoice=myScanner.nextLine().toUpperCase();

        switch (reportChoice){

            case "1":

                monthToDate();
                break;


            case "2":
                previousMonth();

                break;

            case "3":
                yearToDate();

                break;
            case "4":

                break;
            case "5":
                byVendor();

                break;
            case "0":

                reportsMenu();

                break;

        }


    }

}




