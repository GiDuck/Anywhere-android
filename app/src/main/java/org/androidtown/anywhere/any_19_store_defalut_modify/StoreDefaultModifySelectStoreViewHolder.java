package org.androidtown.anywhere.any_19_store_defalut_modify;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-07-17.
 */

public class StoreDefaultModifySelectStoreViewHolder extends RecyclerView.ViewHolder {

    TextView storeTitle, storeAddress;
    ImageView storeImage;
    Button store_delete_btn;

    public StoreDefaultModifySelectStoreOnListItemClickListener addPlace1_onListItemClickListener;

    public StoreDefaultModifySelectStoreViewHolder(View itemView) {
        super(itemView);

        storeTitle = (TextView) itemView.findViewById(R.id.store_title);
        storeAddress = (TextView) itemView.findViewById(R.id.store_address);
        storeImage = (ImageView) itemView.findViewById(R.id.storeImage);
        store_delete_btn=(Button)itemView.findViewById(R.id.store_delete_btn);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlace1_onListItemClickListener.onListItemClick(getAdapterPosition());
            }
        });




    }








    public void setAddPlace1_OnListItemClickListener(StoreDefaultModifySelectStoreOnListItemClickListener addPlace1_onListItemClickListener) {
        this.addPlace1_onListItemClickListener = addPlace1_onListItemClickListener;


    }
}
