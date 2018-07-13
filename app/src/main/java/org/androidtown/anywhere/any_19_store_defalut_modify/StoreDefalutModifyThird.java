package org.androidtown.anywhere.any_19_store_defalut_modify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_17_supplier_main.SupplierMain;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpImageAndObjectUpload;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import info.hoang8f.widget.FButton;

import static org.androidtown.anywhere.header.RegularExpression.VALID_ONLY_NUMBER;

public class StoreDefalutModifyThird extends Header implements View.OnClickListener {
    RelativeLayout actionbar;
    FrameLayout menubar;
    ScrollView container1;
    FButton submit;

    //이미지 픽
    List<Uri> slideImageList;
    Uri titlePhoto;
    //저장 객체
    StoreVO storeVO;
    ArrayList<Integer> checkList;


    RecyclerView menuBoard;
    FButton menuAdd;
    EditText menu;
    EditText menuPrice;
    EditText minPeopleNum;
    EditText maxPeopleNum;
    EditText menuFreePrice;
    StoreMenuModifyAdapter menuAdapter;

    String user_nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere19_store_defalut_modify_03);

        SharedPreferences infoStoarge = getSharedPreferences("anywhere", MODE_PRIVATE);
        user_nick=infoStoarge.getString("user_nick","");

        Intent intent = getIntent();
        storeVO = new StoreVO();

        storeVO = (StoreVO) intent.getSerializableExtra("storeVO");
        slideImageList = intent.getParcelableArrayListExtra("slideImageList");

        titlePhoto = intent.getParcelableExtra("titlePhoto");


        initialize();
        setHeader();
        setMenuResisterAdpater();

    }

    public void setDate() {
        int freeVoucherPosition=0;
        minPeopleNum.setText(Integer.toString(storeVO.getStore_min()));
        maxPeopleNum.setText(Integer.toString(storeVO.getStore_max()));

        for (int i = 0; i < storeVO.getMenu_price().size(); i++) {
            //최소인원, 최대인원 set
            StoreMenuModifyData data = new StoreMenuModifyData();
            data.setMenu(storeVO.getMenu_name().get(i));
            data.setMenuPrice(String.valueOf(storeVO.getMenu_price().get(i)));


            if(storeVO.getMenu_name().get(i).equals("자유이용권")){

                freeVoucherPosition=i;
            }

            Log.d("setDataMenu", storeVO.getMenu_name().get(i));
            Log.d("setDataMenu", String.valueOf(storeVO.getMenu_price().get(i)));
            menuAdapter.add(data);


        }


        menuFreePrice.setText(menuAdapter.getDatas().get(freeVoucherPosition).getMenuPrice());
        //메뉴 데이터 set
        menuAdapter.getDatas().remove(freeVoucherPosition);



    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.menuAdd: {
                if (menu.getText().toString().trim().length() == 0) {

                    Toast.makeText(this, "메뉴 이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;

                } else if (menuPrice.getText().toString().trim().length() == 0) {

                    Toast.makeText(this, "메뉴 가격을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                StoreMenuModifyData data = new StoreMenuModifyData();

                data.setMenu(menu.getText().toString());
                data.setMenuPrice(menuPrice.getText().toString());

                menuAdapter.add(data);
                menu.setText("");
                menuPrice.setText("");
                break;

            }

            case R.id.store_resister_submit: {

                Matcher vaildMaxPeopleNum = VALID_ONLY_NUMBER.matcher(maxPeopleNum.getText().toString());
                Matcher vaildMinPeopleNum = VALID_ONLY_NUMBER.matcher(minPeopleNum.getText().toString());

                if (vaildMaxPeopleNum.find()) {
                    Toast.makeText(this, "최대인원은 숫자로만 입력 해야 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (vaildMinPeopleNum.find()) {
                    Toast.makeText(this, "최소 인원은 숫자로만 이루어 져야 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (minPeopleNum.getText().toString().trim().length() == 0) {
                    Toast.makeText(this, "최소 인원을 설정하세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (maxPeopleNum.getText().toString().trim().length() == 0) {

                    Toast.makeText(this, "최대 인원을 설정하세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Integer.parseInt(minPeopleNum.getText().toString()) > Integer.parseInt(maxPeopleNum.getText().toString().trim())) {
                    Toast.makeText(this, "최소 인원이 더 많습니다.", Toast.LENGTH_SHORT).show();

                } else if (menuAdapter.getDatas().isEmpty()) {

                    Toast.makeText(this, "메뉴를 하나 이상 등록하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else if (menuFreePrice.getText().toString().trim().length() == 0) {

                    Toast.makeText(this, "자유 이용권 가격을 등록하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                storeVO.setStore_max(Integer.parseInt(maxPeopleNum.getText().toString()));
                storeVO.setStore_min(Integer.parseInt(minPeopleNum.getText().toString()));


                ArrayList<Integer> menuPrice = new ArrayList<>();
                ArrayList<String> menuName = new ArrayList<>();

                for (int i = 0; i < menuAdapter.getDatas().size(); i++) {

                    menuPrice.add(Integer.parseInt(menuAdapter.getDatas().get(i).getMenuPrice()));
                    menuName.add(menuAdapter.getDatas().get(i).getMenu());

                }

                menuName.add("자유이용권");
                menuPrice.add(Integer.parseInt(menuFreePrice.getText().toString().trim()));


                storeVO.setMenu_price(menuPrice);
                storeVO.setMenu_name(menuName);
                storeVO.setStore_nick(user_nick);

                Log.d("giduckTest44",storeVO.getPicture_url().size()+"");

                if (slideImageList == null) {
                    Log.d("giduckTest", "슬라이드 이미지 리스트가 null");


                }else{

                    Log.d("giduckTest", slideImageList.size()+"");
                    Log.d("giduckTest", slideImageList.get(0).toString());



                }

                if(titlePhoto==null){

                    Log.d("giduckTest", "타이틀 사진이 null");
                }

                Log.d("giduckTest", "경도 " + storeVO.getStore_longitude());
                Log.d("giduckTest", "위도 " + storeVO.getStore_latitude());


                HttpImageAndObjectUpload hrs = new HttpImageAndObjectUpload(this);
                hrs.createRetrofitObject_Supplier();
                hrs.createApiserverObject();
                hrs.updateMultipleImagesAndObject(slideImageList, titlePhoto, user_nick, storeVO);
                String token = hrs.parsingStringFunc("result");
                try {
                    if (token.equals("true")) {

                        Toast.makeText(this, "가게 수정 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StoreDefalutModifyThird.this, SupplierMain.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                    } else if (token.equals("false")) {

                        Toast.makeText(this, "가게 수정 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(StoreDefalutModifyThird.this, SupplierMain.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Log.d("errorCode", "가게 등록 3 오류 - http 요청 실패");

                    } else {

                        Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                        Log.d("errorCode", "가게 수정 3 오류 - 알 수 없는 오류");
                    }

                }catch (Exception e){

                    e.printStackTrace();
                    Toast.makeText(this, "통신에러가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(StoreDefalutModifyThird.this, SupplierMain.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }
    }

    public void setMenuResisterAdpater() {
        LinearLayoutManager menuManager = new LinearLayoutManager(this);
        menuBoard.setLayoutManager(menuManager);
        setDate();
        menuBoard.setAdapter(menuAdapter);
    }


    public void initialize() {
        //헤더 데이터
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container1 = (ScrollView) findViewById(R.id.container1);

        submit = (FButton) findViewById(R.id.store_resister_submit);
        minPeopleNum = (EditText) findViewById(R.id.minNum);
        maxPeopleNum = (EditText) findViewById(R.id.maxNum);
        menu = (EditText) findViewById(R.id.menu);
        menuPrice = (EditText) findViewById(R.id.menuPrice);
        menuAdd = (FButton) findViewById(R.id.menuAdd);
        menuFreePrice = (EditText) findViewById(R.id.menuFreePrice);
        menuAdd.setOnClickListener(this);
        submit.setOnClickListener(this);

        menuBoard = (RecyclerView) findViewById(R.id.menuBoard);
        menuAdapter = new StoreMenuModifyAdapter(this);
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
