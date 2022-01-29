package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class OrderCreateRequestValidationDataWithAuthTest {

    private final Ingredients ingredients;
    private final int expectedStatus;
    private final boolean expectedOrderCreated;
    private final String expectedMessage;

    private String userToken;
    private OrderClient orderClient;

    public OrderCreateRequestValidationDataWithAuthTest(Ingredients ingredients, int expectedStatus,
                                                        boolean expectedOrderCreated, String expectedMessage) {
        this.ingredients = ingredients;
        this.expectedStatus = expectedStatus;
        this.expectedOrderCreated = expectedOrderCreated;
        this.expectedMessage = expectedMessage;
    }

    @Before
    public void setUp() {
        User user = User.getRandom();
        UserClient userClient = new UserClient();
        userToken = (userClient.create(user)).extract().path("accessToken");

        orderClient = new OrderClient();
    }


    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {Ingredients.getErrorIngredientsBurger(), 500, false, null},
                {Ingredients.getNullIngredientsBurger(), 400, false, "Ingredient ids must be provided"},
        };
    }

    @Test
    @DisplayName("Check not create order with invalid data with authorization user")
    public void orderCanNotBeOrderCreatedWithInvalidDataWithoutAuth() {

        ValidatableResponse responseCreateOrder = orderClient.createOrderWithAuth(userToken, ingredients);
        int statusCode = responseCreateOrder.extract().statusCode();
        assertThat("Status code is incorrect", statusCode, equalTo(expectedStatus));

        if (expectedStatus == 500) {
            return;
        } else {
            boolean isOrderCreated = responseCreateOrder.extract().path("success");
            String errorMessage = responseCreateOrder.extract().path("message");

            assertThat("Order is not created", isOrderCreated, equalTo(expectedOrderCreated));
            assertThat("Error message is incorrect", errorMessage, equalTo(expectedMessage));
        }

    }
}
