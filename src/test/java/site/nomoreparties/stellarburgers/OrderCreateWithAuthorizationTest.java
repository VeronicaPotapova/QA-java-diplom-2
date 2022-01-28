package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class OrderCreateWithAuthorizationTest {

    private User user;
    private UserClient userClient;
    private Ingredients ingredients;
    private OrderClient orderClient;


    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
        ingredients = Ingredients.getRandomIngredientsBurger();
        orderClient = new OrderClient();
    }

    @After
    public void tearDown() {
        userClient.delete(userClient.login(UserCredentials.from(user)).extract().path("accessToken"));
    }

    @Test
    @DisplayName("Check create order with authorization user")
    public void shouldBeOrderCreatedWithAuthorizationUser() {

        ValidatableResponse responseCreateUser = userClient.create(user);
        String userToken = responseCreateUser.extract().path("accessToken");

        ValidatableResponse responseCreateOrder = orderClient.createOrderWithAuth(userToken, ingredients);
        int statusCode = responseCreateOrder.extract().statusCode();
        boolean isOrderCreated = responseCreateOrder.extract().path("success");
        int orderNumber = responseCreateOrder.extract().path("order.number");

        assertTrue("Order is not created", isOrderCreated);
        assertThat("Status code is incorrect", statusCode, equalTo(200));
        assertThat("Number order is incorrect", orderNumber, is(notNullValue()));
    }
}
