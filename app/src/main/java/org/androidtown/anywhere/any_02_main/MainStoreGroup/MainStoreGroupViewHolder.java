package org.androidtown.anywhere.any_02_main.MainStoreGroup;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-04.
 */

public class MainStoreGroupViewHolder extends RecyclerView.ViewHolder{

    public ImageView groupImage;
    public TextView groupText;


    public MainStoreGroupViewHolder(View item) {
        super(item);
        groupImage = (ImageView) item.findViewById(R.id.groupImage);
        groupText = (TextView) item.findViewById(R.id.groupText);
        groupText.getBackground().setAlpha(80);

    }


}
