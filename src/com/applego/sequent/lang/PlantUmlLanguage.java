package com.applego.sequent.lang;

import com.intellij.lang.Language;

public class PlantUmlLanguage extends Language {

    public static final PlantUmlLanguage INSTANCE = new PlantUmlLanguage();

    private PlantUmlLanguage() {
        super("SEQ"); // PUML
    }
}