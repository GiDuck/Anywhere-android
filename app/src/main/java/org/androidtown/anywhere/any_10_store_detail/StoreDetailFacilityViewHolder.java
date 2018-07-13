package org.androidtown.anywhere.any_10_store_detail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-06.
 */

public class StoreDetailFacilityViewHolder extends RecyclerView.ViewHolder{

    public ImageView storeFacilityIcon;
    public TextView storeFacilityIconName;

    public StoreDetailFacilityViewHolder(View item) {
        super(item);
        storeFacilityIcon = (ImageView) item.findViewById(R.id.storeFacilityIcon);
        storeFacilityIconName = (TextView) item.findViewById(R.id.storeFacilityIconName);
    }
}
