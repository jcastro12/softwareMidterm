public class Auth
{
    private final User[] users = {
            new User("Adnan123", "12345", "Customer"),
            new User("Javed123", "54321", "Admin")
    };

    public User authenticate(String login, String pin)
    {
        for (User user : users)
        {
            if (user.getLogin().equals(login) && user.getPin().equals(pin))
            {
                return user;
            }
        }
        return null;
    }
}
