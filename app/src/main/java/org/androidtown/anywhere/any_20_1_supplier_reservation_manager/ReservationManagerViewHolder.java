package org.androidtown.anywhere.any_20_1_supplier_reservation_manager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.androidtown.anywhere.R;

import info.hoang8f.widget.FButton;

/**
 * Created by user on 2017-07-17.
 */

public class ReservationManagerViewHolder extends RecyclerView.ViewHolder{

    public TextView reservation_name, reservation_request, reservation_date, reservation_peopleNum, reservation_state;
    public FButton reservation_detail_btn,reservation_cancel_btn;

    public ReservationManagerViewHolder(View itemView) {
        super(itemView);

        reservation_name = (TextView) itemView.findViewById(R.id.reservation_Name);
        reservation_request = (TextView) itemView.findViewById(R.id.reservation_Request_Content);
        reservation_date = (TextView) itemView.findViewById(R.id.reservation_Date);
        reservation_peopleNum = (TextView) itemView.findViewById(R.id.reservation_PeopleNum);
        reservation_state = (TextView) itemView.findViewById(R.id.reservation_State);
        reservation_detail_btn = (FButton) itemView.findViewById(R.id.reservation_detail_btn);
        reservation_cancel_btn = (FButton) itemView.findViewById(R.id.reservation_cancel_btn);


    }


}
