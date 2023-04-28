package com.blank04.service;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.io.File;



@Service
public class SolverService {

    private final ChatGPTService chatGPTService;
    private final TextAnalyzeService textAnalyzeService;

    public SolverService(ChatGPTService chatGPTService, TextAnalyzeService textAnalyzeService) {
        this.chatGPTService = chatGPTService;
        this.textAnalyzeService = textAnalyzeService;
    }

    public String solve(File file) throws TesseractException {
        String text = textAnalyzeService.analyzePicture(file);
        return chatGPTService.askChatGPTText(text);
    }
}
