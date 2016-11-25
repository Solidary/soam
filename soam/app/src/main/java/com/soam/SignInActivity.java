package com.soam;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.soam.api.ApiRetrofit;
import com.soam.api.UserAuthenticationApi;
import com.soam.api.response.ApiError;
import com.soam.api.response.AuthResponse;
import com.soam.model.User;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.soam.authenticator.AuthConstants.ACCOUNT_TYPE;
import static com.soam.authenticator.AuthConstants.AUTH_TOKEN_TYPE;

public class SignInActivity extends AppCompatActivity
        implements View.OnClickListener {

    public final static String TAG = SignInActivity.class.getSimpleName();
    public final static String PARAM_USERNAME = "username";
    public final static String PARAM_AUTH_TOKEN_TYPE = "auth_token_type";

    public final static String ARG_PHONE = "com.soam.signin.ARG_PHONE";
    public final static String ARG_PASSWORD = "com.soam.signin.ARG_PASSWORD";

    Toolbar toolbar;
    CoordinatorLayout main;

    TextView email;
    TextView password;
    Button signIn;
    TextView signUp;

    Socket socket;

    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        socket = ((SoamApplication) getApplication()).getSocket();

        initComponents();
        setupToolbar();
    }

    private void initComponents() {
        main = (CoordinatorLayout) findViewById(R.id.main_content);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        email = (TextView) findViewById(R.id.email);
        password = (TextView) findViewById(R.id.password);
        signIn = (Button) findViewById(R.id.sign_in);
        signUp = (TextView) findViewById(R.id.sign_up);

        signUp.setOnClickListener(this);
        signIn.setOnClickListener(this);

        // socket.on("SignIn", onSignIn);
    }

    private void setupToolbar() {
        toolbar.setTitle("Sign In");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        socket.off("SignIn", onSignIn);
    }

    @Override
    public void onClick(View view) {
        Class c = null;
        switch (view.getId()) {
            case R.id.sign_in:
                signIn();

                break;

            case R.id.sign_up:
                signUp();
                break;
        }


    }

    private void signUp() {
        Class c;
        c = SignUpActivity.class;
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    private void signIn() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(ARG_PHONE, email.getText().toString());
        intent.putExtra(ARG_PASSWORD, password.getText());

        UserAuthenticationApi api =
                ApiRetrofit.create(UserAuthenticationApi.class);
        Call<AuthResponse> call =
                api.signIn(email.getText().toString(), password.getText().toString());

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                if (response.isSuccessful()) {
                    AuthResponse authResponse = response.body();
                    User user = authResponse.getUser();
                    String token = authResponse.getToken();

                    Account account = new Account(user.getPhone(), ACCOUNT_TYPE);
                    accountManager.addAccountExplicitly(account, token, null);
                    accountManager.setAuthToken(account, AUTH_TOKEN_TYPE, token);

                    AuthPreferences.setUser(getBaseContext(), user);
                    AuthPreferences.setToken(getBaseContext(), token);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    ApiError error = ApiRetrofit.parse(response);

                    Log.e(TAG, "Sign In Failed");
                    Log.e(TAG, error.getMessage());
                    Snackbar.make(main, error.getMessage(), Snackbar.LENGTH_LONG)
                            // .setAction("Action", null)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

}
