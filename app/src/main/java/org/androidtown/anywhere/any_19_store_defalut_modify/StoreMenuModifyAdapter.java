package org.androidtown.anywhere.any_19_store_defalut_modify;

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

public class StoreMenuModifyAdapter extends RecyclerView.Adapter<StoreMenuModifyViewHolder>{

    ArrayList<StoreMenuModifyData> datas;
    Context context;
    int position;
    StoreMenuModifyViewHolder holder;

    public StoreMenuModifyAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void add(StoreMenuModifyData data){
        datas.add(data);
        Log.d("giduckTest3", data.getMenu());
        Log.d("giduckTest3", data.getMenuPrice());
        notifyDataSetChanged();

    }
    @Override
    public StoreMenuModifyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_menu_modify_list,parent,false);
        return new StoreMenuModifyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(StoreMenuModifyViewHolder holder, final int position) {
        Log.d("onBindViewHolder","onBindViewHolder");
        this.holder = holder;
        this.position = position;
        StoreMenuModifyData data = datas.get(position);
        Log.d("onBindViewHolder",datas.get(position).getMenu());

        holder.menu.setText(data.getMenu());
        holder.menuPrice.setText(data.getMenuPrice()+"원");
        holder.menu_remove.setOnClickListener(new View.OnClickListener() {
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

    public ArrayList<StoreMenuModifyData> getDatas() {
        return datas;
    }

}
