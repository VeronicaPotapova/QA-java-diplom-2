package site.nomoreparties.stellarburgers;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class Ingredients extends RestAssuredClient {

    public ArrayList<Object> ingredients;

    public Ingredients(ArrayList<Object> ingredients) {
        this.ingredients = ingredients;
    }

    private static final String INGREDIENTS_PATH = "/api/ingredients";

    public static Ingredients getRandomIngredientsBurger() {
        //data ingredients
        ValidatableResponse responseBurger = given()
                .spec(getBaseSpec())
                .when()
                .get(INGREDIENTS_PATH)
                .then()
                .statusCode(200);

        //list ingredients
        ArrayList<Object> ingredients = new ArrayList<>();

        //get list by type ingredient
        List<Object> bunIngredients = responseBurger.extract().jsonPath().getList("data.findAll{it.type == 'bun'}._id");
        List<Object> mainIngredients = responseBurger.extract().jsonPath().getList("data.findAll{it.type == 'main'}._id");
        List<Object> sauceIngredients = responseBurger.extract().jsonPath().getList("data.findAll{it.type == 'sauce'}._id");

        //add ingredient to the array by index
        ingredients.add(bunIngredients.get(RandomUtils.nextInt(0, 2)));
        ingredients.add(mainIngredients.get(RandomUtils.nextInt(0, 9)));
        ingredients.add(sauceIngredients.get(RandomUtils.nextInt(0, 4)));

        return new Ingredients(ingredients);
    }

    public static Ingredients getNullIngredientsBurger() {
        ArrayList<Object> ingredients = new ArrayList<>();
        return new Ingredients(ingredients);
    }

    public static Ingredients getErrorIngredientsBurger() {
        ArrayList<Object> ingredients = new ArrayList<>();

        ingredients.add(RandomStringUtils.randomAlphabetic(10));
        ingredients.add(RandomStringUtils.randomAlphabetic(10));
        ingredients.add(RandomStringUtils.randomAlphabetic(10));

        return new Ingredients(ingredients);
    }
}
