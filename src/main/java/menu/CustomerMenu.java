package menu;

import database.User;
import database.json.db;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CustomerMenu implements menuInterface
{
    private final Scanner scanner;
    private final User user;

    public CustomerMenu(Scanner scanner, User user)
    {
        this.scanner = scanner;
        this.user = user;
    }

    @Override
    public void display() throws IOException
    {
        while (true)
        {
            System.out.println("\nCustomer Menu:");
            System.out.println("1----Withdraw Cash");
            System.out.println("2----Deposit Cash");
            System.out.println("3----Display Balance");
            System.out.println("4----Exit");
            System.out.print("Select an option: ");
            int choice = inputHelper();

            switch (choice)
            {
                case 1:
                    withdrawCash();
                    break;
                case 2:
                    depositCash();
                    break;
                case 3:
                    System.out.println("Account #" + user.getId());
                    displayDate();
                    updateUserRecord();
                    System.out.println("Current Balance: $" + user.getBalance());
                    break;
                case 4:
                    System.out.println("Exiting... Thank you for using the ATM.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void withdrawCash() throws IOException
    {
        System.out.print("Enter the withdrawal amount: ");
        double amount = doubleInputHelper();
        System.out.println(amount);

        if (amount > 0 && amount <= user.getBalance())
        {
            System.out.println("Account #" + user.getId());
            displayDate();
            performWithdrawal(amount);
            updateUserRecord();
            System.out.println("Withdrawn: " + amount);
            System.out.println("Balance: $" + user.getBalance());
        }
        else
        {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }

    private void depositCash() throws IOException
    {
        System.out.print("Enter the amount of cash to deposit: ");
        double amount = doubleInputHelper();

        if (amount > 0)
        {
            System.out.println("Cash Deposited Successfully.");
            System.out.println("Account #" + user.getId());
            displayDate();
            performDeposit(amount);
            updateUserRecord();
            System.out.println("Deposited: " + amount);
            System.out.println("Balance: $" + user.getBalance());
        }
        else
        {
            System.out.println("Invalid amount.");
        }
    }

    private void performWithdrawal(double amount)
    {
        user.setBalance(user.getBalance() - amount);
    }

    private void performDeposit(double amount)
    {
        user.setBalance(user.getBalance() + amount);
    }

    private void updateUserRecord() throws IOException
    {
        db.updateUser(user);
    }

    private void displayDate()
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.now();
        System.out.println(dtf.format(localDate));
    }

    private double doubleInputHelper()
    {
        double input;
        while (true)
        {
            if (scanner.hasNextDouble())
            {
                input = scanner.nextDouble();
                scanner.nextLine();
                return input;
            }
            else
            {
                System.out.println("Invalid input. Please enter a double.");
                scanner.next();
            }
        }
    }

    public int inputHelper()
    {
        int input;
        while (true)
        {
            if (scanner.hasNextInt())
            {
                input = Integer.parseInt(scanner.next());
                scanner.nextLine();
                return input;
            }
            else
            {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine();
            }
        }
    }
}
