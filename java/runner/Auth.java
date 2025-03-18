package runner;

import com.google.inject.Inject;
import database.DBService;
import database.User;

import java.io.FileNotFoundException;
import java.util.List;

public class Auth {
    private final List<User> users;

    @Inject
    public Auth(DBService databaseService) throws FileNotFoundException {
        this.users = databaseService.loadUsers(); // Use injected database service to load users
    }

    public User authenticate(String username, int pin) {
        for (User user : users) {
            if (user.getLogin().equals(username) && user.getPin()==pin) {
                return user; // Return the user if authentication is successful
            }
        }
        return null; // Return null if authentication fails
    }
}
