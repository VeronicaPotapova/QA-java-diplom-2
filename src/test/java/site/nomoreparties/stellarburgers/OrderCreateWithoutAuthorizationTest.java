package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class OrderCreateWithoutAuthorizationTest {

    private Ingredients ingredients;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        ingredients = Ingredients.getRandomIngredientsBurger();
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Check not create order without authorization user")
    public void orderCanNotBeCreatedWithoutAuthorizationUser() {
        ValidatableResponse responseCreateOrder = orderClient.createOrderWithoutAuth(ingredients);
        int statusCode = responseCreateOrder.extract().statusCode();
        boolean isOrderCreated = responseCreateOrder.extract().path("success");
        String errorMessage = responseCreateOrder.extract().path("message");

        assertTrue("Order is not created", isOrderCreated);
        assertThat("Status code is incorrect", statusCode, equalTo(401));
        assertThat("Error message is incorrect", errorMessage, equalTo("You should be authorised"));
    }
}
