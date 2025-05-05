package database;

import database.json.JSONDBService;

public class DBServiceFactory
{

    private static final String DATABASE_TYPE = "json";  // This could come from a config file or environment variable

    public static DBService getDBService()
    {
        if ("json".equalsIgnoreCase(DATABASE_TYPE))
        {
            return new JSONDBService();  // Return JSON implementation
        }
        else
        {
            throw new IllegalArgumentException("Unsupported database type: " + DATABASE_TYPE);
        }
    }
}
