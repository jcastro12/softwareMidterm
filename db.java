import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class db
{
    private static final String FILE_PATH = "/Users/joshcastro/IdeaProjects/SoftwareMidtermATM/src/main/java/db.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static int nextID = 1; // Default to 1 if not found in JSON

    static
    {
        loadNextID(); // Load nextID when the class is first used
    }

    public static List<User> loadUsers() {
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type dbType = new TypeToken<Database>() {
            }.getType();
            Database db = GSON.fromJson(reader, dbType);

            if (db != null && db.users != null) {
                return db.users;
            }
        } catch (IOException e) {
            System.out.println("Error");
        }
        return null;
    }

    public static void saveDatabase(List<User> users)
    {
        try (FileWriter writer = new FileWriter(FILE_PATH))
        {
            Database db = new Database(users, nextID);
            GSON.toJson(db, writer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void loadNextID()
    {
        try (FileReader reader = new FileReader(FILE_PATH))
        {
            Type dbType = new TypeToken<Database>() {}.getType();
            Database db = GSON.fromJson(reader, dbType);

            if (db != null && db.nextID > 0)
            {
                nextID = db.nextID;
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No database file found. Starting with nextID = 1.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static int getNextID()
    {
        return nextID++;
    }


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
