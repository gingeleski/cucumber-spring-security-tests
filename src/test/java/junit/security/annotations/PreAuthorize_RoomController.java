package junit.security.annotations;

import static org.junit.Assert.*;

import org.junit.Test;
import roombook.room.RoomController;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

public class PreAuthorize_RoomController {

    @Test
    public void testGetRooms() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        assertTrue(TestUtilities.evaluatePreAuthorizeIsAuthenticated(RoomController.class, "getRooms", HttpServletResponse.class));
    }

    @Test
    public void testGetRoomByName() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        assertTrue(TestUtilities.evaluatePreAuthorizeIsAuthenticated(RoomController.class, "getRoomByName", String.class));
    }

    @Test
    public void testGetRoomAvailabilityByName() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        assertTrue(TestUtilities.evaluatePreAuthorizeIsAuthenticated(RoomController.class, "getRoomAvailabilityByName", String.class));
    }

}
