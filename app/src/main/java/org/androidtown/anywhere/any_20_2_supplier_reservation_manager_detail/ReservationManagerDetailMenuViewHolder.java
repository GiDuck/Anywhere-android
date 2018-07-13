package org.androidtown.anywhere.any_20_2_supplier_reservation_manager_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-11.
 */

public class ReservationManagerDetailMenuViewHolder extends RecyclerView.ViewHolder {

    TextView reservationMenu;
    TextView reservationMenuNum;
    TextView reservationMenuPrice;
    public ReservationManagerDetailMenuViewHolder(View item) {
        super(item);
        reservationMenu = (TextView) item.findViewById(R.id.reservationMenu);
        reservationMenuNum = (TextView) item.findViewById(R.id.reservationMenuNum);
        reservationMenuPrice = (TextView) item.findViewById(R.id.reservationMenuPrice);
    }
}
