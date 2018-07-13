package org.androidtown.anywhere.any_19_store_defalut_modify;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-15.
 */

public class StoreMenuModifyViewHolder extends RecyclerView.ViewHolder{
    TextView menu;
    TextView menuPrice;
    Button menu_remove;

    public StoreMenuModifyViewHolder(View item) {
        super(item);
        menu = (TextView) item.findViewById(R.id.menu);
        menuPrice = (TextView) item.findViewById(R.id.menuPrice);
        menu_remove = (Button) item.findViewById(R.id.menu_remove);
    }
}
