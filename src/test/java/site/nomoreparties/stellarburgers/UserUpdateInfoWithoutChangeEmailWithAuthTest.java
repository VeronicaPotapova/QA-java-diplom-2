package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class UserUpdateInfoWithoutChangeEmailWithAuthTest {

    private final User user;
    private final User changeUser;
    private final int expectedStatus;
    private final boolean expectedInfoUpdate;
    private final String expectedMessage;

    private UserClient userClient;
    private String userToken;

    public UserUpdateInfoWithoutChangeEmailWithAuthTest(User user, User changeUser, int expectedStatus, boolean expectedInfoUpdate, String expectedMessage) {
        this.user = user;
        this.changeUser = changeUser;
        this.expectedStatus = expectedStatus;
        this.expectedInfoUpdate = expectedInfoUpdate;
        this.expectedMessage = expectedMessage;
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
        userClient.create(user);
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        User user = User.getRandom();
        return new Object[][]{
                //change pass
                {user, User.getWithRandomPassword(user), 403, false, "User with such email already exists"},
                //change name
                {user, User.getWithRandomName(user), 403, false, "User with such email already exists"},
                //change pass and name
                {user, User.getWithRandomPasswordAndName(user), 403, false, "User with such email already exists"},
        };
    }

    @After
    public void tearDown() {
        userClient.delete(userToken);
    }

    @Test
    @DisplayName("Check user can be update info with authorization")
    public void userCanBeUpdateInfoWithoutChangeEmailWithAuthorization() {
        ValidatableResponse userLogin = userClient.login(UserCredentials.from(user));
        userToken = userLogin.extract().path("accessToken");

        ValidatableResponse updateUserInfo = userClient.updateUserInfo(userToken, changeUser);
        int statusCode = updateUserInfo.extract().statusCode();
        boolean isUserInfoUpdate = updateUserInfo.extract().path("success");
        String errorMessage = updateUserInfo.extract().path("message");

        assertThat("Status code is incorrect", statusCode, equalTo(expectedStatus));
        assertThat("User info can be update", isUserInfoUpdate, equalTo(expectedInfoUpdate));
        assertThat("Error message is incorrect", errorMessage, equalTo(expectedMessage));
    }
}
