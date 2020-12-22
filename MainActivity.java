package com.example.publishinghouseluminecence;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.publishinghouseluminecence.Database.RegUsers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskedittext.MaskEditText;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference usersDBRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signInBnt = findViewById(R.id.idSignInBtn);
        Button signUpBtn = findViewById(R.id.idSignUpBtn);

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        usersDBRef = db.getReference("Users");

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignUpWindow();
            }
        });
        signInBnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignInWindow();
            }
        });
    }

    private void showSignInWindow() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        dialog.setTitle(R.string.SignInName);
        dialog.setMessage(R.string.alertDialogMessageIn);

        LayoutInflater inflater = LayoutInflater.from(this);
        final View signIn=inflater.inflate(R.layout.sign_in_window, null);
        dialog.setView(signIn);

        final TextInputLayout email = signIn.findViewById(R.id.idEmail);
        final TextInputLayout password = signIn.findViewById(R.id.idPassword);
        final MaskEditText phoneNum = signIn.findViewById((R.id.idPhoneNum));

        dialog.setNegativeButton(R.string.alertDialogNegButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton(R.string.SignInName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final boolean em = Check.checkEmail(email, getApplicationContext(), getResources().getString(R.string.wrongEmailSnackbar));
                final boolean pass = Check.checkPass(password, getApplicationContext(), getResources().getString(R.string.passContained), getResources().getString(R.string.emptyPass));
                boolean phone = Check.signUpPhoneNumVerification(phoneNum, getApplicationContext(), getResources().getString(R.string.wrongFormatPhone));
                if (em && pass && phone){
                    auth.signInWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                    DatabaseReference uidRef = rootRef.child("Users").child(uid);
                                    ValueEventListener valueEventListener = new ValueEventListener(){
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Map<String, Object> td = (HashMap<String,Object>) snapshot.getValue();
                                            String emailString = (String) td.get("email");
                                            String n = (String) td.get("nickName");
                                            String phoneBD = (String) td.get("phoneNum");
                                            if(phoneBD.equals(phoneNum.getRawText())) {
                                                Bundle e = new Bundle();
                                                e.putString("email", emailString);
                                                e.putString("key", n);
                                                e.putString("phone", phoneBD);
                                                Intent i = new Intent(MainActivity.this, MainPageActivity.class);
                                                i.putExtras(e);
                                                i.putExtras(e);
                                                i.putExtras(e);
                                                startActivity(i);
                                                finish();
                                            }
                                            else{
                                                ToastAlert.ToastAlert(getApplicationContext(), getResources().getString(R.string.phoneBDphoneEnterdNotMatch));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    };
                                    uidRef.addListenerForSingleValueEvent(valueEventListener);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ToastAlert.ToastAlert(getApplicationContext(), getResources().getString(R.string.signInProblem) + e.getMessage());
                        }
                    });
                }
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
        alert.getWindow().getAttributes();
        TextView textMessageSignIn = alert.findViewById(android.R.id.message);
        textMessageSignIn.setTextSize(20);
        Button cancelBtn = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        cancelBtn.setTextSize(20);
        Button signInBtn = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        signInBtn.setTextSize(20);
    }
    private void showSignUpWindow() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        dialog.setTitle(R.string.alertDialogCaptionReg);
        dialog.setMessage(R.string.alertDialogMessageReg);

        LayoutInflater inflater = LayoutInflater.from(this);
        View regist_window=inflater.inflate(R.layout.sign_up_window, null);
        dialog.setView(regist_window);

        final TextInputLayout email = regist_window.findViewById(R.id.idEmail);
        final TextInputLayout password = regist_window.findViewById(R.id.idPassword);
        final TextInputLayout repPassword = regist_window.findViewById(R.id.idRepPassword);
        final TextInputLayout nickName = regist_window.findViewById(R.id.idNickName);
        final MaskEditText phoneNum = regist_window.findViewById(R.id.idPhoneNum);

        dialog.setNegativeButton(R.string.alertDialogNegButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton(R.string.alertDialogPosButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Query usersQuery = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("nickName").equalTo(nickName.getEditText().getText().toString());
                boolean em=Check.checkEmail(email, getApplicationContext(), getResources().getString(R.string.wrongEmailSnackbar));
                final boolean pass = Check.checkPass(password, getApplicationContext(), getResources().getString(R.string.passContained), getResources().getString(R.string.emptyPass));
                boolean passRepPass = Check.checkPassRepPassword(password, repPassword, getApplicationContext(), getResources().getString(R.string.passNotEqRepSnackbar));
                boolean phone = Check.signUpPhoneNumVerification(phoneNum, getApplicationContext(), getResources().getString(R.string.wrongFormatPhone));
                boolean nick = Check.checkPassNick(nickName, getApplicationContext(), getResources().getString(R.string.emptyNick), getResources().getString(R.string.wrongNickNameSnackbar), usersQuery, getResources().getString(R.string.existedNick));
                if(em && pass && passRepPass && nick &&phone) {
                    auth.createUserWithEmailAndPassword(email.getEditText().getText().toString(), password.getEditText().getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    RegUsers user = new RegUsers();
                                    user.setEmail(email.getEditText().getText().toString());
                                    user.setPassword(password.getEditText().getText().toString());
                                    user.setNickName(nickName.getEditText().getText().toString());
                                    user.setPhoneNum(phoneNum.getRawText());
                                    usersDBRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            ToastAlert.ToastAlert(getApplicationContext(), getResources().getString(R.string.userRegisted));
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ToastAlert.ToastAlert(getApplicationContext(),getResources().getString(R.string.SignUpProblem)+e.getMessage());
                        }
                    });
                }
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
        alert.getWindow().getAttributes();
        TextView textMessageSignIn = alert.findViewById(android.R.id.message);
        textMessageSignIn.setTextSize(20);
        Button cancelBtn = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        cancelBtn.setTextSize(20);
        Button signInBtn = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        signInBtn.setTextSize(20);

    }
}