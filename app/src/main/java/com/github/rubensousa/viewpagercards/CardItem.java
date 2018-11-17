package com.github.rubensousa.viewpagercards;


public class CardItem {

    private int mSentenceResource;
    private int mWordResource;
    private int mAnswerResource;
    private int mCH1Resource;
    private int mCH2Resource;
    private int mCH3Resource;
    private String sentenceSTR;
    private String wordSTR;
    private String answerSTR;
    private String ch1STR;
    private String ch2STR;
    private String ch3STR;

    public CardItem(String word, String sentence, String answer, String ch1, String ch2, String ch3){
        wordSTR = word;
        sentenceSTR = sentence;
        answerSTR = answer;
        ch1STR = ch1;
        ch2STR = ch2;
        ch3STR = ch3;
    }
    public CardItem(int word, int sentence, int answer, int ch1, int ch2, int ch3) {
        mWordResource = word;
        mSentenceResource = sentence;
        mAnswerResource = answer;
        mCH1Resource = ch1;
        mCH2Resource = ch2;
        mCH3Resource = ch3;
    }

    public String getSentenceSTR(){
        return sentenceSTR;
    }

    public String getWordSTR(){
        return wordSTR;
    }

    public String getAnswerSTR() {
        return answerSTR;
    }

    public String getCh1STR() {
        return ch1STR;
    }

    public String getCh2STR() {
        return ch2STR;
    }

    public String getCh3STR() {
        return ch3STR;
    }



    public int getSentence() {
        return mSentenceResource;
    }

    public int getWord() {
        return mWordResource;
    }

    public int getAnswer() {
        return mAnswerResource;
    }

    public int getCh1() {
        return mCH1Resource;
    }

    public int getCh2() {
        return mCH2Resource;
    }

    public int getCh3() {
        return mCH3Resource;
    }
}
