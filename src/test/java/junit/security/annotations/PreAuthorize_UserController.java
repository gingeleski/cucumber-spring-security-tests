package junit.security.annotations;

import static org.junit.Assert.*;

import org.junit.Test;
import roombook.user.UserController;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

public class PreAuthorize_UserController {

    @Test
    public void testLogout() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        assertTrue(TestUtilities.evaluatePreAuthorizeIsAuthenticated(UserController.class, "logout", HttpServletResponse.class));
    }

}
