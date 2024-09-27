import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PotatoTest {

    private static final String FILE_PATH = "src/test/resources/potato.json";
    private static final String BASE_URI = "https://reqres.in/";

    @Test
    @DisplayName("Тест переименования картошки")
    public void testPotato() {
        try {

            String jsonContent = new String(Files.readAllBytes(Path.of(FILE_PATH)));
            JSONObject body = new JSONObject(jsonContent);
            body.put("name", "Tomato");
            body.put("job", "Eat maket");


            String response = given()
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .baseUri(BASE_URI)
                    .body(body.toString())
                    .post("/api/users")
                    .then()
                    .statusCode(201)
                    .extract().response().asString();


            JSONObject jsonResponse = new JSONObject(response);
            assertEquals("Tomato", jsonResponse.getString("name"), "Имя не соответствует ожидаемому значению!");
            assertEquals("Eat maket", jsonResponse.getString("job"), "Работа не соответствует ожидаемому значению!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}