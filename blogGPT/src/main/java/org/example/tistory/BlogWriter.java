package org.example.tistory;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class BlogWriter {

    private static final String API_URL = "https://www.tistory.com/apis/post/write";
    private static final String ACCESS_TOKEN = System.getenv("TISTORY_ACCESS_TOKEN");
    private static final String BLOG_NAME = System.getenv("BLOG_NAME");

    public static void upload(String title, String content) throws IOException, InterruptedException {
        String category = "1"; // category ID
        String visibility = "3"; // 3 for public, 2 for protected, 1 for private
        String output = "json"; // or "xml"

        String url = String.format("%s?access_token=%s&output=%s&blogName=%s&title=%s&content=%s&visibility=%s&category=%s",
                API_URL, ACCESS_TOKEN, output, BLOG_NAME,
                URLEncoder.encode(title, StandardCharsets.UTF_8),
                URLEncoder.encode(content, StandardCharsets.UTF_8),
                visibility, category);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}