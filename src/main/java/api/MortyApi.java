package api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class MortyApi {

    private final String baseUri;

    public MortyApi(String baseUri) {
        this.baseUri = baseUri;
    }

    public Response getCharacters() {
        return given()
                .baseUri(baseUri)
                .when()
                .get("character")
                .then()
                .statusCode(200)
                .extract().response();
    }

    public Response getEpisode(String url) {
        return given().when().get(url)
                .then()
                .statusCode(200)
                .extract().response();
    }

    public Response getCharacter(String url) {
        return given().when().get(url)
                .then()
                .statusCode(200)
                .extract().response();
    }
}