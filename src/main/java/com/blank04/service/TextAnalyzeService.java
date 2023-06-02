package com.blank04.service;

import com.blank04.model.Request;
import com.blank04.repository.RequestRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class TextAnalyzeService {

    private final RequestRepository requestRepository;

    public TextAnalyzeService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public String summarize(String text) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://text-analysis12.p.rapidapi.com/summarize-text/api/v1.1"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", "324078c6ccmshf30d54b878a4a11p1927f8jsn9128530ac437")
                .header("X-RapidAPI-Host", "text-analysis12.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(String.format("{\r%n    \"language\": \"english\",\r%n    \"summary_percent\": 10,\r%n    \"text\": \"%s\"\r%n}", text.replace("\n", " ").replace("\r", ""))))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject responseObject = new JSONObject(response.body());
        String result = responseObject.getString("summary");
        if (result.length() < 10) {
            result = "The text you sent is too small to summarize";
        }
        requestRepository.save(new Request(null, "SUMMARIZE_TEXT", text, result));
        return result;
    }
}
