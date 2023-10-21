package com.library;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static Connection instance;

    private DatabaseConnection(){}

    public Connection getConnection(){
        if(instance == null){
            String databaseName = "Library";
            String databaseUser = "postgres";
            String databasePassword = "!QkaParolka123";
            String url = "jdbc:postgresql://localhost:5432/" + databaseName;

            try {
                Class.forName("org.postgresql.Driver");
                instance = DriverManager.getConnection(url, databaseUser, databasePassword);
                System.out.println("Connected to database!");

//            connection.close();
            }catch (Exception e){
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
                System.exit(0);
            }
        }

        return instance;
    }
}
