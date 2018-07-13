package org.androidtown.anywhere.any_11_store_reservation;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_02_main.Main;
import org.androidtown.anywhere.any_newVO.CustomerReservationVO;
import org.androidtown.anywhere.any_newVO.MenuVO;
import org.androidtown.anywhere.any_newVO.ReservationListVO;
import org.androidtown.anywhere.any_newVO.SalesVO;
import org.androidtown.anywhere.any_newVO.StoreDetailVO;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.hoang8f.widget.FButton;
import retrofit2.Call;

import static org.androidtown.anywhere.R.id.reservation_PeopleNum;
import static org.androidtown.anywhere.httpcontrol.ImageURLParser.imageURLParser;

public class StoreReservation extends Header implements View.OnClickListener, MaterialSpinner.OnItemSelectedListener {

    final SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
    RelativeLayout actionbar;
    FrameLayout menubar;
    ScrollView container1;

    ImageView storeImage;
    ImageView peoplePlus;
    ImageView peopleMinus;

    TextView storeName;
    TextView storeAddress;
    TextView reservationDate;
    TextView reservationMenuNum;
    TextView reservationMenuPrice;

    EditText customerNick;
    EditText customerPhone;
    EditText reservationPeopleNum;
    EditText reservationRequest;

    MaterialSpinner timeSelector;
    MaterialSpinner daySelector;

    RecyclerView reservationMenu;

    FButton payment;


    StoreDetailVO storeDetailVO;
    StoreReservationMenuAdapter menuAdapter;

    ArrayList<ReservationListVO> reservationList;
    ArrayList<ReservationListVO> reservationListVO;
    ArrayList<String> vaild_reservationDate;
    HashMap<String, ArrayList<ReservationListVO>> reservationMap;
    ArrayList<String> selectedTimes;

    SharedPreferences pref;

    int reservation_num;

    public static final Pattern VALID_HAND_PHONE_NUMBER =
            Pattern.compile("^01(?:0|1|[6-9]) - (?:\\d{3}|\\d{4}) - \\d{4}$", Pattern.CASE_INSENSITIVE); //핸드폰 번호 정규식

    public static final Pattern VALID_PHONE_NUMBER =
            Pattern.compile("^\\d{2,3}-\\d{3,4}-\\d{4}$", Pattern.CASE_INSENSITIVE); //일반 전화번호 정규식


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere11_store_reservation);
        Intent intent = getIntent();
        storeDetailVO = (StoreDetailVO) intent.getSerializableExtra("storeDetailVO");

        getReservationList(this);

        initialize();
        setHeader();
        mainDataSet();

        LinearLayoutManager menuManager = new LinearLayoutManager(this);
        reservationMenu.setLayoutManager(menuManager);
        menuAdapter = new StoreReservationMenuAdapter(this);
        setStoreReservationMenuAdd(menuAdapter);
        reservationMenu.setAdapter(menuAdapter);
        reservationMenu.setNestedScrollingEnabled(false);
    }

    public void getReservationList(Activity activity) {
        Intent intent;
        try {
            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createRetrofitObject();
            CustomerController apiService = hrs.createApiserverObject();
            Call call = apiService.getReservationListVO(storeDetailVO.getStoreVO().getStore_num());
            hrs.HttpRequestExecute(call);

            Type type = new TypeToken<List<ReservationListVO>>() {
            }.getType();
            hrs.makeGsonObjectTypeDate();
            reservationList = (ArrayList<ReservationListVO>) hrs.ObjectParsingFunc2(type);

        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(this, "에러가 발생 하였습니다.", Toast.LENGTH_LONG).show();
            finish();
        }

        if (reservationList.size() == 0) {
            Toast.makeText(this, "사용 가능한 일정이 존재 하지 않습니다.", Toast.LENGTH_LONG).show();
            finish();

        } else if (reservationList.isEmpty()) {
            Toast.makeText(this, "사용 가능한 일정이 존재 하지 않습니다.", Toast.LENGTH_LONG).show();
            activity.finish();


        }


    }


    public void setStoreReservationMenuAdd(StoreReservationMenuAdapter menuAdapter) {
        ArrayList<MenuVO> menuVOList = storeDetailVO.getMenuVO();

        for (MenuVO menu : menuVOList) {
            StoreReservationMenuData data1 = new StoreReservationMenuData();
            data1.setMenuName(menu.getMenu_name());
            data1.setMenuPrice(String.valueOf(menu.getMenu_price()));
            data1.setMenuNum("0");
            menuAdapter.add(data1);
        }

    }

    public void mainDataSet() {

        StoreVO storeVO = storeDetailVO.getStoreVO();
        /*ReservationListVO reservationListVO = storeDetailVO.getReservationListVO();
*/


        storeName.setText(storeVO.getStore_name());
        storeAddress.setText(storeVO.getStore_addr());
        reservationMap = new HashMap<>();
        vaild_reservationDate = new ArrayList<>();
        vaild_reservationDate.add("날짜를 선택해 주세요");
        for (int i = 0; i < reservationList.size(); i++) {


            if (!vaild_reservationDate.contains(simpleDate.format(reservationList.get(i).getReservation_date()))) {
                //만약 vaild_reservaionDate 리스트 안에 같은 날짜 문자열이 없다면
                vaild_reservationDate.add(simpleDate.format(reservationList.get(i).getReservation_date()));
                //vaild_reservaionDate 리스트 안에 날짜 문자열 추가 (문자열 reservationDate 리스트에 중복된 문자열을 넣지 않게 하기 위함)
            }
        }

        reservationListVO = new ArrayList<>();

        for (int i = 1; i < vaild_reservationDate.size(); i++) {
            //추출한 날짜 문자열 사이즈 만큼 반복시킨다.
            for (int j = 0; j < reservationList.size(); j++) {
                //총 예약 리스트 사이즈 만큼 반복시킨다.

                if (simpleDate.format(reservationList.get(j).getReservation_date()).equals(vaild_reservationDate.get(i))) {
                    //만약 reservationList의 예약 날짜와 문자열 리스트에 들어있는 예약 날짜가 같다면

                    reservationListVO.add(reservationList.get(j)); //예약 객체 리스트에 add 시켜라.

                }

            }

            reservationMap.put(simpleDate.format(reservationListVO.get(0).getReservation_date()), reservationListVO);
            reservationListVO = new ArrayList<>();


        }


        Glide.with(this).load(imageURLParser(storeVO.getStore_mainurl())).into(storeImage);

        Iterator<String> keys = reservationMap.keySet().iterator();
        while (keys.hasNext()) {

            String key = keys.next();
            Log.d("giduckTest47", key);
            for (int i = 0; i < reservationMap.get(key).size(); i++) {
                Log.d("giduckTest46", "날짜 : " + simpleDate.format(reservationMap.get(key).get(i).getReservation_date()));
                Log.d("giduckTest46", "시작 시간 : " + reservationMap.get(key).get(i).getReservation_starttime());
                Log.d("giduckTest46", "끝 시간 : " + reservationMap.get(key).get(i).getReservation_endtime());
                Log.d("giduckTest46", "-----------------------------------------------------------\n");
            }
        }

        daySelector.setItems(vaild_reservationDate);

        daySelector.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                setTimeSpinner();

            }
        });

    }

    /////////////////////////////////////데이터 ////////////////////////////////////////
    public void initialize() {
        //헤더 데이터
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container1 = (ScrollView) findViewById(R.id.container1);

        storeName = (TextView) findViewById(R.id.storeName);
        storeAddress = (TextView) findViewById(R.id.storeAddress);
        reservationDate = (TextView) findViewById(R.id.reservationDate);
        reservationMenuNum = (TextView) findViewById(R.id.reservationMenuNum);
        reservationMenuPrice = (TextView) findViewById(R.id.reservationMenuPrice);

        customerNick = (EditText) findViewById(R.id.customerNick);
        customerPhone = (EditText) findViewById(R.id.customerPhone);
        reservationPeopleNum = (EditText) findViewById(reservation_PeopleNum);
        reservationRequest = (EditText) findViewById(R.id.reservationRequest);

        storeImage = (ImageView) findViewById(R.id.storeImage);
        peopleMinus = (ImageView) findViewById(R.id.peopleMinus);
        peoplePlus = (ImageView) findViewById(R.id.peoplePlus);

        reservationMenu = (RecyclerView) findViewById(R.id.reservationMenu);

        timeSelector = (MaterialSpinner) findViewById(R.id.timeSelector);
        daySelector = (MaterialSpinner) findViewById(R.id.daySelector);
        payment = (FButton) findViewById(R.id.payment);

        peopleMinus.setOnClickListener(this);
        peoplePlus.setOnClickListener(this);
        payment.setOnClickListener(this);
        daySelector.setOnItemSelectedListener(this);
    }

    /////////////////////////////헤더 셋팅/////////////////////////////
    public void setHeader() {
        setActionBar(actionbar);
        setTitleName("예약하기");
        setBackVisivility(View.VISIBLE);
        setMenuBarView(menubar);
        setMenuBarAnimation(container1);
        setMenuListSelectView(cunsumerMenuListSelect);
    }


    public void buildCustomerReservationVO() {
        SalesVO salesVO;

        int customerTotalMenuNum = 0;
        int customerTotalMenuPrice = 0;
        ArrayList<SalesVO> salesList = new ArrayList<>();
        ArrayList<StoreReservationMenuData> data = menuAdapter.getDatas();
        HashMap<String, StoreReservationMenuData> dataMap = menuAdapter.getDummyDatas();
        Iterator<String> iterator = dataMap.keySet().iterator();
        while (iterator.hasNext()) {

            String key = iterator.next();

            if (Integer.parseInt(dataMap.get(key).getMenuNum()) != 0) {


                if (dataMap.get(key).getMenuNum() == null) {

                    Toast.makeText(this, "메뉴 개수가 공백입니다. 다시 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (dataMap.get(key).getMenuNum().trim().length() == 0) {

                    Toast.makeText(this, "메뉴 개수가 공백입니다. 다시 선택해 주세요.", Toast.LENGTH_SHORT).show();
                    return;

                }

                salesVO = new SalesVO();
                salesVO.setStore_num(storeDetailVO.getStoreVO().getStore_num());
                salesVO.setSales_count(Integer.parseInt(dataMap.get(key).getMenuNum()));
                salesVO.setSales_menu(key);
                salesVO.setSales_price(Integer.parseInt(dataMap.get(key).getMenuPrice()));
                salesVO.setReservation_num(reservation_num);
/*
            salesVO.setReservation_num(storeDetailVO.getReservationListVO().getReservation_num());
*/

                salesList.add(salesVO);

                Log.d("giduckTest41", salesVO.getSales_menu());
                Log.d("giduckTest41", salesVO.getSales_count() + "");
                Log.d("giduckTest41", salesVO.getSales_price() + "");
                Log.d("giduckTest41", salesVO.getReservation_num() + "");
                Log.d("giduckTest41", salesVO.getStore_num() + "");
            }
        }

        Matcher vaild_handphone_number = VALID_PHONE_NUMBER.matcher(customerPhone.getText().toString());
        Matcher vaild_phone_number = VALID_PHONE_NUMBER.matcher(customerPhone.getText().toString());

        if (Integer.parseInt(reservationMenuNum.getText().toString()) < storeDetailVO.getStoreVO().getStore_min() || Integer.parseInt(reservationMenuNum.getText().toString()) == 0) {

            Toast.makeText(this, "최소 인원 이상 메뉴를 선택하십시오.", Toast.LENGTH_SHORT).show();
            return;
        } else if (customerPhone.getText().toString().trim().length() == 0) {

            Toast.makeText(this, "연락처가 공백입니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        } else if (!vaild_handphone_number.matches() || !vaild_phone_number.matches()) {

            Toast.makeText(this, "전화번호 양식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
            return;
        } else if (customerNick.getText().toString().trim().length() == 0) {

            Toast.makeText(this, "이름을 입력해 주세요", Toast.LENGTH_SHORT).show();
            return;
        } else if (Integer.parseInt(reservationMenuNum.getText().toString()) < Integer.parseInt(reservationPeopleNum.getText().toString())) {

            Toast.makeText(this, "1인 1메뉴 이상 주문 하여야 합니다.", Toast.LENGTH_SHORT).show();
            return;

        }


        ArrayList<ReservationListVO> reservation = new ArrayList<>();
        reservation = reservationMap.get(daySelector.getText().toString());

        ReservationListVO reservationVO = new ReservationListVO();
        reservationVO = reservation.get(timeSelector.getSelectedIndex());
        reservationVO.setStore_num(storeDetailVO.getStoreVO().getStore_num());

        pref = getSharedPreferences("anywhere", MODE_PRIVATE);

        reservationVO.setReservation_nick(pref.getString("user_nick", "")); //sharedPreference로 nick 넘겨줌
        reservationVO.setReservation_phone(customerPhone.getText().toString());
        reservationVO.setReservation_name(customerNick.getText().toString());
        reservationVO.setReservation_total(Integer.parseInt(reservationPeopleNum.getText().toString()));
        reservationVO.setReservation_request(reservationRequest.getText().toString());

        Log.d("giduckTest41", reservationVO.getReservation_name());
        Log.d("giduckTest41", reservationVO.getReservation_nick());
        Log.d("giduckTest41", "" + reservationVO.getReservation_num());
        Log.d("giduckTest41", reservationVO.getReservation_phone());
        Log.d("giduckTest41", reservationVO.getReservation_request());
        Log.d("giduckTest41", reservationVO.getReservation_total() + "");
        Log.d("giduckTest41", "스피너 출력 결과" + timeSelector.getText().toString());

        CustomerReservationVO crVO = new CustomerReservationVO();
        crVO.setReservationListVO(reservationVO);
        crVO.setSalesVO(salesList);


        requestHttp(crVO);
    }


    public void requestHttp(CustomerReservationVO crVO) {

        try {
            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createRetrofitObject();
            CustomerController apiService = hrs.createApiserverObject();
            Call call = apiService.reservationInfoSend(crVO);
            hrs.HttpRequestExecute(call);
            hrs.makeGsonObject();
            String result = hrs.parsingStringFunc("result");
            if (result.equals("true")) {

                Toast.makeText(this, "결제 페이지로 이동합니다!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StoreReservation.this, Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            } else if (result.equals("false")) {

                Toast.makeText(this, "해당 일정에 예약이 존재합니다.", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }

        } catch (Exception e) {

            e.printStackTrace();
            Log.d("errorCode", "소비자 예약 storeReservation exception 에러 발생");
            Toast.makeText(this, "오류가 발생 하였습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    //타임 스피너를 초기화 시켜주는 메소드
    public void setTimeSpinner() {
        try {
            selectedTimes = new ArrayList<>();
            reservation_num = reservationMap.get(daySelector.getText().toString()).get(0).getReservation_num();
            for (int i = 0; i < reservationMap.get(daySelector.getText().toString()).size(); i++) {
                ReservationListVO getReservation = reservationMap.get(daySelector.getText().toString()).get(i);


                String am = "오전";
                String pm = "오후";
                int startTime = getReservation.getReservation_starttime();
                int endTime = getReservation.getReservation_endtime();
                String str;
                if (0 < startTime && startTime < 12) {
                    str = am + " " + startTime + "시 ~ ";
                } else if (startTime == 12) {

                    str = "정오 " + startTime + "시 ~ ";

                } else {
                    str = pm + " " + (startTime - 12) + "시 ~ ";
                }


                if (0 < endTime && endTime < 12) {

                    str += am + " " + endTime + "시";

                } else if (endTime == 12) {

                    str += "정오 " + endTime + "시";

                } else if (endTime == 24) {

                    str += "자정 " + endTime + "시";

                } else {

                    str += pm + " " + (endTime - 12) + "시";
                }


                selectedTimes.add(str);
            }

            timeSelector.setItems(selectedTimes);

        } catch (Exception e) {
            e.printStackTrace();
            ArrayList<String> nullPointerArray = new ArrayList<>();
            nullPointerArray.add(" ");
            timeSelector.setItems(nullPointerArray);
        }


    }

    @Override
    public void onClick(View v) {
        int peopleNum;
        int totalMenuNum;
        switch (v.getId()) {

            case R.id.peoplePlus: {
                peopleNum = Integer.parseInt(reservationPeopleNum.getText().toString());

                reservationPeopleNum.setText(String.valueOf(peopleNum + 1));


                break;

            }

            case R.id.peopleMinus: {
                peopleNum = Integer.parseInt(reservationPeopleNum.getText().toString());
                if (peopleNum != 0) {

                    reservationPeopleNum.setText(String.valueOf(peopleNum - 1));

                }
                break;
            }
            case R.id.payment: {
                buildCustomerReservationVO();
                break;
            }


        }
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        if (view.getId() == R.id.daySelector) {

            setTimeSpinner();


        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }


}

