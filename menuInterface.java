import java.io.IOException;
import java.util.Scanner;

public interface menuInterface
{
    Scanner scanner = new Scanner(System.in).useDelimiter("\\n");

    void display() throws IOException;

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


