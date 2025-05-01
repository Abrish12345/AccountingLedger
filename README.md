# CashFlowCommander
**CashFlowCommander** is a command-line Java application that helps users manage their financial transactions. 
It supports adding deposits and payments, and viewing filtered transaction reports like
monthly, yearly, and custom date ranges.


## Features
- Add **Deposits** and **Payments**
- View **All Transactions**
- Filter transactions:
    - Month to Date
    - Previous Month
    - Year to Date
    - Previous Year
- View only **Deposits** or only **Payments**
- All transactions are saved in a CSV file
```
=====================================
 
   *** CashFlowCommander ***
 Welcome to the CashFlowCommander App!
 =====================================
 Please choose an option:
 -------------------------------------
 D) Add Deposit!
 P) Make Payment (Debit)
 L) View Ledger
 X) Exit
 -------------------------------------
 Your choice: 
```
```
You chose to view a ledger
===================================
Please chose an option
-----------------------------------
A) All
D) Deposits
P) Payments
R) Reports
H) Home
```


## Project Structure

- `Transaction.java` - Defines the transaction model
- `CashFlowCommander.java` - Handles main logic and menu navigation
- `AccountLedgerApp.java` - Entry point for launching the application
- `transactions.csv` - File where transactions are saved and read

## How to Run (in IntelliJ)

1. Open the project in **IntelliJ IDEA**
2. Open `AccountLedgerApp.java`
3. Click the green **Run** button or right-click → **Run 'AccountLedgerApp'**
4. Follow the on-screen menu prompts to interact with your ledger

##  To Do

- Add delete/edit transaction feature
- Export report summaries
- Improve input validation

## To Do

- [ ] Add delete/edit transaction functionality
- [ ] Export filtered reports to a file (e.g., PDF or CSV)
- [ ] Improve input validation and error handling
- [ ] Add support for custom date range reports

## Author

**Abraham** 

