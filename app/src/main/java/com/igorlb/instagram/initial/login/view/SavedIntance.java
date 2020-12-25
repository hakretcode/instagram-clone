package com.igorlb.instagram.initial.login.view;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavedIntance extends ViewModel {
    public MutableLiveData<Bundle> mutable = new MutableLiveData<>();

    public void setValueForRestore(Bundle text) {
        mutable.setValue(text);
    }

    public Bundle getBundle() {
        return mutable.getValue();
    }

}
