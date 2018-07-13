package org.androidtown.anywhere.any_23_0_supplier_appraiseManager_storeList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-17.
 */

public class AppraiseManagerStoreListViewHolder extends RecyclerView.ViewHolder{

    TextView storeTitle, storeAddress;
    ImageView storeImage;

    public AppraiseManagerStoreList_OnListItemClickListener addPlace1_onListItemClickListener;

    public AppraiseManagerStoreListViewHolder(View itemView) {
        super(itemView);

        storeTitle = (TextView) itemView.findViewById(R.id.store_title);
        storeAddress = (TextView) itemView.findViewById(R.id.store_address);
        storeImage = (ImageView) itemView.findViewById(R.id.storeImage);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlace1_onListItemClickListener.onListItemClick(getAdapterPosition());
            }
        });



    }

    public void setAddPlace1_OnListItemClickListener(AppraiseManagerStoreList_OnListItemClickListener addPlace1_onListItemClickListener)
    {
        this.addPlace1_onListItemClickListener=addPlace1_onListItemClickListener;


    }
}
