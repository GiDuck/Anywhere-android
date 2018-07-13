package org.androidtown.anywhere.any_14_noticeboard;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.NoticeVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-07-17.
 */

public class NoticeBoardAdapter extends RecyclerView.Adapter<NoticeBoardViewHolder> implements NoticeBoardOnListItemClickListener {

    private Context context;
    private List<NoticeVO> items;
    private NoticeVO item;


    public NoticeBoardAdapter(List<NoticeVO> item, Context context) {
        items = new ArrayList<>();
        this.items = item;
        this.context = context;
    }


    public void add(NoticeVO data) {

        items.add(data);
        notifyDataSetChanged();

    }

    @Override
    public NoticeBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.anywhere14_noticeboard_custom_view, parent, false);

        NoticeBoardViewHolder holder = new NoticeBoardViewHolder(v);
        holder.setQnaBoardOnListitemClickListener(this);

        return holder;
    }


    @Override
    public void onBindViewHolder(NoticeBoardViewHolder holder, int position) {

        item = items.get(position);
        holder.title.setText(item.getNotice_title());
        holder.date.setText(new SimpleDateFormat("yyyy년 MM월 dd일 HH : mm").format(item.getNotice_date()));


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onListItemClick(int position) {


        Intent intent = new Intent(context, NoticeBoardDetail.class);
        intent.putExtra("NoticeVO", items.get(position));
        context.startActivity(intent);

    }

    public void setItems(List<NoticeVO> items) {
        this.items = items;
    }
}
