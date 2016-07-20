package com.soam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignInActivity extends AppCompatActivity
        implements View.OnClickListener {

    Toolbar toolbar;

    Button signIn;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initComponents();
        setupToolbar();
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        signIn = (Button) findViewById(R.id.sign_in);
        signUp = (TextView) findViewById(R.id.sign_up);

        signUp.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    private void setupToolbar() {
        toolbar.setTitleTextColor(0xFF32A75B);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_18dp);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View view) {
        Class c = null;
        switch (view.getId()) {
            case R.id.sign_in:
                c = HomeActivity.class;
                break;

            case R.id.sign_up:
                c = SignUpActivity.class;
                break;
        }

        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
