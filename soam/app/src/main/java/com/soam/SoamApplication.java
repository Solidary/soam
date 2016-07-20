package com.soam;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by maelfosso on 7/16/16.
 */
public class SoamApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("montserrat/Montserrat-Regular.otf")
                .setFontAttrId(R.attr.fontPath)

                .build()
        );
    }
}
