package com.blank04.controller;

import com.blank04.service.SolverService;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("api/v1")
public class MainController {

    private final SolverService solverService;

    public MainController(SolverService solverService) {
        this.solverService = solverService;
    }

    @PostMapping("/solveImage")
    public ResponseEntity<Object> solveImage(@RequestParam("image") MultipartFile image) throws IOException, TesseractException {
        File uploadDir = new File("uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        File uploadedFile = new File(uploadDir.getAbsolutePath() + File.separator + image.getOriginalFilename());
        image.transferTo(uploadedFile);
        return ResponseEntity.ok(solverService.solve(uploadedFile));
    }
}
