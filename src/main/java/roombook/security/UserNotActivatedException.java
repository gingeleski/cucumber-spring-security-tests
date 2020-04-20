package roombook.security;

import org.springframework.security.core.AuthenticationException;

/**
 * This exception is thrown if a user who's not activated tries to authenticate.
 */
public class UserNotActivatedException extends AuthenticationException
{
    private static final long serialVersionUID = -1126699074574529145L;

    public UserNotActivatedException(String message)
    {
        super(message);
    }
}
