package com.sanjit.sisu2.ui.login_register_user;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sanjit.sisu2.MainActivity;
import com.sanjit.sisu2.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private TextView forgotPassword,register;
    private EditText loginemail, loginpassword;
    private Button login;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register= findViewById(R.id.CreateAccount);
        register.setOnClickListener(this);

        login=findViewById(R.id.login);
        login.setOnClickListener(this);

        loginemail=findViewById(R.id.loginemail);
        loginpassword=findViewById(R.id.loginpassword);

        progressBar=findViewById(R.id.loginprogressBar);
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        forgotPassword =findViewById(R.id.forgotpassword);
        forgotPassword.setOnClickListener(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(Login.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.CreateAccount:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.login:
                userLogin();
                break;
            case R.id.forgotpassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void userLogin() {
        String email=loginemail.getText().toString().trim();
        String password=loginpassword.getText().toString().trim();

        if(email.isEmpty()){
            loginemail.setError("Email is required");
            loginemail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            loginpassword.setError("Password is required");
            loginpassword.requestFocus();
            return;
        }
        if(password.length()<8){
            loginpassword.setError("Minimum length of password should be 8");
            loginpassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginemail.setError("Please provide valid email");
            loginemail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                        //redirect to user profile
                      Intent intent = new Intent(Login.this, MainActivity.class);
                      startActivity(intent);
                      finish();

                }else{
                    Toast.makeText(Login.this,"Failed to login! Please check your credentials",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1234){
//            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount account=task.getResult(ApiException.class);
//
//                AuthCredential credential= GoogleAuthProvider.getCredential(account.getIdToken(),null);
//                FirebaseAuth.getInstance().signInWithCredential(credential)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if(task.isSuccessful()){
//                                    isUser();
//                                }else{
//                                    Toast.makeText(Login.this,"Failed to login",Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//
//            } catch (ApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    private void isUser(){


}

}

