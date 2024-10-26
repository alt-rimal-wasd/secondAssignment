package project2;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
//got help from co-pilot
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
