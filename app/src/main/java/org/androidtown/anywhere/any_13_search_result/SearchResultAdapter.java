package org.androidtown.anywhere.any_13_search_result;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_10_store_detail.StoreDetail;
import org.androidtown.anywhere.httpcontrol.ImageURLParser;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by user on 2017-07-17.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultViewHolder>{

    private Context context;
    private ArrayList<SearchResult.TempVO> initTempVO;
    SharedPreferences shef;
    String session_id;
    String user_nick;
    DecimalFormat form = new DecimalFormat("#.##");

    public SearchResultAdapter(ArrayList<SearchResult.TempVO> initTempVO, Context context){

        this.initTempVO = initTempVO;
        this.context = context;
        Log.d("initTempVO",initTempVO.size()+"");
        shef = context.getSharedPreferences("anywhere", Context.MODE_PRIVATE);
        user_nick=shef.getString("user_nick", "");
        session_id=shef.getString("user_email", "");

    }



    @Override
    public SearchResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.anywhere13_searchresult_custom_view, parent, false);

        SearchResultViewHolder holder = new SearchResultViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SearchResultViewHolder holder, int position) {

        final SearchResult.TempVO item = initTempVO.get(position);

        Glide.with(context).load(ImageURLParser.imageURLParser(item.tempStoreVO.getStore_mainurl())).into(holder.storePhotoUri);

        holder.storeName.setText(item.tempStoreVO.getStore_name());
        holder.storeAddress.setText(item.tempStoreVO.getStore_addr());
        holder.minimumPeople.setText(String.valueOf(item.tempStoreVO.getStore_min())+"명");
        holder.freePrice.setText("1인/"+String.valueOf(item.tempMenuVO.getMenu_price())+"원");

        if(item.tempDistance!=0) {
            holder.store_distance.setText(String.valueOf(form.format(item.tempDistance / 1000)) + "km");
        }else{
            holder.store_distance.setVisibility(View.GONE);
        }

        holder.starRank.setRating(item.tempStoreReplyVO.getReply_staravg().floatValue());
        float rating = item.tempStoreReplyVO.getReply_staravg().floatValue();
        holder.ratingCount.setText(String.valueOf(String.format("%.1f", rating)));
        holder.favoriteCount.setText(String.valueOf(item.tempLikeVO.getLike_count()));
        holder.commentCount.setText(String.valueOf(item.tempStoreReplyVO.getReply_starcount()));
        holder.storePhotoUri.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if(action==MotionEvent.ACTION_DOWN){


                }else if(action==MotionEvent.ACTION_UP){

                    Intent intent = new Intent(context, StoreDetail.class);
                    intent.putExtra("store_num",item.tempStoreVO.getStore_num());
                    intent.putExtra("member_nick",user_nick);
                    context.startActivity(intent);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {

        return initTempVO.size();
    }

}