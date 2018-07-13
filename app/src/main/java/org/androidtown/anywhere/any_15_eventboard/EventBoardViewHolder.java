package org.androidtown.anywhere.any_15_eventboard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-17.
 */

public class EventBoardViewHolder extends RecyclerView.ViewHolder {

    public TextView title, date;

    public EventBoardOnListItemClickListener eventBoardOnListitemClickListener;

    public EventBoardViewHolder(View itemView) {
        super(itemView);

        title = (TextView)itemView.findViewById(R.id.eventBoard_title);
        date=(TextView)itemView.findViewById(R.id.eventBoard_date);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventBoardOnListitemClickListener.onListItemClick(getAdapterPosition());
            }
        });

    }

    public void setQnaBoardOnListitemClickListener(EventBoardOnListItemClickListener eventBoardOnListitemClickListener)
    {

        this.eventBoardOnListitemClickListener = eventBoardOnListitemClickListener;


    }

}
