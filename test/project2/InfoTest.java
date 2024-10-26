/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project2;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author emort
 */
public class InfoTest {

    private Info info;

    @Before
    public void setUp() {
        info = new Info();
        info.connectHotelDB();
    }

    @Test
    public void testConnectHotelDB() {
        assertNotNull("Info instance should be initialized", info);
    }

    @Test
    public void testGetAvailableRooms() {
        ResultSet rs = info.getAvailableRooms();
        try {
            assertTrue("ResultSet should have at least one row", rs.next());
        } catch (SQLException ex) {
            fail("SQLException thrown: " + ex.getMessage());
        }
    }
}
