// class to handle admin menuing
package menu;

import database.User;
import database.json.db;

import java.util.List;
import java.util.Scanner;

public class AdminMenu implements menuInterface
{
    private final Scanner scanner;

    public AdminMenu(Scanner scanner)
    {
        this.scanner = scanner;
    }

    // display options
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
            System.out.println("Enter a choice: ");
            int choice = inputHelper();
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

    // method to create new account with user provided input
    private void createNewAccount()
    {
        System.out.println("Create New Account:");
        System.out.print("Enter Login: ");
        String login = scanner.nextLine();
        System.out.print("Enter Pin Code (5 digits): ");
        int pinCode = pinHelper();
        scanner.nextLine();
        System.out.print("Enter Account Holder Name: ");
        String holderName = scanner.nextLine();

        System.out.print("Enter Starting Balance: ");
        double balance = scanner.nextDouble();
        // making sure inputs are valid for status and type
        System.out.print("Enter new Account Status (Active/Disabled): ");
        String status = scanner.next().trim();
        if (!(status.equalsIgnoreCase("active") || status.equalsIgnoreCase("disabled")))
        {
            System.out.println("Invalid status. Must be Active or Disabled.");
            return;
        }

        System.out.print("Enter Account Type (Customer/Admin): ");
        String type = scanner.next().trim();
        if (!(type.equalsIgnoreCase("customer") || type.equalsIgnoreCase("admin")))
        {
            System.out.println("Invalid type. Must be Customer or Admin.");
            return;
        }
        // add new user to current database, loading info and then immediately updating to come as close as possible to matching ACID
        User newUser = new User(holderName, login, pinCode, type, balance, status);
        List<User> users = db.loadUsers(); // Load users from JSON
        users.add(newUser); // Add user to list
        db.saveDatabase(users); // Save to database
        System.out.println("Account Successfully Created!");
        System.out.println("Account Number: " + newUser.getId());
    }

    // method to delete account from DB
    private void deleteAccount()
    {
        System.out.println("Enter the account number to delete: ");
        int num = inputHelper();
        User userToFind = findUser(db.loadUsers(), num);
        // if exists, confirms and then deletes
        if (userToFind != null)
        {
            System.out.print("To confirm, please re-enter account number to delete: ");
            String confirmation = scanner.next();
            if (confirmation.equalsIgnoreCase(Integer.toString(num)))
            {
                // loads users again
                List<User> users = db.loadUsers();
                users.remove(userToFind);
                db.saveDatabase(users);
                System.out.println("Account #" + num + " deleted successfully.");
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

    // method to update account info
    private void updateAccountInfo()
    {
        List<User> users = db.loadUsers();
        System.out.println("Enter the account number to update: ");
        User userToFind = findUser(users, inputHelper());

        if (userToFind != null)
        {
            System.out.println("Account found: " + userToFind.getName());
            System.out.println("What would you like to update?");
            System.out.println("1 - Update Account Holder Name");
            System.out.println("2 - Update Account Status");
            System.out.println("3 - Update Account Login");
            System.out.println("4 - Update Account PIN");
            System.out.print("Select an option: ");
            int updateChoice = inputHelper();
            // gets input from user and updates correct value
            switch (updateChoice)
            {
                case 1:
                    System.out.print("Enter new Account Holder Name: ");
                    userToFind.setName(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("Enter new Account Status (Active/Disabled): ");
                    String newStatus = scanner.next().trim();
                    if (newStatus.equalsIgnoreCase("active") || newStatus.equalsIgnoreCase("disabled"))
                    {
                        userToFind.setStatus(newStatus);
                    }
                    else
                    {
                        System.out.println("Invalid status. Must be Active or Disabled.");
                    }
                    break;
                case 3:
                    System.out.print("Enter new Login: ");
                    userToFind.setLogin(scanner.next());
                    break;
                case 4:
                    System.out.print("Enter new PIN (5 digits): ");
                    int newPin = pinHelper();
                    userToFind.setPin(newPin);
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

    // method to search for and display an account
    private void searchAccount()
    {
        List<User> users = db.loadUsers();
        System.out.println("Enter the account number to display: ");
        User userToFind = findUser(users, inputHelper());

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


    // helper method to find a given user in the list of users
    private User findUser(List<User> users, int accountNumber)
    {
        for (User user : users)
        {
            if (user.getId() == accountNumber)
            {
                return user;
            }
        }
        return null;
    }

    // helper method to ensure Pin input is valid
    private int pinHelper()
    {
        String pinCode = "";
        while (true)
        {
            pinCode = scanner.next();
            if (pinCode.length() == 5 && pinCode.matches("\\d+"))
            {
                return (Integer.parseInt(pinCode));
            }
            System.out.println("Try again. Pin needs to be a 5 digit integer.");
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
                scanner.nextLine(); // Consume the newline character
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
