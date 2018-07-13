package org.androidtown.anywhere.any_23_0_supplier_appraiseManager_storeList;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_23_1_supplier_appraiseManager.AppraiseManager;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.httpcontrol.ImageURLParser;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-17.
 */

public class AppraiseManagerStoreListAdapter extends RecyclerView.Adapter<AppraiseManagerStoreListViewHolder> implements AppraiseManagerStoreList_OnListItemClickListener {

    private Context context;
    private ArrayList<StoreVO> items;
    private StoreVO item;

    public AppraiseManagerStoreListAdapter(Context context) {


        this.context = context;


    }

    public void add(ArrayList<StoreVO> item) {
        this.items=item;
        notifyDataSetChanged();

    }


    @Override
    public AppraiseManagerStoreListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.anywhere23_1_appraise_manager_custom_view, parent, false);

        AppraiseManagerStoreListViewHolder holder = new AppraiseManagerStoreListViewHolder(v);
        holder.setAddPlace1_OnListItemClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(AppraiseManagerStoreListViewHolder holder, int position) {


        item = items.get(position);

        holder.storeTitle.setText(item.getStore_name());
        holder.storeAddress.setText(item.getStore_addr());
        Glide.with(context).load(new ImageURLParser().imageURLParser(item.getStore_mainurl())).into(holder.storeImage);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(context, AppraiseManager.class);
        intent.putExtra("store_num", String.valueOf(items.get(position).getStore_num()));
        context.startActivity(intent);

    }


}
