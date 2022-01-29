package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserUpdateInfoWithoutAuthTest {

    private UserClient userClient;
    User user = User.getRandom();

    @Before
    public void setUp() {
        userClient = new UserClient();
        userClient.create(user);
    }

    @After
    public void tearDown() {
        userClient.delete(userClient.login(UserCredentials.from(user)).extract().path("accessToken"));
    }

    @Test
    @DisplayName("Check user can not be update info without authorization")
    public void userCanNotBeUpdateInfoWithoutAuthorization() {
        ValidatableResponse updateUserInfo = userClient.updateUserInfoWithoutAuth(User.getWithRandomEmail(user));
        int statusCode = updateUserInfo.extract().statusCode();
        boolean isUserInfoUpdate = updateUserInfo.extract().path("success");
        String errorMessage = updateUserInfo.extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo(401));
        assertThat("User info can be update without authorization", isUserInfoUpdate, equalTo(false));
        assertThat("Error message is incorrect", errorMessage, equalTo("You should be authorised"));
    }
}
