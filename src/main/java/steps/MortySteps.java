package steps;


import api.MortyApi;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

public class MortySteps {

    private final MortyApi api;

    public MortySteps(String baseUri) {
        this.api = new MortyApi(baseUri);
    }

    public void testLastCharacterWithMorty() {
        JSONArray charactersJson = fetchCharacters();
        JSONObject mortyJson = findCharacterJson(charactersJson, "Morty Smith");

        if (mortyJson == null) {
            fail("Морти не найден!");
        }

        String lastEpisodeUrl = getLastEpisodeUrl(mortyJson);
        Response episodeResponse = getEpisode(lastEpisodeUrl);
        String lastCharacterUrl = getLastCharacterUrl(new JSONObject(episodeResponse.asString()));
        Response lastCharacterResponse = getCharacter(lastCharacterUrl);
        JSONObject lastCharacterJson = new JSONObject(lastCharacterResponse.asString());

        assertCharacterMatch(mortyJson, lastCharacterJson);
    }

    private JSONArray fetchCharacters() {
        Response response = api.getCharacters();
        return new JSONObject(response.asString()).getJSONArray("results");
    }

    public Response getEpisode(String url) {
        return api.getEpisode(url);
    }

    public Response getCharacter(String url) {
        return api.getCharacter(url);
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

        assertEquals(mortySpecies, lastCharacterSpecies, "Расы не совпадают!");
        assertNotEquals(mortyLocation, lastCharacterLocation, "Местоположения совпадают!");
    }
}