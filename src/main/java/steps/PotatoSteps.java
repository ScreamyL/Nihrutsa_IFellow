package steps;

import api.PotatoApi;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Props.props;

public class PotatoSteps {

    private final PotatoApi api;

    public PotatoSteps(String baseUri) {
        this.api = new PotatoApi(baseUri);
    }

    public void performPotatoTest() {
        Response response = createPotatoFromFile("Tomato", "Eat maket");

        assertEquals(201, response.getStatusCode(), "Статус ответа не соответствует 201!");

        JSONObject jsonResponse = new JSONObject(response.asString());

        assertEquals("Tomato", jsonResponse.getString("name"), "Имя не соответствует ожидаемому значению!");
        assertEquals("Eat maket", jsonResponse.getString("job"), "Работа не соответствует ожидаемому значению!");
    }

    public Response createPotatoFromFile(String name, String job) {
        try {
            String content = new String(Files.readAllBytes(Path.of(props.file_path())));
            JSONObject body = new JSONObject(content);

            body.put("name", name);
            body.put("job", job);

            return api.createPotato(body);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}