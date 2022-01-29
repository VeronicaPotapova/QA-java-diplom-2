package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserAuthorizationWithValidDataTest {

    private UserClient userClient;
    User user = User.getRandom();
    private String userToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        userClient.create(user);
    }

    @After
    public void tearDown() {
        userClient.delete(userToken);
    }

    @Test
    @DisplayName("Check user can be authorization with valid data")
    public void userCanBeAuthorizationWithValidData() {
        ValidatableResponse response = userClient.login(UserCredentials.from(user));
        int statusCode = response.extract().statusCode();
        userToken = response.extract().path("accessToken");

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertThat("User token is incorrect", userToken, is(notNullValue()));
    }
}
