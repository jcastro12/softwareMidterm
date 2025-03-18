// implementation of JSON DB

package database.JSON;

import database.DBService;
import database.User;

import java.util.List;

public class JSONDBService implements DBService {
    @Override
    public List<User> loadUsers() {
        return db.loadUsers(); // Load users from the database.JSON.db class
    }
}
