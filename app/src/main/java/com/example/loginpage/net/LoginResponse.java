package com.example.loginpage.net;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("error_code")
    public int errorCode;

    @SerializedName("error_message")
    public String errorMessage;

    @SerializedName("login_status")
    public boolean loginStatus;
}
