package com.tech.pentateuch.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tech.pentateuch.R;

public class LoginActivity extends AppCompatActivity {

    Button signUp;
    private  Button loginBtn;
    private EditText email;
    private EditText pass;
    private ImageButton imgBtnLogo;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signUp = (Button) findViewById(R.id.btn_sign);
        loginBtn = (Button)findViewById(R.id.btn_login);
        email = (EditText)findViewById(R.id.et_email);
        pass = (EditText)findViewById(R.id.et_pass);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setEnabled(false);
                loginUser();
            }
        });
        imgBtnLogo = (ImageButton)findViewById(R.id.img_btn_logo);
        imgBtnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.pentateuchtechnologies.com/"));
                startActivity(browserIntent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loginUser() {
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
        final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Logging in..");

        String emailId = email.getText().toString();
        String password = pass.getText().toString();
        if(TextUtils.isEmpty(emailId)&&TextUtils.isEmpty(password))
        {
            Toast.makeText(getApplicationContext(),"Email or Password should not empty",Toast.LENGTH_SHORT).show();
            loginBtn.setEnabled(true);
            return;
        }
        dialog.show();
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(emailId,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
                        }
                    });

                }
                else {
                    loginBtn.setEnabled(true);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
