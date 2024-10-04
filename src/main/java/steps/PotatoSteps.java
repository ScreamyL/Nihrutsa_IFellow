package steps;


import api.PotatoApi;
import io.restassured.response.Response;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PotatoSteps {

    private final PotatoApi api;

    public PotatoSteps(String baseUri) {
        this.api = new PotatoApi(baseUri);
    }

    public void performPotatoTest() {
        Response response = createPotato("Tomato", "Eat maket");
        JSONObject jsonResponse = new JSONObject(response.asString());

        assertEquals("Tomato", jsonResponse.getString("name"), "Имя не соответствует ожидаемому значению!");
        assertEquals("Eat maket", jsonResponse.getString("job"), "Работа не соответствует ожидаемому значению!");
    }

    public Response createPotato(String name, String job) {
        JSONObject body = new JSONObject();
        body.put("name", name);
        body.put("job", job);
        return api.createPotato(body);
    }
}
