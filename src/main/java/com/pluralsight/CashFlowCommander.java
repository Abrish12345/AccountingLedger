package com.pluralsight;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Scanner;

import static com.pluralsight.AccountingLedgerApp.saveTransactionToCsv;

public class CashFlowCommander {
    public static void main(String[] args) {

        //Create a scanner object to read user input from the console
        Scanner myScanner = new Scanner(System.in);

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



                    break;

                    //user chose to make payment.
                case "P":

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
                    System.out.println("you successfully deposited to your account.");

                    //capture the current date and time
                    LocalDate paymentDate= LocalDate.now();
                    LocalTime paymentTime= LocalTime.now().withNano(0);



                    Transaction transaction1 = new Transaction(paymentDate,paymentTime,paymentDescription,paymentVendor,paymentAmount);

                     
                    saveTransactionToCsv(transaction1);

                    //

                    break;

                    //user chose to view the ledger
                case "L":
                    System.out.println("You chose to view a ledger");
                    System.out.println("==================================");
                    System.out.println("Please choose an option");
                    System.out.println("----------------------------------");
                    System.out.println("A) All");
                    System.out.println("D) Deposits");
                    System.out.println("P) Payments");
                    System.out.println("R) Reports");
                    System.out.println("H) Home");



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

}
