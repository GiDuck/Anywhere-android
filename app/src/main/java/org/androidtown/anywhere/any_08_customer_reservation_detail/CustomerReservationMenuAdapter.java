package org.androidtown.anywhere.any_08_customer_reservation_detail;

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

public class CustomerReservationMenuAdapter extends RecyclerView.Adapter<CustomerReservationMenuViewHolder> {

    Activity activity;
    ArrayList<CustomerReservationMenuData> datas;
    public CustomerReservationMenuAdapter(Activity activity){
        this.activity = activity;
        datas = new ArrayList<>();
    }

    public void add(CustomerReservationMenuData data){
        datas.add(data);
    }

    @Override
    public CustomerReservationMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.anywhere08_customer_reservation_menu_list,parent,false);

        return new CustomerReservationMenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomerReservationMenuViewHolder holder, int position) {
        CustomerReservationMenuData data = datas.get(position);
        holder.reservationMenu.setText(data.getReservationMenu());
        holder.reservationMenuNum.setText(data.getReservationMenuNum());
        holder.reservationMenuPrice.setText(data.getReservationMenuPrice());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
