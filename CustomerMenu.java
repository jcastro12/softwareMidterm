import java.util.Scanner;

public class CustomerMenu implements menuInterface
{
    private final Scanner scanner;
    private double balance;

    public CustomerMenu(Scanner scanner, double initialBalance)
    {
        this.scanner = scanner;
        this.balance = initialBalance;
    }

    @Override
    public void display()
    {
        while (true)
        {
            System.out.println("\nCustomer Menu:");
            System.out.println("1 - Withdraw Cash");
            System.out.println("2 - Deposit Cash");
            System.out.println("3 - Display Balance");
            System.out.println("4 - Exit");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            switch (choice)
            {
                case 1:
                    withdrawCash();
                    break;
                case 2:
                    depositCash();
                    break;
                case 3:
                    displayBalance();
                    break;
                case 4:
                    System.out.println("Exiting... Thank you for using the ATM.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void withdrawCash()
    {
        System.out.print("Enter the withdrawal amount: ");
        double amount = scanner.nextDouble();

        if (amount <= 0)
        {
            System.out.println("Invalid amount. Please enter a positive number.");
            return;
        }
        if (amount > balance)
        {
            System.out.println("Insufficient balance. Transaction canceled.");
            return;
        }

        balance -= amount;
        System.out.println("Cash successfully withdrawn.");
        System.out.println("Amount: " + amount);
        System.out.println("New Balance: " + balance);
    }

    private void depositCash()
    {
        System.out.print("Enter the cash amount to deposit: ");
        double amount = scanner.nextDouble();

        if (amount <= 0)
        {
            System.out.println("Invalid amount. Please enter a positive number.");
            return;
        }

        balance += amount;
        System.out.println("Cash successfully deposited.");
        System.out.println("Amount: " + amount);
        System.out.println("New Balance: " + balance);
    }

    private void displayBalance()
    {
        System.out.println("Current Balance: " + balance);
    }
}
