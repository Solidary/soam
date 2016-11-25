package com.soam.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.soam.model.Element;
import com.soam.model.Need;
import com.soam.model.Place;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by maelfosso on 7/17/16.
 */
public class Constants {

    public static Resources getStrRes(Context context){
        return context.getResources();
    }

    public static String formatTime(long time){
        // income time
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(time);

        // current time
        Calendar curDate = Calendar.getInstance();
        curDate.setTimeInMillis(System.currentTimeMillis());

        SimpleDateFormat dateFormat = null;
        if(date.get(Calendar.YEAR)==curDate.get(Calendar.YEAR)){
            if(date.get(Calendar.DAY_OF_YEAR) == curDate.get(Calendar.DAY_OF_YEAR) ){
                dateFormat = new SimpleDateFormat("h:mm a", Locale.US);
            }
            else{
                dateFormat = new SimpleDateFormat("MMM d", Locale.US);
            }
        }
        else{
            dateFormat = new SimpleDateFormat("MMM yyyy", Locale.US);
        }
        return dateFormat.format(time);
    }


    public static float getAPIVerison() {

        Float f = null;
        try {
            StringBuilder strBuild = new StringBuilder();
            strBuild.append(android.os.Build.VERSION.RELEASE.substring(0, 2));
            f = new Float(strBuild.toString());
        } catch (NumberFormatException e) {
            Log.e("", "erro ao recuperar a versão da API" + e.getMessage());
        }

        return f.floatValue();
    }

    public static String fromSecondToHM(int seconds) {
        long h = TimeUnit.MILLISECONDS.toHours(new Long(seconds));
        long m = TimeUnit.MILLISECONDS.toMinutes(new Long(seconds));
        long s = TimeUnit.MILLISECONDS.toSeconds(new Long(seconds));

        return String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(new Long(seconds)),
                TimeUnit.MILLISECONDS.toSeconds(new Long(seconds)) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(new Long(seconds))));
    }

    public static String loadJSONFromAsset(Context ctx, String fjson) {
        String json = null;

        try {
            InputStream is = ctx.getAssets().open(fjson);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

    public static List<Need> getNeedsData(Context ctx) {
        String ISO_FORMAT = "dd-MM-yyyy'T'HH:mm";

        Type NeedCollectionType = new TypeToken<List<Need>>(){}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(ISO_FORMAT);
        Gson gson = gsonBuilder.create();
        List<Need> items = gson.fromJson(loadJSONFromAsset(ctx, "needs.json"), NeedCollectionType);

        return items;
    }

    public static List<Place> getPlacesData(Context ctx) {
        String ISO_FORMAT = "dd-MM-yyyy'T'HH:mm";

        Type NeedCollectionType = new TypeToken<List<Place>>(){}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(ISO_FORMAT);
        Gson gson = gsonBuilder.create();
        List<Place> items = gson.fromJson(loadJSONFromAsset(ctx, "places.json"), NeedCollectionType);

        return items;
    }

    public static List<Element> getElementsData(Context ctx) {
        String ISO_FORMAT = "dd-MM-yyyy'T'HH:mm";

        Type NeedCollectionType = new TypeToken<List<Element>>(){}.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(ISO_FORMAT);
        Gson gson = gsonBuilder.create();
        List<Element> items = gson.fromJson(loadJSONFromAsset(ctx, "elements.json"), NeedCollectionType);

        return items;
    }

}
