package org.androidtown.anywhere.any_20_2_supplier_reservation_manager_detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.ReservationListVO;
import org.androidtown.anywhere.any_newVO.SalesVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.SupplierController;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;
import retrofit2.Call;

public class ReservationManagerDetail extends Header implements View.OnClickListener{
    RelativeLayout actionbar;
    FrameLayout menubar;
    ScrollView container1;

    TextView reservationName;
    TextView reservationPhone;
    TextView reservationDate;
    TextView reservationPeopleNum;
    TextView reservationRequest;
    RecyclerView reservationMenu;
    TextView reservationMenuNum;
    TextView reservationMenuPrice;
    FButton reservationCheck;

    ReservationListVO reservation;

    String user_nick;
    int store_num;
    ArrayList<SalesVO> salesList;

    int totalMenuNum;
    int totalMenuPrice;

    SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy년 MM월 dd일");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere20_2_reservation_manager_detail_view);

        SharedPreferences infoStoarge = getSharedPreferences("anywhere", MODE_PRIVATE);
        user_nick=infoStoarge.getString("user_nick","");

        Intent intent = getIntent();
        reservation=(ReservationListVO)intent.getSerializableExtra("reservationVO");
        store_num = intent.getIntExtra("store_num", store_num);

        totalMenuNum=0;
        totalMenuPrice=0;
        initialize();
        setHeader();



        LinearLayoutManager menuManager = new LinearLayoutManager(this);
        reservationMenu.setLayoutManager(menuManager);
        ReservationManagerDetailMenuAdapter menuAdapter = new ReservationManagerDetailMenuAdapter(this);
        setReservationMenuData(menuAdapter);
        reservationMenu.setAdapter(menuAdapter);
        reservationCheck.setOnClickListener(this);

        pushData();
    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        if(v.getId()== R.id.reservationCheck){
            finish();

        }
    }

    public void setReservationMenuData(ReservationManagerDetailMenuAdapter menuAdapter ) {

        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createSupplierRetrofitObject();
        SupplierController apiService = hrs.createSupplierApiserverObject();
        Call call = apiService.getMenuList(store_num, reservation.getReservation_num());
        hrs.HttpRequestExecute(call);
        hrs.makeGsonObjectTypeDate();
        Type type = new TypeToken<List<SalesVO>>() {
        }.getType();
        salesList = (ArrayList<SalesVO>) hrs.parsingFunc(type);


        for (SalesVO sales : salesList) {

            ReservationManagerDetailMenuData data1 = new ReservationManagerDetailMenuData();
            data1.setReservationMenu(sales.getSales_menu());


            data1.setReservationMenuNum(String.valueOf(sales.getSales_count()));


            data1.setReservationMenuPrice(String.valueOf(sales.getSales_price()));


            totalMenuNum+=sales.getSales_count();
            totalMenuPrice+=sales.getSales_price();
            menuAdapter.add(data1);

        }
    }

    /////////////////////////////////////데이터 ////////////////////////////////////////
    public void initialize(){
        //헤더 데이터
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container1 = (ScrollView) findViewById(R.id.container1);

        reservationName = (TextView) findViewById(R.id.reservationName);
        reservationPhone = (TextView) findViewById(R.id.reservationPhone);
        reservationDate = (TextView) findViewById(R.id.reservationDate);
        reservationPeopleNum = (TextView) findViewById(R.id.reservationPeopleNum);
        reservationRequest = (TextView) findViewById(R.id.reservationRequest);
        reservationMenu = (RecyclerView) findViewById(R.id.reservationMenu);
        reservationMenuNum = (TextView) findViewById(R.id.reservationMenuNum);
        reservationMenuPrice = (TextView) findViewById(R.id.reservationMenuPrice);
        reservationCheck = (FButton) findViewById(R.id.reservationCheck);
    }

    /////////////////////////////헤더 셋팅/////////////////////////////
    public void setHeader(){
        setActionBar(actionbar);
        setTitleName("예약확인");
        setBackVisivility(View.VISIBLE);
        setMenuBarView(menubar);
        setMenuBarAnimation(container1);
        setMenuListSelectView(cunsumerMenuListSelect);
    }

    public void pushData(){
        reservationName.setText(reservation.getReservation_name());
        reservationPhone.setText(reservation.getReservation_phone());
        reservationDate.setText(simpleDate.format(reservation.getReservation_date()));
        reservationPeopleNum.setText(String.valueOf(reservation.getReservation_total()));
        reservationRequest.setText(reservation.getReservation_request());
        reservationMenu = (RecyclerView) findViewById(R.id.reservationMenu);
        reservationMenuNum.setText(String.valueOf(totalMenuNum));
        reservationMenuPrice.setText(String.valueOf(totalMenuPrice)+"원");
        reservationCheck = (FButton) findViewById(R.id.reservationCheck);

    }
}
