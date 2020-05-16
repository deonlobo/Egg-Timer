package com.deonlobo.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekbar;
    TextView timerTextView;
    Boolean counterIsActive =false;
    Button button ;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        timerTextView.setText("0:30");
        timerSeekbar.setProgress(30);
        countDownTimer.cancel();
        button.setText("Go!");
        timerSeekbar.setEnabled(true);
        counterIsActive=false;
    }

    public void updateTimer(int secondsLeft){
        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft -(minutes * 60);

        String secondString = Integer.toString(seconds);
        if (seconds <= 9){
            secondString="0"+secondString;
        }

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void controlTimer(View view){
        if (counterIsActive==false) {

            counterIsActive = true;
            timerSeekbar.setEnabled(false);
            button.setText("Stop");

            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.foghorn);
                    mediaPlayer.start();
                }
            }.start();
        }else{
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekbar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
