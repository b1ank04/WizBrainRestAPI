package com.blank04.service;

import com.blank04.model.MyTranslator;
import com.blank04.utils.FileConverter;
import com.deepl.api.DeepLException;
import net.sourceforge.tess4j.TesseractException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class TranslationService {

    private final ImageAnalyzeService imageAnalyzeService;
    private final RequestService requestService;

    public TranslationService(ImageAnalyzeService imageAnalyzeService, RequestService requestService) {
        this.imageAnalyzeService = imageAnalyzeService;
        this.requestService = requestService;
    }


    public String translateTextRapidAPI(String text, String newLanguage) throws InterruptedException, IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://deepl-translator.p.rapidapi.com/translate"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", "324078c6ccmshf30d54b878a4a11p1927f8jsn9128530ac437")
                .header("X-RapidAPI-Host", "deepl-translator.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(String.format("{\r%n    \"text\": \"%s.\",\r%n    \"source\": \"AUTO\",\r%n    \"target\": \"%s\"\r%n}", text.replace("\n", "\\n"), newLanguage)))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject object = new JSONObject(response.body());
        return object.getString("text");
    }

    public String translateTextDeepL(String text, String newLanguage) throws InterruptedException, DeepLException {
        String result = MyTranslator.getInstance().translateText(text, null, newLanguage).getText();
        requestService.save("TRANSLATE_TEXT",text, result);
        return result;
    }

    public String translateImage(MultipartFile file, String newLanguage) throws IOException, TesseractException, InterruptedException, DeepLException {
        BufferedImage image = FileConverter.convertMultipart(file);
        String text = imageAnalyzeService.analyzeImage(image);
        String result = translateTextDeepL(text, newLanguage);
        requestService.save("TRANSLATE_IMAGE",text, result);
        return result;
    }
}
