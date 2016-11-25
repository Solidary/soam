package com.soam;

import android.app.Application;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by maelfosso on 7/16/16.
 */
public class SoamApplication extends Application {

    private Socket socket;
//    {
//        try {
//            IO.Options opts = new IO.Options();+
//            opts.forceNew = true;
//            opts.query = "token=" + AuthPreferences.getToken(this);
//
//            socket = IO.socket("http://192.168.43.113:3000", opts);
////            socket = IO.socket("http://192.168.43.113:3000");
//        }catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("montserrat/Montserrat-Regular.otf")
                .setFontAttrId(R.attr.fontPath)

                .build()
        );
    }

    public Socket getSocket() {
        if (socket == null) {
            try {
                IO.Options opts = new IO.Options();
                opts.forceNew = true;
                opts.query = "token=" + AuthPreferences.getToken(this);

                socket = IO.socket("http://192.168.43.113:3000", opts);
//            socket = IO.socket("http://192.168.43.113:3000");
            }catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        return socket;
    }
}
