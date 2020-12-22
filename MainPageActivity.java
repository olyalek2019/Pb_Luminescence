package com.example.publishinghouseluminecence;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

public class MainPageActivity extends AppCompatActivity {
    private EditText editTextMobile;
    private String verificationId;
    private FirebaseAuth mAuth;
    private String email_str;
    private String nick;
    private ProgressBar rollingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView userName = findViewById(R.id.idUserName);
        Button startBtn = findViewById(R.id.idStartButton);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        nick = bundle.getString("key");
        email_str = bundle.getString("email");
        final String phoneNum = bundle.getString("phone");
        userName.setText(String.format("Dear %s,", nick));

        mAuth = FirebaseAuth.getInstance();
        rollingBar = findViewById(R.id.idProgressBar);
        editTextMobile = findViewById(R.id.editTextCode);

        sendVerificationCode(phoneNum);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = editTextMobile.getText().toString().trim();
                if(code.isEmpty() || code.length()<6){
                    editTextMobile.setError("Enter code");
                    editTextMobile.requestFocus();
                    return;
                }
                verifyVerificationCode(code);
            }
        });
    }
    private void verifyVerificationCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Bundle e = new Bundle();
                    e.putString("email", email_str);
                    e.putString("nickName", nick);
                    Intent i = new Intent(MainPageActivity.this, DrawerActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtras(e);
                    i.putExtras(e);
                    startActivity(i);
                }
                else {
                    ToastAlert.ToastAlert(getApplicationContext(), getResources().getString(R.string.codeProblem));
                }
            }
        });
    }

    private void sendVerificationCode(String mobile) {
        rollingBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+38" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editTextMobile.setText(code);
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            ToastAlert.ToastAlert(MainPageActivity.this, e.getMessage());
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }
    };

}