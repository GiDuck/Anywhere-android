package org.androidtown.anywhere.any_20_2_supplier_reservation_manager_detail;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.anywhere.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-11.
 */

public class ReservationManagerDetailMenuAdapter extends RecyclerView.Adapter<ReservationManagerDetailMenuViewHolder> {

    Activity activity;
    ArrayList<ReservationManagerDetailMenuData> datas;
    public ReservationManagerDetailMenuAdapter(Activity activity){
        this.activity = activity;
        datas = new ArrayList<>();
    }

    public void add(ReservationManagerDetailMenuData data){
        datas.add(data);
    }

    @Override
    public ReservationManagerDetailMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.anywhere20_2_reservation_manager_detail_menu,parent,false);

        return new ReservationManagerDetailMenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReservationManagerDetailMenuViewHolder holder, int position) {
        ReservationManagerDetailMenuData data = datas.get(position);
        holder.reservationMenu.setText(data.getReservationMenu());
        holder.reservationMenuNum.setText(data.getReservationMenuNum());
        holder.reservationMenuPrice.setText(data.getReservationMenuPrice()+"원");

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
