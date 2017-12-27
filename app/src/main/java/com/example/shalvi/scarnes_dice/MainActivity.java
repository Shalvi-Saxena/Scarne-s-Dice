package com.example.shalvi.scarnes_dice;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.RunnableFuture;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity
{
    private int us=0;
    private int ut=0;
    private int cs=0;
    private int ct=0;
    ImageView iv;
    TextView tv1, tv2;
    Button b1;
    Button b2;
    Button b3;
    private int i =0;
    Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = (ImageView)findViewById(R.id.iv);
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        b1 = (Button)findViewById(R.id.b1);
        b2 = (Button)findViewById(R.id.b2);
        b3 = (Button)findViewById(R.id.b3);
    }

    public void roll(View view)
    {
        int temp = r.nextInt(6)+1;
        setImage(temp);
        if(temp==1)
        {
            ut=0;
            hold(tv1);
        }
        else
        {
            ut+=temp;
            tv1.setText("Your score: " + ut + " computer score: " + ct);
            winner();
            if (tv2.getText() == "You Won!!")
                reset(tv1);
        }
    }

    public void setImage(int a)
    {
        iv.setImageResource(getResources().getIdentifier("@drawable/dice"+a, "drawable", getPackageName()));
    }

    public void reset(View view)
    {
        us=0;   ut=0;
        cs=0;   ct=0;
        i=0;
        b1.setEnabled(true);
        b2.setEnabled(true);
        tv1.setText("Your turn score will display here!");
        tv2.setText("Your overall score will display here!");
        setImage(1);
    }

    public void hold(View view)
    {
        us+=ut;
        cs+=ct;
        ut=0;
        ct=0;
        i=0;
        tv1.setText("Your score: "+ut+" computer score: "+ct);
        tv2.setText("Your Overall score: "+us+" Computer's overall score: "+cs);
        setImage(1);
        winner();
        computerTurn();
    }

    Handler h = new Handler();

    Runnable run = new Runnable() {
        @Override
        public void run() {
            int temp = r.nextInt(6)+1;
            setImage(temp);
            if(temp!=1 && ct<15)
            {
                ct += temp;
                tv1.setText("Your score: "+ut+" computer score: "+ct);
                h.postDelayed(run, 500);
                winner();
            }
            else if(temp==1)
            {
                ct=0;
                tv1.setText("Your score: "+ut+" computer score: "+ct);
                h.removeCallbacks(run);
            }
            else
            {
                cs+=ct;
                ct=0;
                tv2.setText("Your Overall score: "+us+" Computer's overall score: "+cs);
                h.removeCallbacks(run);
            }
        }
    };

    Runnable run1 = new Runnable() {
        @Override
        public void run() {
            if(us>=50 || cs>=50) {
                tv2.setText("You Won!!");
                h.postDelayed(run1, 1000);
            }
            h.removeCallbacks(run1);
        }
    };

    public void computerTurn()
    {
        b1.setClickable(false);
        b2.setClickable(false);
        h.postDelayed(run, 500);
        b1.setClickable(true);
        b2.setClickable(true);
    }

    public void winner()
    {
        h.postDelayed(run1, 1000);
    }
}
