package com.blank04.service;

import com.blank04.model.ResultDto;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Service
public class SolverService {

    private final ChatGPTService chatGPTService;
    private final TextAnalyzeService textAnalyzeService;

    public SolverService(ChatGPTService chatGPTService, TextAnalyzeService textAnalyzeService) {
        this.chatGPTService = chatGPTService;
        this.textAnalyzeService = textAnalyzeService;
    }

    public ResultDto solveImage(MultipartFile image) throws TesseractException, IOException {
        File uploadDir = new File("uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        File uploadedFile = new File(uploadDir.getAbsolutePath() + File.separator + image.getOriginalFilename());
        image.transferTo(uploadedFile);
        String text = textAnalyzeService.analyzePicture(uploadedFile);
        return new ResultDto(text, chatGPTService.askChatGPTText(text));
    }

    public ResultDto solvePdf(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            return new ResultDto(text, chatGPTService.askChatGPTText(text));
        }
    }

}
