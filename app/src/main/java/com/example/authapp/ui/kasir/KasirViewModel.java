package com.example.authapp.ui.kasir;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KasirViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public KasirViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}