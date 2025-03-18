import java.io.FileNotFoundException;
import java.util.List;

public class Auth
{
    private final List<User> users;

    public Auth() throws FileNotFoundException
    {
        this.users = db.loadUsers();
    }

    public User authenticate(String username, String pin)
    {
        for (User user : users)
        {
            if (user.getLogin().equals(username) && user.getPin().equals(pin))
            {
                return user;
            }
        }
        return null;
    }
}
