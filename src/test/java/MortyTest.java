import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class MortyTest {

    private static final String BASE_URI = "https://rickandmortyapi.com/api/";

    @Test
    @DisplayName("Проверка данных последнего персонажа, появлявшегося с Морти")
    public void testLastCharacterWithMorty() {
        Response charactersResponse = given()
                .baseUri(BASE_URI)
                .when()
                .get("character")
                .then()
                .statusCode(200)
                .extract().response();

        JSONArray charactersJson = new JSONObject(charactersResponse.asString()).getJSONArray("results");
        JSONObject mortyJson = findCharacterJson(charactersJson, "Morty Smith");

        if (mortyJson == null) {
            System.out.println("Морти не найден!");
            return;
        }

        String lastEpisodeUrl = getLastEpisodeUrl(mortyJson);
        Response episodeResponse = given().when().get(lastEpisodeUrl).then()
                .statusCode(200)
                .extract().response();

        String lastCharacterUrl = getLastCharacterUrl(new JSONObject(episodeResponse.asString()));
        Response lastCharacterResponse = given().when().get(lastCharacterUrl).then()
                .statusCode(200)
                .extract().response();

        JSONObject lastCharacterJson = new JSONObject(lastCharacterResponse.asString());

        assertCharacterMatch(mortyJson, lastCharacterJson);
    }

    private JSONObject findCharacterJson(JSONArray charactersJson, String characterName) {
        for (int i = 0; i < charactersJson.length(); i++) {
            JSONObject character = charactersJson.getJSONObject(i);
            if (characterName.equals(character.getString("name"))) {
                return character;
            }
        }
        return null;
    }

    private String getLastEpisodeUrl(JSONObject mortyJson) {
        JSONArray episodeList = mortyJson.getJSONArray("episode");
        return episodeList.getString(episodeList.length() - 1);
    }

    private String getLastCharacterUrl(JSONObject episodeJson) {
        JSONArray charactersInEpisode = episodeJson.getJSONArray("characters");
        return charactersInEpisode.getString(charactersInEpisode.length() - 1);
    }

    private void assertCharacterMatch(JSONObject mortyJson, JSONObject lastCharacterJson) {
        String mortySpecies = mortyJson.getString("species");
        String mortyLocation = mortyJson.getJSONObject("location").getString("name");

        String lastCharacterSpecies = lastCharacterJson.getString("species");
        String lastCharacterLocation = lastCharacterJson.getJSONObject("location").getString("name");

        boolean speciesMatch = mortySpecies.equals(lastCharacterSpecies);
        boolean locationMatch = mortyLocation.equals(lastCharacterLocation);

        System.out.println("Совпадение расы: " + speciesMatch);
        System.out.println("Совпадение местоположения: " + locationMatch);
    }
}