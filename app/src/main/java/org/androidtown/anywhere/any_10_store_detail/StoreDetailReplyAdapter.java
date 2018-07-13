package org.androidtown.anywhere.any_10_store_detail;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.StoreReplyVO;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;

/**
 * Created by user on 2017-07-10.
 */

public class StoreDetailReplyAdapter extends RecyclerView.Adapter<StoreDetailReplyViewHolder> {

    private ArrayList<StoreReplyVO> replyList;
    private ArrayList<StoreDetailReplyData> datas;
    private Context context;
    String user_nick;
    int store_num;

    public StoreDetailReplyAdapter(Context context, String user_nick) {
        this.context = context;
        datas = new ArrayList<>();
        this.user_nick = user_nick;
    }

    public void add(StoreDetailReplyData data) {
        datas.add(data);
        notifyDataSetChanged();
    }

    @Override
    public StoreDetailReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.anywhere10_store_detail_reply_list, parent, false);

        return new StoreDetailReplyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StoreDetailReplyViewHolder holder, final int position) {
        StoreDetailReplyData data = datas.get(position);

        if (replyList.get(position).getReply_lev() == 0) { //원댓일때
            holder.replyMarker.setVisibility(View.GONE);
            holder.replyNick.setText(data.getReplyNick());
            holder.replyStar.setRating(data.getReplyStar());
            holder.replyDate.setText(data.getReplyDate());
            holder.replyContent.setText(data.getReplyContent());


            if (data.getReplyNick().equals(user_nick)) {

                holder.deleteReply.setVisibility(View.VISIBLE);
                holder.deleteReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        makeDeleteDialog(position);
                    }
                });

            }
        } else { //대댓일때


            holder.replyMarker.setVisibility(View.VISIBLE);
            holder.replyNick.setText(data.getReplyNick());
            holder.replyStar.setVisibility(View.GONE);
            holder.replyDate.setText(data.getReplyDate());
            holder.replyContent.setText(data.getReplyContent());


            if (data.getReplyNick().equals(user_nick)) {

                holder.deleteReply.setVisibility(View.VISIBLE);
                holder.deleteReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        makeDeleteDialog(position);
                    }
                });

            }


        }
    }

    public void deleteReply(final int position) {

        HashMap<String, String> map = new HashMap<>();
        map.put("store_num", String.valueOf(replyList.get(position).getStore_num()));
        map.put("reply_ref", String.valueOf(replyList.get(position).getReply_ref()));
        map.put("reply_seq", String.valueOf(replyList.get(position).getReply_seq()));
        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createRetrofitObject();
        CustomerController apiService = hrs.createApiserverObject();
        Call call = apiService.deleteFeedBack(map);
        hrs.HttpRequestExecute(call);
        String result = hrs.parsingStringFunc("result");

        if (result.equals("true")) {

            Toast.makeText(context, "후기 삭제를 완료 하였습니다.", Toast.LENGTH_SHORT).show();
            datas.remove(position);
            notifyDataSetChanged();


        } else if (result.equals("false")) {

            Toast.makeText(context, "후기 삭제 실패하였습니다.", Toast.LENGTH_SHORT).show();
        }


    }


    public void makeDeleteDialog(final int position) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("후기를 삭제하시겠습니까?");
        alertDialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteReply(position);
            }
        });

        alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.setTitle("후기 삭제");
        alert.show();


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public ArrayList<StoreReplyVO> getReplyList() {
        return replyList;
    }

    public void setReplyList(ArrayList<StoreReplyVO> replyList) {
        this.replyList = replyList;
    }
}
