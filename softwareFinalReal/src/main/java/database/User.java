// class to store user information
package database;

import database.json.db;

public class User
{
    private final int id;
    private String login;
    private int pin;
    private String name;
    private final String type;
    private double balance;
    private String status;

    public User(String name, String login, int pin, String type, double initial, String status)
    {
        this.id = db.getNextID();
        this.login = login;
        this.pin = pin;
        this.balance = initial;
        this.name = name;
        this.status = status;
        this.type = type;
    }

    // getters and setters only included as necessary
    public String getLogin()
    {
        return login;
    }

    public int getPin()
    {
        return pin;
    }

    public String getType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

    public double getBalance()
    {
        return balance;
    }

    public String getStatus()
    {
        return status;
    }

    public int getId()
    {
        return id;
    }

    public void setBalance(double n)
    {
        balance = n;
    }

    public void setName(String n)
    {
        name = n;
    }

    public void setStatus(String n)
    {
        status = n;
    }

    public void setLogin(String n)
    {
        login = n;
    }

    public void setPin(int n)
    {
        pin = n;
    }

}
