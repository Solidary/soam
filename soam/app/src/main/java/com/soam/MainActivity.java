package com.soam;

import com.soam.adapter.PageAdapter;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.soam.fragment.WizardFragment;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    ViewPager pager;
    Button signUp;
    TextView signIn;
    View first, second, third;

    PageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wizard);

        initComponents();
        setupViewPager();
    }

    private void initComponents() {
        pager = (ViewPager) findViewById(R.id.pager);
        signUp = (Button) findViewById(R.id.sign_up);
        signIn = (TextView) findViewById(R.id.sign_in);

        first = (View) findViewById(R.id.first_indicator);
        second = (View) findViewById(R.id.second_indicator);
        third = (View) findViewById(R.id.third_indicator);

        signUp.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    private void setupViewPager() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(WizardFragment.newInstance("@drawable/post_run",
                getResources().getString(R.string.post_needs),
                getResources().getString(R.string.post_needs_description)));
        fragments.add(WizardFragment.newInstance("@drawable/post_run",
                getResources().getString(R.string.make_run),
                getResources().getString(R.string.make_run_description)));
        fragments.add(WizardFragment.newInstance("@drawable/post_run",
                getResources().getString(R.string.win_money),
                getResources().getString(R.string.win_money_description)));

        adapter = new PageAdapter(this.getBaseContext(), fragments, getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOnPageChangeListener(new WizardPageListener());
        updateWizardIndicators(0);
    }

    public void updateWizardIndicators(int position) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int resizeWidth = (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 20, metrics);
        int defaultWidth = (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 5, metrics);
        switch (position) {
            case 0:
                first.getLayoutParams().width = resizeWidth;
                first.setBackgroundResource(R.drawable.wizard_indicator_background_on);
                first.requestLayout();

                second.getLayoutParams().width = defaultWidth;
                second.setBackgroundResource(R.drawable.wizard_indicator_background_off);
                second.requestLayout();

                third.getLayoutParams().width = defaultWidth;
                third.setBackgroundResource(R.drawable.wizard_indicator_background_off);
                third.requestLayout();

                break;
            case 1:
                first.getLayoutParams().width = defaultWidth;
                first.setBackgroundResource(R.drawable.wizard_indicator_background_off);
                first.requestLayout();

                second.getLayoutParams().width = resizeWidth;
                second.setBackgroundResource(R.drawable.wizard_indicator_background_on);
                second.requestLayout();

                third.getLayoutParams().width = defaultWidth;
                third.setBackgroundResource(R.drawable.wizard_indicator_background_off);
                third.requestLayout();

                break;
            case 2:
                first.getLayoutParams().width = defaultWidth;
                first.setBackgroundResource(R.drawable.wizard_indicator_background_off);
                first.requestLayout();

                second.getLayoutParams().width = defaultWidth;
                second.setBackgroundResource(R.drawable.wizard_indicator_background_off);
                second.requestLayout();

                third.getLayoutParams().width = resizeWidth;
                third.setBackgroundResource(R.drawable.wizard_indicator_background_on);
                third.requestLayout();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    @Override
    public void onClick(View view) {
        Class c = null;
        switch (view.getId()) {
            case R.id.sign_up:
                c = SignUpActivity.class;

                break;
            case R.id.sign_in:
                c = SignInActivity.class;

                break;
        }

        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    
    public class WizardPageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

        @Override
        public void onPageSelected(int position) {
            updateWizardIndicators(position);
        }
    }
}
