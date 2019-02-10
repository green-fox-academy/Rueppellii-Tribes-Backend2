package rueppellii.backend2.tribes.user;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertNotNull;

public class TestLoginTest {
    private final String USERNAME = "Name213";
    private final String JSON = "application/json";

    @BeforeEach
    void setup() throws Exception {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;

    }

    @Test
    final void testUserLogin() {
        Map<String, String> loginDetails = new HashMap<>();
        loginDetails.put("username", USERNAME);
        loginDetails.put("password", "pass");

        Response response = given().contentType(JSON).accept(JSON).body(loginDetails).when().post("/api/auth/login").then().statusCode(200).extract().response();

        String authorizationHeader = response.header("Authorization");
//        String xmlHeader = response.header("X-Requested-With", "XMLHttpRequest")

        assertNotNull(authorizationHeader);
    }
}
