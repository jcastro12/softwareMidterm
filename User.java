public class User
{
    private final String login;
    private final String pin;
    private final String type;
    private final int balance;

    public User(String login, String pin, String type)
    {
        this.login = login;
        this.pin = pin;
        this.type = type;
        this.balance = 0;
    }

    public String getLogin()
    {
        return login;
    }

    public String getPin()
    {
        return pin;
    }

    public String getType()
    {
        return type;
    }

    public int getBalance()
    {
        return balance;
    }
}
