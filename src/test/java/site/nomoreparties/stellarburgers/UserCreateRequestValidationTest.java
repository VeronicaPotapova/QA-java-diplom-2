package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class UserCreateRequestValidationTest {

    private final User user;
    private final int expectedStatus;
    private final String expectedMessage;

    private UserClient userClient;

    public UserCreateRequestValidationTest(User user, int expectedStatus, String expectedMessage) {
        this.user = user;
        this.expectedStatus = expectedStatus;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {User.getWithEmailOnly(), 403, "Email, password and name are required fields"},
                {User.getWithPasswordOnly(), 403, "Email, password and name are required fields"},
                {User.getWithNameOnly(), 403, "Email, password and name are required fields"},
                {User.getWithEmailAndPasswordOnly(), 403, "Email, password and name are required fields"},
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Check user can not be created without one of fields")
    public void userCanNotBeCreatedWithoutFields() {

        ValidatableResponse response = userClient.create(user);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo(expectedStatus));
        assertThat("Error message is incorrect", errorMessage, equalTo(expectedMessage));
    }
}
