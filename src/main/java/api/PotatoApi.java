package api;

import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class PotatoApi {

    private final String baseUri;

    public PotatoApi(String baseUri) {
        this.baseUri = baseUri;
    }

    public Response createPotato(JSONObject body) {
        return given()
                .header("Content-Type", "application/json;charset=UTF-8")
                .baseUri(baseUri)
                .body(body.toString())
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract().response();
    }
}