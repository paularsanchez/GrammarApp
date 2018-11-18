package models;

public class Question {
    public String term,hint,answer,choice1,choice2,choice3,definition,sentence;

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

    public Question(){}

//    @Override
//    public String toString() {
//        return "Question{" +
//                "term='" + term + '\'' +
//                ", hint='" + hint + '\'' +
//                ", answer='" + answer + '\'' +
//                ", choice1='" + choice1 + '\'' +
//                ", choice2='" + choice2 + '\'' +
//                ", choice3='" + choice3 + '\'' +
//                ", definition='" + definition + '\'' +
//                ", sentence='" + sentence + '\'' +
//                '}';
//    }
}


