import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ATM
{
    private static final Auth authService;

    static {
        try {
            authService = new Auth();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\\n");


    public static void main(String[] args) throws IOException {
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
                    menu = new CustomerMenu(scanner, user);
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