package project2;

import Project1.Room;
import Project1.SingleRoom;
import Project1.DoubleRoom;
import Project1.Suite;
import org.junit.Test;
import static org.junit.Assert.*;

public class RoomFactoryTest {
    
    @Test
    public void testCreateSingleRoom() {
        Room room = RoomFactory.createRoom("single", "s001", 100.0);
        assertNotNull("Room should be created", room);
        assertTrue("Room should be an instance of SingleRoom", room instanceof SingleRoom);
        assertEquals("Room number should be 's001'", "s001", room.getRoomNumber());
        assertEquals("Room price should be 100.0", 100.0, room.getPrice(), 0.0);
    }

    @Test
    public void testCreateDoubleRoom() {
        Room room = RoomFactory.createRoom("double", "d001", 150.0);
        assertNotNull("Room should be created", room);
        assertTrue("Room should be an instance of DoubleRoom", room instanceof DoubleRoom);
        assertEquals("Room number should be 'd001'", "d001", room.getRoomNumber());
        assertEquals("Room price should be 150.0", 150.0, room.getPrice(), 0.0);
    }

    @Test
    public void testCreateSuite() {
        Room room = RoomFactory.createRoom("suite", "suite001", 250.0);
        assertNotNull("Room should be created", room);
        assertTrue("Room should be an instance of Suite", room instanceof Suite);
        assertEquals("Room number should be 'suite001'", "suite001", room.getRoomNumber());
        assertEquals("Room price should be 250.0", 250.0, room.getPrice(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidRoomType() {
        RoomFactory.createRoom("invalid", "x001", 100.0);
    }
}
