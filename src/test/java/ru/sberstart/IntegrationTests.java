package ru.sberstart;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.sberstart.dto.CardDto;
import ru.sberstart.dto.CreateCardDto;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;

class IntegrationTests {
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void createCard_Success() throws SQLException, IOException, URISyntaxException, InterruptedException {
        Main.main(null);

        CreateCardDto obj = new CreateCardDto("85232125695565656823");
        HttpClient client = HttpClient.newBuilder()    .version(HttpClient.Version.HTTP_1_1)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8081/client/card/create"))
                .headers("Content-Type", "application/json;charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(obj)))
                .build();

        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals(200, response.statusCode());
        CardDto result = objectMapper.readValue((byte[]) response.body(), CardDto.class);
        Assertions.assertNotNull(result.getCardNumber());
    }
}
