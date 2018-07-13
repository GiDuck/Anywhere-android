package org.androidtown.anywhere.any_22_supplier_addplace2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_17_supplier_main.SupplierMain;
import org.androidtown.anywhere.any_newVO.ReservationListVO;
import org.androidtown.anywhere.any_newVO.ReservationVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.SupplierController;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;
import retrofit2.Call;

import static java.lang.Integer.parseInt;

public class AddPlaceSecond extends Header implements View.OnClickListener {

   private final SimpleDateFormat simpleDate = new SimpleDateFormat("yy-MM-dd");

    private RelativeLayout actionbar;
    private FrameLayout menubar;
    private ScrollView sv;

    int store_num;

    ReservationVO reservationVO;

    //캘린더
    MaterialCalendarView calendarView;
    RadioButton singlePick;
    RadioButton rangePick;
    int radioCount = 0;
    HashSet<String> dateSet;

    //모임 설정
    CheckBox checkMeet;
    CheckBox checkShow;
    CheckBox checkCommon;
    CheckBox checkStudy;
    CheckBox checkOpen;
    CheckBox checkElse;
    CheckBox checkParty;
    CheckBox checkSpeach;
    //등록하기
    FButton submitBtn;
    //예약시간 설정
    MaterialSpinner startTimeSpinner;
    MaterialSpinner endTimeSpinner;



    //예약시간 설정을 위한 데이터
    Calendar calendar;
    ArrayList<String> noReservedTimeTable;
    ArrayList<String> reservedTimeTable;
    Date today = new Date();
    String[] tempArray;
    String[] todayArray;
    String todayStr;
    int reservedDate_Year;
    int reservedDate_Month;
    int reservedDate_Day;
    int today_Year;
    int today_Month;
    int today_Day;
    List<CalendarDay> noReservedList;
    List<CalendarDay> reservedList;
    //HttpRequest 객체
    Call call;
    List<ReservationListVO> reservationSchedule;
    Type type;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere22_add_place_second);

        Intent intent = getIntent();

        store_num = intent.getIntExtra("store_num", store_num);
        reservationVO = new ReservationVO();
        todayStr = simpleDate.format(today);


        init();
        setDate();
        calendarSet();
        markReservation();

        if(radioCount==0){
            calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);
        }

        dateSet = new HashSet<>();

        calendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {

                Log.d("기덕테스트", "*********************************다중선택!***********************************");
                Log.d("Test", "test1 " + dates.get(0).getDate());
                Log.d("Test", "test1 " + dates.get(0).getMonth());

                for (CalendarDay day : dates) {
                    if (day.getMonth() == 12) {

                        dateSet.add(Integer.toString(day.getYear()) + "/" + 1 + "/" + day.getDay());

                    } else {
                        dateSet.add(Integer.toString(day.getYear()) + "/" + (Integer.valueOf(day.getMonth()) + Integer.valueOf(1)) + "/" + day.getDay());
                    }
                    Log.d("range select calender",  Integer.toString(day.getYear()) + "/" + (Integer.valueOf(day.getMonth()) + Integer.valueOf(1)) + "/" + day.getDay());
                }


            }
        });


        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String str = null;
                Log.d("기덕테스트", "***********************한개씩선택************************");

                if (date.getMonth() == 12) {

                    str = Integer.toString(date.getYear()) + "/" + 1 + "/" + date.getDay();
                    Log.d("기덕테스트", "str 1 : " + str);

                    if(dateSet.contains(str)) {

                        dateSet.remove(str);
                        Log.d("기덕테스트", "str 1 제거됌");

                    }else if(!dateSet.contains(str)){
                        dateSet.add(str);
                        Log.d("기덕테스트", "str 1 추가됌");


                    }
                } else {
                    str = Integer.toString(date.getYear()) + "/" + (Integer.valueOf(date.getMonth()) + Integer.valueOf(1)) + "/" + date.getDay();
                    Log.d("기덕테스트", "str 2 : " + str);

                    if(dateSet.contains(str)) {

                        dateSet.remove(str);
                        Log.d("기덕테스트", "str 2 삭제됌 ");

                    }else if(!dateSet.contains(str)){
                        Log.d("기덕테스트", "str  2추가됌 ");

                        dateSet.add(str);

                    }
                }
                Log.d("one select calender", Integer.toString(date.getYear()) + "/" + (Integer.valueOf(date.getMonth()) + Integer.valueOf(1)) + "/" + date.getDay());


            }
        });


    }

    //생성자
    public void setDate() {
        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createSupplierRetrofitObject();
        SupplierController apiService = hrs.createSupplierApiserverObject();

        call = apiService.getReservationListVO(store_num);
        hrs.HttpRequestExecute(call);
        hrs.makeGsonObjectTypeDate();

        type = new TypeToken<List<ReservationListVO>>() {
        }.getType();
        reservationSchedule = (ArrayList<ReservationListVO>) hrs.parsingFunc(type);
    }



    public void init()

    {

        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        sv = (ScrollView) findViewById(R.id.container1);

        SharedPreferences pref = getSharedPreferences("anywhere", MODE_PRIVATE);

        //액션바 셋팅
        setActionBar(actionbar);
        //액션바 이름 셋팅
        setTitleName("예약 등록");
        //액션바 안에 백버튼 보일지 안보일지 셋팅
        setBackVisivility(View.VISIBLE);
        //메뉴바 셋팅
        setMenuBarView(menubar);
        //메뉴바 에니메이션 셋팅
        setMenuBarAnimation(sv);
        //메뉴바 안에 리스트 셋
        setMenuListSelectView(pref.getString("user_division", ""));


        singlePick = (RadioButton) findViewById(R.id.singlePick);
        rangePick = (RadioButton) findViewById(R.id.rangePick);
        calendarView = (MaterialCalendarView) findViewById(R.id.addPlace2_Calendar);
        submitBtn = (FButton) findViewById(R.id.addPlace2_submit);

        singlePick.setOnClickListener(this);
        rangePick.setOnClickListener(this);
        submitBtn.setOnClickListener(this);

        reservedTimeTable = new ArrayList<>();


        checkMeet = (CheckBox) findViewById(R.id.checkMeet);
        checkShow = (CheckBox) findViewById(R.id.checkShow);
        checkCommon = (CheckBox) findViewById(R.id.checkCommon);
        checkStudy = (CheckBox) findViewById(R.id.checkStudy);
        checkOpen = (CheckBox) findViewById(R.id.checkOpen);
        checkElse = (CheckBox) findViewById(R.id.checkElse);
        checkParty = (CheckBox) findViewById(R.id.checkParty);
        checkSpeach = (CheckBox) findViewById(R.id.checkSpeach);

        String[] timeItem = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};

        startTimeSpinner = (MaterialSpinner) findViewById(R.id.addPlace2_startHour);
        endTimeSpinner = (MaterialSpinner) findViewById(R.id.addPlace2_EndHour);

        startTimeSpinner.setItems(timeItem);
        endTimeSpinner.setItems(timeItem);

    }


    //캘린더 초기화
    public void calendarSet() {

        Locale locale = new Locale("ko");
        DateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월", locale);
        DateFormatTitleFormatter titleFormatter = new DateFormatTitleFormatter(dateFormat);
        calendarView.setTitleFormatter(titleFormatter);

        Date date = new Date();

        calendarView.state().edit().setMinimumDate(date).commit();


    }


    public void markReservation() {


        //오늘 날짜
        todayArray = todayStr.split("-");

        today_Year = parseInt(todayArray[0]);
        today_Month = parseInt(todayArray[1]);
        today_Day = parseInt(todayArray[2]);

        Log.d("calendar", "오늘" + today_Year + "년" + today_Month + "월" + today_Day + "일");

        reservedTimeTable = new ArrayList<>();
        noReservedTimeTable = new ArrayList<>();
        for(int i = 0; i <reservationSchedule.size();i++){
            if(reservationSchedule.get(i).getReservation_state()==1) {

                reservedTimeTable.add(simpleDate.format(reservationSchedule.get(i).getReservation_date()));

            }else if(reservationSchedule.get(i).getReservation_state()==0){

                noReservedTimeTable.add(simpleDate.format(reservationSchedule.get(i).getReservation_date()));
            }
        }


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
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.singlePick:
                calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE);
                radioCount=1;
                break;

            case R.id.rangePick:
                calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);
                radioCount=1;
                break;

            case R.id.addPlace2_submit:

                CheckBox[] checkBox = {checkMeet, checkShow, checkCommon, checkStudy, checkOpen, checkElse, checkParty, checkSpeach};
                String checkStr = null;


                if (dateSet.size()==0) {
                    Toast.makeText(AddPlaceSecond.this, "날짜를 하나 이상 입력해 주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                String startHour = startTimeSpinner.getText().toString();
                String endHour = endTimeSpinner.getText().toString();

                if (parseInt(startHour) >= parseInt(endHour)) {
                    Toast.makeText(AddPlaceSecond.this, "시작 시간은 끝나는 시간 보다 먼저 있어야 합니다.", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (parseInt(startHour) == parseInt(endHour)) {
                    Toast.makeText(AddPlaceSecond.this, "운영 시간은 한 시간 이상 선택하십시오", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat transDateFormat = new SimpleDateFormat("yyyy/MM/dd");

                ArrayList<Date> dates = new ArrayList<>();

                Iterator<String> it = dateSet.iterator();

                while (it.hasNext()){
                    try {
                        dates.add(transDateFormat.parse(it.next()));
                    }catch (Exception e){

                    }
                }

                reservationVO.setReservation_date(dates);
                reservationVO.setReservation_starttime(Integer.parseInt(startHour));
                reservationVO.setReservation_endtime(Integer.parseInt(endHour));
                reservationVO.setStore_num(store_num);

                int token = 0;
                for (int i = 0; i < checkBox.length; i++) {
                    if (checkBox[i].isChecked()) {
                        if (token == 0) {
                            checkStr = checkBox[i].getText().toString();
                            token++;
                        } else {
                            checkStr += checkBox[i].getText();

                        }
                    }
                }

                reservationVO.setReservation_type(checkStr);

                Log.d("giducKTest", "체크박스 타입" + checkStr);
                Log.d("giduckTest", "예약VO 예약 날짜 개수" + reservationVO.getReservation_date().size());
                Log.d("giduckTest", "예약VO 시작 시간" + reservationVO.getReservation_starttime());
                Log.d("giduckTest", "예약VO 종료 시간" + reservationVO.getReservation_endtime());
                Log.d("giduckTest", "가게 번호" + reservationVO.getStore_num());

                HttpRequestSyncObject hrs = new HttpRequestSyncObject();
                hrs.createSupplierRetrofitObject();
                SupplierController apiService = hrs.createSupplierApiserverObject();
                Call call = apiService.setReservationVO(reservationVO);
                hrs.HttpRequestExecute(call);
                String result = hrs.parsingStringFunc("result");

                if (result.equals("true")) {

                    Toast.makeText(this, "예약이 등록되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddPlaceSecond.this, SupplierMain.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (result.equals("false")) {

                    Toast.makeText(this, "예약 날짜가 중복됩니다.", Toast.LENGTH_SHORT).show();
                    break;
                } else {

                    Toast.makeText(this, "통신 실패", Toast.LENGTH_SHORT).show();
                    break;
                }


                break;

        }
    }
}
