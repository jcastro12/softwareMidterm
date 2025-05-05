import static org.junit.Assert.*;

import com.google.inject.Guice;
import database.GuiceMod;
import database.User;
import database.json.db;
import menu.ATM;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.google.inject.Injector;


import java.io.*;
import java.util.List;

public class ATMTest {
    private ATM atm;
    private InputStream originalSystemInStream;
    private PrintStream originalSystemOutStream;
    private final List<User> initialUsers = db.loadUsers();

    @Before
    public void setUp() {
        originalSystemInStream = System.in;  // Save the original System.in stream
        originalSystemOutStream = System.out;
    }

    @Test
    public void testDepositValidCash() throws IOException {
        String simulatedInput = "testUser\n12345\n2\n500\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Cash Deposited Successfully."));
        assertTrue(output.contains("Deposited: 500.0"));
        assertTrue(output.contains("Balance: $40500.0"));

        System.out.println("Captured Output: \n" + output);
    }

    @Test
    public void testRunATMLoginSuccess() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "testUser\n12345\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Exiting... Thank you for using the ATM."));
    }

    @Test
    public void testRunATMLoginSuccessAdmin() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Exiting... Thank you for using the ATM."));
    }

    @Test
    public void testRunATMLoginFailure() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "testUser\n12355\ntestUser\n12345\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.trim().contains("Invalid login"));
    }

    @Test
    public void testRunATMInvalidPinLength() throws IOException {
        String simulatedInput = "testUser\n1234\n12345\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Pin needs to be a 5 digit integer."));
    }

    @Test
    public void testRunATMInvalidMenuSelection() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "testUser\n12345\n6\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid choice. Try again."));
    }

    @Test
    public void testRunATMInvalidMenuSelectionString() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "testUser\n12345\nbob\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid input. Please enter an integer."));
    }

    @Test
    public void testViewBalanceForCustomer() throws IOException {
        // Simulated input: username, PIN, then menu option 3 (view balance), then option 4 (exit)
        String simulatedInput = "testUser\n12345\n3\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Current Balance: $40000"));
    }

    @Test
    public void testWithdrawValidCash() throws IOException {
        // Simulated input: username, PIN, then menu option 3 (view balance), then option 4 (exit)
        String simulatedInput = "testUser\n12345\n1\n500\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Withdrawn: 500"));
        assertTrue(output.contains("Balance: $39500"));
    }
    @Test
    public void testWithdrawInvalidCash() throws IOException {
        // Simulated input: username, PIN, then menu option 3 (view balance), then option 4 (exit)
        String simulatedInput = "testUser\n12345\n1\n50000\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid amount"));
    }


    @Test
    public void testDepositInvalidCashDouble() throws IOException {
        String simulatedInput = "testUser\n12345\n2\nbob\n100.0\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid input. Please enter a double."));
    }

    @Test
    public void testDepositInvalidCash() throws IOException {
        String simulatedInput = "testUser\n12345\n2\n-200\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid amount."));
    }

    @Test
    public void testAdminCreateCustomerAccount() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n1\nbob\n12345\nBob Roberts\n10.0\nActive\nCustomer\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Account Successfully Created!"));
        assertTrue(output.contains("Account Number: "));
    }

    @Test
    public void testAdminCreateAdminAccount() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n1\nbob\n12345\nBob Roberts\n10.0\nActive\nAdmin\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Account Successfully Created!"));
        assertTrue(output.contains("Account Number: "));
    }

    @Test
    public void testAdminCreateInvalidTypeAccount() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n1\nbob\n12345\nBob Roberts\n10.0\nActive\nwrong\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid type. Must be Customer or Admin."));
    }

    @Test
    public void testAdminCreateCustomerDisabledAccount() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n1\nbob\n12345\nBob Roberts\n10.0\nDisabled\nCustomer\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Account Successfully Created!"));
        assertTrue(output.contains("Account Number: "));
    }

    @Test
    public void testAdminCreateCustomerInvalidStatusAccount() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n1\nbob\n12345\nBob Roberts\n10.0\ninvalid\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid status. Must be Active or Disabled."));
    }

    @Test
    public void testAdminDeleteCustomer() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n1\nbob\n12345\nBob Roberts\n10.0\nActive\nCustomer\n2\n9\n9\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Account #9 deleted successfully."));
    }

    @Test
    public void testAdminDeleteCustomerFail() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n1\nbob\n12345\nBob Roberts\n10.0\nActive\nCustomer\n2\n9\n1\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Account deletion canceled."));
    }

    @Test
    public void testAdminDeleteCustomerAccountNotFound() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n2\n9\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Account not found."));
    }

    @Test
    public void testAdminUpdateCustomerName() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n3\n8\n1\nTest User 1\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Account found: Test User"));
        assertTrue(output.contains("Enter new Account Holder Name: "));
        assertTrue(output.contains("Account updated successfully!"));
    }

    @Test
    public void testAdminUpdateCustomerStatusValid() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n3\n8\n2\nActive\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Enter new Account Status (Active/Disabled): "));
        assertTrue(output.contains("Account updated successfully!"));
    }

    @Test
    public void testAdminUpdateCustomerStatusInvalid() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n3\n8\n2\ninvalid\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid status. Must be Active or Disabled."));
    }

    @Test
    public void testAdminUpdateCustomerLogin() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n3\n8\n3\nNewLogin\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Enter new Login: "));
        assertTrue(output.contains("Account updated successfully!"));
    }

    @Test
    public void testAdminUpdateCustomerPin() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n3\n8\n4\n65432\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Enter new PIN"));
        assertTrue(output.contains("Account updated successfully!"));
    }

    @Test
    public void testAdminUpdateCustomerPinInvalid() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n3\n8\n4\n1234\n12345\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Try again. Pin needs to be a 5 digit integer."));
        assertTrue(output.contains("Account updated successfully!"));
    }

    @Test
    public void testAdminUpdateWrongSelection() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n3\n8\n5\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid choice. No updates made."));
    }

    @Test
    public void testAdminUpdateNoAccount() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n3\n99\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Account not found."));
    }

    @Test
    public void testAdminSearchAccount() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n4\n8\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Enter the account number to display: "));
        assertTrue(output.contains("The account information is:"));
    }

    @Test
    public void testAdminSearchAccountNonexistent() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n4\n99\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Enter the account number to display: "));
        assertTrue(output.contains("Account not found."));
    }

    @Test
    public void testAdminInvalidMenu() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\n6\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid choice. Try again."));
    }

    @Test
    public void testAdminInvalidMenuString() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "admin\n54321\nbob\n5\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        Injector injector = Guice.createInjector(new GuiceMod());
        atm = injector.getInstance(ATM.class);
        atm.runATM();

        String output = testOut.toString();
        assertTrue(output.contains("Invalid input. Please enter an integer."));
    }

    @Test
    public void testATMMain() throws IOException {
        // Simulating user input for login and PIN using ByteArrayInputStream
        String simulatedInput = "testUser\n12345\n4\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        ATM.main(null);

        String output = testOut.toString();
        assertTrue(output.contains("Exiting... Thank you for using the ATM."));
    }

    @After
    public void restoreInOutDB() {
        // Restore the original System.in after the test
        System.setIn(originalSystemInStream);
        System.setOut(originalSystemOutStream);
        db.saveDatabase(initialUsers);
    }
}
