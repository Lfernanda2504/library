package com.aluracurso.library.model;

public enum Language {
    en("[en]", "Ingles"),
    es("[es]", "Español"),
    fr("[fr]", "Frances"),
    pt("[pt]", "Portugues");

    private String codeGutendex;
    private String languages;


    public String getCodeGutendex() {
        return codeGutendex;
    }

    public String getLanguages() {
        return languages;
    }


    Language(String codeGutendex, String languages) {
        this.codeGutendex = codeGutendex;
        this.languages = languages;
    }

    public static Language fromString(String text){
        for (Language language : Language.values()){
            if(language.codeGutendex.equalsIgnoreCase(text)){
                return language;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: "+text);
    }

    public static Language fromLanguages(String text){
        for (Language language : Language.values()){
            if (language.languages.equalsIgnoreCase(text)){
                return language;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado" +text);
    }
}
