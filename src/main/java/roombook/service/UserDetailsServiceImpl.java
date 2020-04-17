package roombook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository UserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        roombook.model.user.User User = UserRepository.findByUsername(username);
        if (User == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(User.getUsername(), User.getPassword(), emptyList());
    }

    @Bean
    UserRepository userRepository() {
        return new UserRepository();
    }
}
