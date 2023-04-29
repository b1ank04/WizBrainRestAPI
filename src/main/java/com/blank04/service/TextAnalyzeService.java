package com.blank04.service;

import com.blank04.model.MyTesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
public class TextAnalyzeService {
    public String analyzePicture(File file) throws TesseractException {
        Tesseract tesseract = MyTesseract.getInstance();
        return tesseract.doOCR(file);
    }
}
