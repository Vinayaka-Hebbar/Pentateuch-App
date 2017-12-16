package com.tech.pentateuch.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tech.pentateuch.R;

public class RegisterActivity extends AppCompatActivity {

    private Button cancelBtn;
    private Button submitBtn;
    private CheckBox checkBoxAccept;
    private FirebaseAuth auth;
    private EditText email,pass,confPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        submitBtn = (Button)findViewById(R.id.btn_submit);
        email = (EditText)findViewById(R.id.et_email_reg);
        pass = (EditText)findViewById(R.id.et_pass_reg);
        confPass = (EditText)findViewById(R.id.et_pass_conf_reg);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBtn.setEnabled(false);
                signUp();
            }
        });
        checkBoxAccept = (CheckBox)findViewById(R.id.cb_accept);
        checkBoxAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    submitBtn.setEnabled(checkBoxAccept.isChecked());
            }
        });
        cancelBtn = (Button)findViewById(R.id.btn_cancel_reg);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void signUp() {
        String emailId = email.getText().toString();
        String password = pass.getText().toString();
        String confPassword = confPass.getText().toString();
        if(TextUtils.isEmpty(emailId) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confPassword))
            return;
        if(!password.equals(confPassword))
        {
            Toast.makeText(getApplicationContext(),"Password not matched",Toast.LENGTH_SHORT).show();
            return;
        }
        auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(emailId,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Intent intent = new Intent(RegisterActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    submitBtn.setEnabled(true);
                    Toast.makeText(getApplicationContext(),"Failed to SignUp\n" + task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
