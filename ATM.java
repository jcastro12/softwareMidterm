import java.awt.*;
import java.util.Scanner;

public class ATM
{
    private static final Auth authService = new Auth();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.println("Welcome to the ATM System");

        while (true)
        {
            System.out.print("Enter login: ");
            String login = scanner.nextLine();

            System.out.print("Enter PIN code: ");
            String pin = scanner.nextLine();

            User user = authService.authenticate(login, pin);

            if (user != null)
            {
                System.out.println("Login successful!");

                menuInterface menu;
                if (user.getType().equals("Customer"))
                {
                    menu = new CustomerMenu(scanner, user.getBalance());
                }
                else
                {
                    menu = new AdminMenu(scanner);
                }

                menu.display();
                break;
            }
            else
            {
                System.out.println("Invalid login or PIN. Try again.");
            }
        }
    }
}