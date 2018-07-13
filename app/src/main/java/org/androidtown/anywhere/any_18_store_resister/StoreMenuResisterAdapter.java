package org.androidtown.anywhere.any_18_store_resister;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.anywhere.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-15.
 */

public class StoreMenuResisterAdapter extends RecyclerView.Adapter<StoreMenuResisterViewHolder> {

    ArrayList<StoreMenuResisterData> datas;
    Context context;
    int position;
    StoreMenuResisterViewHolder holder;

    public StoreMenuResisterAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void add(StoreMenuResisterData data) {
        datas.add(data);
        notifyDataSetChanged();
    }


    @Override
    public StoreMenuResisterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_menu_resister_list, parent, false);
        return new StoreMenuResisterViewHolder(v);
    }


    @Override
    public void onBindViewHolder(StoreMenuResisterViewHolder holder, final int position) {
        Log.d("data", "onBindViewHolder");
        this.holder = holder;
        this.position = position;
        StoreMenuResisterData data = datas.get(position);
        Log.d("data", datas.get(position).toString());

        holder.menu.setText(data.getMenu());
        holder.menuPrice.setText(data.getMenuPrice()+"원");

        holder.menuRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datas.remove(position);
                notifyDataSetChanged();

            }
        });

    }




    @Override
    public int getItemCount() {
        return datas.size();
    }

    public ArrayList<StoreMenuResisterData> getDatas() {
        return datas;
    }
}
