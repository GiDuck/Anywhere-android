package org.androidtown.anywhere.any_07_customer_reservation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_08_customer_reservation_detail.CustomerReservationDetail;
import org.androidtown.anywhere.any_newVO.CustomerReservationCheckVO;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.util.ArrayList;
import java.util.HashMap;

import info.hoang8f.widget.FButton;
import retrofit2.Call;

/**
 * Created by user on 2017-07-04.
 */

public class CustomerReservationListAdapter extends RecyclerView.Adapter<CustomerReservationListViewHolder> implements View.OnClickListener {

    private Activity activity;
    private ArrayList<CustomerReservationListData> datas;

    //후기 다이얼로그
    View dialogLayout;
    Dialog myDialog;
    EditText textAfterComment;
    RatingBar ratingCounter;
    FButton after_comment_submit;

    CustomerReservationCheckVO reservationCheckVO;


    public CustomerReservationListAdapter(Activity activity) {
        this.activity = activity;
        datas = new ArrayList<>();


    }

    public void add(CustomerReservationListData data) {
        datas.add(data);
        notifyDataSetChanged();

    }

    //리사이클러뷰에 추가할 아이템들
    @Override
    public CustomerReservationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.anywhere07_customer_reservation_list, parent, false);
        return new CustomerReservationListViewHolder(v);
    }

    //뷰를 바인딩해준다.
    @Override
    public void onBindViewHolder(final CustomerReservationListViewHolder holder, final int position) {

        LinearLayout.LayoutParams btnLayoutParam =
                (LinearLayout.LayoutParams) holder.reservationDetailButton.getLayoutParams();

        CustomerReservationListData data = datas.get(position);

        Glide.with(activity).load(data.getStoreImage()).into(holder.storeImage); //글라이드 사용해 가게 사진 보여주기
        holder.storeName.setText(data.getStoreName());
        holder.storeAddress.setText(data.getStoreAddress());
        holder.reservationDate.setText(data.getReservationDate());
        holder.subView.setVisibility(View.VISIBLE); //예약중, 결제완료, 예약취소 상태일 때
        // 예약 상태를 보여주는 subView를 visible 상태로 하고 예약 상태를 문자로 보여줌

        if (Integer.parseInt(data.getReservationState()) == 0) { //reservationState가 0일때는 예약중
            holder.writeAfterCommentFrame.setVisibility(View.GONE);
            holder.reservationState.setText("예약중");
        } else if (Integer.parseInt(data.getReservationState()) == 1) { //1일때는 결제완료
            holder.writeAfterCommentFrame.setVisibility(View.GONE);
            holder.reservationState.setText("결제 완료");
        } else if (Integer.parseInt(data.getReservationState()) == 2) { //2일때는 예약 취소
            holder.reservationCancelButton.setVisibility(View.GONE);
            holder.reservationState.setText("예약 취소");
            btnLayoutParam.width = btnLayoutParam.MATCH_PARENT; //gravity를 동적으로 변경
            btnLayoutParam.height = btnLayoutParam.WRAP_CONTENT;

            btnLayoutParam.setMargins(40, 10, 40, 0);

            holder.reservationDetailButton.setLayoutParams(btnLayoutParam);

            //예약 상태가 사용 완료 일때는 후기를 작성할 수 있으므로 후기 작성 폼을 활성화 시킨다.
        } else if (Integer.parseInt(data.getReservationState()) == 3) {
            holder.reservationState.setText("사용 완료");
            holder.reservationControllFrame.setVisibility(View.GONE);
            holder.writeAfterCommentFrame.setVisibility(View.VISIBLE);
            holder.writeAfterCommentButton.setVisibility(View.VISIBLE);
            holder.writeAfterCommentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    makeAfterCommentDialog(position);

                }
            });


        }

        holder.reservationCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                makeDialog(position);

            }
        });
        holder.reservationDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailAction(position);
            }
        });
        //현재 가르키고 있는 position 값을 통해 작업을 하기 위해 이너 클래스를 통한 인터페이스 구현


    }

    //예약 내역 상세보기로 들어가는 메소드
    public void detailAction(int position) {

        Intent intent = new Intent(activity, CustomerReservationDetail.class);
        intent.putExtra("store_num", datas.get(position).getStore_num());
        intent.putExtra("reservation_num", datas.get(position).getReservation_num());
        intent.putExtra("storeVO", reservationCheckVO.getStoreVO().get(position));
        intent.putExtra("reservationListVO", reservationCheckVO.getReservationListVO().get(position));

        Log.d("testCode", "가게 번호 : " + datas.get(position).getStore_num());
        Log.d("testCode", "예약 번호 : " + datas.get(position).getReservation_num());
        activity.startActivity(intent);
        activity.finish();

    }

    @Override
    public void onClick(View v) {
        int click = v.getId();
        Intent intent = null;
        switch (click) {


            case R.id.writeAfterCommentButton: {


            }

        }
    }


    //예약 취소 다이얼로그를 띄우는 메소드
    public void makeDialog(final int position) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setMessage("예약을 취소합니다. \n환불, 취소 규정은 확인하셨나요? \n" +
                "확인 버튼을 누르면 취소가 진행됩니다.");
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                cancelReservation(position);
            }
        });

        alertDialog.setNegativeButton("돌아가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(activity, "돌아가기", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.setTitle("예약 취소");
        alert.show();

    }

    public void cancelReservation(final int position) {

        HashMap<String, String> map = new HashMap<>();
        map.put("store_num", String.valueOf(datas.get(position).getStore_num()));
        map.put("reservation_num", String.valueOf(datas.get(position).getReservation_num()));

        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createRetrofitObject();
        CustomerController apiService = hrs.createApiserverObject();
        Call call = apiService.cancelReservation(map);
        hrs.HttpRequestExecute(call);
        String result = hrs.parsingStringFunc("result");

        if (result.equals("true")) {

            Toast.makeText(activity, "예약이 취소 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, CustomerReservation.class);
            activity.startActivity(intent);
            activity.finish();
        } else if (result.equals("false")) {

            Toast.makeText(activity, "예약 취소가 실패 하였습니다", Toast.LENGTH_SHORT).show();
        }

    }

    //후기 작성 다이얼로그를 띄우는 메소드
    public void makeAfterCommentDialog(final int position) {

        LayoutInflater dialog = LayoutInflater.from(activity);
        dialogLayout = dialog.inflate(R.layout.anywhere07_customer_reservation_write_after_comment, null);
        myDialog = new Dialog(activity);
        myDialog.setContentView(dialogLayout);
        myDialog.setCanceledOnTouchOutside(false);


        textAfterComment = (EditText) dialogLayout.findViewById(R.id.textAfterComment);
        ratingCounter = (RatingBar) dialogLayout.findViewById(R.id.ratingCounter);
        textAfterComment.setMovementMethod(new ScrollingMovementMethod());
        after_comment_submit = (FButton) dialogLayout.findViewById(R.id.after_comment_submit);
        after_comment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AfterCommentDialogGetData(position);
            }
        });
        myDialog.show();

    }

    //후기 작성 후에 작성된 데이터를 가지고 오는 메소드
    public void AfterCommentDialogGetData(final int position) {
        try {
            String userComment = null;
            Float userRating = null;

            userComment = textAfterComment.getText().toString();
            userRating = ratingCounter.getRating();

            //예외처리 구문
            if (userComment.trim().length() == 0) {
                Toast.makeText(activity, "아무것도 작성하시지 않았네요. 귀찮으시더라도 조금만 글을 길게 써주시는건 어떤가요? ", Toast.LENGTH_LONG).show();
                return;
            } else if (userComment.trim().length() < 10) {
                Toast.makeText(activity, "10자 미만으로 적어주셨네요. 귀찮으시더라도 조금만 글을 길게 써주시는건 어떤가요? ", Toast.LENGTH_LONG).show();
                return;
            } else if (ratingCounter.getRating() == 0) {

                Toast.makeText(activity, "별점이 아직 선택되지 않았네요! 반 개 이상 선택해 주시면 감사하겠습니다. ", Toast.LENGTH_LONG).show();
                return;
            }

            SharedPreferences shef = activity.getSharedPreferences("anywhere", Context.MODE_PRIVATE);
            HashMap<String, String> map = new HashMap<>();
            map.put("store_num", datas.get(position).getStore_num());
            map.put("reply_nick", shef.getString("user_nick", ""));
            map.put("reply_content", userComment);
            map.put("reply_star", String.valueOf(userRating));

            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createRetrofitObject();
            CustomerController apiService = hrs.createApiserverObject();
            Call call = apiService.writeFeedBack(map);
            hrs.HttpRequestExecute(call);
            String result = hrs.parsingStringFunc("result");

            if (result.equals("true")) {

                Toast.makeText(activity, "후기 등록이 성공 하였습니다.", Toast.LENGTH_SHORT).show();

//            Toast.makeText(activity, "후기 데이터는 : " + userComment + " 의견이 있고 별점은 " + userRating + "입니다.", Toast.LENGTH_LONG).show();
                myDialog.cancel();

            } else if (result.equals("false")) {
                Toast.makeText(activity, "후기 등록이 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                myDialog.cancel();


            }


        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(activity, "통신 중에 에러가 발생 하였습니다.", Toast.LENGTH_SHORT).show();
            myDialog.cancel();

        }
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    //reservationCheckVO를 CustomerReservation 액티비티로 부터 가지고 오는 메소드
    public void setReservationCheckVO(CustomerReservationCheckVO reservationCheckVO) {
        this.reservationCheckVO = reservationCheckVO;
    }
}
