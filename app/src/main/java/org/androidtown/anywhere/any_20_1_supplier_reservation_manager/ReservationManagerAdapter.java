package org.androidtown.anywhere.any_20_1_supplier_reservation_manager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_20_2_supplier_reservation_manager_detail.ReservationManagerDetail;
import org.androidtown.anywhere.any_newVO.ReservationListVO;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.SupplierController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

/**
 * Created by user on 2017-07-17.
 */

public class ReservationManagerAdapter extends RecyclerView.Adapter<ReservationManagerViewHolder> {

    private Activity activity;
    private List<ReservationListVO> items;
    private ReservationListVO item;
    private int store_num;

    final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");

    public ReservationManagerAdapter(List<ReservationListVO> items, int store_num, Activity activity) {

        this.activity = activity;
        this.items = items;
        this.store_num = store_num;
    }

    public void reservationData(List<ReservationListVO> items){
        this.items = items;
        notifyDataSetChanged();
        Log.d("확인","어뎁터 확인");
    }

    @Override
    public ReservationManagerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.anywhere20_1_reservation_manager_custom_view, parent, false);

        ReservationManagerViewHolder holder = new ReservationManagerViewHolder(v);

        return holder;
    }


    @Override
    public void onBindViewHolder(ReservationManagerViewHolder holder, final int position) {

        item = items.get(position);

        Date reservationDate = item.getReservation_date();
        String strDate = sdf.format(reservationDate);

        holder.reservation_name.setText(item.getReservation_name());
        holder.reservation_date.setText(strDate + " " + item.getReservation_starttime()+"시"+ " ~ " + item.getReservation_endtime()+"시");
        holder.reservation_peopleNum.setText(Integer.toString(item.getReservation_total()));
        holder.reservation_request.setText(item.getReservation_request());

        holder.reservation_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ReservationManagerDetail.class);
                intent.putExtra("reservationVO", items.get(position));
                intent.putExtra("store_num", store_num);
                activity.startActivity(intent);
            }
        });



        if (item.getReservation_state() == 3) {

            holder.reservation_state.setText("사용 완료");
            holder.reservation_detail_btn.setVisibility(View.VISIBLE);
            holder.reservation_cancel_btn.setVisibility(View.GONE);

        } else if (item.getReservation_state() == 2) {

            holder.reservation_state.setText("예약 취소");
            holder.reservation_detail_btn.setVisibility(View.VISIBLE);
            holder.reservation_cancel_btn.setVisibility(View.GONE);

        } else if (item.getReservation_state() == 1) {

            holder.reservation_state.setText("결제 완료");
            holder.reservation_detail_btn.setVisibility(View.VISIBLE);
            holder.reservation_cancel_btn.setVisibility(View.GONE);

        } else if (item.getReservation_state() == 4) {

            holder.reservation_state.setText("예약 실패");
            holder.reservation_detail_btn.setVisibility(View.GONE);
            holder.reservation_cancel_btn.setVisibility(View.GONE);

        }else if(item.getReservation_state()==0){
            holder.reservation_state.setText("예약 대기");
            holder.reservation_detail_btn.setVisibility(View.GONE);
            holder.reservation_cancel_btn.setVisibility(View.VISIBLE);
            holder.reservation_cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog(items.get(position).getReservation_num());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void showDialog(final int reservation_num) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setMessage("예약 일정이 삭제됩니다. 계속 진행 하시겠습니까?");
        alertDialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                HttpRequestSyncObject hrs = new HttpRequestSyncObject();
                hrs.createSupplierRetrofitObject();
                SupplierController apiService = hrs.createSupplierApiserverObject();
                Call call = apiService.deleteReservation(store_num, reservation_num);
                hrs.HttpRequestExecute(call);
                String result = hrs.parsingStringFunc("result");
                if (result.equals("true")) {
                    Toast.makeText(activity, "예약 삭제가 성공 하였습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, ReservationManager.class);
                    intent.putExtra("store_num", store_num);
                    activity.startActivity(intent);
                    activity.finish();

                } else if (result.equals("false")) {

                    Toast.makeText(activity, "예약 삭제가 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alert = alertDialog.create();
        alert.setTitle("예약 삭제하기");
        alert.show();


    }


}
