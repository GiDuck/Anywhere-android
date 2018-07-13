package org.androidtown.anywhere.any_23_1_supplier_appraiseManager;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.StoreReplyVO;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.SupplierController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;


/**
 * Created by user on 2017-07-17.
 */

public class AppraiseManagerAdapter extends RecyclerView.Adapter<AppraiseManagerViewHolder> {

    private Activity activity;
    private ArrayList<StoreReplyVO> items;
    private StoreReplyVO item;
    String store_num;
    String user_nick;


    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM/dd");

    public AppraiseManagerAdapter(ArrayList<StoreReplyVO> items, String store_num, Activity activity) {
        this.activity = activity;
        this.items = items;
        this.store_num = store_num;

        SharedPreferences shef = activity.getSharedPreferences("anywhere", Context.MODE_PRIVATE);
        user_nick = shef.getString("user_nick", "");

    }



    @Override
    public AppraiseManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.anywhere23_appraise_manager_custom_view, parent, false);

        AppraiseManagerViewHolder holder = new AppraiseManagerViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(final AppraiseManagerViewHolder holder, final int position) {


        item = items.get(position);

        holder.nickname.setText(item.getReply_nick());
        holder.comment_content.setText(item.getReply_content());
        holder.comment_date.setText(simpleDate.format(item.getReply_date()));

        if (item.getReply_lev() == 0) { //lev가 0일때 (원댓일때)
            holder.appraiseManager_reply_marker.setVisibility(View.GONE);

            holder.comment_rating_bar.setVisibility(View.VISIBLE);
            holder.comment_rating_bar.setRating(item.getReply_star().floatValue());

            holder.deleteReplySupplier.setVisibility(View.GONE);

            holder.appendReplySupplier.setVisibility(View.VISIBLE);
            holder.appendReplySupplier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    replyDialog(position);
                }

            });


        } else if(item.getReply_lev() == 1){ //lev가 0이 아닐때 (대댓일때)


            holder.appraiseManager_reply_marker.setVisibility(View.VISIBLE);
            holder.comment_rating_bar.setVisibility(View.GONE);
            holder.deleteReplySupplier.setVisibility(View.VISIBLE);
            holder.appendReplySupplier.setVisibility(View.GONE);


            if (item.getReply_nick().equals(user_nick)) {
                holder.deleteReplySupplier.setVisibility(View.VISIBLE);
                holder.deleteReplySupplier.setOnClickListener(new View.OnClickListener() { //댓글 삭제 버튼
                    @Override
                    public void onClick(View v) {

                        deleteDialog(position);

                    }
                });
            }

        }


    }

    //삭제 다이얼로그 띄우기
    public void deleteDialog(final int position) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setMessage("작성한 댓글이 삭제됩니다. 계속 진행 하시겠습니까?");

        alertDialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteReplyAction(position);
            }
        });

        alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.setTitle("댓글 삭제");
        alert.show();

    }

    //삭제 로직
    public void deleteReplyAction(final int position) {

        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("store_num", store_num);
            map.put("reply_ref", String.valueOf(items.get(position).getReply_ref()));
            map.put("reply_seq", String.valueOf(items.get(position).getReply_seq()));

            Log.d("testCode", "-----------사업자 댓글 관리 삭제 부분------------\n\n");

            Log.d("testCode", "store_num : " + store_num);
            Log.d("testCode", "reply_ref : " + String.valueOf(items.get(position).getReply_ref()));
            Log.d("testCode", "reply_seq : " + String.valueOf(items.get(position).getReply_seq()));

            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createSupplierRetrofitObject();
            SupplierController apiService = hrs.createSupplierApiserverObject();
            Call call = apiService.deleteFeedBack(map);
            hrs.HttpRequestExecute(call);
            String result = hrs.parsingStringFunc("result");
            if (result.equals("true")) {

                Toast.makeText(activity, "댓글 삭제 성공 하였습니다.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, AppraiseManager.class);
                intent.putExtra("store_num", store_num);
                activity.startActivity(intent);
                activity.finish();
            } else if (result.equals("false")) {

                Toast.makeText(activity, "댓글 삭제 실패 하였습니다.", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "오류가 발생 하였습니다.", Toast.LENGTH_SHORT).show();
        }

    }

    private void replyDialog(final int position){

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.anywhere23_1_appraise_manager_reply_dialog,null);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("댓글 작성");
        builder.setIcon(R.drawable.logo_blue);
        builder.setView(dialogView);

        final EditText replyContent = (EditText)dialogView.findViewById(R.id.replyContent);


        builder.setPositiveButton("댓글 작성", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendReply(replyContent,position);
            }
        });

        builder.setNegativeButton("취소 하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();

    }

    //대댓글 달기
    public void sendReply(EditText replyContent, final int position) {


        Map<String, String> map = new HashMap<>();

        map.put("store_num", store_num);
        map.put("reply_nick", user_nick);
        map.put("reply_content", replyContent.getText().toString());
        map.put("reply_ref", String.valueOf(items.get(position).getReply_ref()));

        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createSupplierRetrofitObject();
        SupplierController apiService = hrs.createSupplierApiserverObject();
        Call call = apiService.writeReFeedBack(map);
        hrs.HttpRequestExecute(call);
        String result = hrs.parsingStringFunc("result");
        if (result.equals("true")) {

            Toast.makeText(activity, "댓글 달기 성공 하였습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, AppraiseManager.class);
            intent.putExtra("store_num", store_num);
            activity.startActivity(intent);
            activity.finish();

        } else if (result.equals("false")) {

            Toast.makeText(activity, "댓글 달기 실패 하였습니다.", Toast.LENGTH_SHORT).show();

        }



    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
