package org.androidtown.anywhere.any_17_supplier_main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_16_0_qnaboard.QnaBoard;
import org.androidtown.anywhere.any_20_0_supplier_reservation_manager_storeList.ReservationManagerStoreList;
import org.androidtown.anywhere.any_21_supplier_addplace1.AddPlaceFirst;
import org.androidtown.anywhere.any_24_myStoreManager.MyStoreManager;
import org.androidtown.anywhere.any_25_anyOther.ServiceInfo;
import org.androidtown.anywhere.any_tools.PermissionCamera;
import org.androidtown.anywhere.any_tools.PermissionFlag;
import org.androidtown.anywhere.any_tools.PermissionLocation;
import org.androidtown.anywhere.any_tools.PermissionStorage;
import org.androidtown.anywhere.any_tools.SupplierReservationStateService;
import org.androidtown.anywhere.header.Header;

public class SupplierMain extends Header implements BottomNavigationView.OnNavigationItemSelectedListener{

    FrameLayout frameLayout;
    ScrollView scrollView;
    long pressedTime;

    RelativeLayout addPlace, reservationManager, salesManager, myStoreManager;

    BottomNavigationView bottomNavi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere17_supplier_main);
        pressedTime = 0;
        SharedPreferences shef = getSharedPreferences("anywhere", MODE_PRIVATE);
        String session_id = shef.getString("user_email", "");
        String user_nick = shef.getString("user_nick","");
        String user_division = shef.getString("user_division","");

        setHeader();
        initialize();

        SupplierMainHead mainHead = new SupplierMainHead(this);
        RelativeLayout headview = (RelativeLayout) findViewById(R.id.head);
        headview.addView(mainHead);

        addPlace.setOnClickListener(onClickListener);
        reservationManager.setOnClickListener(onClickListener);
        salesManager.setOnClickListener(onClickListener);
        myStoreManager.setOnClickListener(onClickListener);

        bottomNavi.setOnNavigationItemSelectedListener(this);

        if(Build.VERSION.SDK_INT>=23){
            new PermissionLocation(this);
            new PermissionStorage(this);
            new PermissionCamera(this);
        }else{
            PermissionFlag.storageFlag = true;
            PermissionFlag.locationFlag = true;
            PermissionFlag.cameraFlag=true;
        }

        Intent intent = new Intent(this, SupplierReservationStateService.class);
        if(user_nick!=null&&!user_nick.equals("")&&user_division.equals("1")) {
            SupplierReservationStateService.supplierServiceFlag= true;
            startService(intent);
        }else{
            SupplierReservationStateService.supplierServiceFlag= false;
            stopService(intent);
        }


    }

    public void setHeader(){
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.actionbar);
        //액션바 셋팅
        setActionBar(relativeLayout);
        //액션바 이름 셋팅
        setTitleName("Anywhere");
        //액션바 안에 백버튼 보일지 안보일지 셋팅
        setBackVisivility(View.GONE);
        //전체 레이아웃
        frameLayout = (FrameLayout) findViewById(R.id.menubar);
        //액션바 밑에 있는 레이아웃
        scrollView = (ScrollView) findViewById(R.id.container1);
        //메뉴바 셋팅
        setMenuBarView(frameLayout);
        //메뉴바 에니메이션 셋팅
        setMenuBarAnimation(scrollView);
        setMenuListSelectView(supplierMenuListSelect);
    }

    public void initialize(){
        SupplierMainNaviBoard naviBoard = new SupplierMainNaviBoard(this);
        RelativeLayout main = (RelativeLayout) findViewById(R.id.main);
        main.addView(naviBoard);

        addPlace=(RelativeLayout)naviBoard.findViewById(R.id.addPlace);
        reservationManager=(RelativeLayout)naviBoard.findViewById(R.id.reservationManager);
        salesManager=(RelativeLayout)naviBoard.findViewById(R.id.salesManager);
        myStoreManager=(RelativeLayout)naviBoard.findViewById(R.id.myStoreManager);

        bottomNavi = (BottomNavigationView) findViewById(R.id.bottomNavi);

    }


    RelativeLayout.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId())
            {
                case R.id.addPlace :
                    intent = new Intent(SupplierMain.this, AddPlaceFirst.class);
                    startActivity(intent);
                    break;

                case R.id.reservationManager :
                    intent = new Intent(SupplierMain.this, ReservationManagerStoreList.class);
                    startActivity(intent);
                    break;

                case R.id.salesManager :
                    Toast.makeText(SupplierMain.this, "현재 서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.myStoreManager :
                    intent = new Intent(SupplierMain.this, MyStoreManager.class);
                    startActivity(intent);
                    break;


            }

        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent = null;
        switch (menuItem.getItemId()){
            case R.id.menual:{
                Toast.makeText(this, "서비스 준비 중 입니다.", Toast.LENGTH_SHORT).show();                return true;
            }
            case R.id.help:{
                intent=new Intent(SupplierMain.this, QnaBoard.class);
                startActivity(intent);
                return true;
            }
            case R.id.info:{
                intent=new Intent(SupplierMain.this, ServiceInfo.class);
                startActivity(intent);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() { //메인에서 뒤로 가기 버튼을 눌렀을때


        if (frameLayout.getVisibility() == View.GONE) { //만약 메뉴바가 나온 상태가 아니면

            //두 번 클릭시 어플 종료되는 메소드 실행
            if (pressedTime == 0) {
                Toast.makeText(SupplierMain.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                pressedTime = System.currentTimeMillis();
            } else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if (seconds > 2000) {
                    Toast.makeText(SupplierMain.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                    pressedTime = 0;
                } else {
                    /*super.onBackPressed();*/
                    finish(); // app 종료 시키기
                }
            }

        } else {
            //아니면 부모 클래스 (액션바)에 있는 메소드 실행 (메뉴바 안보이게 하기)
            super.onBackPressed();
        }


    }

}