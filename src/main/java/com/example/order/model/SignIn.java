package com.example.order.model;

public class SignIn {
    private static SignIn instance = null;

    private SignIn(){}

    public static SignIn getInstance() {

        if (instance== null)
            instance = new SignIn();
        return instance;
    }
}
