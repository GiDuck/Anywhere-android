package org.androidtown.anywhere.any_16_1_qnaboard_reply;

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
import android.widget.LinearLayout;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_16_0_qnaboard.QnaBoardDetail;
import org.androidtown.anywhere.any_newVO.QaReplyVO;
import org.androidtown.anywhere.any_newVO.QaVO;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

/**
 * Created by user on 2017-07-17.
 */

public class QnaBoardReplyAdapter extends RecyclerView.Adapter<QnaBoardReplyViewHolder> {

    private Activity context;
    private List<QaReplyVO> items;
    private QaReplyVO item;
    QaVO boardVO;
    private String user_nick;
    private SharedPreferences shef;


    public QnaBoardReplyAdapter(List<QaReplyVO> item, Activity context, QaVO boardVO) {
        items = new ArrayList<>();
        this.items = item;
        this.context = context;
        this.boardVO = boardVO;
        shef=context.getSharedPreferences("anywhere", Context.MODE_PRIVATE);
        user_nick=shef.getString("user_nick", "");


    }

    public void add(QaReplyVO data) {

        items.add(data);
        notifyDataSetChanged();

    }


    @Override
    public QnaBoardReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.anywhere16_qna_board_detail_reply_custom_view, parent, false);

        QnaBoardReplyViewHolder holder = new QnaBoardReplyViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final QnaBoardReplyViewHolder holder, final int position) {
        LinearLayout.LayoutParams customView = (LinearLayout.LayoutParams) holder.customFrame.getLayoutParams();
        item = items.get(position);
        holder.qnaBoard_reply_id.setText(item.getReply_nick());
        holder.qnaBoard_reply_content.setText(item.getReply_content());
        holder.qnaBoard_reply_date.setText(new SimpleDateFormat("yy-MM-dd HH : MM").format(item.getReply_date()));


        if(!user_nick.equals(item.getReply_nick())){


            holder.qnaBoard_reply_modfiy.setVisibility(View.GONE);
            holder.qnaBoard_reply_delete.setVisibility(View.GONE);

        }


        if (item.getReply_lev() != 0) {


            customView.setMargins(120, 10, 10, 10);
            holder.customFrame.setLayoutParams(customView);
            holder.qnaBoard_reply_Info.setVisibility(View.VISIBLE);

        }


        holder.inflateReplyController.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.replyControlLayout.getVisibility() == View.VISIBLE) {
                    holder.replyControlLayout.setVisibility(View.GONE);
                    holder.inflateReplyController.setBackgroundResource(R.drawable.ic_expand_more_black_24dp);


                } else if (holder.replyControlLayout.getVisibility() == View.GONE) {
                    holder.replyControlLayout.setVisibility(View.VISIBLE);
                    holder.inflateReplyController.setBackgroundResource(R.drawable.ic_keyboard_arrow_up_black_24dp);

                }

                if(holder.qnaBoard_reply_append_controll.getVisibility()==View.VISIBLE){

                    holder.qnaBoard_reply_append_controll.setVisibility(View.GONE);

                }

                if(holder.qnaBoard_reply_modfiy_controll.getVisibility()==View.VISIBLE){

                    holder.qnaBoard_reply_modfiy_controll.setVisibility(View.GONE);
                }




            }
        });

        holder.qnaBoard_reply_modfiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.qnaBoard_reply_modfiy_controll.setVisibility(View.VISIBLE);
                holder.qnaBoard_reply_modfiy_edit.setText(item.getReply_content());
                holder.qnaBoard_reply_modfiy_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        modifyReply(holder, position);

                    }
                });

            }
        });


        holder.qnaBoard_reply_append_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                appendReply(holder, position);


            }
        });


        holder.qnaBoard_reply_append.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                holder.qnaBoard_reply_append_controll.setVisibility(View.VISIBLE);


            }
        });
        holder.qnaBoard_reply_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setMessage("댓글이 삭제됩니다. 계속 진행하시겠습니까?");
                alertDialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteReply(holder, position);

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
        });

    }

    public void appendReply(QnaBoardReplyViewHolder holder, int position) {
        SharedPreferences shef = context.getSharedPreferences("anywhere", Context.MODE_PRIVATE);
        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createRetrofitObject();
        CustomerController apiService = hrs.createApiserverObject();
        HashMap<String, String> replyMap = new HashMap<>();
        replyMap.put("qa_num", String.valueOf(items.get(position).getQa_num()));
        replyMap.put("reply_nick", shef.getString("user_nick", ""));
        replyMap.put("reply_content", holder.qnaBoard_reply_append_edit.getText().toString());
        replyMap.put("reply_ref", String.valueOf(items.get(position).getReply_ref()));
        Call call = apiService.appendReply(replyMap);
        hrs.HttpRequestExecute(call);
        String result = hrs.parsingStringFunc("result");

        if (result.equals("true")) {

            Toast.makeText(context, "댓글 등록 성공", Toast.LENGTH_SHORT).show();
            holder.qnaBoard_reply_append_edit.setText(" ");
            holder.qnaBoard_reply_append_controll.setVisibility(View.GONE);

            Intent intent = new Intent(context, QnaBoardDetail.class);
            intent.putExtra("QaVO", boardVO);
            context.startActivity(intent);
            context.finish();

        } else if (result.equals("false")) {

            Toast.makeText(context, "댓글 등록 실패", Toast.LENGTH_SHORT).show();
        }


    }

    public void modifyReply(QnaBoardReplyViewHolder holder, int position) {

        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createRetrofitObject();
        CustomerController apiService = hrs.createApiserverObject();
        HashMap<String, String> replyMap = new HashMap<>();
        replyMap.put("qa_num", String.valueOf(items.get(position).getQa_num()));
        replyMap.put("reply_content", holder.qnaBoard_reply_modfiy_edit.getText().toString());
        replyMap.put("reply_seq", String.valueOf(items.get(position).getReply_seq()));
        Call call = apiService.modifyReply(replyMap);
        hrs.HttpRequestExecute(call);
        String result = hrs.parsingStringFunc("result");

        if (result.equals("true")) {

            Toast.makeText(context, "댓글 수정 성공", Toast.LENGTH_SHORT).show();
            holder.qnaBoard_reply_append_edit.setText(" ");
            holder.qnaBoard_reply_append_controll.setVisibility(View.GONE);

            Intent intent = new Intent(context, QnaBoardDetail.class);
            intent.putExtra("QaVO", boardVO);
            context.startActivity(intent);
            context.finish();

        } else if (result.equals("false")) {

            Toast.makeText(context, "댓글 수정 실패", Toast.LENGTH_SHORT).show();
        }


    }


    public void deleteReply(QnaBoardReplyViewHolder holder, int position) {

        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createRetrofitObject();
        CustomerController apiService = hrs.createApiserverObject();
        HashMap<String, String> replyMap = new HashMap<>();
        replyMap.put("qa_num", String.valueOf(items.get(position).getQa_num()));
        replyMap.put("reply_seq", String.valueOf(items.get(position).getReply_seq()));
        replyMap.put("reply_ref", String.valueOf(items.get(position).getReply_ref()));
        Call call = apiService.deleteReply(replyMap);
        hrs.HttpRequestExecute(call);
        String result = hrs.parsingStringFunc("result");

        if (result.equals("true")) {

            Toast.makeText(context, "댓글 삭제 성공", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, QnaBoardDetail.class);
            intent.putExtra("QaVO", boardVO);
            context.startActivity(intent);
            context.finish();

        } else if (result.equals("false")) {

            Toast.makeText(context, "댓글 삭제 실패", Toast.LENGTH_SHORT).show();
            Log.d("errorCode", "삭제 실패 - 통신 문제");
        }


    }

    @Override
    public int getItemCount() {
        return items.size();
    }




}
