package project2;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Info {
    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;

    public Info() {
        dbManager = DBManager.getInstance();
        conn = dbManager.getConnection();
    }

    public void connectHotelDB() {
        try {
            this.statement = conn.createStatement();
            this.dropTablesIfExists("ROOM");
            this.dropTablesIfExists("CUSTOMER");
            this.createTables();
            this.insertRoomData();
            this.insertCustomerData();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void dropTablesIfExists(String tableName) throws SQLException {
        DatabaseMetaData dbmd = this.conn.getMetaData();
        ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(), null);
        if (rs.next()) {
            this.statement.executeUpdate("DROP TABLE " + tableName);
            System.out.println("Table " + tableName + " has been deleted.");
        }
    }

    private void createTables() throws SQLException {
        this.statement.executeUpdate("CREATE TABLE ROOM (ROOMID VARCHAR(20) PRIMARY KEY, TYPE VARCHAR(20), PRICE FLOAT, AVAILABLE BOOLEAN)");
        this.statement.executeUpdate("CREATE TABLE CUSTOMER (CUSTOMERID VARCHAR(20) PRIMARY KEY, NAME VARCHAR(50), PHONE VARCHAR(20), EMAIL VARCHAR(50))");
    }

    private void insertRoomData() throws SQLException {
        conn.setAutoCommit(false); // Start transaction

        try {
            String[] roomInserts = {
                "('s001', 'Single', 100.0, TRUE)",
                "('s002', 'Single', 100.0, TRUE)",
                "('s003', 'Single', 100.0, TRUE)",
                "('s004', 'Single', 100.0, TRUE)",
                "('s005', 'Single', 100.0, TRUE)",
                "('s006', 'Single', 100.0, TRUE)",
                "('s007', 'Single', 100.0, TRUE)",
                "('s008', 'Single', 100.0, TRUE)",
                "('s009', 'Single', 100.0, TRUE)",
                "('s010', 'Single', 100.0, TRUE)",
                "('d001', 'Double', 150.0, TRUE)",
                "('d002', 'Double', 150.0, TRUE)",
                "('d003', 'Double', 150.0, TRUE)",
                "('d004', 'Double', 150.0, TRUE)",
                "('d005', 'Double', 150.0, TRUE)",
                "('d006', 'Double', 150.0, TRUE)",
                "('d007', 'Double', 150.0, TRUE)",
                "('d008', 'Double', 150.0, TRUE)",
                "('d009', 'Double', 150.0, TRUE)",
                "('d010', 'Double', 150.0, TRUE)",
                "('suite001', 'Suite', 250.0, TRUE)",
                "('suite002', 'Suite', 250.0, TRUE)",
                "('suite003', 'Suite', 250.0, TRUE)",
                "('suite004', 'Suite', 250.0, TRUE)",
                "('suite005', 'Suite', 250.0, TRUE)",
                "('suite006', 'Suite', 250.0, TRUE)",
                "('suite007', 'Suite', 250.0, TRUE)",
                "('suite008', 'Suite', 250.0, TRUE)",
                "('suite009', 'Suite', 250.0, TRUE)",
                "('suite010', 'Suite', 250.0, TRUE)"
            };

            for (String roomInsert : roomInserts) {
                this.statement.executeUpdate("INSERT INTO ROOM VALUES " + roomInsert);
            }

            conn.commit(); // Commit transaction
        } catch (SQLException ex) {
            conn.rollback(); // Rollback transaction if any errors occur
            System.out.println("Error inserting room data: " + ex.getMessage());
        } finally {
            conn.setAutoCommit(true); // Restore default auto-commit behavior
        }
    }

    private void insertCustomerData() throws SQLException {
        conn.setAutoCommit(false); // Start transaction

        try {
            String[] customerInserts = {
                "('c001', 'MATT', '0211111113', 'MATT_BRADLEY@EMAIL.COM')",
                "('c002', 'TUGA', '122', 'ASD@GMAIL.COM')",
                "('c003', 'abraham', '0211111114', 'abharam@gmail.com')",
                "('c004', 'BRIAN', '0211188513', 'unknown@example.com')",
                "('c005', 'DAVID JOHNES', '0211111111', 'DAVIDJOHNES@EMAIL.COM')",
                "('c006', 'MASE', '789', 'MASE@GMAIL.COM')",
                "('c007', 'FOX', '1234565', 'FOX@GMAIL.COM')",
                "('c008', 'TOM', '234567', 'TOM@GMAIL.COM')",
                "('c009', 'MAX', '7782309', 'MAX@GMAIL.COM')"
            };

            for (String customerInsert : customerInserts) {
                this.statement.executeUpdate("INSERT INTO CUSTOMER VALUES " + customerInsert);
            }

            conn.commit(); // Commit transaction
        } catch (SQLException ex) {
            conn.rollback(); // Rollback transaction if any errors occur
            System.out.println("Error inserting customer data: " + ex.getMessage());
        } finally {
            conn.setAutoCommit(true); // Restore default auto-commit behavior
        }
    }

    public ResultSet getAvailableRooms() {
        ResultSet rs = null;
        try {
            rs = this.statement.executeQuery("SELECT * FROM ROOM WHERE AVAILABLE = TRUE");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rs;
    }

    private void checkExistedTable(String name) {
        try {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, name.toUpperCase(), null);
            if (rs.next()) {
                this.statement.executeUpdate("DROP TABLE " + name);
                System.out.println("Table " + name + " has been deleted.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void closeConnection() {
        this.dbManager.closeConnections();
    }
}
