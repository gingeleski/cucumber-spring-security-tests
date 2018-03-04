package roombook.user;

public abstract class User
{
    private String username;
    private String password;

    public User()
    {
        this.setUsername(null);
        this.setPassword(null);
    }

    public User(String name, String username, String password)
    {
        this.setUsername(username);
        this.setPassword(password);
    }

    public abstract String getType();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}