package com.dawoo.gamebox.util;

import android.os.CountDownTimer;
import android.widget.TextView;

public class MyCountDownTimer extends CountDownTimer {
    TextView timeButton;


    public MyCountDownTimer(TextView timeButton ,long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.timeButton = timeButton;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        //防止计时过程中重复点击
        timeButton.setClickable(false);
        timeButton.setText(millisUntilFinished/1000+"秒后重新获取");

    }

    @Override
    public void onFinish() {
        //重新给Button设置文字
        timeButton.setText("重新获取");
        //设置可点击
        timeButton.setClickable(true);
    }

    public void finish(){
        onFinish();
        cancel();
    }



}
