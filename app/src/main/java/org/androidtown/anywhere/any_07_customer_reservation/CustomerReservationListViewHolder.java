package org.androidtown.anywhere.any_07_customer_reservation;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.androidtown.anywhere.R;

import info.hoang8f.widget.FButton;

/**
 * Created by user on 2017-07-04.
 */

public class CustomerReservationListViewHolder extends RecyclerView.ViewHolder{

    //예약 관련 뷰
    ImageView storeImage;
    TextView storeName;
    TextView storeAddress;
    TextView reservationDate;
    TextView reservationState;
    FButton reservationCancelButton;
    FButton reservationDetailButton;
    FButton writeAfterCommentButton;
    FButton checkAfterCommentButton;

    CardView subView;
    LinearLayout writeAfterCommentFrame;
    LinearLayout reservationControllFrame;

    CardView user_feedBack;

    TextView user_feedBack_content;
    TextView user_feedBack_date;

    RatingBar user_feedBack_rating;
    TextView user_feedBack_ratingNum;

    public CustomerReservationListViewHolder(View item) {
        super(item);

        //예약 내역 확인 (기본 뷰)
        storeImage = (ImageView) item.findViewById(R.id.storeImage);
        storeName = (TextView) item.findViewById(R.id.storeName);
        storeAddress = (TextView) item.findViewById(R.id.storeAddress);
        reservationDate = (TextView) item.findViewById(R.id.reservationDate);
        reservationState = (TextView) item.findViewById(R.id.reservationState);

        //예약 상세 보기, 예약 취소 버튼 (기본 세팅)
        reservationDetailButton = (FButton) item.findViewById(R.id.reservationDetailButton);
        reservationCancelButton = (FButton) item.findViewById(R.id.reservationCancelButton);

        //예약 상태 알려주는 폼
        subView=(CardView)item.findViewById(R.id.subView);

        //예약 상세보기, 예약 취소 버튼을 포함 하고 있는 폼
        reservationControllFrame=(LinearLayout)item.findViewById(R.id.reservationControllFrame);

        //후기 작성 버튼을 포함 하고 있는 폼
        writeAfterCommentFrame=(LinearLayout)item.findViewById(R.id.writeAfterCommentFrame);

        //사용 완료시 후기 작성 버튼
        writeAfterCommentButton=(FButton)item.findViewById(R.id.writeAfterCommentButton);

        //후기 작성 후 후기 확인 버튼
        checkAfterCommentButton=(FButton)item.findViewById(R.id.checkAfterCommentButton);

        //후기 작성 폼 (기본 gone)
        user_feedBack=(CardView)item.findViewById(R.id.user_feedBack);

        //후기 보기 폼에 있는 뷰

        //후기 내용
        user_feedBack_content=(TextView) item.findViewById(R.id.user_feedBack_content);

        //후기 작성 날짜, 시간
        user_feedBack_date=(TextView)item.findViewById(R.id.user_feedBack_date);

        //후기 ratingBar
        user_feedBack_rating=(RatingBar)item.findViewById(R.id.user_feedBack_rating);

        //후기 점수
        user_feedBack_ratingNum=(TextView)item.findViewById(R.id.user_feedBack_ratingNum);








    }
}
