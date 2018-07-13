package org.androidtown.anywhere.any_11_store_reservation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-11.
 */

public class StoreReservationMenuViewHolder extends RecyclerView.ViewHolder {

    TextView menuName;
    ImageView menuMinus;
    ImageView menuPlus;
    EditText menuNum;
    TextView menuPrice;

    public StoreReservationMenuViewHolder(View item) {
        super(item);

        menuName = (TextView) item.findViewById(R.id.menuName);
        menuMinus = (ImageView) item.findViewById(R.id.menuMinus);
        menuPlus = (ImageView) item.findViewById(R.id.menuPlus);
        menuNum = (EditText) item.findViewById(R.id.menuNum);
        menuPrice = (TextView) item.findViewById(R.id.menuPrice);


    }
}
