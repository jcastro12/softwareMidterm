package menu;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import database.Auth;
import database.GuiceMod;
import database.User;

import java.io.IOException;
import java.util.Scanner;

public class ATM
{
    private static final Scanner defaultScanner = new Scanner(System.in);
    private final Auth authService;
    private final Scanner scanner;

    @Inject
    public ATM(Auth authService, Scanner scanner)
    {
        this.authService = authService;
        this.scanner = scanner != null ? scanner : defaultScanner; // Use default if null
    }

    public static void main(String[] args) throws IOException
    {
        Injector injector = Guice.createInjector(new GuiceMod());
        ATM atm = injector.getInstance(ATM.class);

        atm.runATM();
    }

    public void runATM() throws IOException
    {
        System.out.println("Welcome to the menu System");

        while (true)
        {
            System.out.print("Enter login: ");
            String login = scanner.next();
            System.out.print("Enter PIN code: ");
            String pinCode = getPinCodeInput();
            User user = authService.authenticate(login, Integer.parseInt(pinCode));
            if (user != null)
            {
                System.out.println("Login successful!");
                menuInterface menu = createMenuForUser(user);
                menu.display();
                break;
            }
            else
            {
                scanner.nextLine();
                System.out.println("Invalid login or PIN. Try again.");
            }
        }
    }

    private String getPinCodeInput()
    {
        String pinCode;
        while (true)
        {
            pinCode = scanner.next();
            if (pinCode.length() == 5 && pinCode.matches("\\d+"))
            {
                break;
            }
            System.out.println("Try again. Pin needs to be a 5 digit integer.");
        }
        return pinCode;
    }

    private menuInterface createMenuForUser(User user)
    {
        if ("Customer".equals(user.getType()))
        {
            return new CustomerMenu(scanner, user);
        }
        else
        {
            return new AdminMenu(scanner);
        }
    }
}
