package steps;


import api.MortyApi;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static utils.Props.props;

public class MortySteps {

    private final MortyApi api;
    private JSONArray charactersJson;
    private JSONObject mortyJson;
    private JSONObject lastCharacterJson;

    public MortySteps() {
        String baseUri = props.morty_uri();
        this.api = new MortyApi(baseUri);
    }

    @Когда("я запрашиваю список персонажей")
    public void IFetchTheCharacterList() {
        charactersJson = fetchCharacters();
    }

    @Если("я нахожу Морти")
    public void IFindMorty() {
        mortyJson = findCharacterJson(charactersJson, "Morty Smith");

        if (mortyJson == null) {
            fail("Морти не найден!");
        }
    }

    @Когда("я получаю последний эпизод Морти")
    public void IGetTheLastEpisodeOfMorty() {
        String lastEpisodeUrl = getLastEpisodeUrl(mortyJson);
        Response episodeResponse = getEpisode(lastEpisodeUrl);
        String lastCharacterUrl = getLastCharacterUrl(new JSONObject(episodeResponse.asString()));
        Response lastCharacterResponse = getCharacter(lastCharacterUrl);
        lastCharacterJson = new JSONObject(lastCharacterResponse.asString());
    }

    @Тогда("я проверяю, что последний персонаж не соответствует Морти")
    public void ICheckThatTheLastCharacterMatchesMorty() {
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
