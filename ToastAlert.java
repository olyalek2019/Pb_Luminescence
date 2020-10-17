package com.example.publishinghouseluminecence;

import android.content.Context;
import android.widget.Toast;
public class ToastAlert {
    public static void ToastAlert(Context l, String s){
        Toast.makeText(l, s, Toast.LENGTH_LONG).show();
    }
}
