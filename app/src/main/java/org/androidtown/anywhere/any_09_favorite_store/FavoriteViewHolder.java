package org.androidtown.anywhere.any_09_favorite_store;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by gdtbg on 2017-07-03.
 */

public class FavoriteViewHolder extends RecyclerView.ViewHolder {
    public ImageView storePhoto;
    public TextView storeName;
/*
    public LikeButton likeButton;
*/

    public FavoriteOnListItemClickListener favoriteOnListitemClickListener;

    public FavoriteViewHolder(final View itemView) {
        super(itemView);

        storePhoto = (ImageView) itemView.findViewById(R.id.favoriteStoreImage);
        storeName = (TextView) itemView.findViewById(R.id.favoriteStoreName);
/*
        likeButton = (LikeButton) itemView.findViewById(R.id.likeButton);
*/

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favoriteOnListitemClickListener.onListItemClick(getAdapterPosition());
            }
        });





    }

    public void setOnListitemClickListener(FavoriteOnListItemClickListener favoriteOnListitemClickListener) {

        this.favoriteOnListitemClickListener = favoriteOnListitemClickListener;

    }

}
