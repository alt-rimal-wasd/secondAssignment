package project2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    private static DBManager instance;
    private Connection conn;

    /*
    //local server
    private static final String URL = "jdbc:derby://localhost:1527/project2;create=true";
    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";
     */
    // Embedded database URL
    private static final String EMBEDDED_URL = "jdbc:derby:project2;create=true";

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

    // Establish connection
    private void establishConnection() {
        if (this.conn == null) {
            try {
                // Load the Derby embedded driver
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

                // Connect to embedded DB
                conn = DriverManager.getConnection(EMBEDDED_URL);

                System.out.println(EMBEDDED_URL + " connected successfully.");
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
        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    public void updateDB(String sql) {
        Connection connection = this.conn;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
