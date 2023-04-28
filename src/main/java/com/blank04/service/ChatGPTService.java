package com.blank04.service;

import com.blank04.configuration.OpenApiConstants;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class ChatGPTService {

    public static final String CHOICES = "choices";
    public static final String TEXT = "text";
    @Value("${api.text.temperature}")
    Double temperature;
    @Value("${api.text.model}")
    String textModel;
    @Value("${api.text.top-p}")
    Double topP;
    @Value("${api.text.frequency-penalty}")
    Double freqPenalty;
    @Value("${api.text.presence-penalty}")
    Double presPenalty;
    @Value("${api.token}")
    String apiToken;
    @Value("${api.text.max-tokens}")
    Integer maxTokens;
    @Value("${api.url.completions}")
    String urlCompletions;

    public String askChatGPTText(String msg){

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = setHeaders();

        JSONObject request = new JSONObject();
        request.put(OpenApiConstants.MODEL, textModel);
        request.put(OpenApiConstants.PROMPT, msg);
        request.put(OpenApiConstants.TEMPERATURE, temperature);
        request.put(OpenApiConstants.MAX_TOKENS, maxTokens);
        request.put(OpenApiConstants.TOP_P, topP);
        request.put(OpenApiConstants.FREQUENCY_PENALTY, freqPenalty);
        request.put(OpenApiConstants.PRESENCE_PENALTY, presPenalty);

        HttpEntity<String> requestEntity = new HttpEntity<>(request.toString(), headers);

        URI chatGptUrl = URI.create(urlCompletions);
        ResponseEntity<String> responseEntity = restTemplate.
                postForEntity(chatGptUrl, requestEntity, String.class);

        JSONObject responseJson = new JSONObject(responseEntity.getBody());
        JSONArray choices = (JSONArray) responseJson.get(CHOICES);
        JSONObject firstChoice = (JSONObject) choices.get(0);
        return (String) firstChoice.get(TEXT);
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(OpenApiConstants.AUTHORIZATION, apiToken);
        return headers;
    }
}
