package site.nomoreparties.stellarburgers;

import org.apache.commons.lang3.RandomStringUtils;

public class User {

    public String email;
    public String password;
    public String name;

    public User() {

    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static final String EMAIL_POSTFIX = "@yandex.ru";

    public static User getRandom() {
        final String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public static User getWithEmailOnly() {
        return new User().setEmail(RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX);
    }

    public static User getWithPasswordOnly() {
        return new User().setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    public static User getWithNameOnly() {
        return new User().setName(RandomStringUtils.randomAlphabetic(10));
    }

    public static User getWithEmailAndPasswordOnly() {
        return new User().setEmail(RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX)
                .setPassword(RandomStringUtils.randomAlphabetic(10));
    }

    public static User getWithRandomEmail(User user) {
        String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        return new User(email, user.password, user.name);
    }

    public static User getWithRandomPassword(User user) {
        String password = RandomStringUtils.randomAlphabetic(10);
        return new User(user.email, password, user.name);
    }

    public static User getWithRandomName(User user) {
        String name = RandomStringUtils.randomAlphabetic(10);
        return new User(user.email, user.password, name);
    }

    public static User getWithRandomEmailAndPassword(User user) {
        String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        String password = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, user.name);
    }

    public static User getWithRandomEmailAndName(User user) {
        String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, user.password, name);
    }

    public static User getWithRandomPasswordAndName(User user) {
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);
        return new User(user.email, password, name);
    }

    public static User getWithRandomNewData(User user) {
        String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        String password = RandomStringUtils.randomAlphabetic(10);
        String name = RandomStringUtils.randomAlphabetic(10);
        return new User(email, password, name);
    }
}
