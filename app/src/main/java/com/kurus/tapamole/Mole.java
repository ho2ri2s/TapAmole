package com.kurus.tapamole;

import android.os.Handler;
import android.widget.ImageView;

public class Mole {
    int state;      //もぐらの状態 0:潜っている 1:出てきている 2: 叩かれている
    ImageView imgMole;
    Handler handler;    //Handlerスレッド間の処理を投げる役割
    Runnable hide;      //Handlerで投げる処理の中身を書くためのクラス

    public Mole(ImageView imageView){
        state = 0;
        imgMole = imageView;
        imgMole.setImageResource(R.drawable.mole1);

        handler = new Handler();
        hide = new Runnable() {
            @Override
            public void run() {
                state = 0;
                imgMole.setImageResource(R.drawable.mole1);
            }
        };
    }

    public void start(){
        if(state == 0){//もぐらが引っ込んでいる状態のとき
            state = 1;
            imgMole.setImageResource(R.drawable.mole2);

            //delayMillisミリ秒後に、Runnableの処理を実行する
            handler.postDelayed(hide, 1000);
        }
    }
    public int tapMole(){
        if(state == 1){//モグラが出ている状態のとき
           state = 2;
           imgMole.setImageResource(R.drawable.mole3);

           handler.removeCallbacks(hide); //start時のポストを消去
           handler.postDelayed(hide, 1000);
           return 1; //スコア1点を返す
        }
        return 0; //スコア0点を返す
    }
}
