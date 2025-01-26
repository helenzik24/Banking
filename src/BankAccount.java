
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankAccount {
    private double balance;
    private String accountName;

    // List to store all accounts
    private static List<BankAccount> accounts = new ArrayList<>();

    // Default constructor
    public BankAccount() {
        this.balance = 0.0;
        this.accountName = "Unnamed Account";
        accounts.add(this);
    }

    // Constructor with balance and name parameters
    public BankAccount(double balance, String accountName) {
        this.balance = balance;
        this.accountName = accountName;
        accounts.add(this);
    }

    // Method to deposit an amount
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Method to withdraw an amount
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrew: " + amount);
        } else if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            System.out.println("Withdrawal amount must be positive.");
        }
    }

    // Method to print the current balance
    public void printBalance() {
        System.out.println(accountName + " - Current balance: " + balance);
    }

    // Method to transfer balance to another account
    public void transfer(BankAccount toAccount, double amount) {
        if (amount > 0 && amount <= balance) {
            this.withdraw(amount);
            toAccount.deposit(amount);
            System.out.println("Transferred: " + amount + " to " + toAccount.accountName);
        } else {
            System.out.println("Transfer failed. Check the amount and your balance.");
        }
    }

    // Method to generate a report of all accounts
    public static void generateReport() {
        try (FileWriter writer = new FileWriter("accounts_report.txt")) {
            writer.write("Account Name, Balance\n");
            for (BankAccount account : accounts) {
                writer.write(account.accountName + ", " + account.balance + "\n");
            }
            System.out.println("Account report saved to accounts_report.txt");
        } catch (IOException e) {
            System.out.println("An error occurred while generating the report.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create two accounts for testing
        BankAccount account1 = new BankAccount(50.0, "Savings Account");
        BankAccount account2 = new BankAccount(100.0, "Checking Account");

        System.out.println("Welcome to the Banking Application!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Print Balance");
            System.out.println("4. Transfer");
            System.out.println("5. Generate Report");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter account name: ");
                    scanner.nextLine(); // Consume newline
                    String depositAccountName = scanner.nextLine();
                    BankAccount depositAccount = findAccountByName(depositAccountName);
                    if (depositAccount != null) {
                        System.out.print("Enter deposit amount: ");
                        double depositAmount = scanner.nextDouble();
                        depositAccount.deposit(depositAmount);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 2:
                    System.out.print("Enter account name: ");
                    scanner.nextLine(); // Consume newline
                    String withdrawAccountName = scanner.nextLine();
                    BankAccount withdrawAccount = findAccountByName(withdrawAccountName);
                    if (withdrawAccount != null) {
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawAmount = scanner.nextDouble();
                        withdrawAccount.withdraw(withdrawAmount);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 3:
                    for (BankAccount account : accounts) {
                        account.printBalance();
                    }
                    break;
                case 4:
                    System.out.print("Enter source account name: ");
                    scanner.nextLine(); // Consume newline
                    String sourceAccountName = scanner.nextLine();
                    BankAccount sourceAccount = findAccountByName(sourceAccountName);
                    if (sourceAccount != null) {
                        System.out.print("Enter target account name: ");
                        String targetAccountName = scanner.nextLine();
                        BankAccount targetAccount = findAccountByName(targetAccountName);
                        if (targetAccount != null) {
                            System.out.print("Enter transfer amount: ");
                            double transferAmount = scanner.nextDouble();
                            sourceAccount.transfer(targetAccount, transferAmount);
                        } else {
                            System.out.println("Target account not found.");
                        }
                    } else {
                        System.out.println("Source account not found.");
                    }
                    break;
                case 5:
                    generateReport();
                    break;
                case 6:
                    System.out.println("Thank you for using the Banking Application!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Helper method to find an account by name
    private static BankAccount findAccountByName(String accountName) {
        for (BankAccount account : accounts) {
            if (account.accountName.equalsIgnoreCase(accountName)) {
                return account;
            }
        }
        return null;
    }
}
