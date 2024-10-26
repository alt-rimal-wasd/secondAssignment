/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author emort
 */
public class DBManager {

    private static DBManager instance;
    private Connection conn;
    private static final String URL = "jdbc:derby://localhost:1527/project2;create=true";
    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";

    private DBManager() {
        establishConnection();
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return this.conn;
    }

    private void establishConnection() {
        if (this.conn == null) {
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver"); // Load the Derby driver
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                System.out.println(URL + " connected successfully.");
            } catch (ClassNotFoundException ex) {
                System.out.println("Driver not found: " + ex.getMessage());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public ResultSet queryDB(String sql) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    public void updateDB(String sql) {
        Statement statement;
        try {
            statement = conn.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
