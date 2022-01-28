package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class UserNotAuthorizationWithErrorDataTest {

    private final UserCredentials credentials;
    private final int expectedStatus;
    private final String expectedMessage;

    public UserNotAuthorizationWithErrorDataTest(UserCredentials credentials, int expectedStatus, String expectedMessage) {
        this.credentials = credentials;
        this.expectedStatus = expectedStatus;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {UserCredentials.getWithEmailOnly(User.getWithEmailOnly()), 401, "email or password are incorrect"},
                {UserCredentials.getWithPasswordOnly(User.getWithPasswordOnly()), 401, "email or password are incorrect"},
                {UserCredentials.from(User.getWithEmailAndPasswordOnly()), 401, "email or password are incorrect"},
                {UserCredentials.getWithoutData(), 401, "email or password are incorrect"},
        };
    }

    @Test
    @DisplayName("Check user can not be authorization with error data")
    public void userCanNotBeAuthorizationWithErrorData() {
        ValidatableResponse response = new UserClient().login(credentials);
        int statusCode = response.extract().statusCode();
        String errorMessage = response.extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo(expectedStatus));
        assertThat("Error message is incorrect", errorMessage, equalTo(expectedMessage));
    }
}
