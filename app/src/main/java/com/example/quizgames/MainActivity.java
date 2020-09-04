package com.example.quizgames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int score=0,questions=0;
    Button playButton,endButton;
    TextView timeView, scoreView, questionView,answerStatusView;
    Button A,B,C,D;
    CountDownTimer Gametimer;
    GridLayout answerGrid;
    int x,y,a,b,c,d,ans,ansposition;
    int timeout=60;

    private void disableButtons(GridLayout layout) {

        // Get all touchable views
        ArrayList<View> layoutButtons = layout.getTouchables();

        // loop through them, if they are an instance of Button, disable it.
        for(View v : layoutButtons){
            if( v instanceof Button ) {
                (v).setEnabled(false);
            }
        }
    }

    private void enableButtons(GridLayout layout) {
        //enabling all answer option buttons

        int count = layout.getChildCount();
        for(int i = 0 ; i <count ; i++){
            View child = layout.getChildAt(i);
            child.setEnabled(true);
        }
    }

    public void askQuestion()
    {
        Random random= new Random();

        x=random.nextInt(100);
        y=random.nextInt(100);

        ans=x+y;

        ansposition=random.nextInt(4);

        a=random.nextInt(100);
        b=random.nextInt(100);
        c=random.nextInt(100);
        d=random.nextInt(100);


        switch (ansposition){
            case 0: a=ans;
                break;
            case 1: b=ans;
                break;
            case 2: c=ans;
                break;
            case 3: d=ans;
                break;
        }


        A.setText(""+a);
        B.setText(""+b);
        C.setText(""+c);
        D.setText(""+d);


        questionView.setText(x+"+"+y);

    }

    public void selectedAns(View view)
    {
        answerStatusView=findViewById(R.id.answerStatus);
        Button selectedAns=(Button)view;
        int SelectedAns= Integer.parseInt(selectedAns.getText().toString());
        questions++;
        if(SelectedAns==ans)
        {
            answerStatusView.setText("Correct");
            score++;
        }
        else
        {
            answerStatusView.setText("Wrong");
        }

        scoreView.setText(score+ " / "+questions);
        askQuestion();

    }


    public void resetGame()
    {
        timeView.setText("00:00s");
        scoreView.setText("0 / 0");
        questionView.setText("Question");
        A.setText("A");
        B.setText("B");
        C.setText("C");
        D.setText("D");
        playButton.setText("play");
        playButton.setTag(1); // to play again
        disableButtons(answerGrid);
        answerStatusView.setText("");
    }

    public void playReset(final View view)
    {
        score=0;
        timeView=findViewById(R.id.timertextView);
        scoreView=findViewById(R.id.scoretextView);
        questionView =findViewById(R.id.questiontextView);
        A=findViewById(R.id.abutton);
        B=findViewById(R.id.bbutton);
        C=findViewById(R.id.cbutton);
        D=findViewById(R.id.dbutton);
        playButton=findViewById(R.id.playresetbutton);
        endButton=findViewById(R.id.endGameButton);
        answerGrid=findViewById(R.id.answerGrid);




        //reset
        //set tag to 0 for reset
        if (Integer.parseInt(view.getTag().toString())==0)
        {
            resetGame();
            score=0;
            questions=0;
        }
        else
        {
            score=0;
            questions=0;
            //play game
            playButton.setVisibility(View.INVISIBLE);
            endButton.setVisibility(View.VISIBLE);




            final int timer=61;
            timeout=60;


            Gametimer=new CountDownTimer(timer*1000,1000){

                @Override
                public void onTick(long l) {
                    timeView.setText( "00:" + l/1000 + "s" );
                    --timeout;
                    if(timeout%2==0)
                    {
                        askQuestion();
                    }
                }

                @Override
                public void onFinish() {
                    endButton=findViewById(R.id.endGameButton);
                    endButton.setVisibility(View.INVISIBLE);
                    playButton.setText("Reset");
                    playButton.setVisibility(View.VISIBLE);
                    playButton.setTag(0);
                    disableButtons(answerGrid);
                    if(questions>0)
                        answerStatusView.setText("Score: "+(score*100)/questions+"%");
                }
            }.start();
            askQuestion();
            enableButtons(answerGrid);


        }

    }

    public void endGame(View view)
    {
        endButton=findViewById(R.id.endGameButton);
        endButton.setVisibility(View.INVISIBLE);
        Gametimer.cancel();
        playButton.setText("Reset");
        playButton.setVisibility(View.VISIBLE);
        playButton.setTag(0);
        disableButtons(answerGrid);
        if(questions>0)
            answerStatusView.setText("Score: "+(score*100)/questions+"%");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        answerGrid=findViewById(R.id.answerGrid);
        disableButtons(answerGrid);

    }
}