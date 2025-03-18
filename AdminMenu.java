import java.util.Scanner;

public class AdminMenu implements menuInterface
{
    private final Scanner scanner;

    public AdminMenu(Scanner scanner)
    {
        this.scanner = scanner;
    }

    @Override
    public void display()
    {
        while (true)
        {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1 - Create New Account");
            System.out.println("2 - Delete Existing Account");
            System.out.println("3 - Update Account Information");
            System.out.println("4 - Search for Account");
            System.out.println("5 - Exit");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            switch (choice)
            {
                case 1:
                    createNewAccount();
                    break;
                case 2:
                    deleteAccount();
                    break;
                case 3:
                    updateAccountInfo();
                    break;
                case 4:
                    searchAccount();
                    break;
                case 5:
                    System.out.println("Exiting... Thank you for using the ATM.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void createNewAccount()
    {
        System.out.println("Create New Account:");
        System.out.print("Enter Login: ");
        String login = scanner.next();

        System.out.print("Enter Pin Code (5 digits): ");
        String pinCode = scanner.next();

        if (pinCode.length() != 5 || !pinCode.matches("\\d+"))
        {
            System.out.println("Invalid pin code. It should be a 5-digit number.");
            return;
        }

        System.out.print("Enter Account Holder Name: ");
        String holderName = scanner.next();

        System.out.print("Enter Starting Balance: ");
        double balance = scanner.nextDouble();

        System.out.print("Enter Account Status (Active/Disabled): ");
        String status = scanner.next();

        int accountNumber = 1;

        System.out.println("Account Successfully Created!");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Holder: " + holderName);
        System.out.println("Starting Balance: " + balance);
        System.out.println("Status: " + status);
    }

    private void deleteAccount()
    {
        System.out.print("Enter the account number to delete: ");
        int accountNumber = scanner.nextInt();

        System.out.print("Are you sure you want to delete account #" + accountNumber + "? (yes/no): ");
        String confirmation = scanner.next();

        if (confirmation.equalsIgnoreCase("yes"))
        {
            System.out.println("Account #" + accountNumber + " deleted successfully.");
        }
        else
        {
            System.out.println("Account deletion canceled.");
        }
    }

    private void updateAccountInfo()
    {
        System.out.print("Enter the account number to update: ");
        int accountNumber = scanner.nextInt();

        System.out.println("Account found: Account #" + accountNumber);

        System.out.println("What would you like to update?");
        System.out.println("1 - Update Account Holder Name");
        System.out.println("2 - Update Account Status");
        System.out.println("3 - Update Account Login");
        System.out.println("4 - Update Account PIN");
        System.out.print("Select an option: ");
        int updateChoice = scanner.nextInt();

        switch (updateChoice)
        {
            case 1:
                System.out.print("Enter new Login: ");
                String newLogin = scanner.next();
                System.out.println("Login updated to: " + newLogin);
                break;
            case 2:
                System.out.print("Enter new Pin Code: ");
                String newPin = scanner.next();
                System.out.println("Pin Code updated.");
                break;
            case 3:
                System.out.print("Enter new Account Holder Name: ");
                String newHolderName = scanner.next();
                System.out.println("Account Holder Name updated to: " + newHolderName);
                break;
            case 4:
                System.out.print("Enter new Balance: ");
                double newBalance = scanner.nextDouble();
                System.out.println("Balance updated to: " + newBalance);
                break;
            case 5:
                System.out.print("Enter new Account Status (Active/Disabled): ");
                String newStatus = scanner.next();
                System.out.println("Account Status updated to: " + newStatus);
                break;
            default:
                System.out.println("Invalid choice. No updates made.");
        }
    }

    private void searchAccount()
    {
        System.out.print("Enter Account Number to search: ");
        int accountNumber = scanner.nextInt();

        System.out.println("Account found: ");
        System.out.println("Account #" + accountNumber);
        System.out.println("Account Holder: John Doe");
        System.out.println("Balance: 5000");
        System.out.println("Status: Active");
        System.out.println("Login: johndoe");
        System.out.println("Pin Code: 12345");
    }
}
