package org.androidtown.anywhere.any_14_noticeboard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-17.
 */

public class NoticeBoardViewHolder extends RecyclerView.ViewHolder{

    public TextView title, writer, date;

    public NoticeBoardOnListItemClickListener noticeBoardOnListitemClickListener;

    public NoticeBoardViewHolder(View itemView) {
        super(itemView);

        title = (TextView)itemView.findViewById(R.id.noticeBoard_title);
        date=(TextView)itemView.findViewById(R.id.noticeBoard_date);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noticeBoardOnListitemClickListener.onListItemClick(getAdapterPosition());

            }
        });

    }

    public void setQnaBoardOnListitemClickListener(NoticeBoardOnListItemClickListener noticeBoardOnListitemClickListener)
    {

        this.noticeBoardOnListitemClickListener = noticeBoardOnListitemClickListener;


    }

}
