package com.github.rubensousa.viewpagercards;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Question {
    String term,hint,answer,choice1,choice2,choice3,definition,sentence;
    public Question(String Word, String Hint, String Answer, String Ch1, String Ch2, String Ch3,
                String Definition, String Sentence){
            this.term = Word;
            this.hint = Hint;
            this.answer = Answer;
            this.choice1 = Ch1;
            this.choice2 = Ch2;
            this.choice3 = Ch3;
            this.definition = Definition;
            this.sentence = Sentence;
    }
}


