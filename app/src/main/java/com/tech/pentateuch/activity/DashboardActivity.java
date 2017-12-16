package com.tech.pentateuch.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tech.pentateuch.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    @Override
    public void onBackPressed() {
        showCloseAnimation();
        super.onBackPressed();
    }

    private void showCloseAnimation() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                overridePendingTransition(R.anim.anim_fade_out_right,R.anim.anim_fade_in_left);
            }
        });
    }
}
