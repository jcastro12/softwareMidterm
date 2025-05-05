// interface for menus
package menu;

import java.io.IOException;
import java.util.Scanner;

public interface menuInterface
{
    // to be overwritten
    void display() throws IOException;

    int inputHelper();
}


