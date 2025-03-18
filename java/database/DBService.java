//interface for different types of databases
package database;

import java.util.List;

public interface DBService {
    List<User> loadUsers(); // Method to load users from a database or file
}
