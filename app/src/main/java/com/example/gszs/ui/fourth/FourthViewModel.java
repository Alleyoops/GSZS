package com.example.gszs.ui.fourth;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FourthViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> name;

    public FourthViewModel() {
        name = new MutableLiveData<>();
    }

    public MutableLiveData<String> getName() {
        return name;
    }

}