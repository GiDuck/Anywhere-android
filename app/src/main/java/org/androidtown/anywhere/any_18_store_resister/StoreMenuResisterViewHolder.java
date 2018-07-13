package org.androidtown.anywhere.any_18_store_resister;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-15.
 */

public class StoreMenuResisterViewHolder extends RecyclerView.ViewHolder{
    TextView menu;
    TextView menuPrice;
    Button menuRemove;

    public StoreMenuResisterViewHolder(View item) {
        super(item);
        menu = (TextView) item.findViewById(R.id.menu);
        menuPrice = (TextView) item.findViewById(R.id.menuPrice);
        menuRemove = (Button) item.findViewById(R.id.menuRemove);
    }
}
