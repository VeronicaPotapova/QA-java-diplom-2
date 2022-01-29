package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserDuplicateNotCreateTest {

    private UserClient userClient;
    private String userToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @After
    public void tearDown() {
        userClient.delete(userToken);
    }

    @Test
    @DisplayName("Check duplicate user can not be created")
    public void duplicateUserCanNotBeCreated() {
        User user = User.getRandom();
        userClient.create(user);
        userToken = userClient.login(UserCredentials.from(user)).extract().path("accessToken");

        ValidatableResponse response = userClient.create(user);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo(403));
        assertThat("Error message is incorrect", errorMessage, equalTo("User already exists"));
    }

}
