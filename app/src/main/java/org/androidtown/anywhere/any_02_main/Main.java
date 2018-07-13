package org.androidtown.anywhere.any_02_main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_02_main.MainStoreGroup.MainStoreGroupAdapter;
import org.androidtown.anywhere.any_02_main.MainStoreGroup.MainStoreGroupData;
import org.androidtown.anywhere.any_02_main.ViewPaper.AutoScrollViewPager;
import org.androidtown.anywhere.any_02_main.ViewPaper.MainSlideData;
import org.androidtown.anywhere.any_02_main.ViewPaper.MainSlideViewPageAdapter;
import org.androidtown.anywhere.any_12_search_store.SearchStore;
import org.androidtown.anywhere.any_25_anyOther.ServiceInfo;
import org.androidtown.anywhere.any_tools.CustomerReservationStateService;
import org.androidtown.anywhere.any_tools.PermissionCamera;
import org.androidtown.anywhere.any_tools.PermissionFlag;
import org.androidtown.anywhere.any_tools.PermissionLocation;
import org.androidtown.anywhere.any_tools.PermissionStorage;
import org.androidtown.anywhere.header.Header;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

import static org.androidtown.anywhere.httpcontrol_retrofitController.URL_setup.TAG_IMAGE_URL;

public class Main extends Header implements View.OnClickListener {
    FrameLayout frameLayout;
    ScrollView scrollView;
    String test[] = {"Page1", "Page2", "Page3", "Page4", "Page5"};
    AutoScrollViewPager mainSlideViewPager;
    RelativeLayout actionbar;
    String[] divName = {"미팅", "전시회", "일반모임", "스터디", "오픈마켓", "파티", "강연", "기타"};
    LinearLayout serviceInfo;
    MaterialRippleLayout allianceButton;
    public static int mathCounter;
    long pressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere02_main);
        mathCounter = 0;
        pressedTime = 0;



        if (Build.VERSION.SDK_INT >= 23) {

                new PermissionLocation(this);

                new PermissionStorage(this);

                new PermissionCamera(this);

        } else {
            PermissionFlag.storageFlag = true;
            PermissionFlag.locationFlag = true;
            PermissionFlag.cameraFlag = true;
        }



        try {
            PackageInfo info = getPackageManager().getPackageInfo("org.androidtown.anywhere", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        SharedPreferences shef = getSharedPreferences("anywhere", MODE_PRIVATE);
        String session_id = shef.getString("user_email", "");
        String user_nick = shef.getString("user_nick", "");
        String user_division = shef.getString("user_division", "");


        initialize();
        setHeader();

        //Customer 소비자
        Intent intent = new Intent(this, CustomerReservationStateService.class);
        if (user_nick != null && !user_nick.equals("") && user_division.equals("0")) {
            CustomerReservationStateService.customerServiceFlag = true;
            startService(intent);
        } else {
            CustomerReservationStateService.customerServiceFlag = false;
            stopService(intent);
        }
        //뷰페이지 어뎁터에 들어갈 데이터
        ArrayList<MainSlideData> data = new ArrayList<>();



       /* for(int i=0;i<test.length;i++){
            MainSlideData slideData = new MainSlideData();
            slideData.setUrl(test[i]);
            data.add(slideData);
        }*/

        MainSlideData slideData = new MainSlideData();
        slideData.setUrl("http://www.mrwallpaper.com/wallpapers/i-love-coffee-1920x1080.jpg");
        data.add(slideData);

        slideData = new MainSlideData();
        slideData.setUrl("https://i.ytimg.com/vi/TQr0EuFGhSY/maxresdefault.jpg");
        data.add(slideData);

        slideData = new MainSlideData();
        slideData.setUrl("http://www.coinside.ru/wp-content/uploads/2014/05/food-drinks-best-caffee-hd-wallpaper-best-coffee-shop-in-jakarta-best-coffee-in-the-world-best-coffee-best-coffee-in-singapore-best-coffee-table-books-best-coffee-grinder-best-coffee-machine-best-coff.jpg");
        data.add(slideData);

        slideData = new MainSlideData();
        slideData.setUrl("http://www.wallhd4.com/wp-content/uploads/2015/03/good-morning-coffee-wallpapers-12.jpeg");
        data.add(slideData);

        slideData = new MainSlideData();
        slideData.setUrl("https://www.walldevil.com/wallpapers/a89/breakfast-caffee-coffee-croissant.jpg");
        data.add(slideData);


        //검색 레이아웃
        SearchStore searchStore = new SearchStore(this);
        RelativeLayout search = (RelativeLayout) findViewById(R.id.search);
        search.addView(searchStore);

        allianceButton = (MaterialRippleLayout) findViewById(R.id.allianceButton);
        allianceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main.this, RequestAlliance.class);
                startActivity(intent);

            }
        });

        //뷰페이지 찾아주기
        mainSlideViewPager = (AutoScrollViewPager) findViewById(R.id.mainSlideViewPager);
        //뷰페이지 indicator
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.slideIndicator);
        //뷰페이지 어뎁터 생성
        MainSlideViewPageAdapter slidePage = new MainSlideViewPageAdapter(this, data);
        //어뎁터 셋팅
        mainSlideViewPager.setAdapter(slidePage);
        //자동 스크롤 모드 설정
        mainSlideViewPager.setInterval(4000); //보여주고 넘길 시간 설정(ms)
        mainSlideViewPager.startAutoScroll(); //자동 스크롤 시작
        //indicator 셋팅
        indicator.setViewPager(mainSlideViewPager);


        /////리사이클러뷰 사용
        RecyclerView storeGroup = (RecyclerView) findViewById(R.id.storeGroup);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        storeGroup.setLayoutManager(linearLayoutManager);

        MainStoreGroupAdapter storeGroupAdapter = new MainStoreGroupAdapter(this);
        setStoreGroupData(storeGroupAdapter);

        storeGroup.setAdapter(storeGroupAdapter);


    }

    public void initialize() {

        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        //전체 레이아웃
        frameLayout = (FrameLayout) findViewById(R.id.menubar);
        //액션바 밑에 있는 레이아웃
        scrollView = (ScrollView) findViewById(R.id.scroll);
        serviceInfo = (LinearLayout) findViewById(R.id.serviceInfo);

        LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);

        serviceInfo = (LinearLayout) inflater.inflate(R.layout.anywhere02_main_service_info, serviceInfo, true);
        serviceInfo.setOnClickListener(this);

    }

    public void setHeader() {
        //액션바 셋팅
        setActionBar(actionbar);
        //액션바 이름 셋팅
        setTitleName("Anywhere");
        //액션바 안에 백버튼 보일지 안보일지 셋팅
        setBackVisivility(View.GONE);
        //메뉴바 셋팅
        setMenuBarView(frameLayout);
        //메뉴바 에니메이션 셋팅
        setMenuBarAnimation(scrollView);
        //메뉴바 안에 리스트 셋팅
        setMenuListSelectView(cunsumerMenuListSelect);

    }


    //데이터 집어넣기
    public void setStoreGroupData(MainStoreGroupAdapter adapter) {


        for (int i = 0; i < divName.length; i++) {
            MainStoreGroupData data1 = new MainStoreGroupData();
            data1.setStoreGroupName("#" + divName[i]);
            data1.setStoreGroupURl(TAG_IMAGE_URL + divName[i] + ".jpg");
            adapter.add(data1);
        }


    }


    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.serviceInfo:
                intent = new Intent(Main.this, ServiceInfo.class);
                startActivity(intent);
                break;

        }

    }


    @Override
    public void onBackPressed() { //메인에서 뒤로 가기 버튼을 눌렀을때


        if (frameLayout.getVisibility() == View.GONE) { //만약 메뉴바가 나온 상태가 아니면

            //두 번 클릭시 어플 종료되는 메소드 실행
            if (pressedTime == 0) {
                Toast.makeText(Main.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                pressedTime = System.currentTimeMillis();
            } else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if (seconds > 2000) {
                    Toast.makeText(Main.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
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

