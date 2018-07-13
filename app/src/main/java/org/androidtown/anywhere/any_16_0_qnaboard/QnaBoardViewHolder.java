package org.androidtown.anywhere.any_16_0_qnaboard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-17.
 */

public class QnaBoardViewHolder extends RecyclerView.ViewHolder {

    public TextView title, writer, date, readState ;


    public QnaBoardOnListItemClickListener qnaBoardOnListitemClickListener;

    public QnaBoardViewHolder(View itemView) {
        super(itemView);

        title = (TextView)itemView.findViewById(R.id.qnaBoard_Title);
        writer=(TextView)itemView.findViewById(R.id.qnaBoard_writer);
        date=(TextView)itemView.findViewById(R.id.qnaBoard_date);
        readState=(TextView)itemView.findViewById(R.id.qnaBoard_readState);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qnaBoardOnListitemClickListener.onListItemClick(getAdapterPosition());
            }
        });

    }

    public void setQnaBoardOnListitemClickListener(QnaBoardOnListItemClickListener qnaBoardOnListitemClickListener)
    {

        this.qnaBoardOnListitemClickListener = qnaBoardOnListitemClickListener;


    }
}
