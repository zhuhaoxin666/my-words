package com.example.words.bean;

public class WordAndMeaning {

    private String meaning;
    private String word;

    @Override
    public String toString() {
        return "WordAndMeaning{" +
                "word='" + word + '\'' +
                ", meaning='" + meaning + '\'' +
                '}';
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
