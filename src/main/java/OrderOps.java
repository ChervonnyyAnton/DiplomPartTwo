import site.nomoreparties.stellarburgers.client.Base;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderOps {


    @Step("Create order request")
    public ValidatableResponse create(String token, String body) {
        return given().spec(Base.getBaseSpec()).auth().oauth2(token).body(body).when().post("orders").then();
    }

    @Step("Get order list for user request")
    public ValidatableResponse getUserOrderList(String token) {
        return given().spec(Base.getBaseSpec()).auth().oauth2(token).get("orders").then();
    }
}