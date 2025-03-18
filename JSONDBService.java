import java.util.List;

public class JSONDBService implements DBService {
    @Override
    public List<User> loadUsers() {
        return db.loadUsers(); // Load users from the db class
    }
}
