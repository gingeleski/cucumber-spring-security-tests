package roombook.service;

import java.util.ArrayList;
import java.util.List;

import roombook.model.user.User;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public User findByUsername(String username) {
        for (User user : this.users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public void save(User user) {
        this.users.add(user);
    }
}
