// interface for menus
package runner.menu;

import java.io.IOException;
import java.util.Scanner;

public interface menuInterface
{
    Scanner scanner = new Scanner(System.in).useDelimiter("\\n");
    // to be overwritten
    void display() throws IOException;
    // same for all menus, ensures that int input is valid for menu selection
    default int inputHelper(){
        int input;
        while (true)
        {
            if (scanner.hasNextInt())
            {
                input = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                return input;
            }
            else
            {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // Discard the invalid input
            }
        }
    }
}


