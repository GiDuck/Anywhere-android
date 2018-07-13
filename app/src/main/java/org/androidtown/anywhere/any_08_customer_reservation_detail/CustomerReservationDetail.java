package org.androidtown.anywhere.any_08_customer_reservation_detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_07_customer_reservation.CustomerReservation;
import org.androidtown.anywhere.any_newVO.ReservationListVO;
import org.androidtown.anywhere.any_newVO.SalesVO;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;
import retrofit2.Call;

//고객 예약 상세보기
public class CustomerReservationDetail extends Header implements View.OnClickListener {
    RelativeLayout actionbar;
    FrameLayout menubar;
    ScrollView container1;

    TextView reservationStoreName;
    TextView reservationDate;
    TextView reservationAddress;
    TextView reservationRequest;
    RecyclerView reservationMenu;
    TextView reservationMenuNum;
    TextView reservationMenuPrice;
    FButton reservationCheck;

    String user_nick;
    int store_num;
    int reservation_num;
    ArrayList<SalesVO> salesVO;
    StoreVO storeVO;
    ReservationListVO reservationListVO;

    SimpleDateFormat simpleDate = new SimpleDateFormat("yy년 MM월 dd일");

    int totalPrice;
    int totalNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere08_customer_reservation_detail);
        Intent intent = getIntent();
        store_num = Integer.parseInt(intent.getStringExtra("store_num"));
        reservation_num = Integer.parseInt(intent.getStringExtra("reservation_num"));
        storeVO = (StoreVO) intent.getSerializableExtra("storeVO");
        reservationListVO = (ReservationListVO) intent.getSerializableExtra("reservationListVO");
        //필요한 데이터들을 인텐트를 통해 전달 받음.


        totalPrice = 0; //총 가격
        totalNum = 0; // 총 선택 갯수

        //세션 유지를 위한 데이터 세팅
        SharedPreferences infoStoarge = getSharedPreferences("anywhere", MODE_PRIVATE);
        user_nick = infoStoarge.getString("user_nick", "");

        initialize(); //초기화
        setHeader(); //헤더 장착

        LinearLayoutManager menuManager = new LinearLayoutManager(this);
        reservationMenu.setLayoutManager(menuManager);
        CustomerReservationMenuAdapter menuAdapter = new CustomerReservationMenuAdapter(this);

        reservationMenu.setAdapter(menuAdapter);
        setReservationMenuData(menuAdapter);
        reservationCheck.setOnClickListener(this);

        dataSet();

    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        if (v.getId() == R.id.reservationCheck) {
            intent = new Intent(CustomerReservationDetail.this, CustomerReservation.class);
            startActivity(intent);
            finish();

        }
    }

    //메뉴 데이터를 서버로 부터 받아서 세팅해 주는 메소드
    public void setReservationMenuData(CustomerReservationMenuAdapter menuAdapter) {
        Log.d("testCode", "기덕 닉네임 : " + user_nick);
        Log.d("testCode", "기덕 가게번호 : " + store_num);
        Log.d("testCode", "기덕 예약번호 : " + reservation_num);

        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createRetrofitObject();
        CustomerController apiService = hrs.createApiserverObject();
        Call call = apiService.getDetailReservationList(store_num, reservation_num);
        hrs.HttpRequestExecute(call);
        hrs.makeGsonObjectTypeDate();
        Type type = new TypeToken<List<SalesVO>>() {
        }.getType();
        salesVO = (ArrayList<SalesVO>) hrs.parsingFunc(type);


        for (int i = 0; i < salesVO.size(); i++) {

            CustomerReservationMenuData data1 = new CustomerReservationMenuData();
            data1.setReservationMenu(salesVO.get(i).getSales_menu());
            data1.setReservationMenuNum(String.valueOf(salesVO.get(i).getSales_count()));
            data1.setReservationMenuPrice(String.valueOf(salesVO.get(i).getSales_price()));
            menuAdapter.add(data1);
            //어뎁터에 해당 예약 정보 중 메뉴 예약 내역을 세팅함

            totalPrice += salesVO.get(i).getSales_price();
            totalNum += salesVO.get(i).getSales_count();
            //총 가격과 총 갯수 계산


        }


    }

    //예약 내역 상세 보기에 있는 뷰 들에 데이터를 세팅 해 주는 메소드
    public void dataSet() {

        reservationStoreName.setText(storeVO.getStore_name());
        Log.d("giduckTest44", "dataSet : " + storeVO.getStore_name());
        reservationDate.setText(simpleDate.format(reservationListVO.getReservation_date()));
        Log.d("giduckTest44", "dataSet : " + simpleDate.format(reservationListVO.getReservation_date()));
        reservationAddress.setText(storeVO.getStore_addr());
        Log.d("giduckTest44", "dataSet : " + storeVO.getStore_addr());
        reservationRequest.setText(reservationListVO.getReservation_request());
        Log.d("giduckTest44", "dataSet : " + reservationListVO.getReservation_request());

        reservationMenuNum.setText(String.valueOf(totalNum));
        reservationMenuPrice.setText(String.valueOf(totalPrice));


    }

    /////////////////////////////////////데이터 ////////////////////////////////////////
    public void initialize() {
        //헤더 데이터
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container1 = (ScrollView) findViewById(R.id.container1);

        reservationStoreName = (TextView) findViewById(R.id.reservationStoreName);
        reservationDate = (TextView) findViewById(R.id.reservationDate);
        reservationAddress = (TextView) findViewById(R.id.reservationAddress);
        reservationRequest = (TextView) findViewById(R.id.reservationRequest);
        reservationMenu = (RecyclerView) findViewById(R.id.reservationMenu);
        reservationMenuNum = (TextView) findViewById(R.id.reservationMenuNum);
        reservationMenuPrice = (TextView) findViewById(R.id.reservationMenuPrice);
        reservationCheck = (FButton) findViewById(R.id.reservationCheck);
    }

    /////////////////////////////헤더 셋팅/////////////////////////////
    public void setHeader() {
        setActionBar(actionbar);
        setTitleName("예약확인");
        setBackVisivility(View.VISIBLE);
        setMenuBarView(menubar);
        setMenuBarAnimation(container1);
        setMenuListSelectView(cunsumerMenuListSelect);
    }

}
