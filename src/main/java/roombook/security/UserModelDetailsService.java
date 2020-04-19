package roombook.security;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import roombook.model.user.User;
import roombook.repository.UserRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Authenticate a user via the database.
 */
@Component("userDetailsService")
public class UserModelDetailsService implements UserDetailsService
{
    private static final Logger LOG = LoggerFactory.getLogger(UserModelDetailsService.class);

    private final UserRepository userRepository;

    public UserModelDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login)
    {
        LOG.debug("Authenticating user '{}'.", login);

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);

        return userRepository.findOneWithAuthoritiesByUsername(lowercaseLogin)
                                .map(user -> createSpringSecurityUser(lowercaseLogin, user))
                                .orElseThrow(() -> new UsernameNotFoundException("User "
                                                                + lowercaseLogin + " was not found in the database."));

    }

    private org.springframework.security.core.userdetails.User
                                                        createSpringSecurityUser(String lowercaseLogin, User user)
    {
        if (!user.isActivated())
        {
            throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                                                        .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                                                        .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                                                                                                    grantedAuthorities);
    }
}
