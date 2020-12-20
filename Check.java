package com.example.publishinghouseluminecence;


import android.content.Context;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Check extends ToastAlert{
    public static boolean checkEmail(TextInputLayout text, Context l, String s){
        if(text.getEditText().getText().toString().matches("")){
            ToastAlert.ToastAlert(l,s);
            return false;
        }
        else {
            return true;
        }
    }
    public static boolean checkPassNick(TextInputLayout text, Context l, String empty, String shorty){
        if(!text.getEditText().getText().toString().matches("[a-zA-Z0-9]*")) {
            ToastAlert.ToastAlert(l,shorty);
            return false;
        }
        else if(text.getEditText().getText().toString().length()<6){
            ToastAlert.ToastAlert(l,empty);
            return false;
        }
        else {
            return true;
        }
    }
    public static boolean passRepPassword(TextInputLayout pass, TextInputLayout repPass, Context l, String s){
        if(!pass.getEditText().getText().toString().equals(repPass.getEditText().getText().toString())){
            ToastAlert.ToastAlert(l,s);
            return false;
        }
        else{
        return true;
        }
    }
    public static boolean signUpPhoneNumVerification(TextInputLayout phoneNum, Context l, String s){
        String regex = "[0-9]+";
        if(phoneNum.getEditText().getText().toString().length()!=10 || !phoneNum.getEditText().getText().toString().matches(regex)) {
            ToastAlert.ToastAlert(l, s);
            return false;
        }
        else{
            return true;
        }
    }
}
