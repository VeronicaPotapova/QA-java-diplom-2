package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestAssuredClient {

    private static final String USER_PATH = "/api/auth";

    @Step("create user")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH + "/register")
                .then();
    }

    @Step("authorization user")
    public ValidatableResponse login(UserCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(USER_PATH + "/login")
                .then();
    }

    @Step("update user info with authorization")
    public ValidatableResponse updateUserInfo(String userToken, User changeUser) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(userToken.replace("Bearer ", ""))
                .body(changeUser)
                .when()
                .patch(USER_PATH + "/user")
                .then();
    }

    @Step("update user info without authorization")
    public ValidatableResponse updateUserInfoWithoutAuth(User changeUser) {
        return given()
                .spec(getBaseSpec())
                .body(changeUser)
                .when()
                .patch(USER_PATH + "/user")
                .then();
    }

    @Step("delete user")
    public ValidatableResponse delete(String userToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(userToken.replace("Bearer ", ""))
                .when()
                .delete(USER_PATH + "/user")
                .then();
    }
}
