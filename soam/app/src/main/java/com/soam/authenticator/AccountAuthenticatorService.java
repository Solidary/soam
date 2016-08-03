package com.soam.authenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import okhttp3.Authenticator;

/**
 * Created by maelfosso on 8/2/16.
 */
public class AccountAuthenticatorService extends Service {
    private static AccountAuthenticator AUTHENTICATOR = null;
    @Override
    public IBinder onBind(Intent intent) {
        // return intent.getAction().equals(ACTION_AUTHENTICATOR_INTENT) ? getAuthenticator().getIBinder() : null;

        return getAuthenticator().getIBinder();
    }

    private AccountAuthenticator getAuthenticator() {
        if (AUTHENTICATOR == null)
            AUTHENTICATOR = new AccountAuthenticator(this);
        return AUTHENTICATOR;
    }
}
