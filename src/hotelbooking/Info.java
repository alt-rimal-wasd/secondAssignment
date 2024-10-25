/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelbooking;

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
        dbManager = new DBManager();
        conn = dbManager.getConnection();
    }

    public void connectHotelDB() {
        try {
            this.statement = conn.createStatement();
            this.checkExistedTable("ROOM");
            this.statement.addBatch("CREATE TABLE ROOM (ROOMID INT, TYPE VARCHAR(20), PRICE FLOAT)");
            this.statement.addBatch("INSERT INTO ROOM VALUES (1, 'Single', 100.00),"
                    + "(2, 'Double', 150.00),"
                    + "(3, 'Suite', 250.00)");
            this.statement.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void createCustomerTable() {
        try {
            this.statement = conn.createStatement();
            this.checkExistedTable("CUSTOMER");
            this.statement.addBatch("CREATE TABLE CUSTOMER (CUSTOMERID INT, NAME VARCHAR(50), EMAIL VARCHAR(50))");
            this.statement.executeBatch();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ResultSet getAvailableRooms() {
        ResultSet rs = null;
        try {
            rs = this.statement.executeQuery("SELECT * FROM ROOM");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rs;
    }

    public void checkExistedTable(String name) {
        try {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            String[] types = {"TABLE"};
            statement = this.conn.createStatement();
            ResultSet rs = dbmd.getTables(null, null, null, types);

            while (rs.next()) {
                String table_name = rs.getString("TABLE_NAME");
                if (table_name.equalsIgnoreCase(name)) {
                    statement.executeUpdate("DROP TABLE " + name);
                    System.out.println("Table " + name + " has been deleted.");
                    break;
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void closeConnection() {
        this.dbManager.closeConnections();
    }
}