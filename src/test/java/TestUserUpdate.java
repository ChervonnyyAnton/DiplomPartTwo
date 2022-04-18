import site.nomoreparties.stellarburgers.data.User;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestUserUpdate {

    private Faker fake;
    private User user;
    private UserOps ops;
    private ValidatableResponse response;
    private ValidatableResponse updatedResponse;

    public String token;
    public String userEmail;
    public String userName;
    public String newEmail;
    public String newName;
    public String body;
    public String updatedEmail;
    public String updatedName;
    public int statusCode;
    public String message;

    @Before
    public void setup() {
        ops = new UserOps();
        fake = new Faker();
        response = ops.registerAndAuthorizeValidUser();
        token = response.extract().path("accessToken");
        token = token.replaceAll("Bearer", "").trim();
        userName = response.extract().path("user.name");
        userEmail = response.extract().path("user.email");
        newName = fake.name().firstName().toLowerCase();
        newEmail = newName + "@yandex.ru";
        user = new User();
    }

    @After
    public void cleanup() {
        if (token != null) {
            ops.delete(token);
        }
    }

    @Test
    public void testIfCanUpdateEmailAuthorized() {
        user.setEmail(newEmail);
        body = new Gson().toJson(user);
        updatedResponse = ops.update(token, body);
        updatedEmail = updatedResponse.extract().path("user.email");
        statusCode = updatedResponse.extract().statusCode();
        message = updatedResponse.extract().path("message");
        Assert.assertEquals(message, 200, statusCode);
        Assert.assertEquals(message, newEmail, updatedEmail);
    }

    @Test
    public void testIfCanUpdateUserAuthorized() {
        user.setName(newName);
        body = new Gson().toJson(user);
        updatedResponse = ops.update(token, body);
        updatedName = updatedResponse.extract().path("user.name");
        statusCode = updatedResponse.extract().statusCode();
        message = updatedResponse.extract().path("message");
        Assert.assertEquals(message, 200, statusCode);
        Assert.assertEquals(message, newName, updatedName);
    }

    @Test
    public void testIfCanNotUpdateEmailUnAuthorized() {
        user.setEmail(newEmail);
        body = new Gson().toJson(user);
        updatedResponse = ops.update("", body);
        updatedEmail = updatedResponse.extract().path("user.email");
        statusCode = updatedResponse.extract().statusCode();
        message = updatedResponse.extract().path("message");
        Assert.assertEquals("Can edit user data unauthorized", 401, statusCode);
        Assert.assertEquals("response body is different", "You should be authorised", message);
    }

    @Test
    public void testIfCanNotUpdateUserUnAuthorized() {
        user.setName(newName);
        body = new Gson().toJson(user);
        updatedResponse = ops.update("", body);
        updatedName = updatedResponse.extract().path("user.name");
        statusCode = updatedResponse.extract().statusCode();
        message = updatedResponse.extract().path("message");
        Assert.assertEquals("Can edit user data unauthorized", 401, statusCode);
        Assert.assertEquals("response body is different", "You should be authorised", message);
    }
}
