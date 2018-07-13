package org.androidtown.anywhere.any_21_supplier_addplace1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_22_supplier_addplace2.AddPlaceSecond;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.httpcontrol.ImageURLParser;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-17.
 */

public class AddplaceFirstAdapter extends RecyclerView.Adapter<AddPlaceFirstViewHolder> implements AddPlaceFirstOnListItemClickListener {

    private Activity context;
    private ArrayList<StoreVO> items;
    private StoreVO item;

    public AddplaceFirstAdapter(Activity context) {


        this.context = context;


    }

    public void add(ArrayList<StoreVO> item) {
        this.items=item;
        notifyDataSetChanged();

    }


    @Override
    public AddPlaceFirstViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.anywhere21_addplace1_custom_view, parent, false);

        AddPlaceFirstViewHolder holder = new AddPlaceFirstViewHolder(v);
        holder.setAddPlace1_OnListItemClickListener(this);



        return holder;
    }

    @Override
    public void onBindViewHolder(AddPlaceFirstViewHolder holder, int position) {


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
        Intent intent = new Intent(context, AddPlaceSecond.class);
        intent.putExtra("store_num", items.get(position).getStore_num());
        context.startActivity(intent);

    }






}
