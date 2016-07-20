package com.soam;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initComponents();
        setupToolbar();
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setupToolbar() {
        toolbar.setTitleTextColor(0xFF32A75B);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_18dp);
        setSupportActionBar(toolbar);
        // toolbar.setTitleMargin(0, 0, 0, 0);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
