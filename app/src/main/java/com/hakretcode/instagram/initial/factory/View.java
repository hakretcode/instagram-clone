package com.hakretcode.instagram.initial.factory;

public interface View {
    void setFailure(String error);

    void setProgressVisibility(boolean buttonEnable, boolean progress);

    void next();
}
