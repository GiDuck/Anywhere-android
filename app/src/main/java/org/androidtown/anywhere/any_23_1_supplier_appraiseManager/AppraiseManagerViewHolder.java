package org.androidtown.anywhere.any_23_1_supplier_appraiseManager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-17.
 */

public class AppraiseManagerViewHolder extends RecyclerView.ViewHolder{

    public TextView nickname, comment_content, comment_date,appendReplySupplier,deleteReplySupplier;
    public RatingBar comment_rating_bar;

    ImageView appraiseManager_reply_marker;



    public AppraiseManagerViewHolder(View itemView) {
        super(itemView);

        nickname=(TextView)itemView.findViewById(R.id.appraiseManager_userNick);
        comment_content=(TextView)itemView.findViewById(R.id.appraiseManager_content);
        comment_date=(TextView)itemView.findViewById(R.id.appraiseManager_date);
        appendReplySupplier=(TextView)itemView.findViewById(R.id.appendReplySupplier);
        deleteReplySupplier=(TextView)itemView.findViewById(R.id.deleteReplySupplier);

        comment_rating_bar=(RatingBar)itemView.findViewById(R.id.appraiseManager_userRatingBar);

        appraiseManager_reply_marker = (ImageView) itemView.findViewById(R.id.appraiseManager_reply_marker);

    }
}
