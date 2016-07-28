package com.soam;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.soam.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class NeedActivity extends AppCompatActivity {

    public static String EXTRA_OBJ_NEED = "com.soam.NEED";

    TextView description;
    TextView amount;
    TextView place;
    TextView customer;
    TextView taxi;
    RecyclerView elements;

    Toolbar toolbar;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need);

        initComponents();
        setupToolbar();
        setupElements();
    }

    private void initComponents() {
        description = (TextView) findViewById(R.id.description);
        amount = (TextView) findViewById(R.id.amount);
        place = (TextView) findViewById(R.id.place);
        customer = (TextView) findViewById(R.id.customer);
        taxi = (TextView) findViewById(R.id.taxi);
        elements = (RecyclerView) findViewById(R.id.elements);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void setupToolbar() {
        // toolbar.setTitleTextColor(0xFF32A75B);
        // toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_18dp);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void setupElements() {
        elements.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
