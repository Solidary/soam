package com.soam.authenticator;

import static com.soam.authenticator.AuthConstants.AUTH_TOKEN_TYPE;
import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.soam.SignInActivity;

/**
 * Created by maelfosso on 8/2/16.
 */
public class AccountAuthenticator extends AbstractAccountAuthenticator {
    public static final String TAG = AccountAuthenticator.class.getSimpleName();

    private final Context context;

    public AccountAuthenticator(Context context) {
        super(context);

        this.context = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    /*
   * The user has requested to add a new account to the system. We return an intent that will launch our login screen
   * if the user has not logged in yet, otherwise our activity will just pass the user's credentials on to the account
   * manager.
   */
    @Override
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String accountType,
                             String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Log.d(TAG, "addAccount()");
        final Intent intent = new Intent(context, SignInActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
        // intent.putExtra(SignInActivity.PARAM_TOKEN_TYPE, authTokenType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account,
                               String authTokenType, Bundle options) throws NetworkErrorException {
        Log.d(TAG, "getAuthToken() account="+account.name+ " type="+account.type);

        final Bundle bundle = new Bundle();

        // If the caller requested an authToken type we don't support, then
        // return an error
        if (!authTokenType.equals(AUTH_TOKEN_TYPE)) {
            Log.d(TAG, "Invalid AuthToken Type " + authTokenType);

            bundle.putString(AccountManager.KEY_ERROR_MESSAGE, "Invalid AuthToken Type");
            return bundle;
        }

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken
        final AccountManager accountManager = AccountManager.get(context);

        // Password is storing the refresh token
        final String password = accountManager.getPassword(account);
        if (password != null) {
            Log.d(TAG, "Trying to refresh access token");

            try {

            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Failed refreshing token. " + e.getMessage());
            }
        }

        // Otherwise... start the login intent
        final Intent intent = new Intent(context, SignInActivity.class);
        intent.putExtra(SignInActivity.PARAM_USERNAME, account.name);
        intent.putExtra(SignInActivity.PARAM_AUTH_TOKEN_TYPE, authTokenType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);

        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return authTokenType.equals(AUTH_TOKEN_TYPE) ? authTokenType : null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] features) throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
        return result;
    }
}
