import java.io.IOException;
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
    public void display() throws IOException {
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

    private void withdrawCash() throws IOException {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        if (amount > 0 && amount <= user.getBalance())
        {
            user.setBalance(user.getBalance() - amount);
            db.updateUser(user); // Save changes for this user
            System.out.println("Withdrawal successful. New balance: $" + user.getBalance());
        }
        else
        {
            System.out.println("Invalid amount or insufficient funds.");
        }
    }

    private void depositCash() throws IOException {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();

        if (amount > 0)
        {
            user.setBalance(user.getBalance() + amount);
            db.updateUser(user); // Save changes for this user
            System.out.println("Deposit successful. New balance: $" + user.getBalance());
        }
        else
        {
            System.out.println("Invalid amount.");
        }
    }
}
