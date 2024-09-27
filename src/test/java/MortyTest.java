import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MortyTest {

    private static final String BASE_URI = "https://rickandmortyapi.com/api/";

    @Test
    @DisplayName("Проверка данных последнего персонажа, появлявшегося с Морти")
    public void testLastCharacterWithMorty() {

        Response charactersResponse = RestAssured.get(BASE_URI + "character");
        if (charactersResponse.statusCode() != 200) {
            System.out.println("Ошибка при получении списка персонажей: " + charactersResponse.asString());
            return;
        }

        JSONArray charactersJson = new JSONObject(charactersResponse.asString()).getJSONArray("results");
        JSONObject mortyJson = null;

        for (int i = 0; i < charactersJson.length(); i++) {
            JSONObject character = charactersJson.getJSONObject(i);
            if ("Morty Smith".equals(character.getString("name"))) {
                mortyJson = character;
                break;
            }
        }

        if (mortyJson == null) {
            System.out.println("Морти не найден!");
            return;
        }

        JSONArray episodeList = mortyJson.getJSONArray("episode");
        String lastEpisodeUrl = episodeList.getString(episodeList.length() - 1);

        Response episodeResponse = RestAssured.get(lastEpisodeUrl);
        if (episodeResponse.statusCode() != 200) {
            System.out.println("Ошибка при получении данных последнего эпизода: " + episodeResponse.asString());
            return;
        }
        JSONObject episodeJson = new JSONObject(episodeResponse.asString());
        JSONArray charactersInEpisode = episodeJson.getJSONArray("characters");

        String lastCharacterUrl = charactersInEpisode.getString(charactersInEpisode.length() - 1);

        Response lastCharacterResponse = RestAssured.get(lastCharacterUrl);
        if (lastCharacterResponse.statusCode() != 200) {
            System.out.println("Ошибка при получении данных последнего персонажа: " + lastCharacterResponse.asString());
            return;
        }
        JSONObject lastCharacterJson = new JSONObject(lastCharacterResponse.asString());

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