import site.nomoreparties.stellarburgers.data.User;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestUserLogin {

    private Faker fake;
    private User user;
    private UserOps ops;
    private ValidatableResponse response;

    public String name;
    public String password;
    public String email;
    public String token;
    public int statusCode;
    public String body;
    public String message;

    @Before
    public void setup() {
        fake = new Faker();
        name = fake.name().firstName();
        email = name + "@yandex.ru";
        password = name + "1234!";
        user = new User();
        ops = new UserOps();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        body = new Gson().toJson(user);
        response = ops.register(body);
        token = response.extract().path("accessToken");
    }

    @After
    public void cleanup() {
        if (token != null) {
            ops.delete(token);
        }
    }

    @Test
    public void testIfCanLoginWithValidUser() {
        //Arrange
        user = new User();
        user.setEmail(email);
        user.setPassword(password);
        //Act
        body = new Gson().toJson(user);
        response = ops.login(body);
        token = response.extract().path("accessToken");
        message = response.extract().path("message");
        statusCode = response.extract().statusCode();
        //Assert
        Assert.assertEquals(message, 200, statusCode);
        Assert.assertTrue(message, response.extract().path("success"));
    }

    @Test
    public void testIfCanLoginWithInvalidUser() {
        //Arrange
        user = new User();
        user.setEmail(email);
        user.setPassword("123456");
        //Act
        body = new Gson().toJson(user);
        response = ops.login(body);
        token = response.extract().path("accessToken");
        message = response.extract().path("message");
        statusCode = response.extract().statusCode();
        //Assert
        Assert.assertEquals(message, 401, statusCode);
        Assert.assertEquals("email or password are incorrect", message);
    }
}