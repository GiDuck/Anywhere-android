package org.androidtown.anywhere.any_10_store_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.androidtown.anywhere.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-06.
 */

public class StoreDetailFacilityAdapter extends RecyclerView.Adapter<StoreDetailFacilityViewHolder>{

    private ArrayList<StoreDetailFacilityData> datas;
    private Context context;

    public StoreDetailFacilityAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<>();
    }

    public void add(StoreDetailFacilityData data){
        datas.add(data);
        notifyDataSetChanged();
    }
    @Override
    public StoreDetailFacilityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.anywhere10_store_detail_facility,parent,false);

        return new StoreDetailFacilityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StoreDetailFacilityViewHolder holder, int position) {
        StoreDetailFacilityData data = datas.get(position);

        Glide.with(context).load(data.getFacilityIcon()).into(holder.storeFacilityIcon);
        holder.storeFacilityIconName.setText(data.getFacilityName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
