package com.github.rubensousa.viewpagercards;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class CardPagerAdapter extends PagerAdapter implements CardAdapter {
    private int topscore = 0, bottomscore = 0;
    private List<CardItem> mData;
    private List<CardView> mViews;
    private float mBaseElevation;
    private int position2 = 0;
    private static int i = 0;
    private MainActivity outsideActivity;
    Context context;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    MyCallBack myCallBack = null;
    ArrayList<Boolean> questionsAttempted = new ArrayList<>();

    public CardPagerAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public CardPagerAdapter(MainActivity view, Context context, MyCallBack callBack) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        outsideActivity = view;
        this.context = context;
        myCallBack = callBack;
        prefs = context.getSharedPreferences("scores", Context.MODE_PRIVATE);
        editor = prefs.edit();
        //when program starts up, we need the previous top score and previous bottom score
        //at first the user has attempted NO questions
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        position = position2;
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Inhere", "This card was clicked " + position);
            }
        });

        container.addView(view);
        bind(mData.get(position), view);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        for(int i = 0; i < mData.size(); i++){
            questionsAttempted.add(false);
        }
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    public static int questionIndex() {
        return i;
    }

    public static void setQuestionIndex(int curPos) {
        i = curPos;
    }

    private void bind(CardItem item, final View view) {
        TextView wordTextView = (TextView) view.findViewById(R.id.wordTextView);
        TextView sentenceTextView = (TextView) view.findViewById(R.id.sentenceTextView);

        final Button buttonA = (Button) view.findViewById(R.id.button1);
        Button buttonB = (Button) view.findViewById(R.id.button2);
        Button buttonC = (Button) view.findViewById(R.id.button3);
        Button buttonD = (Button) view.findViewById(R.id.button4);

        final Button[] buttons = new Button[4];
        buttons[0] = buttonA;
        buttons[1] = buttonB;
        buttons[2] = buttonC;
        buttons[3] = buttonD;

        if (item != null) {
            wordTextView.setText(item.getWordSTR());
            sentenceTextView.setText(item.getSentenceSTR());
            final ArrayList<Integer> choices = new ArrayList<>();
            choices.add(0);
            choices.add(1);
            choices.add(2);
            choices.add(3);
            Collections.shuffle(choices);


            buttons[choices.get(0)].setText(item.getAnswerSTR());
            buttons[choices.get(0)].setOnClickListener(new View.OnClickListener() {
                MainActivity m;

                @Override
                public void onClick(View v) {
                    buttons[choices.get(0)].setBackgroundColor(Color.parseColor("#4CAF50"));
                    if (questionsAttempted.get(questionIndex()) == false) {
                        //we know this is the correct one
                        questionsAttempted.add(questionIndex(), true);
                        buttons[choices.get(0)].setBackgroundColor(Color.parseColor("#4CAF50"));
                        myCallBack.UpdateScore(++topscore + "/" + (++bottomscore));
                    }
                }
            });
            buttons[choices.get(1)].setText(item.getCh1STR());
            buttons[choices.get(1)].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttons[choices.get(1)].setBackgroundColor(Color.parseColor("#D32F2F"));
                    if (questionsAttempted.get(questionIndex()) == false) {
                        //we know this is the correct one
                        questionsAttempted.add(questionIndex(), true);
                        myCallBack.UpdateScore(topscore + "/" + (++bottomscore));
                    }
                }
            });
            buttons[choices.get(2)].setText(item.getCh2STR());
            buttons[choices.get(2)].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttons[choices.get(2)].setBackgroundColor(Color.parseColor("#D32F2F"));
                    if (questionsAttempted.get(questionIndex()) == false) {
                        //we know this is the correct one
                        questionsAttempted.add(questionIndex(), true);
                        myCallBack.UpdateScore(topscore + "/" + (++bottomscore));
                    }
                }
            });
            buttons[choices.get(3)].setText(item.getCh3STR());
            buttons[choices.get(3)].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttons[choices.get(3)].setBackgroundColor(Color.parseColor("#D32F2F"));
                    if (questionsAttempted.get(questionIndex()) == false) {
                        //we know this is the correct one
                        questionsAttempted.add(questionIndex(), true);
                        myCallBack.UpdateScore(topscore + "/" + (++bottomscore));
                    }
                }
            });
        }
    }
}
