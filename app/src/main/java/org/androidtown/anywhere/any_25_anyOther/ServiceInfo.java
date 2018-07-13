package org.androidtown.anywhere.any_25_anyOther;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.header.Header;

import info.hoang8f.widget.FButton;

public class ServiceInfo extends Header implements View.OnClickListener {
    FButton serviceInfoBtn, personalInfoBtn, locationInfoBtn;
    RelativeLayout actionbar;
    FrameLayout menubar;
    RelativeLayout container1;

    SharedPreferences shef;
    String user_division;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);

        shef=getSharedPreferences("anywhere", MODE_PRIVATE);
        user_division=shef.getString("user_division", "");


        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        //전체 레이아웃
        menubar = (FrameLayout) findViewById(R.id.menubar);
        //액션바 밑에 있는 레이아웃
        container1=(RelativeLayout)findViewById(R.id.container1);
        serviceInfoBtn=(FButton)findViewById(R.id.serviceInfoBtn);
        personalInfoBtn=(FButton)findViewById(R.id.personalInfoBtn);
        locationInfoBtn=(FButton)findViewById(R.id.locationInfoBtn);

        setHeader();

        serviceInfoBtn.setOnClickListener(this);
        personalInfoBtn.setOnClickListener(this);
        locationInfoBtn.setOnClickListener(this);




    }

    public void setHeader() {
        //액션바 셋팅
        setActionBar(actionbar);
        //액션바 이름 셋팅
        setTitleName("서비스 정보");
        //액션바 안에 백버튼 보일지 안보일지 셋팅
        setBackVisivility(View.VISIBLE);
        //메뉴바 셋팅
        setMenuBarView(menubar);
        //메뉴바 에니메이션 셋팅
        setMenuBarAnimation(container1);
        //메뉴바 안에 리스트 셋팅
        if(user_division.equals("0")) {
            setMenuListSelectView(cunsumerMenuListSelect);
        }else if(user_division.equals("1")){
            setMenuListSelectView(supplierMenuListSelect);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()){

            case R.id.serviceInfoBtn :
                intent=new Intent(ServiceInfo.this, ServiceInfoDetail.class);
                intent.putExtra("token", "serviceInfo");
                startActivity(intent);
                break;
            case R.id.personalInfoBtn:
                intent=new Intent(ServiceInfo.this, ServiceInfoDetail.class);
                intent.putExtra("token", "personalInfo");
                startActivity(intent);
                break;
            case R.id.locationInfoBtn:
                intent=new Intent(ServiceInfo.this, ServiceInfoDetail.class);
                intent.putExtra("token", "locationInfo");
                startActivity(intent);
                break;
        }

    }
}
