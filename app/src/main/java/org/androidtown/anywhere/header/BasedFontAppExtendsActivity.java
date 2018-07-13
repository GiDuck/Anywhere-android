package org.androidtown.anywhere.header;

import android.app.Activity;
import android.content.Context;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by gdtbg on 2017-08-09.
 */

public class BasedFontAppExtendsActivity extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
