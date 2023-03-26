package org.example.openai;

import org.example.utils.JsonUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImageApi {
    public String run(String prompt) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        int n = 1;
        String size = "1024x1024";

        String url = "https://api.openai.com/v1/images/generations";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString("{\"prompt\": \"" + prompt + "\", \"n\": " + n + ", \"size\": \"" + size + "\"}"))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return JsonUtils.extractURLFromJSON(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
