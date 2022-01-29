package site.nomoreparties.stellarburgers;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "/api/orders";

    @Step("create order with authorization")
    public ValidatableResponse createOrderWithAuth(String userToken, Ingredients ingredients) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(userToken.replace("Bearer ", ""))
                .body(ingredients)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("create order without authorization")
    public ValidatableResponse createOrderWithoutAuth(Ingredients ingredients) {
        return given()
                .spec(getBaseSpec())
                .body(ingredients)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("get orders list for an authorization user")
    public ValidatableResponse returnOrdersListAuthUser(String userToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(userToken.replace("Bearer ", ""))
                .when()
                .get(ORDER_PATH)
                .then();
    }

    @Step("get orders list for not authorization user")
    public ValidatableResponse returnOrdersListWithoutAuthUser() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

}
