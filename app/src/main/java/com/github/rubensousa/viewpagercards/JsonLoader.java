package com.github.rubensousa.viewpagercards;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by psanchez on 3/23/2018.
 */

public class JsonLoader {
    public static JsonLoader sJsonLoader;
    private List<Question> Questions = new ArrayList<>();


    public JsonLoader(Context context){

    }

    public static JsonLoader getJsonLoader(Context context){
        if(sJsonLoader == null){
            sJsonLoader = new JsonLoader(context);
        }
        return sJsonLoader;
    }

    public List<Question> getQuestions(){
        return Questions;
    }
    public Question getAQuestion(int position){
        return Questions.get(position);
    }

public void loadQuestions(Reader filename){
        final Type QUESTION_TYPE = new TypeToken<List<Question>>(){}.getType();
        Gson gson = new Gson();

             JsonReader reader = new JsonReader(filename);
            List<Question> lQuestions = gson.fromJson(reader,QUESTION_TYPE);
            for(Question i : lQuestions){
                //Log.v("json",i.toString());
                Questions.add(i);
            }

            for(int i = 0; i < lQuestions.size(); i++){
                Questions.add(lQuestions.get(i));
            }



}

}

