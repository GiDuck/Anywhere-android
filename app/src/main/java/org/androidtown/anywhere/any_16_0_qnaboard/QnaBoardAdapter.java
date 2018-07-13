package org.androidtown.anywhere.any_16_0_qnaboard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_VO.before_VO.BoardVO;
import org.androidtown.anywhere.any_newVO.QaVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017-07-17.
 */

public class QnaBoardAdapter extends RecyclerView.Adapter<QnaBoardViewHolder> implements QnaBoardOnListItemClickListener {

    private Context context;
    private List<QaVO> items;
    private QaVO item;
    BoardVO boardVO;
    private Uri uri;


    public QnaBoardAdapter(List<QaVO> item, Context context) {
        items = new ArrayList<>();
        this.items = item;
        this.context = context;


    }


    public void add(QaVO data) {

        items.add(data);
        notifyDataSetChanged();

    }

    @Override
    public QnaBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.anywhere16_qna_board_custom_view, parent, false);

        QnaBoardViewHolder holder = new QnaBoardViewHolder(v);
        holder.setQnaBoardOnListitemClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(QnaBoardViewHolder holder, final int position) {

        item = items.get(position);

        String part_username=item.getQa_nick();

        if(part_username.length()>8){

            part_username=part_username.substring(0, 8);
            part_username+="...";
        }
        holder.title.setText(item.getQa_title());
        holder.writer.setText(part_username);
        holder.date.setText(new SimpleDateFormat("yyyy년 MM월 dd일 HH : mm").format(item.getQa_date()));




        if (item.getQa_check() == 0) {

            holder.readState.setText("읽지않음");
        } else if (item.getQa_check() == 1) {

            holder.readState.setText("읽음");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void onListItemClick(int position) {

        Intent intent = new Intent(context, QnaBoardDetail.class);
        intent.putExtra("QaVO", items.get(position));
        context.startActivity(intent);
    }

    public List<QaVO> getItems() {
        return items;
    }

    public void setItems(List<QaVO> items) {
        this.items = items;
    }
}
