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
public class UserUpdateInfoWithChangeEmailWithAuthTest {

    private final User user;
    private final User changeUser;
    private final int expectedStatus;
    private final boolean expectedInfoUpdate;

    private UserClient userClient;
    private String userToken;

    public UserUpdateInfoWithChangeEmailWithAuthTest(User user, User changeUser, int expectedStatus, boolean expectedInfoUpdate) {
        this.user = user;
        this.changeUser = changeUser;
        this.expectedStatus = expectedStatus;
        this.expectedInfoUpdate = expectedInfoUpdate;
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
                //change email
                {user, User.getWithRandomEmail(user), 200, true},
                //change email and pass
                {user, User.getWithRandomEmailAndPassword(user), 200, true},
                //change emil and name
                {user, User.getWithRandomEmailAndName(user), 200, true},
                //change all data
                {user, User.getWithRandomNewData(user), 200, true},
        };
    }

    @After
    public void tearDown() {
        userClient.delete(userToken);
    }

    @Test
    @DisplayName("Check user can be update info with authorization")
    public void userCanBeUpdateInfoWithChangeEmailWithAuthorization() {
        ValidatableResponse userLogin = userClient.login(UserCredentials.from(user));
        userToken = userLogin.extract().path("accessToken");

        ValidatableResponse updateUserInfo = userClient.updateUserInfo(userToken, changeUser);
        int statusCode = updateUserInfo.extract().statusCode();
        boolean isUserInfoUpdate = updateUserInfo.extract().path("success");

        assertThat("Status code is incorrect", statusCode, equalTo(expectedStatus));
        assertThat("", isUserInfoUpdate, equalTo(expectedInfoUpdate));
    }
}
