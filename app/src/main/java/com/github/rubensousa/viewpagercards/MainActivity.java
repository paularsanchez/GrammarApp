package com.github.rubensousa.viewpagercards;
//The foundation of this app is based on github code written by Ruben Sousa.
//The original name of this package was viewpagercards.

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.app.Activity;
import android.os.Parcelable;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static com.github.rubensousa.viewpagercards.R.*;

public class MainActivity extends AppCompatActivity implements MyCallBack
//implements View.OnClickListener
        //CompoundButton.OnCheckedChangeListener
        {

    private Button mDictionary;
    private Button mHint;
    private ViewPager mViewPager;
    private JsonLoader mQuestions = JsonLoader.getJsonLoader(this);
    private List<Question> myQuestions;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    //private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private boolean mShowingFragments = false;
    private ListView listView;
    public static int topS = 0, bottomS = 0;
    //we could call this anything
    public void setTScore(int top, int bottom){
        TextView score = (TextView)findViewById(id.tScore);
        score.setText(top + "/" + bottom);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        mViewPager = (ViewPager) findViewById(id.viewPager);
        mDictionary = (Button) findViewById(R.id.definitionBtn);
        mHint = (Button)findViewById(R.id.bHint);
        //((CheckBox) findViewById(id.checkBox)).setOnCheckedChangeListener(this);
        //mButton.setOnClickListener(this);

        mCardAdapter = new CardPagerAdapter(this, this, this);

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        //mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);
        loadQuestionsToActivity();
        loadtoAdapter();
        TextView textView = findViewById(id.tScore);
        SharedPreferences sharedPref = getSharedPreferences("scores", Context.MODE_PRIVATE);
        textView.setText(sharedPref.getInt("top", 0) + "/" + sharedPref.getInt("bottom", 0));
        mHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hintDialog();
            }
        });

        mDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dictionaryDialog();
            }
        });

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    public void loadQuestionsToActivity(){
        InputStream file = getResources().openRawResource(raw.questionlistjson);
        try {
            Reader reader = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            mQuestions.loadQuestions(reader);

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }

    public void loadtoAdapter(){
        myQuestions = mQuestions.getQuestions();
        for(Question q : myQuestions){
            mCardAdapter.addCardItem(new CardItem(q.term, q.sentence, q.answer, q.choice1, q.choice2, q.choice3));
        }
    }

            public void hintDialog(){
                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.fragment_adapter,null);
                final AlertDialog d = new AlertDialog.Builder(this)
                        .setView(v)
                        .create();
                d.show();

                Button close = (Button)v.findViewById(id.return_button);
                TextView hintText = (TextView)v.findViewById(id.definitionTextView);
                hintText.setText(mQuestions.getAQuestion(CardPagerAdapter.questionIndex()).hint);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });


            }

            public void dictionaryDialog(){
                LayoutInflater inflater = getLayoutInflater();
                final View v = inflater.inflate(R.layout.fragment_adapter,null);
                final AlertDialog d = new AlertDialog.Builder(this)
                        .setView(v)
                        .create();
                d.show();

                Button close = (Button)v.findViewById(id.return_button);
                TextView definitionText = (TextView)v.findViewById(id.definitionTextView);
                TextView titleText = (TextView)v.findViewById(id.titleTextView);
                titleText.setText("Definition");
                definitionText.setText(mQuestions.getAQuestion(CardPagerAdapter.questionIndex()).definition);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        d.dismiss();
                    }
                });


            }

            public String getTscore() {
                TextView score = (TextView)findViewById(id.tScore);
                String curScore = score.getText().toString();
                return curScore;
            }

            @Override
            public void UpdateScore(String score) {
                TextView textView = (TextView)findViewById(id.tScore);
                textView.setText(score);
            }

            public void resetScore(View view){
                SharedPreferences sharedPref = getSharedPreferences("scores", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("top", 0);
                editor.putInt("bottom", 0);
                editor.commit();
                TextView score = findViewById(id.tScore);
                score.setText("0/0");
            }


            /*@Override
    public void onClick(View view) {
        if (!mShowingFragments) {
            mButton.setText("Definition");
            mViewPager.setAdapter(mFragmentCardAdapter);
            mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        } else {
            mButton.setText("Dictionary?");
            mViewPager.setAdapter(mCardAdapter);
            mViewPager.setPageTransformer(false, mCardShadowTransformer);
        }

        mShowingFragments = !mShowingFragments;
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    /*@Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mCardShadowTransformer.enableScaling(b);
        mFragmentCardShadowTransformer.enableScaling(b);
    }
    */

}
