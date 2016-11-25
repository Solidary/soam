package com.soam;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.soam.adapter.PageAdapter;
import com.soam.fragment.ElementsFragment;
import com.soam.fragment.FragmentAdapter;
import com.soam.fragment.InitFragment;
import com.soam.fragment.PlacesFragment;
import com.soam.fragment.SummaryFragment;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class INeedSomethingActivity extends AppCompatActivity implements ElementsFragment.OnElementFragmentListener{
    public static final String TAG = INeedSomethingActivity.class.getSimpleName();

    Toolbar toolbar;
    Toolbar searchToolbar;

    ImageButton cancel;
    ImageButton ok;
    ImageButton next, previous;

    ViewPager pager;
    FragmentAdapter adapter;
    // PageAdapter adapter;

    Boolean isSearch = false;

    InitFragment f_init;
    ElementsFragment f_elements;
    PlacesFragment f_places;
    SummaryFragment f_summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ineed_something);

        initComponents();
        setupToolbar();
        setupViewPager();
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_viewpager);
        searchToolbar = (Toolbar) findViewById(R.id.toolbar_search);

        pager = (ViewPager) findViewById(R.id.pager);

        prepareActionBar(toolbar);
    }

    private void prepareActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        if (!isSearch) {
            setupToolbar();
        }
    }

    private void closeSearch() {
        if (isSearch) {
            isSearch = false;
            if (pager.getCurrentItem() == 0) {
                //f_message.mAdapter.getFilter().filter("");
            } else {
                //f_contact.mAdapter.getFilter().filter("");
            }
            prepareActionBar(toolbar);
            searchToolbar.setVisibility(View.GONE);
            supportInvalidateOptionsMenu();
        }
    }

    private void setupToolbar() {
        cancel = (ImageButton) toolbar.findViewById(R.id.cancel_action);
        ok = (ImageButton) toolbar.findViewById(R.id.ok_action);
        next = (ImageButton) toolbar.findViewById(R.id.next);
        previous = (ImageButton) toolbar.findViewById(R.id.previous);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ok clicked", Snackbar.LENGTH_SHORT).show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(pager.getCurrentItem() + 1);

                if (pager.getCurrentItem() == (adapter.getCount() - 1)) {
                    next.setVisibility(View.GONE);
                }
                if (pager.getCurrentItem() > 0) {
                    previous.setVisibility(View.VISIBLE);
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(pager.getCurrentItem() - 1);

                if (pager.getCurrentItem() == 0) {
                    previous.setVisibility(View.GONE);
                }
                if (pager.getCurrentItem() < (adapter.getCount() - 1)) {
                    next.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setupViewPager() {
        adapter = new FragmentAdapter(getSupportFragmentManager());

        if (f_init == null) {
            f_init = new InitFragment();
        }
        if (f_elements == null) {
            f_elements = new ElementsFragment();
        }
        if (f_places == null) {
            f_places = new PlacesFragment();
        }
        if (f_summary == null) {
            f_summary = new SummaryFragment();
        }

        adapter.addFragment(f_init, "");
        adapter.addFragment(f_elements, "");
        adapter.addFragment(f_places, "");
        adapter.addFragment(f_summary, "");

        pager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (isSearch) {
            inflater.inflate(R.menu.menu_search_toolbar , menu);

            final SearchView search = (SearchView)menu.findItem(R.id.action_search).getActionView();
            search.setIconified(false);
            search.setQueryHint("Search something....");
            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    f_elements.mAdapter.getFilter().filter(s);

                    return true;
                }
            });
            search.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    closeSearch();
                    return true;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    @Override
    public void onFabSearchClick() {
        isSearch = true;
        searchToolbar.setVisibility(View.VISIBLE);
        prepareActionBar(searchToolbar);
    }
}
