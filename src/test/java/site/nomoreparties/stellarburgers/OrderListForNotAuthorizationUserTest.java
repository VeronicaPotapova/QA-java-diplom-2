package site.nomoreparties.stellarburgers;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;

public class OrderListForNotAuthorizationUserTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Check orders list can not be returned for not authorization user")
    public void ordersListCanNotBeReturnedForNotAuthUser() {

        ValidatableResponse responseOrdersList = orderClient.returnOrdersListWithoutAuthUser();
        int statusCode = responseOrdersList.extract().statusCode();
        boolean isResponseOrdersList = responseOrdersList.extract().path("success");
        String errorMessage = responseOrdersList.extract().path("message");

        assertThat("Orders list can not returned", isResponseOrdersList, equalTo(false));
        assertThat("Status code is incorrect", statusCode, equalTo(401));
        assertThat("Error message is incorrect", errorMessage, CoreMatchers.equalTo("You should be authorised"));
    }
}
