package org.androidtown.anywhere.any_15_eventboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.EventVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-07-17.
 */

public class EventBoardAdapter extends RecyclerView.Adapter<EventBoardViewHolder> implements EventBoardOnListItemClickListener{

    private Context context;
    private List<EventVO> items;
    private EventVO item;
    private Uri uri;


    public EventBoardAdapter(List<EventVO> item, Context context) {
        items = new ArrayList<>();
        this.items = item;
        this.context = context;
    }


    public void add(EventVO data) {

        items.add(data);
        notifyDataSetChanged();

    }

    @Override
    public EventBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.anywhere15_event_board_custom_view, parent, false);

        EventBoardViewHolder holder = new EventBoardViewHolder(v);
        holder.setQnaBoardOnListitemClickListener(this);

        return holder;
    }


    @Override
    public void onBindViewHolder(EventBoardViewHolder holder, int position) {

        item = items.get(position);
        holder.title.setText(item.getEvent_title());
        SimpleDateFormat sdf = new SimpleDateFormat("yy년 MM월 dd일 HH : mm");
        holder.date.setText(sdf.format(item.getEvent_date()));


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onListItemClick(int position) {

        Intent intent = new Intent(context, EventBoardDetail.class);
        intent.putExtra("boardVO", items.get(position));
        context.startActivity(intent);

    }

    public List<EventVO> getItems() {
        return items;
    }

    public void setItems(List<EventVO> items) {
        this.items = items;
    }
}
