package org.androidtown.anywhere.any_17_supplier_main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-17.
 */

public class SupplierMainNaviBoard extends RelativeLayout {

    public SupplierMainNaviBoard(Context context) {
        super(context);
        init(context);
    }

    public SupplierMainNaviBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.anywhere17_supplier_main_naviboard,this,true);


    }

}
