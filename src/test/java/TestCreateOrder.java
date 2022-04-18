import site.nomoreparties.stellarburgers.data.Ingredients;
import site.nomoreparties.stellarburgers.data.Order;
import com.google.gson.Gson;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCreateOrder {

    private Order order;
    private UserOps uOps;
    private OrderOps oOps;
    private Ingredients ingredients;

    public String body;
    public int statusCode;
    public String message;
    public String token;

    @Before
    public void setup() {
        oOps = new OrderOps();
        uOps = new UserOps();
        order = new Order();
        ingredients = new Ingredients();

        token = uOps.registerAndAuthorizeValidUser().extract().path("accessToken");
        token = token.replaceAll("Bearer", "").trim();

    }

    @After
    public void cleanup() {
        if (token != null) {
            uOps.delete(token);
        }
    }

    @Test
    public void testIfCanCreateOrderAuthorized() {
        order.setIngredients(ingredients.getRandomList());
        body = new Gson().toJson(order);
        ValidatableResponse response = oOps.create(token, body);
        statusCode = response.extract().statusCode();
        message = response.extract().path("message");
        Assert.assertEquals(message, 200, statusCode);
        Assert.assertTrue(message, response.extract().path("success"));
    }

    @Test
    public void testIfCanCreateOrderUnAuthorized() {
        order.setIngredients(ingredients.getRandomList());
        body = new Gson().toJson(order);
        ValidatableResponse response = oOps.create("", body);
        statusCode = response.extract().statusCode();
        message = response.extract().path("message");
        Assert.assertEquals(message, 200, statusCode);
        Assert.assertTrue(message, response.extract().path("success"));
    }

    @Test
    public void testIfCanCreateOrderWithNoIngredients() {
        body = new Gson().toJson(order.getIngredients());
        ValidatableResponse response = oOps.create(token, body);
        statusCode = response.extract().statusCode();
        Assert.assertEquals(400, statusCode);
    }

    @Test
    public void testIfCanCreateOrderWithInvalidHash() {
        order.setIngredients(ingredients.getRandomInvalidList());
        body = new Gson().toJson(order);
        ValidatableResponse response = oOps.create(token, body);
        statusCode = response.extract().statusCode();
        Assert.assertEquals(500, statusCode);
    }
}