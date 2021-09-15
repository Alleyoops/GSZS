package com.example.gszs.ui.home;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("不要管我，我是用来test的,并且我是可以滚动的哦（for viewModel）");
    }

    public LiveData<String> getText() {
        return mText;
    }
}