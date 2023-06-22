package com.blank04.service;

import com.blank04.model.MyTesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class ImageAnalyzeService {
    public String analyzeImage(BufferedImage image) throws TesseractException {
        Tesseract tesseract = MyTesseract.getInstance();
        return tesseract.doOCR(image);
    }

}
