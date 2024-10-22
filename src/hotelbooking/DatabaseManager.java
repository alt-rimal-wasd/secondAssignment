/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbooking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author emort
 */
public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:hotelbooking.db";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void createTables() {
        String customerTable = "CREATE TABLE IF NOT EXISTS customers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "phone TEXT NOT NULL UNIQUE," +
                "email TEXT NOT NULL" +
                ");";

        String roomTable = "CREATE TABLE IF NOT EXISTS rooms (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "roomNumber TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "available BOOLEAN NOT NULL," +
                "type TEXT NOT NULL" +
                ");";

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(customerTable);
            stmt.execute(roomTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
