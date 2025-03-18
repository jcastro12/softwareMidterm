// main runner method for ATM program

package runner;

import java.io.IOException;
import java.util.Scanner;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import database.GuiceMod;
import database.User;
import runner.menu.AdminMenu;
import runner.menu.CustomerMenu;
import runner.menu.menuInterface;

public class ATM {
    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

    private final Auth authService; // menu.Auth service injected

    // Constructor injection
    @Inject
    public ATM(Auth authService) {
        this.authService = authService;
    }

    public static void main(String[] args) throws IOException {
        // Create the Guice injector
        Injector injector = Guice.createInjector(new GuiceMod());

        // Get an instance of menu with dependencies injected
        ATM atm = injector.getInstance(ATM.class);

        System.out.println("Welcome to the menu System");

        // loops until user exits
        while (true) {
            System.out.print("Enter login: ");
            String login = scanner.nextLine();

            System.out.print("Enter PIN code: ");
            // checks for valid pin
            String pinCode;
            while (true) {
                pinCode = scanner.next();
                if (pinCode.length() == 5 && pinCode.matches("\\d+")) {
                    break;
                }
                System.out.println("Try again. Pin needs to be a 5 digit integer.");
            }
            // checks if name/pin match in DB
            User user = atm.authService.authenticate(login, Integer.parseInt(pinCode));
            if (user != null) {
                System.out.println("Login successful!");

                menuInterface menu;
                if (user.getType().equals("Customer")) {
                    menu = new CustomerMenu(scanner, user);
                } else {
                    menu = new AdminMenu(scanner);
                }
                // displays proper menu
                menu.display();
                break;
            } else {
                scanner.nextLine();

                System.out.println("Invalid login or PIN. Try again.");
            }
        }
    }
}
