package com.soam;

import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.soam.adapter.NeedListAdapter;
import com.soam.model.Need;
import com.soam.model.User;
import com.soam.utils.Constants;
import com.soam.utils.Tools;
import com.soam.utils.widget.CircleTransform;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NeedListAdapter.OnNeedClickListener {

    public final static String TAG = HomeActivity.class.getName();

    NavigationView navigationView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton fab;

    NeedListAdapter adapter;

    String argPhone;
    Socket socket;
    Boolean isConnected;

    String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initComponents();
        setupToolbar();
        setupRecyclerView();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setupNavigationView();

       /* if (Tools.getAPIVerison() >= 5.0) {
            View toolbarShadow = (View) findViewById(R.id.toolbar_shadow);
            toolbarShadow.setVisibility(View.GONE);
        }*/
        Tools.systemBarLolipop(this);

        // argPhone = (String) getIntent().getStringExtra(SignInActivity.ARG_PHONE);
        // authToken = getIntent().getExtras().getString(AccountManager.KEY_AUTHTOKEN);

        socket = ((SoamApplication)getApplication()).getSocket();
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socket.on(Socket.EVENT_ERROR, onError);
        socket.on("unauthorized", onUnAuthorized);

        socket.connect();
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = new Intent(getApplicationContext(), INeedSomethingActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setupToolbar() {
        // toolbar.setTitleTextColor(0xFF32A75B);
        // toolbar.getBackground().setAlpha(0);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void setupNavigationView() {
        View navHeader = navigationView.inflateHeaderView(R.layout.nav_header_home);
        ImageView image = (ImageView) navHeader.findViewById(R.id.photo);
        TextView name = (TextView) navHeader.findViewById(R.id.name);
        TextView email = (TextView) navHeader.findViewById(R.id.email);
        TextView phone = (TextView) navHeader.findViewById(R.id.phone);

        User user = AuthPreferences.getUser(this);
        Log.d(TAG, user.toString());
        Picasso.with(this).load(user.getProfileImageUrl())
                .resize(250,250)
                .transform(new CircleTransform())
                .into(image);
        name.setText(user.getName());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());

    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NeedListAdapter(this, new ArrayList<Need>()); // Constants.getNeedsData(this));
        adapter.setOnNeedClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        socket.disconnect();
        socket.off(Socket.EVENT_CONNECT, onConnect);
        socket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socket.off(Socket.EVENT_ERROR, onError);
        socket.off("unauthorized", onUnAuthorized);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(View view, Need need, int position) {
        Intent intent = new Intent(this, NeedActivity.class);
        intent.putExtra(NeedActivity.EXTRA_OBJ_NEED, need);
        startActivity(intent);
    }


    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            isConnected = true;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), "Connected...", Toast.LENGTH_LONG)
                            .show();
                    isConnected = true;

                    // socket.emit("Welcome", argPhone);
                }
            });
        }
    };
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            isConnected = false;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), "Disconnected....", Toast.LENGTH_LONG)
                            .show();
                    isConnected = false;
                }
            });
        }
    };
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            isConnected = false;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), "Failed to connect...", Toast.LENGTH_LONG)
                            .show();
                    isConnected = false;
                }
            });
        }
    };
    private Emitter.Listener onError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };
    private Emitter.Listener onUnAuthorized = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = (String) args[0];

                    Toast.makeText(getBaseContext(), data, Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
    };
}
