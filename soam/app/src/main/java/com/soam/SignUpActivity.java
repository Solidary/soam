package com.soam;

import static com.soam.authenticator.AuthConstants.AUTH_TOKEN_TYPE;
import static com.soam.authenticator.AuthConstants.ACCOUNT_TYPE;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.soam.api.ApiRetrofit;
import com.soam.api.UserAuthenticationApi;
import com.soam.api.response.ApiError;
import com.soam.api.response.AuthResponse;
import com.soam.authenticator.AuthConstants;
import com.soam.model.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends AppCompatActivity
        implements View.OnClickListener {
    public static String TAG = SignUpActivity.class.getSimpleName();

    Toolbar toolbar;
    FloatingActionButton fab;

    EditText name;
    EditText email;
    EditText phone;
    EditText password;
    EditText confirmPassword;

    Button createAccount;

    AccountManager accountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        accountManager = AccountManager.get(this);

        initComponents();
        setupToolbar();
    }

    private void initComponents() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);

        createAccount = (Button) findViewById(R.id.create_account);
        createAccount.setOnClickListener(this);
    }

    private void setupToolbar() {
        toolbar.setTitleTextColor(0xFF32A75B);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_green_18dp);
        setSupportActionBar(toolbar);

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

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.create_account:
                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    Snackbar.make(view, "Password should be identic", Snackbar.LENGTH_LONG)
                            // .setAction("Action", null)
                            .show();

                    Log.d(TAG, password.getText().toString());
                    Log.d(TAG, confirmPassword.getText().toString());
                    return;
                }

                User user = new User();
                user.setName(name.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPhone(phone.getText().toString());
                user.setPassword(password.getText().toString());

                UserAuthenticationApi api =
                        ApiRetrofit.create(UserAuthenticationApi.class);
                Call<AuthResponse> call = api.signUp(user);

                Log.d(TAG, ApiRetrofit.SOAM_BASE_API_URL);
                call.enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {

                        if (response.isSuccessful()) {
                            Log.d(TAG, "Sign Up Successed");

                            AuthResponse authResponse = response.body();
                            User user = authResponse.getUser();
                            String token = authResponse.getToken();

                            Log.d(TAG, authResponse.getToken());
                            Log.d(TAG, authResponse.getUser().toString());

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

                            Log.e(TAG, "Sign Up Failed");
                            Log.e(TAG, error.getMessage());
                            Snackbar.make(view, error.getMessage(), Snackbar.LENGTH_LONG)
                                    // .setAction("Action", null)
                                    .show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });

                break;
        }
    }
}
