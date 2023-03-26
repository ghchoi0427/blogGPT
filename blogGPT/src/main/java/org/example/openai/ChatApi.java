package org.example.openai;

import org.example.utils.JsonUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChatApi {
    private static final String OPENAI_API_KEY = System.getenv("OPENAI_API_KEY");

    public String run(String topic, int maxTokens) {
        String url = "https://api.openai.com/v1/chat/completions";
        String model = "gpt-3.5-turbo";
        String content = "write an essay about: " + topic + "in form of html tags";
        String role = "user";

        String requestBody = "{"
                + "\"model\": \"" + model + "\","
                + "\"messages\": [{"
                + "\"role\": \"" + role + "\","
                + "\"content\": \"" + content + "\""
                + "}],"
                + "\"max_tokens\": " + maxTokens
                + "}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + OPENAI_API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return JsonUtils.getResponseContent(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }


}