package org.androidtown.anywhere.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-03.
 */

public class HeaderLogin extends RelativeLayout{

    public HeaderLogin(Context context) {
        super(context);
        init(context);
    }

    public HeaderLogin(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.header_login,this,true);


    }


}
