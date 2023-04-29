package com.blank04.controller;

import com.blank04.model.ResultDto;
import com.blank04.service.SolverService;
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

    public MainController(SolverService solverService) {
        this.solverService = solverService;
    }

    @PostMapping("/solve")
    public ResponseEntity<ResultDto> solveImage(@RequestParam("image") MultipartFile image) throws IOException, TesseractException {
        Tika tika = new Tika();
        String fileType = tika.detect(image.getBytes());
        if (fileType.equals("image/png") || fileType.equals("image/jpg") || fileType.equals("image/jpeg")) {
            return ResponseEntity.ok(solverService.solveImage(image));
        } else if (fileType.equals("application/pdf")) {
            return ResponseEntity.ok(solverService.solvePdf(image));
        } else {
            return ResponseEntity.badRequest()
                    .body(new ResultDto("The provided file has unacceptable type. \n Please, upload the file one of the provided types: JPG, PNG, PDF"));
        }
    }
}
