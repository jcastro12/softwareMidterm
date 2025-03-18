package database;

import com.google.inject.AbstractModule;

import java.io.FileNotFoundException;
import java.util.Scanner;

import database.JSON.JSONDBService;
import runner.Auth;

public class GuiceMod extends AbstractModule {
    @Override
    protected void configure() {
        bind(Scanner.class).toInstance(new Scanner(System.in).useDelimiter("\\n")); // Provide a Scanner instance
        try {
            bind(Auth.class).toInstance(new Auth(new JSONDBService())); // Provide menu.Auth instance with injected DatabaseService
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
