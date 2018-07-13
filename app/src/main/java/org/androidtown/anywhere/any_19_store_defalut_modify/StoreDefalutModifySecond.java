package org.androidtown.anywhere.any_19_store_defalut_modify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.header.Header;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StoreDefalutModifySecond extends Header implements View.OnClickListener {
    RelativeLayout actionbar;
    FrameLayout menubar;
    ScrollView container1;

    EditText storeIntroOneLine;
    EditText storeIntroText;
    EditText storeReservationText;

    int testnum;
    CheckBox checkBeam;
    CheckBox checkRadio;
    CheckBox checkCon;
    CheckBox checkPark;
    CheckBox checkBoard;
    CheckBox checkPrint;
    CheckBox checkWifi;
    CheckBox checkAir;
    CheckBox checkFax;
    CheckBox checkTv;



    ArrayList<Integer> checkList; //checkBox에 각각 매핑된 숫자들을 저장하는 리스트

    //이미지 픽
    List<Uri> slideImageList;
    Uri titlePhoto;
    //저장 객체
    StoreVO storeVO;
    Intent intent;

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere19_store_defalut_modify_02);

        testnum=0;
        Intent intent = getIntent();
        slideImageList = new ArrayList<>();
        storeVO = (StoreVO) intent.getSerializableExtra("storeVO");
        slideImageList = intent.getParcelableArrayListExtra("slideImageList");
        titlePhoto = intent.getParcelableExtra("titlePhoto");
        //Uri를 getExtra로 들고오면 NullPointerException 발생
        //Uri 들고오려면 (Uri).toString 이렇게 변환해서 String으로 들고와서 uri.parse로 변환하던지
        //아니면 getParcelableExtra로 꺼내야함.

        initialize();
        setHeader();


        next.setOnClickListener(this);

        setData();


    }

    public void setData() {
        storeIntroText.setText(storeVO.getStore_spaceintro().toString());
        storeIntroOneLine.setText(storeVO.getStore_intro().toString());
        storeReservationText.setText(storeVO.getStore_attention().toString());

        setBeforeCheckBox(storeVO.getFacility_num());


    }


    public void setBeforeCheckBox(ArrayList<Integer> checkbox) {

        CheckBox[] checkArray = {checkBeam, checkBoard, checkAir, checkRadio, checkPrint
                , checkFax, checkCon, checkWifi, checkTv, checkPark};

        Log.d("giduckTest1", "checkArray : " + checkArray.length);

        for (int i = 0; i < checkArray.length; i++) {

            if (checkbox.contains(i + 1)) {

                Log.d("giduckTest1", "checkArray : " + checkArray.length);
                Log.d("giduckTest1", "checkArray : " + checkArray[i]);
                checkArray[i].setChecked(true);
                testnum++;

            }
        }


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        setCheckList();

        if (id == R.id.next) {

            if (storeIntroOneLine.getText().toString().trim().length() == 0) {

                Toast.makeText(this, "가게 한줄 소개를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            } else if (storeIntroText.getText().toString().trim().length() == 0) {

                Toast.makeText(this, "가게 소개를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            } else if (storeReservationText.getText().toString().trim().length() == 0) {
                Toast.makeText(this, "예약 안내를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            } else if (checkList.isEmpty()) {
                Toast.makeText(this, "시설 안내를 하나 이상 선택하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, StoreDefalutModifyThird.class);
            storeVO.setStore_intro(storeIntroOneLine.getText().toString());
            storeVO.setStore_attention(storeReservationText.getText().toString());
            storeVO.setFacility_num(checkList);
            storeVO.setStore_spaceintro(storeIntroText.getText().toString());
            intent.putExtra("storeVO", storeVO);
            intent.putExtra("slideImageList", (Serializable) slideImageList);
            intent.putExtra("titlePhoto", titlePhoto);

            startActivity(intent);
        }
    }

    public void setCheckList() {

        CheckBox[] checkArray = {checkBeam, checkBoard, checkAir, checkRadio, checkPrint
                , checkFax, checkCon, checkWifi, checkTv, checkPark};

        checkList = new ArrayList<>();


        for (int i = 0; i < checkArray.length; i++) {

            if (checkArray[i].isChecked()) {

                checkList.add((i + 1));

            }

        }


    }

    public void initialize() {
        //헤더 데이터
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container1 = (ScrollView) findViewById(R.id.container1);

        storeIntroText = (EditText) findViewById(R.id.storeIntroText);
        storeReservationText = (EditText) findViewById(R.id.storeReservationText);
        storeIntroOneLine = (EditText) findViewById(R.id.storeIntroOneLine);

        checkBeam = (CheckBox) findViewById(R.id.checkBeam);
        checkRadio = (CheckBox) findViewById(R.id.checkRadio);
        checkCon = (CheckBox) findViewById(R.id.checkCon);
        checkPark = (CheckBox) findViewById(R.id.checkPark);
        checkBoard = (CheckBox) findViewById(R.id.checkBoard);
        checkPrint = (CheckBox) findViewById(R.id.checkPrint);
        checkWifi = (CheckBox) findViewById(R.id.checkWifi);
        checkAir = (CheckBox) findViewById(R.id.checkAir);
        checkFax = (CheckBox) findViewById(R.id.checkFax);
        checkTv = (CheckBox) findViewById(R.id.checkTv);

        next = (Button) findViewById(R.id.next);

    }

    public void setHeader() {
        setActionBar(actionbar);
        setTitleName("가게 수정하기");
        setBackVisivility(View.VISIBLE);
        setMenuBarView(menubar);
        setMenuBarAnimation(container1);
        setMenuListSelectView(supplierMenuListSelect);
    }
}
