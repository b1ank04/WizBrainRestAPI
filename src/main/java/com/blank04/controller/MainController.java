package com.blank04.controller;

import com.blank04.model.dto.SolveResponseDto;
import com.blank04.service.SolverService;
import com.blank04.service.TextAnalyzeService;
import com.blank04.service.TranslationService;
import com.deepl.api.DeepLException;
import net.sourceforge.tess4j.TesseractException;
import org.apache.tika.Tika;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1")
public class MainController {

    private final SolverService solverService;
    private final TranslationService translationService;
    private final TextAnalyzeService textAnalyzeService;
    private final Tika tika = new Tika();

    public MainController(SolverService solverService, TranslationService translationService, TextAnalyzeService textAnalyzeService) {
        this.solverService = solverService;
        this.translationService = translationService;
        this.textAnalyzeService = textAnalyzeService;
    }

    @PostMapping("/solve")
    public ResponseEntity<SolveResponseDto> solve(@RequestParam("file") MultipartFile file) throws IOException, TesseractException {
        String fileType = tika.detect(file.getBytes());
        if (fileType.equals("image/png") || fileType.equals("image/jpg") || fileType.equals("image/jpeg")) {
            return ResponseEntity.ok(solverService.solveImage(file));
        } else if (fileType.equals("application/pdf")) {
            return ResponseEntity.ok(solverService.solvePdf(file));
        } else {
            return ResponseEntity.badRequest()
                    .body(new SolveResponseDto("The provided file has unacceptable type. \n Please, upload the file one of the provided types: JPG, PNG, PDF"));
        }
    }

    @PostMapping("/translateText")
    public ResponseEntity<String> translateText(@RequestParam("text") String text,
                                                @RequestParam("new_language") String newLanguage)
            throws InterruptedException, DeepLException {
        String result = translationService.translateTextDeepL(text, newLanguage);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/translateFile")
    public ResponseEntity<String> translateFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("new_language") String newLanguage) throws IOException, TesseractException, InterruptedException {
        String fileType = tika.detect(file.getBytes());
        String translatedText = "";
        if (fileType.equals("image/png") || fileType.equals("image/jpg") || fileType.equals("image/jpeg")) {
            translatedText = translationService.translateImage(file, newLanguage);
        }
        return ResponseEntity.ok(translatedText);
    }

    @PostMapping("/summarize")
    public ResponseEntity<String> summarize(@RequestParam("text") String text) throws IOException, InterruptedException {
        return ResponseEntity.ok(textAnalyzeService.summarize(text));
    }
}
