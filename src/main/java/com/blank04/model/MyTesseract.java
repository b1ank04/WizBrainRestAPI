package com.blank04.model;

import lombok.experimental.UtilityClass;
import net.sourceforge.tess4j.Tesseract;


@UtilityClass
public class MyTesseract {

    private static Tesseract tesseract = null;

    public static Tesseract getInstance() {
        if (tesseract == null) {
            tesseract = new Tesseract();
            tesseract.setDatapath("src/main/resources/traindata/LSTM");
            tesseract.setLanguage("eng+ukr+rus");
            tesseract.setOcrEngineMode(1);
            tesseract.setVariable("user_defined_dpi", "300");
        }
        return tesseract;
    }

}
