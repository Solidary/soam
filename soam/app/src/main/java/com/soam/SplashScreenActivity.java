package com.soam;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.soam.api.ApiRetrofit;
import com.soam.api.UserAuthenticationApi;
import com.soam.api.response.AuthResponse;
import com.soam.authenticator.AuthConstants;
import com.soam.model.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {
    private final static String TAG = SplashScreenActivity.class.getName();

    AccountManager accountManager;
    Account account;
    String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /*Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(30000);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
//                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                    startActivity(intent);

                }
            }
        };
        timer.start();*/
        /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },
                3000
        );*/
        Log.d(TAG, "Before Check Account");
//        checkAccount();
        new AccountTask().execute("");
        Log.d(TAG, "After Check Account");
//        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void checkAccount() {
        accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType(AuthConstants.ACCOUNT_TYPE);
        if (accounts.length == 0) {
            Log.e(TAG, "No account of type " + AuthConstants.ACCOUNT_TYPE + " founds");


            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            /*accountManager.addAccount(
                    AuthConstants.ACCOUNT_TYPE,
                    AuthConstants.ACCOUNT_TYPE,
                    null,
                    new Bundle(),
                    this,
                    new OnAccountAddComplete(),
                    null
            );*/
//            return;
        } else {
            account = accounts[0];
            fetchAuthToken();
        }
    }

    private void fetchAuthToken() {
        Bundle options = new Bundle();
        AccountManagerFuture<Bundle> future = accountManager.getAuthToken(
            account,
                AuthConstants.AUTH_TOKEN_TYPE,
                options,
                this,
                null, // new OnAccountManagerComplete(),
                null //new Handler(new OnError())
        );

        Bundle result = null;
        try {
            result = future.getResult();
        } catch (Exception e) {
            // throw new AuthFailureError("Error while retrieving auth token", e);
            e.printStackTrace();
        }
        authToken = result.getString(AccountManager.KEY_AUTHTOKEN);
        Log.d(TAG, "FetchAuthToken " + authToken);

//        return authToken;
    }

    private class OnAccountManagerComplete implements AccountManagerCallback<Bundle> {

        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            Bundle bundle;

            try {
                bundle = result.getResult();
            } catch (OperationCanceledException oce) {
                oce.printStackTrace();
                return;
            } catch (AuthenticatorException ae) {
                ae.printStackTrace();
                return;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return;
            }

            authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
            Log.d(TAG + " main", "Received Authentication Token : " + authToken);

        }

    }

    private class OnAccountAddComplete implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            Bundle bundle;
            try {
                bundle = result.getResult();
            } catch (OperationCanceledException e) {
                e.printStackTrace();
                return;
            } catch (AuthenticatorException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            account = new Account(
                    bundle.getString(AccountManager.KEY_ACCOUNT_NAME),
                    bundle.getString(AccountManager.KEY_ACCOUNT_TYPE)
            );
            Log.d(TAG + " Main", "Added Account " + account.name + ", Fetching");
            fetchAuthToken();
        }
    }

    private class AccountTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            accountManager = AccountManager.get(getBaseContext());
            Account[] accounts = accountManager.getAccountsByType(AuthConstants.ACCOUNT_TYPE);
            if (accounts.length == 0) {
                Log.e(TAG, "No account of type " + AuthConstants.ACCOUNT_TYPE + " founds");

                return "No";
            } else {
                account = accounts[0];
                fetchAuthToken();


                return authToken;
            }
        }

        @Override
        protected void onPostExecute(final String s) {
//            super.onPostExecute(s);
            Log.d(TAG, "OnPostExecute - " + s);
            final Intent intent;
            if (s.equals("No")) {
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
                return;
            }


            UserAuthenticationApi userAuthenticationApi =
                    ApiRetrofit.create(UserAuthenticationApi.class, authToken);
            Call<User> call = userAuthenticationApi.me();


            intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
            /*intent.putExtra("AUTHTOKEN", s);*/

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                    if (response.isSuccessful()) {
                        User user = response.body();
                        Log.d(TAG, user.toString());
                        AuthPreferences.setToken(getBaseContext(), s);
                        AuthPreferences.setUser(getBaseContext(), user);

                        startActivity(intent);
                    } else {
                        Log.e(TAG, "1- OnResponse " + response.code());
                        /*try {
                            Log.e(TAG, "2- OnResponse " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/

                        Log.e(TAG, response.message());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t)
                {
                    Log.e(TAG, "OnFailure ");
                    t.printStackTrace();
                    Log.e(TAG, t.getMessage());
                }
            });
//            startActivity(intent);
            finish();
        }
    }
}
