package com.example.mychatdomain.mychatapplication;

//This interface is used to listen callbacks between Fragments and activities.

public interface CallBackListener {
    public void onRegistrationDataPass(String email, String username, String password);
    public void onSignInDataPass(String email, String password);
}
