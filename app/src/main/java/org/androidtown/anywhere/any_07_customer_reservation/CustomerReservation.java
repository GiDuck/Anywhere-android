package org.androidtown.anywhere.any_07_customer_reservation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.CustomerReservationCheckVO;
import org.androidtown.anywhere.any_newVO.ReservationListVO;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;

import static org.androidtown.anywhere.httpcontrol.ImageURLParser.imageURLParser;

//고객 예약 보기
public class CustomerReservation extends Header {

    RelativeLayout actionBar;
    FrameLayout menuBar;
    ScrollView container1;
    RecyclerView reservationList;
    Date date = new Date();
    String user_nick;
    CustomerReservationCheckVO datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere07_customer_reservation);

        //세션 유지를 위한 데이터 세팅
        SharedPreferences infoStoarge = getSharedPreferences("anywhere", MODE_PRIVATE);
        user_nick = infoStoarge.getString("user_nick", "");


        actionBar = (RelativeLayout) findViewById(R.id.actionbar);
        menuBar = (FrameLayout) findViewById(R.id.menubar);
        container1 = (ScrollView) findViewById(R.id.container1);
        reservationList = (RecyclerView) findViewById(R.id.reservationList);

        //액션바 셋팅
        setActionBar(actionBar);
        //액션바 이름 셋팅
        setTitleName("예약 내역");
        //액션바 안에 백버튼 보일지 안보일지 셋팅
        setBackVisivility(View.VISIBLE);

        //메뉴바 셋팅
        setMenuBarView(menuBar);
        //메뉴바 에니메이션 셋팅
        setMenuBarAnimation(container1);
        //메뉴바 안에 리스트 셋팅
        setMenuListSelectView(cunsumerMenuListSelect);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        CustomerReservationListAdapter adapter = new CustomerReservationListAdapter(this);
        setReservationListData(adapter);
        reservationList.setLayoutManager(layoutManager);
        reservationList.setAdapter(adapter);


    }

    //리사이클러뷰에 들어갈 데이터들을 받아서 어뎁터에 추가 해 주는 메소드
    public void setReservationListData(CustomerReservationListAdapter adapter) {
        try {
            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createRetrofitObject();
            CustomerController apiService = hrs.createApiserverObject();
            Call call = apiService.getReservationList(user_nick);
            hrs.HttpRequestExecute(call);
            hrs.makeGsonObjectTypeDate();
            Type type = new TypeToken<CustomerReservationCheckVO>() {
            }.getType();
            datas = (CustomerReservationCheckVO) hrs.parsingFunc(type);
            adapter.setReservationCheckVO(datas);

            //건네받은 VO에서 데이터를 빼서 어뎁터의 add메소드를 통해 데이터를 세팅.
            for (int i = 0; i < datas.getReservationListVO().size(); i++) {

                ReservationListVO reservation = datas.getReservationListVO().get(i);
                StoreVO store = datas.getStoreVO().get(i);

                CustomerReservationListData data1 = new CustomerReservationListData();
                data1.setStoreImage(imageURLParser(store.getStore_mainurl()));
                data1.setStoreName(store.getStore_name());
                data1.setStoreAddress(store.getStore_addr());
                data1.setReservationDate(new SimpleDateFormat("yyyy-MM-dd HH시 MM분").format(reservation.getReservation_date()));
                data1.setReservationState(String.valueOf(reservation.getReservation_state()));
                data1.setStore_num(String.valueOf(reservation.getStore_num()));
                data1.setReservation_num(String.valueOf(reservation.getReservation_num()));
                adapter.add(data1);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
