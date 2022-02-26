package com.example.gui.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;

    public Connection getConnection() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/database.csv"));
            String user = br.readLine();
            String password = br.readLine();
            String url = br.readLine();

            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
