package steps;


import api.PotatoApi;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Props.props;

public class PotatoSteps {

    private final PotatoApi api;
    private Response response;

    public PotatoSteps() {
        String baseUri = props.potato_uri();
        this.api = new PotatoApi(baseUri);
    }


    @Когда("мы создаем potato из файла, добавляя к нему свои данные")
    public void whenWeCreatePotatoFromFile() {
        response = createPotatoFromFile("Tomato", "Eat maket");
    }

    @Тогда("статус ответа должен быть 201")
    public void thenStatusShouldBe201() {
        assertEquals(201, response.getStatusCode(), "Статус ответа не соответствует 201!");
    }

    @Тогда("имя картошки должно быть {string}")
    public void thenPotatoNameShouldBe(String expectedName) {
        JSONObject jsonResponse = new JSONObject(response.asString());
        assertEquals(expectedName, jsonResponse.getString("name"), "Имя не соответствует ожидаемому значению!");
    }

    @Тогда("работа картошки должна быть {string}")
    public void thenPotatoJobShouldBe(String expectedJob) {
        JSONObject jsonResponse = new JSONObject(response.asString());
        assertEquals(expectedJob, jsonResponse.getString("job"), "Работа не соответствует ожидаемому значению!");
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