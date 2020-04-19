package roombook.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import roombook.security.SecurityUtils;
import roombook.model.user.User;
import roombook.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthorities()
    {
        return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
}
