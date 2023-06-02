package com.blank04.model;

import com.deepl.api.Translator;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MyTranslator {

    private static final String API_KEY = "2d9c3a1b-98f1-fed5-8e47-949363ae5efc:fx";
    private static Translator translator = null;

    public static Translator getInstance() {
        if (translator == null) {
            translator = new Translator(API_KEY);
        }
        return translator;
    }
}
