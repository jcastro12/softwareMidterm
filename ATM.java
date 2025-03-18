import java.io.IOException;
import java.util.Scanner;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ATM {
    private static final Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

    private final Auth authService; // Auth service injected

    // Constructor injection
    @Inject
    public ATM(Auth authService) {
        this.authService = authService;
    }

    public static void main(String[] args) throws IOException {
        // Create the Guice injector
        Injector injector = Guice.createInjector(new GuiceMod());

        // Get an instance of ATM with dependencies injected
        ATM atm = injector.getInstance(ATM.class);

        System.out.println("Welcome to the ATM System");

        while (true) {
            System.out.print("Enter login: ");
            String login = scanner.nextLine();

            System.out.print("Enter PIN code: ");
            String pin = scanner.nextLine();

            User user = atm.authService.authenticate(login, pin);

            if (user != null) {
                System.out.println("Login successful!");

                menuInterface menu;
                if (user.getType().equals("Customer")) {
                    menu = new CustomerMenu(scanner, user);
                } else {
                    menu = new AdminMenu(scanner);
                }

                menu.display();
                break;
            } else {
                System.out.println("Invalid login or PIN. Try again.");
            }
        }
    }
}
