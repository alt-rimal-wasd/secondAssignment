/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author emort
 */
public class DBManagerTest {

    private DBManager dbManager;

    @Before
    public void setUp() {
        dbManager = DBManager.getInstance();
    }

    @Test
    public void testGetConnection() {
        Connection conn = dbManager.getConnection();
        assertNotNull("Connection should be established", conn);
    }

    @Test
    public void testQueryDB() {
        Connection conn = dbManager.getConnection();
        assertNotNull("Connection should be established", conn);
        dbManager.updateDB("CREATE TABLE TestTable (ID INT PRIMARY KEY, NAME VARCHAR(255))");
        dbManager.updateDB("INSERT INTO TestTable VALUES (1, 'TestName')");
        ResultSet rs = dbManager.queryDB("SELECT * FROM TestTable WHERE ID = 1");
        try {
            assertTrue("ResultSet should have at least one row", rs.next());
            assertEquals("Name should be 'TestName'", "TestName", rs.getString("NAME"));
        } catch (SQLException ex) {
            fail("SQLException thrown: " + ex.getMessage());
        } finally {
            dbManager.updateDB("DROP TABLE TestTable");
        }
    }
}
