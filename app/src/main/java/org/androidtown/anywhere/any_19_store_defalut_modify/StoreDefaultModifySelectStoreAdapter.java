package org.androidtown.anywhere.any_19_store_defalut_modify;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol.ImageURLParser;
import org.androidtown.anywhere.httpcontrol_retrofitController.SupplierController;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by user on 2017-07-17.
 */

public class StoreDefaultModifySelectStoreAdapter extends RecyclerView.Adapter<StoreDefaultModifySelectStoreViewHolder> implements StoreDefaultModifySelectStoreOnListItemClickListener {

    private Context context;
    private List<StoreVO> items;
    private StoreVO item;
    private Activity activity;

    public StoreDefaultModifySelectStoreAdapter(Context context) {

        this.context = context;


    }


    public void add(ArrayList<StoreVO> items) {
        this.items = items;
        notifyDataSetChanged();

    }

    public void remove(final int position) {


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context); // 삭제 다이얼로그 바디
        alertDialog.setMessage("등록된 가게가 삭제됩니다. 계속 진행하시겠습니까?"); //메세지

        alertDialog.setPositiveButton("예", new DialogInterface.OnClickListener() { //진행 버튼
            @Override
            public void onClick(DialogInterface dialog, int which) {

                HttpRequestSyncObject hrs = new HttpRequestSyncObject();
                hrs.createSupplierRetrofitObject();
                SupplierController apiService = hrs.createSupplierApiserverObject();
                Call call = apiService.deleteStore(items.get(position).getStore_num());
                hrs.HttpRequestExecute(call);
                String result = hrs.parsingStringFunc("result");

                try {
                    if (result.equals("true")) {

                        items.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "삭제 완료", Toast.LENGTH_SHORT).show();
                    } else if (result.equals("false")) {

                        Toast.makeText(context, "삭제 실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "에러가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                }


            }
        });

        alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() { //취소 버튼
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });


        AlertDialog alert = alertDialog.create(); //메인 다이얼로그 생성
        alert.setTitle("가게 삭제"); //타이틀
        alert.show(); //다이얼로그 보기

    }


    @Override
    public StoreDefaultModifySelectStoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.anywhere19_store_default_modify_select_store_custom_view, parent, false);

        StoreDefaultModifySelectStoreViewHolder holder = new StoreDefaultModifySelectStoreViewHolder(v);
        holder.setAddPlace1_OnListItemClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(StoreDefaultModifySelectStoreViewHolder holder, final int position) {


        item = items.get(position);
        holder.storeTitle.setText(item.getStore_name());
        holder.storeAddress.setText(item.getStore_addr());

        Glide.with(context).load(new ImageURLParser().imageURLParser(item.getStore_mainurl())).into(holder.storeImage);

        holder.store_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        try {
            return items.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }


    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(context, StoreDefalutModifyFirst.class);
        intent.putExtra("store_num", items.get(position).getStore_num());
        context.startActivity(intent);

    }


}
