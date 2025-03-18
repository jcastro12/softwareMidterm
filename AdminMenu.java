import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
        List<User> users = db.loadUsers(); // Load users from JSON

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

        System.out.print("Enter Account Type (Customer/Admin): ");
        String type = scanner.next();

        User newUser = new User(holderName, login, pinCode, type, balance, status);
        users.add(newUser); // Add user to list

        db.saveDatabase(users); // Save to database
        System.out.println("Account Successfully Created!");
        System.out.println("Account Number: " + newUser.getId());
    }

    private void deleteAccount()
    {
        List<User> users = db.loadUsers();
        System.out.print("Enter the account number to delete: ");
        int accountNumber = scanner.nextInt();

        Optional<User> userToDelete = users.stream()
                .filter(user -> user.getId() == accountNumber)
                .findFirst();

        if (userToDelete.isPresent())
        {
            System.out.print("Are you sure you want to delete account #" + accountNumber + "? (yes/no): ");
            String confirmation = scanner.next();

            if (confirmation.equalsIgnoreCase("yes"))
            {
                users.remove(userToDelete.get());
                db.saveDatabase(users);
                System.out.println("Account #" + accountNumber + " deleted successfully.");
            }
            else
            {
                System.out.println("Account deletion canceled.");
            }
        }
        else
        {
            System.out.println("Account not found.");
        }
    }

    private void updateAccountInfo()
    {
        List<User> users = db.loadUsers();
        System.out.print("Enter the account number to update: ");
        int accountNumber = scanner.nextInt();

        Optional<User> userToUpdate = users.stream()
                .filter(user -> user.getId() == accountNumber)
                .findFirst();

        if (userToUpdate.isPresent())
        {
            User user = userToUpdate.get();

            System.out.println("Account found: " + user.getName());
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
                    System.out.print("Enter new Account Holder Name: ");
                    user.setName(scanner.next());
                    break;
                case 2:
                    System.out.print("Enter new Account Status (Active/Disabled): ");
                    user.setStatus(scanner.next());
                    break;
                case 3:
                    System.out.print("Enter new Login: ");
                    user.setLogin(scanner.next());
                    break;
                case 4:
                    System.out.print("Enter new PIN (5 digits): ");
                    String newPin = scanner.next();
                    if (newPin.length() != 5 || !newPin.matches("\\d+"))
                    {
                        System.out.println("Invalid pin. It should be 5 digits.");
                        return;
                    }
                    user.setPin(newPin);
                    break;
                default:
                    System.out.println("Invalid choice. No updates made.");
                    return;
            }

            db.saveDatabase(users);
            System.out.println("Account updated successfully!");
        }
        else
        {
            System.out.println("Account not found.");
        }
    }

    private void searchAccount()
    {
        List<User> users = db.loadUsers();
        int accountNumber = -1;

        while (true)
        {
            System.out.print("Enter Account Number to search: ");
            if (scanner.hasNextInt())
            {
                accountNumber = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                break;
            }
            else
            {
                System.out.println("Invalid input. Please enter a valid account number.");
                scanner.next(); // Discard the invalid input
            }
        }

        User userToFind = null;
        for (User user : users)
        {
            if (user.getId() == accountNumber)
            {
                userToFind = user;
                break;
            }
        }

        if (userToFind != null)
        {
            System.out.println("The account information is:");
            System.out.println("Account # " + userToFind.getId());
            System.out.println("Holder: " + userToFind.getName());
            System.out.println("Balance: " + userToFind.getBalance());
            System.out.println("Status: " + userToFind.getStatus());
            System.out.println("Login: " + userToFind.getLogin());
            System.out.println("Pin Code: " + userToFind.getPin());
        }
        else
        {
            System.out.println("Account not found.");
        }
    }
}
