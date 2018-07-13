package org.androidtown.anywhere.any_02_main.MainStoreGroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_13_search_result.SearchResult;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-04.
 */

public class MainStoreGroupAdapter extends RecyclerView.Adapter<MainStoreGroupViewHolder>{

    ArrayList<MainStoreGroupData> datas;
    Context context;

    public MainStoreGroupAdapter(Context context){
        datas = new ArrayList<>();
        this.context = context;


    }
    //레이아웃 인플레이션
    @Override
    public MainStoreGroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.anywhere02_main_store_group,parent,false);
        return new MainStoreGroupViewHolder(v);
    }

    public void add(MainStoreGroupData data){
        datas.add(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final MainStoreGroupViewHolder holder, final int position) {
        MainStoreGroupData data = datas.get(position);
        holder.groupText.setText(data.getStoreGroupName());

        Glide.with(context).load(data.getStoreGroupURl()).into(holder.groupImage);
        holder.groupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchResult.class);
                intent.putExtra("searchByTag", datas.get(position).getStoreGroupName());
                context.startActivity(intent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return datas.size();
    }


}

