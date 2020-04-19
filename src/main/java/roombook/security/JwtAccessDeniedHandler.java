package roombook.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * This handler is invoked when a user makes a request with insufficient authorization. Response will have a
 * 403 Forbidden status with the message specified.
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler
{
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                                                AccessDeniedException accessDeniedException) throws IOException
    {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
    }
}
