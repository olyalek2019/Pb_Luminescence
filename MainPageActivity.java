package com.example.publishinghouseluminecence;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainPageActivity extends AppCompatActivity {
    TextView userName;
    Button startBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userName = findViewById(R.id.idUserName);
        startBtn = findViewById(R.id.idStartButton);

        Bundle b = getIntent().getExtras();
        final String nick = b.getString("key");
        final String email_str = b.getString("email");
        userName.setText(nick);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle e = new Bundle();
                e.putString("email", email_str);
                e.putString("nickName", nick);
                Intent i = new Intent(MainPageActivity.this, DrawerActivity.class);
                i.putExtras(e);
                i.putExtras(e);
                startActivity(i);
                finish();
            }
        });
    }
}