package com.example.publishinghouseluminecence;


import android.app.DownloadManager;
import android.content.Context;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.santalu.maskedittext.MaskEditText;

public class Check extends ToastAlert{
    public static boolean checkEmail(TextInputLayout text, Context l, String s){
        if(text.getEditText().getText().toString().matches("") || !text.getEditText().getText().toString().contains("@")){
            ToastAlert.ToastAlert(l,s);
            return false;
        }
        else {
            return true;
        }
    }
    public static boolean checkPassNick(TextInputLayout text, final Context l, String empty, String wrongWritten, Query query, final String existedNick){
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()!=0){
                    ToastAlert.ToastAlert(l,existedNick);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ToastAlert(l, error.getMessage());
            }
        });
        if(!text.getEditText().getText().toString().matches("([\\w-]+)")) {
            ToastAlert.ToastAlert(l,wrongWritten);
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
    public static boolean checkPassRepPassword(TextInputLayout pass, TextInputLayout repPass, Context l, String s){
        if(!pass.getEditText().getText().toString().equals(repPass.getEditText().getText().toString())){
            ToastAlert.ToastAlert(l,s);
            return false;
        }
        else{
        return true;
        }
    }
    public static boolean checkPass(TextInputLayout pass, Context l, String passRegex, String passEmpty){
        if(pass.getEditText().getText().toString().equals("")){
            ToastAlert.ToastAlert(l, passEmpty);
        }
        if(!pass.getEditText().getText().toString().matches("[a-zA-Z0-9]*")){
            ToastAlert.ToastAlert(l, passRegex);
            return false;
        }
        else{
            return true;
        }
    }
    public static boolean signUpPhoneNumVerification(MaskEditText phoneNum, Context l, String s){
        String regex = "[0-9]+";
        if(phoneNum.getRawText().length()!=10 || !phoneNum.getRawText().matches(regex)) {
            ToastAlert.ToastAlert(l, s);
            return false;
        }
        else{
            return true;
        }
    }
}
