package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class OrderListForUserTest {

    private UserClient userClient;
    private String userToken;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        User user = User.getRandom();
        userClient = new UserClient();
        ValidatableResponse responseCreateUser = userClient.create(user);
        userToken = responseCreateUser.extract().path("accessToken");

        Ingredients ingredients = Ingredients.getRandomIngredientsBurger();
        orderClient = new OrderClient();
        orderClient.createOrderWithAuth(userToken, ingredients);
    }

    @After
    public void tearDown() {
        userClient.delete(userToken);
    }

    @Test
    @DisplayName("Check orders list can be returned for an authorization user")
    public void ordersListCanBeReturnedForAuthUser() {

        ValidatableResponse responseOrdersList = orderClient.returnOrdersListAuthUser(userToken);
        int statusCode = responseOrdersList.extract().statusCode();
        String ordersList = responseOrdersList.extract().body().asString();

        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertTrue("Orders list is empty or contains incorrect data", ordersList.contains("_id"));
    }
}
