package junit.security.annotations;

import static org.junit.Assert.*;

import org.junit.Test;
import roombook.user.UserController;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;

public class PermitAll_UserController {

    @Test
    public void testLogin() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        assertTrue(TestUtilities.evaluatePermitAll(UserController.class, "login", String.class, String.class, HttpServletResponse.class));
    }

}
