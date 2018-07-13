package org.androidtown.anywhere.any_12_search_store;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_13_search_result.SearchResult;
import org.androidtown.anywhere.header.Header;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class SearchDetailStore extends Header implements OnDateSelectedListener, View.OnClickListener, PlaceSelectionListener {

    RelativeLayout actionbar;
    FrameLayout menubar;
    ScrollView container1;


    MaterialCalendarView searchCalender; //캘린더
    Date date;

    MaterialSpinner minTime; //시작시간
    MaterialSpinner maxTime; //종료시간

    //지역 검색
    PlaceAutocompleteFragment storeAutoAddress;
    String address;

    ImageView peoplePlus; //인원수 추가
    ImageView peopleMinus; //인원수 빼기
    EditText reservationPeopleNum; //인원수

    //라디오 버튼
    RadioButton radioMeet; //미팅
    RadioButton radioDisplay; //전시회
    RadioButton radioNormal; //보통
    RadioButton radioStudy; //스터디
    RadioButton radioOpenMarket; //오픈마켓
    RadioButton radioParty; //파티
    RadioButton radioLecture; //강연
    RadioButton radioEx; //기타

    String selectDateStr; //선택 날짜

    RadioButton[] radioArray;

    FButton detail_search_btn;

    String[] timeItem = {"01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere12_search_detail_store);


        initialize();
        setHeader();

        minTime.setItems(timeItem);
        maxTime.setItems(timeItem);
        setSearchCalender();


    }


    @Override
    public void onPlaceSelected(Place place) {
        address = place.getAddress().toString();
        Log.d("주소", address);
    }

    @Override
    public void onError(Status status) {

    }

    //달력 셋팅
    public void setSearchCalender() {
        //달력 타이틀 고치기
        Locale locale = new Locale("ko");
        DateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월", locale);
        DateFormatTitleFormatter titleFormatter = new DateFormatTitleFormatter(dateFormat);
        searchCalender.setTitleFormatter(titleFormatter);

        date = new Date();

        searchCalender.setSelectionColor(Color.parseColor("#8c8cf5"));
        searchCalender.state().edit().setMinimumDate(date).commit();
        searchCalender.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        searchCalender.setOnDateChangedListener(this);
    }

    //단일 요일 클릭시
    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        selectDateStr = dateFormat.format(date.getDate());
        Log.d("selectDate", selectDateStr);
    }

    public void initialize() {
        //헤더 데이터
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container1 = (ScrollView) findViewById(R.id.container1);

        searchCalender = (MaterialCalendarView) findViewById(R.id.searchCalender);
        minTime = (MaterialSpinner) findViewById(R.id.minTime);
        maxTime = (MaterialSpinner) findViewById(R.id.maxTime);


        peoplePlus = (ImageView) findViewById(R.id.peoplePlus); //인원수 추가
        peopleMinus = (ImageView) findViewById(R.id.peopleMinus); //인원수 빼기
        reservationPeopleNum = (EditText) findViewById(R.id.reservationPeopleNum); //인원수

        //라디오 버튼
        radioMeet = (RadioButton) findViewById(R.id.radioMeet); //미팅
        radioDisplay = (RadioButton) findViewById(R.id.radioDisplay); //전시회
        radioNormal = (RadioButton) findViewById(R.id.radioNormal); //보통
        radioStudy = (RadioButton) findViewById(R.id.radioStudy); //스터디
        radioOpenMarket = (RadioButton) findViewById(R.id.radioOpenMarket); //오픈마켓
        radioParty = (RadioButton) findViewById(R.id.radioParty); //파티
        radioLecture = (RadioButton) findViewById(R.id.radioLecture); //강연
        radioEx = (RadioButton) findViewById(R.id.radioEx); //기타

        detail_search_btn = (FButton) findViewById(R.id.detail_search_btn);

        radioArray = new RadioButton[]{radioMeet, radioDisplay, radioNormal, radioStudy, radioOpenMarket, radioParty, radioLecture, radioEx};

        peoplePlus.setOnClickListener(this);
        peopleMinus.setOnClickListener(this);
        detail_search_btn.setOnClickListener(this);


        for (int i = 0; i < radioArray.length; i++) {

            radioArray[i].setOnClickListener(this);
        }

        storeAutoAddress = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.storeAutoAddress);
        storeAutoAddress.setOnPlaceSelectedListener(this);
        storeAutoAddress.setHint("ex)부산시 해운대구 반송2동");
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build();
        storeAutoAddress.setFilter(typeFilter);

    }

    public void setHeader() {
        setActionBar(actionbar);
        setTitleName("상세검색");
        setBackVisivility(View.VISIBLE);
        setMenuBarView(menubar);
        setMenuBarAnimation(container1);
        setMenuListSelectView(cunsumerMenuListSelect);
    }

    public void checkRadioBtn(View view) {


        RadioButton radio = (RadioButton) view;

        for (int i = 0; i < radioArray.length; i++) {

            if (radioArray[i].isChecked()) {

                radioArray[i].setChecked(false);

            }

        }

        radio.setChecked(true);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.peoplePlus: {
                try {
                    int plusNum = Integer.parseInt(reservationPeopleNum.getText().toString());
                    reservationPeopleNum.setText(String.valueOf(plusNum + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            case R.id.peopleMinus: {

                try {
                    int minusNum = Integer.parseInt(reservationPeopleNum.getText().toString());
                    if (minusNum != 0) {
                        reservationPeopleNum.setText(String.valueOf(minusNum - 1));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }


            case R.id.radioMeet: {
                checkRadioBtn(v);
                break;
            }
            case R.id.radioDisplay: {
                checkRadioBtn(v);

                break;
            }

            case R.id.radioNormal: {
                checkRadioBtn(v);
                break;
            }

            case R.id.radioStudy: {
                checkRadioBtn(v);

                break;
            }

            case R.id.radioOpenMarket: {
                checkRadioBtn(v);

                break;
            }
            case R.id.radioParty: {
                checkRadioBtn(v);

                break;
            }
            case R.id.radioLecture: {
                checkRadioBtn(v);

                break;
            }
            case R.id.radioEx: {
                checkRadioBtn(v);

                break;
            }
            case R.id.detail_search_btn: {

                try {
                    if (selectDateStr == null) {

                        Toast.makeText(this, "날짜를 선택하세요", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (address.equals("") || address == null) {
                        Toast.makeText(this, "주소를 입력해 주세요", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (Integer.parseInt(reservationPeopleNum.getText().toString()) == 0) {
                        Toast.makeText(this, "인원 수를 선택하세요", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (Integer.parseInt(minTime.getText().toString().trim()) == 0) {

                        Toast.makeText(this, "희망 하는 시작 시간을 입력하세요", Toast.LENGTH_SHORT).show();
                        return;

                    } else if (Integer.parseInt(maxTime.getText().toString().trim()) == 0) {

                        Toast.makeText(this, "희망 하는 종료 시간을 입력하세요", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (Integer.parseInt(maxTime.getText().toString().trim()) - Integer.parseInt(minTime.getText().toString().trim()) < 0) {

                        Toast.makeText(this, "시작 시간은 종료 시간 보다 항상 먼저 있어야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (Integer.parseInt(reservationPeopleNum.getText().toString()) == 0) {

                        Toast.makeText(this, "시작 시간은 종료 시간 보다 항상 먼저 있어야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (radioArray.length == 0) {
                        Toast.makeText(this, "모임 주제를 선택해 주세요.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String radioText = null;
                    for (int i = 0; i < radioArray.length; i++) {

                        if (radioArray[i].isChecked()) {
                            radioText = radioArray[i].getText().toString();

                        }

                    }



                Intent intent = new Intent(this, SearchResult.class);
                intent.putExtra("search", "search");
                intent.putExtra("selectDate", selectDateStr);
                intent.putExtra("selectAddress", address);
                intent.putExtra("selectMinTime", minTime.getText().toString());
                intent.putExtra("selectMaxTime", maxTime.getText().toString());
                intent.putExtra("slectReservationPeopleNum", reservationPeopleNum.getText().toString());
                intent.putExtra("selectRadio", radioText);

                startActivity(intent);

                } catch (Exception e) {

                    e.printStackTrace();
                    if(reservationPeopleNum.getText()==null){
                        Toast.makeText(this, "인원수가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show();
                        Log.d("errorCode", "가게 상세 검색 제출에서 예외 에러 발생");

                    }
                    break;
                }
                break;

            }

        }

    }


}