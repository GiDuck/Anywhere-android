package org.androidtown.anywhere.any_13_search_result;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-17.
 */

public class SearchResultViewHolder extends RecyclerView.ViewHolder{

    public ImageView storePhotoUri;
    public TextView storeName, storeAddress, minimumPeople,freePrice,store_distance, ratingCount, commentCount, favoriteCount;

    public RatingBar starRank;

    public SearchResultViewHolder(View itemView) {
        super(itemView);

        storePhotoUri = (ImageView)itemView.findViewById(R.id.storePhotoUri);

        storeName = (TextView)itemView.findViewById(R.id.storeName);
        storeAddress=(TextView)itemView.findViewById(R.id.storeAddress);
        freePrice=(TextView)itemView.findViewById(R.id.freePrice);
        minimumPeople=(TextView)itemView.findViewById(R.id.minimumPeople);
        store_distance=(TextView)itemView.findViewById(R.id.store_distance);
        ratingCount=(TextView)itemView.findViewById(R.id.ratingCount);
        commentCount=(TextView)itemView.findViewById(R.id.commentCount);
        favoriteCount=(TextView)itemView.findViewById(R.id.favoriteCount);
        starRank=(RatingBar)itemView.findViewById(R.id.starRank);



    }

}