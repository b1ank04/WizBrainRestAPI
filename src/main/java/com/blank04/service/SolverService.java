package com.blank04.service;

import com.blank04.model.dto.SolveResponseDto;
import com.blank04.utils.FileConverter;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;


@Service
public class SolverService {

    private final ChatGPTService chatGPTService;
    private final ImageAnalyzeService imageAnalyzeService;
    private final DocumentAnalyzeService documentAnalyzeService;

    public SolverService(ChatGPTService chatGPTService, ImageAnalyzeService imageAnalyzeService, DocumentAnalyzeService documentAnalyzeService) {
        this.chatGPTService = chatGPTService;
        this.imageAnalyzeService = imageAnalyzeService;
        this.documentAnalyzeService = documentAnalyzeService;
    }

    public SolveResponseDto solveImage(MultipartFile image) throws TesseractException, IOException {
        BufferedImage bufferedImage = FileConverter.convertMultipart(image);
        String text = imageAnalyzeService.analyzeImage(bufferedImage);
        return new SolveResponseDto(text, chatGPTService.askChatGPTText(text));
    }

    public SolveResponseDto solvePdf(MultipartFile file) throws IOException {
        String text = documentAnalyzeService.analyzePdf(file);
        return new SolveResponseDto(text, chatGPTService.askChatGPTText(text));
    }
}
