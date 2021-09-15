package com.example.gszs.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationsViewModel extends ViewModel {

    //声明
    private MutableLiveData<String> mText;
    private MutableLiveData<Integer> num;
    public NotificationsViewModel() {
        //实例化
        mText = new MutableLiveData<>();
        num = new MutableLiveData<>();
        //更新数据
        mText.setValue("This is notifications fragment1");
        num.setValue(288);
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<Integer> getInteger() {
        return num;
    }



    private Timer timer;
    private int currentSecond;

    /**
     * 开始计时
     * */
    public void startTiming()
    {
        if (timer == null)
        {
            currentSecond = 0;
            timer = new Timer();
            TimerTask timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    currentSecond++;
                    if(onTimeChangeListener != null)
                    {
                        onTimeChangeListener.onTimeChanged(currentSecond);
                    }
                }
            };
            timer.schedule(timerTask, 1000, 1000);//延迟3秒执行
        }
    }

    /**
     * 通过接口的方式，完成对调用者的通知，这种方式不是太好，更好的方式是通过LiveData组件来实现
     * */
    public interface OnTimeChangeListener
    {
        void onTimeChanged(int second);
    }

    private OnTimeChangeListener onTimeChangeListener;

    public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener)
    {
        this.onTimeChangeListener = onTimeChangeListener;

    }




}