// database class for JSON database implementation

package database.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import database.User;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class db
{
    // JSON dictionary path
    private static final String FILE_PATH = "C:\\Users\\jcastro1\\IdeaProjects\\softwareFinalReal\\src\\main\\java\\database\\json\\db.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static int nextID = 1; // Default to 1 if not found in JSON

    // get the next ID from JSON
    static
    {
        loadNextID(); // Load nextID when the class is first used
    }

    // method to load all users from JSON file
    public static List<User> loadUsers()
    {
        try (FileReader reader = new FileReader(FILE_PATH))
        {
            Type dbType = new TypeToken<Database>()
            {
            }.getType();
            Database db = GSON.fromJson(reader, dbType);

            if (db != null && db.users != null)
            {
                return db.users;
            }
        }
        catch (IOException e)
        {
            System.out.println("Error");
        }
        return null;
    }

    // method to write the updated/current DB to the JSON file
    @SuppressWarnings("checkstyle:RightCurly")
    public static void saveDatabase(List<User> users)
    {
        try (FileWriter writer = new FileWriter(FILE_PATH))
        {
            int maxId = users.stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0);
            nextID = maxId + 1;
            Database db = new Database(users, nextID);
            GSON.toJson(db, writer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // method to load the next ID from JSON file for creating new account
    private static void loadNextID()
    {
        try (FileReader reader = new FileReader(FILE_PATH))
        {
            Type dbType = new TypeToken<Database>()
            {
            }.getType();
            Database db = GSON.fromJson(reader, dbType);

            if (db != null && db.nextID > 0)
            {
                nextID = db.nextID;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    // method to update ID of next account created without having to re-visit the JSON
    public static int getNextID()
    {
        return nextID++;
    }

    // method to update a single user in the JSON instead of the entire file.
    public static void updateUser(User updatedUser)
    {
        List<User> users = loadUsers();
        for (User user : users)
        {
            if (user.getId() == updatedUser.getId())
            {
                user.setBalance(updatedUser.getBalance()); // Update balance
                break;
            }
        }
        saveDatabase(users);
    }

    // database class
    private static class Database
    {
        List<User> users;
        int nextID;

        Database(List<User> users, int nextID)
        {
            this.users = users;
            this.nextID = nextID;
        }
    }
}
