package org.androidtown.anywhere.any_10_store_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-10.
 */

public class StoreDetailReplyViewHolder extends RecyclerView.ViewHolder {

    TextView replyNick;
    TextView replyDate;
    TextView replyContent;
    RatingBar replyStar;

    LinearLayout replyController;
    TextView appendReply;
    TextView deleteReply;

    ImageView replyMarker;

    public StoreDetailReplyViewHolder(View item) {
        super(item);
        replyNick = (TextView) item.findViewById(R.id.replyNick);
        replyDate = (TextView) item.findViewById(R.id.replyDate);
        replyContent = (TextView) item.findViewById(R.id.replyContent);
        replyStar = (RatingBar) item.findViewById(R.id.replyStar);
        replyController = (LinearLayout) item.findViewById(R.id.replyController);
        appendReply = (TextView) item.findViewById(R.id.appendReply);
        deleteReply = (TextView) item.findViewById(R.id.deleteReply);
        replyMarker=(ImageView)item.findViewById(R.id.replyMarker);


    }
}
