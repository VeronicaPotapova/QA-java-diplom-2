package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class UserCreateWithValidDataTest {

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
    @DisplayName("Check user can be created with valid data")
    public void userCanBeCreatedWithValidData() {
        User user = User.getRandom();

        ValidatableResponse response = userClient.create(user);
        int statusCode = response.extract().statusCode();
        boolean isUserCreated = response.extract().path("success");
        userToken = response.extract().path("accessToken");

        assertTrue("User is not created", isUserCreated);
        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertThat("User token is incorrect", userToken, is(notNullValue()));
    }
}
