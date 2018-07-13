package org.androidtown.anywhere.any_20_1_supplier_reservation_manager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.ReservationListVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.SupplierController;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

import static java.lang.Integer.parseInt;

public class ReservationManager extends Header implements OnDateSelectedListener,View.OnClickListener{

    int store_num;
    ReservationManagerAdapter Adapter;
    LinearLayoutManager llm;
    RecyclerView rv;


    RelativeLayout actionbar;
    FrameLayout menubar;
    MaterialCalendarView calendarView;
    TextView currentReservationNumber;
    LinearLayout subController;
    Button reservation_all;

    final SimpleDateFormat simpleDate = new SimpleDateFormat("yy-MM-dd");


    //레트로핏에서 받아온 데이터
    ArrayList<ReservationListVO> reservationList;
    //현재 예약중 수
    int nowReservation;

    //캘린더 관련 데이터
    ArrayList<String> reservedTimeTable;
    ArrayList<String> noReservedTimeTable;
    List<CalendarDay> noReservedList;
    List<CalendarDay> reservedList;
    Calendar calendar;
    String[] tempArray;
    String[] todayArray;
    String todayStr;
    int reservedDate_Year;
    int reservedDate_Month;
    int reservedDate_Day;
    int today_Year;
    int today_Month;
    int today_Day;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere20_1_reservation_manager_detail);


        Intent intent = getIntent();
        store_num = intent.getIntExtra("store_num", store_num);
        nowReservation = 0;

        setData();
        viewInit();
        setHeader();
        setCalender();
        setReservationDate();
        markReservation();

        Adapter = new ReservationManagerAdapter(reservationList, store_num, this);
        llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setAdapter(Adapter);
    }

    public void viewInit(){
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        rv = (RecyclerView) findViewById(R.id.reservationManager_recyclerView);
        currentReservationNumber = (TextView) findViewById(R.id.currentReservationNumber);
        subController = (LinearLayout) findViewById(R.id.subController);

        calendarView = (MaterialCalendarView) findViewById(R.id.ReservationManager_calendar);

        reservation_all = (Button) findViewById(R.id.reservation_all);
        reservation_all.setOnClickListener(this);

    }

    //헤더 셋팅
    public void setHeader(){
        //액션바 셋팅
        setActionBar(actionbar);
        //액션바 이름 셋팅
        setTitleName("예약 관리");
        //액션바 안에 백버튼 보일지 안보일지 셋팅
        setBackVisivility(View.VISIBLE);
        //메뉴바 셋팅
        setMenuBarView(menubar);
        //메뉴바 에니메이션 셋팅
        setMenuBarAnimation(rv);
        //메뉴바 안에 리스트 셋
        setMenuListSelectView(supplierMenuListSelect);

    }


    //캘린더 셋팅
    public void setCalender() {
        //달력 타이틀 고치기
        Locale locale = new Locale("ko");
        DateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월", locale);
        DateFormatTitleFormatter titleFormatter = new DateFormatTitleFormatter(dateFormat);
        calendarView.setTitleFormatter(titleFormatter);

        java.util.Date date = new java.util.Date();

        calendarView.setSelectionColor(Color.parseColor("#8c8cf5"));
        calendarView.state().edit().setMinimumDate(date).commit();
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        calendarView.setOnDateChangedListener(this);
    }

    //레트로핏 받은 데이터
    public void setData() {

        try {
            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createSupplierRetrofitObject();
            SupplierController apiService = hrs.createSupplierApiserverObject();
            Call call = apiService.getReservationData(store_num);
            hrs.HttpRequestExecute(call);
            hrs.makeGsonObjectTypeDate();
            Type type = new TypeToken<List<ReservationListVO>>() {
            }.getType();
            reservationList = (ArrayList<ReservationListVO>) hrs.parsingFunc(type);
        }catch (Exception e){
            Toast.makeText(this,"통신 에러 발생",Toast.LENGTH_SHORT).show();
        }
    }

    public void setReservationDate(){

        reservedTimeTable = new ArrayList<>();
        noReservedTimeTable = new ArrayList<>();

        for (int i=0;i<reservationList.size();i++) {

            if(reservationList.get(i).getReservation_state() == 0){

                noReservedTimeTable.add(simpleDate.format(reservationList.get(i).getReservation_date()));

            } else if (reservationList.get(i).getReservation_state() == 1) {

                nowReservation++;
                reservedTimeTable.add(simpleDate.format(reservationList.get(i).getReservation_date()));

            }

        }
        //현재 예약 건수
        currentReservationNumber.setText(Integer.toString(nowReservation));
    }


    public void markReservation() {

        java.util.Date today = new java.util.Date();

        todayStr = simpleDate.format(today);
        //오늘 날짜
        todayArray = todayStr.split("-");

        today_Year = parseInt(todayArray[0]);
        today_Month = parseInt(todayArray[1]);
        today_Day = parseInt(todayArray[2]);

        Log.d("calendar", "오늘" + today_Year + "년" + today_Month + "월" + today_Day + "일");

        /*   ---------------------------------회원이 예약한 날짜에만 점으로 표시해 주는 메소드----------------------------------*/
        reservedList = new ArrayList<>();
        //예약 된 날짜
        for (int i = 0; i< reservedTimeTable.size(); i++) {

            calendar = Calendar.getInstance();
            tempArray = reservedTimeTable.get(i).split("-");

            reservedDate_Year = parseInt(tempArray[0]);
            reservedDate_Month = parseInt(tempArray[1]);
            reservedDate_Day = parseInt(tempArray[2]);

            Log.d("calendar", "예약된날" + reservedDate_Year + "년" + reservedDate_Month + "월" + reservedDate_Day + "일");

            if (today_Year > reservedDate_Year) {
                calendar.add(Calendar.YEAR, reservedDate_Year - today_Year);

                Log.d("calendar", "예약한 년 < 이번년" + (reservedDate_Year - today_Year));


            } else if (today_Year < reservedDate_Year) {

                calendar.add(Calendar.YEAR, reservedDate_Year - today_Year);

                Log.d("calendar", "예약한 년 > 이번년" + (reservedDate_Year - today_Year));

            } else if (today_Year == reservedDate_Year) {
                calendar.add(Calendar.YEAR, 0);

            }


            if (today_Month > reservedDate_Month) {
                calendar.add(Calendar.MONTH, reservedDate_Month - today_Month);

                Log.d("calendar", "예약한 달 < 이번달" + (reservedDate_Month - today_Month));


            } else if (today_Month < reservedDate_Month) {

                calendar.add(Calendar.MONTH, reservedDate_Month - today_Month);

                Log.d("calendar", "예약한 달 > 이번달" + (reservedDate_Month - today_Month));

            } else if (today_Month == reservedDate_Month) {
                calendar.add(Calendar.MONTH, 0);

            }


            if (today_Day > reservedDate_Day) {
                calendar.add(Calendar.DAY_OF_MONTH, reservedDate_Day - today_Day);

                Log.d("calendar", "예약한 일<오늘" + (reservedDate_Day - today_Day));

            } else if (today_Day < reservedDate_Day) {

                calendar.add(Calendar.DAY_OF_MONTH, reservedDate_Day - today_Day);

                Log.d("calendar", "예약한 일>오늘" + (reservedDate_Day - today_Day));


            } else if (today_Day == reservedDate_Day) {

                calendar.add(Calendar.DAY_OF_MONTH, 0);

            }

            CalendarDay calendarDay = CalendarDay.from(calendar);
            reservedList.add(calendarDay);

        }

        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return reservedList.contains(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAnywhereBlue)));
            }
        });

/*   ---------------------------------사업자가 등록한 모든 예약 일정을 보여주는 메소드----------------------------------*/
        noReservedList = new ArrayList<>();

        //예약 안된 날짜
        for (int i = 0; i< noReservedTimeTable.size(); i++) {

            calendar = Calendar.getInstance();
            tempArray = noReservedTimeTable.get(i).split("-");

            reservedDate_Year = parseInt(tempArray[0]);
            reservedDate_Month = parseInt(tempArray[1]);
            reservedDate_Day = parseInt(tempArray[2]);

            Log.d("calendar", "예약날" + reservedDate_Year + "년" + reservedDate_Month + "월" + reservedDate_Day + "일");

            if (today_Year > reservedDate_Year) {
                calendar.add(Calendar.YEAR, reservedDate_Year - today_Year);

                Log.d("calendar", "예약한 년 < 이번년" + (reservedDate_Year - today_Year));


            } else if (today_Year < reservedDate_Year) {

                calendar.add(Calendar.YEAR, reservedDate_Year - today_Year);

                Log.d("calendar", "예약한 년 > 이번년" + (reservedDate_Year - today_Year));

            } else if (today_Year == reservedDate_Year) {
                calendar.add(Calendar.YEAR, 0);

            }

            if (today_Month > reservedDate_Month) {
                calendar.add(Calendar.MONTH, reservedDate_Month - today_Month);

                Log.d("calendar", "예약한 달 < 이번달" + (reservedDate_Month - today_Month));


            } else if (today_Month < reservedDate_Month) {

                calendar.add(Calendar.MONTH, reservedDate_Month - today_Month);

                Log.d("calendar", "예약한 달 > 이번달" + (reservedDate_Month - today_Month));

            } else if (today_Month == reservedDate_Month) {
                calendar.add(Calendar.MONTH, 0);

            }


            if (today_Day > reservedDate_Day) {
                calendar.add(Calendar.DAY_OF_MONTH, reservedDate_Day - today_Day);

                Log.d("calendar", "예약한 일<오늘" + (reservedDate_Day - today_Day));

            } else if (today_Day < reservedDate_Day) {

                calendar.add(Calendar.DAY_OF_MONTH, reservedDate_Day - today_Day);

                Log.d("calendar", "예약한 일>오늘" + (reservedDate_Day - today_Day));


            } else if (today_Day == reservedDate_Day) {

                calendar.add(Calendar.DAY_OF_MONTH, 0);

            }

            CalendarDay calendarDay = CalendarDay.from(calendar);
            noReservedList.add(calendarDay);

        }

        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return noReservedList.contains(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(10,getResources().getColor(R.color.colorAnywhereOrange)));

            }
        });

    }


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

        //날짜를 클릭했을 경우 관련 데이터
        String clickDay;

        if (selected) {

            clickDay = simpleDate.format(date.getDate());

            ArrayList<ReservationListVO> reservationItems = new ArrayList<>();


            for (ReservationListVO reservation : reservationList) {

                if (simpleDate.format(reservation.getReservation_date()).equals(clickDay)) {

                    reservationItems.add(reservation);
                }

            }

            Adapter.reservationData(reservationItems);

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id==reservation_all.getId()){
            Adapter.reservationData(reservationList);
        }
    }
}
