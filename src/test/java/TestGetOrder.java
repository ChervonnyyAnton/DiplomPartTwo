import site.nomoreparties.stellarburgers.data.Ingredients;
import site.nomoreparties.stellarburgers.data.Order;
import com.google.gson.Gson;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestGetOrder {

    private Order order;
    private UserOps uOps;
    private OrderOps oOps;
    private Ingredients ingredients;

    public String body;
    public int statusCode;
    public String token;

    @Before
    public void setup() {
        oOps = new OrderOps();
        uOps = new UserOps();
        order = new Order();
        ingredients = new Ingredients();

        token = uOps.registerAndAuthorizeValidUser().extract().path("accessToken");
        token = token.replaceAll("Bearer", "").trim();

        order.setIngredients(ingredients.getRandomList());
        body = new Gson().toJson(order);
        oOps.create(token, body);
    }

    @After
    public void cleanup() {
        if (token != null) {
            uOps.delete(token);
        }
    }

    @Test
    public void testIfCanCreateOrderAuthorized() {
        ValidatableResponse response = oOps.getUserOrderList(token);
        statusCode = response.extract().statusCode();
        Assert.assertEquals(200, statusCode);
        Assert.assertNotNull(response.extract().path("orders"));
    }

    @Test
    public void testIfCanCreateOrderUnAuthorized() {
        ValidatableResponse response = oOps.getUserOrderList("");
        statusCode = response.extract().statusCode();
        Assert.assertEquals(401, statusCode);
        Assert.assertEquals("unexpected response message", "You should be authorised", response.extract().path("message"));
    }
}