package com.example.mychatdomain.mychatapplication;

public interface CallBackListener {
    public void onRegistrationDataPass(String email, String username, String password);
    public void onSignInDataPass(String email, String password);
}
