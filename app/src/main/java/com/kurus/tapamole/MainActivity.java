package com.kurus.tapamole;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView txtScore;
    TextView txtTimer;

    int[] imageResources = {    //imageViewのリソースID
            R.id.imageView1, R.id.imageView2, R.id.imageView3,
            R.id.imageView4, R.id.imageView5, R.id.imageView6,
            R.id.imageView7, R.id.imageView8, R.id.imageView9,
            R.id.imageView10, R.id.imageView11, R.id.imageView12
    };

    Mole[] moles;                       //モグラの配列
    int time;                           //時間の変数
    int score;                          //スコアの変数

    Timer timer;
    TimerTask timerTask;
    Handler handler;                    //タイマーからUIスレッドへの通信用
    Random random = new Random();       //ランダムな数字を発生させる

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtScore = (TextView)findViewById(R.id.txtScore);
        txtTimer = (TextView)findViewById(R.id.txtTime);

        //モグラの数だけモグラの配列を作る
        moles = new Mole[12];
        for(int i = 0; i< 12; i++){
            //レイアウトのImageViewを１つずつ取り出し
            ImageView imageView = (ImageView)findViewById(imageResources[i]);
            //imageViewを使ってi番目のもぐらのインスタンスを生成
            moles[i] = new Mole(imageView);
        }

        handler = new Handler();
    }

    public void start(View view){
        time = 60;
        score = 0;
        txtTimer.setText(String.valueOf(time));
        txtScore.setText(String.valueOf(score));

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //0から11までのランダムな数字を生成
                        int r = random.nextInt(12);
                        //r番目のもぐらが飛び出す
                        moles[r].start();

                        time = time - 1;
                        txtTimer.setText(String.valueOf(time));
                        if(time <= 0) timer.cancel();
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    public void tapMole(View view){
        //叩いたImageViewのタグを取得
        String tagString = (String)view.getTag();
        //tagStringをint型に変換
        int tagInt = Integer.valueOf(tagString);
        //対応したモグラをたたく
        score += moles[tagInt].tapMole();
        txtScore.setText(String.valueOf(score));
    }
}
