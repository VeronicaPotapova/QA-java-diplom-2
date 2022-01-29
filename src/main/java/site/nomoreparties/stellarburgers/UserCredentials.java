package site.nomoreparties.stellarburgers;

public class UserCredentials {

    public String email;
    public String password;

    public UserCredentials() {

    }

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentials from(User user) {
        return new UserCredentials(user.email, user.password);
    }

    public UserCredentials setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserCredentials setPassword(String password) {
        this.password = password;
        return this;
    }

    public static UserCredentials getWithEmailOnly(User user) {
        return new UserCredentials().setEmail(user.email);
    }

    public static UserCredentials getWithPasswordOnly(User user) {
        return new UserCredentials().setPassword(user.password);
    }

    public static UserCredentials getWithoutData() {
        return new UserCredentials();
    }
}
