package com.example.publishinghouseluminecence;


import android.content.Context;
import com.rengwuxian.materialedittext.MaterialEditText;

public class Check extends ToastAlert{
    public static boolean checkEmail(MaterialEditText text, Context l, String s){
        if(text.getText().toString().matches("")|| !text.getText().toString().contains("@")){
            ToastAlert.ToastAlert(l,s);
            return false;
        }
        else {
            return true;
        }
    }
    public static boolean checkPassNick(MaterialEditText text, Context l, String empty, String shorty){
        if(text.getText().toString().matches("")){
            ToastAlert.ToastAlert(l,empty);
            return false;
        }
        else if(text.getText().toString().length()<6){
            ToastAlert.ToastAlert(l,shorty);
            return false;
        }
        else {
            return true;
        }
    }
    public static boolean passRepPassword(MaterialEditText pass, MaterialEditText repPass, Context l, String s){
        if(!pass.getText().toString().equals(repPass.getText().toString())){
            ToastAlert.ToastAlert(l,s);
            return false;
        }
        return true;
    }
}
