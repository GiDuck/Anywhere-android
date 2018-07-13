package org.androidtown.anywhere.header;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by user on 2017-07-17.
 */

public class CustomFontApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "야놀자 야체 Regular.ttf"))
                .addBold(Typekit.createFromAsset(this, "야놀자 야체 Bold.ttf"))
                .addCustom1(Typekit.createFromAsset(this, "sansation.regular.ttf"));


    }

}