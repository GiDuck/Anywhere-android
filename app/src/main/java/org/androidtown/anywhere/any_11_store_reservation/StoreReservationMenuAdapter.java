package org.androidtown.anywhere.any_11_store_reservation;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidtown.anywhere.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by user on 2017-07-11.
 */

public class StoreReservationMenuAdapter extends RecyclerView.Adapter<StoreReservationMenuViewHolder> {

    Context context;
    ArrayList<StoreReservationMenuData> datas;
    HashMap<String, StoreReservationMenuData> dummyDatas;
    TextView reservationMenuNum;
    TextView reservationMenuPrice;


    public StoreReservationMenuAdapter(Activity context) {
        this.context = context;
        datas = new ArrayList<>();
        dummyDatas = new HashMap<>();
        reservationMenuNum = (TextView) context.findViewById(R.id.reservationMenuNum);
        reservationMenuPrice = (TextView) context.findViewById(R.id.reservationMenuPrice);


    }


    public void add(StoreReservationMenuData data) {
        datas.add(data);
        dummyDatas.put(data.getMenuName(), data);


    }

    @Override
    public StoreReservationMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.anywhere11_store_reservation_menu_list, parent, false);
            return new StoreReservationMenuViewHolder(v);
        }catch (Exception e){
            e.printStackTrace();
        return null;
        }

    }

    @Override
    public void onBindViewHolder(final StoreReservationMenuViewHolder holder, final int position) {
        StoreReservationMenuData data = datas.get(position);


        holder.menuName.setText(data.getMenuName());
        holder.menuPrice.setText(data.getMenuPrice());
        holder.menuNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (holder.menuNum.getText().toString().trim().length() == 0 || holder.menuNum.getText().equals("")) {

                    holder.menuNum.setText("0");
                    return;

                }

                if (Integer.parseInt(holder.menuNum.getText().toString()) < 0) {
                    holder.menuNum.setText("0");
                    return;

                }


            }
        });

        holder.menuNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {


              /*  if(holder.menuNum.getText().toString().trim().length()==0 || holder.menuNum.getText().equals("")){

                    holder.menuNum.setText("0");
                    return;

                }

                if(Integer.parseInt(holder.menuNum.getText().toString())<0){
                    holder.menuNum.setText("0");
                    return;

                }*/


                try {
                    Log.d("기덕1", holder.menuName.getText().toString() + " 메뉴 num  " + holder.menuNum.getText().toString());


                    StoreReservationMenuData dummy = new StoreReservationMenuData();
                    dummy.setMenuName(holder.menuName.getText().toString());
                    dummy.setMenuNum(holder.menuNum.getText().toString());
                    dummy.setMenuPrice(String.valueOf(Integer.parseInt(holder.menuPrice.getText().toString())));

                    dummyDatas.put(holder.menuName.getText().toString(), dummy);

                    int totalMenuPrice = 0;
                    int totalMenuNum = 0;


                    Iterator<String> iterator = dummyDatas.keySet().iterator();
                    while (iterator.hasNext()) {

                        String key = iterator.next();
                        totalMenuNum += Integer.parseInt(dummyDatas.get(key).getMenuNum());
                        totalMenuPrice += (Integer.parseInt(dummyDatas.get(key).getMenuPrice()) * Integer.parseInt(dummyDatas.get(key).getMenuNum()));

                    }

               /*     for (StoreReservationMenuData data : datas) {

                        totalMenuNum += Integer.parseInt(data.getMenuNum());
                        totalMenuPrice += Integer.parseInt(data.getMenuPrice());


                    }*/

                /*    totalMenuNum += Integer.parseInt(holder.menuNum.getText().toString());
                    totalMenuPrice += (Integer.parseInt(holder.menuNum.getText().toString()) * Integer.parseInt(holder.menuPrice.getText().toString()));
*/
                    reservationMenuNum.setText(String.valueOf(totalMenuNum));
                    reservationMenuPrice.setText(String.valueOf(totalMenuPrice));
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });


        holder.menuPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.menuNum.getText() == null) {

                    holder.menuNum.setText("0");
                } else if (holder.menuNum.getText().toString().trim().length() == 0) {

                    holder.menuNum.setText("0");

                }

                int menuCount = Integer.parseInt(holder.menuNum.getText().toString());
                int reservationNum = Integer.parseInt(reservationMenuNum.getText().toString());
                int reservationPrice = Integer.parseInt(datas.get(position).getMenuPrice());
                int reservationMenuTotalPrice = Integer.parseInt(reservationMenuPrice.getText().toString());
                int menuNumber = 0;

                holder.menuNum.setText(String.valueOf(menuCount + 1));

                reservationMenuNum.setText(String.valueOf(reservationNum + 1));
                reservationMenuPrice.setText(String.valueOf(reservationMenuTotalPrice + reservationPrice));
                menuNumber = Integer.parseInt(datas.get(position).getMenuNum());
                datas.get(position).setMenuNum(String.valueOf(menuNumber + 1));

            }
        });

        holder.menuMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.menuNum.getText() == null) {

                    holder.menuNum.setText("0");
                } else if (holder.menuNum.getText().toString().trim().length() == 0) {

                    holder.menuNum.setText("0");

                }

                int menuCount = Integer.parseInt(holder.menuNum.getText().toString());
                int reservationNum = Integer.parseInt(reservationMenuNum.getText().toString());
                int reservationPrice = Integer.parseInt(datas.get(position).getMenuPrice());
                int reservationMenuTotalPrice = Integer.parseInt(reservationMenuPrice.getText().toString());
                int menuNumber = 0;
                if (menuCount > 0) {
                    holder.menuNum.setText(String.valueOf(menuCount - 1));
                    reservationMenuNum.setText(String.valueOf(reservationNum - 1));
                    reservationMenuPrice.setText(String.valueOf(reservationMenuTotalPrice - reservationPrice));
                    menuNumber = Integer.parseInt(datas.get(position).getMenuNum());
                    datas.get(position).setMenuNum(String.valueOf(menuNumber - 1));

                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    public ArrayList<StoreReservationMenuData> getDatas() {
        return datas;
    }

    public HashMap<String, StoreReservationMenuData> getDummyDatas() {
        return dummyDatas;
    }

}



