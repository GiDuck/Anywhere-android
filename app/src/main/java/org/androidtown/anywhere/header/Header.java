package org.androidtown.anywhere.header;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidtown.anywhere.R;

/**
 * Created by user on 2017-06-30.
 */

public class Header extends BasedFontApp {


    //액션바 레이아웃 데이터
    private RelativeLayout actionbar;
    //메뉴바 레이아웃 데이터
    private FrameLayout menuBar;

    //메뉴 리스트를 위한 데이터 타입
    public final String cunsumerMenuListSelect = "0";
    public final String supplierMenuListSelect = "1";

    //메뉴바 애니메이션을 위한 데이터
    private HeaderAnimation menuBarAnimation;
    private Animation menuBartranslateLeft;
    private Animation menuBartranslateRight;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //뒤로가기 버튼을 위한 데이터
    private long backKeyPressedCloseActivity = 0;

    //////////////////////////////////////////////////액션바///////////////////////////////////////////////////
    public void setActionBar(RelativeLayout relativeLayout) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        actionbar = (RelativeLayout) inflater.inflate(R.layout.header_actionbar, relativeLayout, true);

        //하위 레이아웃 클릭을 막을 수 있도록
        actionbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //백버튼을 클릭했을 경우
        getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    //메뉴버튼 정의
    public ImageView getMenu() {
        ImageView menu = (ImageView) findViewById(R.id.menu);
        return menu;
    }

    //백버튼 정의
    public ImageView getBack() {
        ImageView back = (ImageView) findViewById(R.id.back);
        return back;
    }

    //타이틀버튼 정의
    public TextView getTitleName() {
        TextView title = (TextView) findViewById(R.id.title);
        return title;
    }

    //타이틀 이름 정의
    public void setTitleName(String title) {
        getTitleName().setText(title);
    }

    //백 버튼을 보여줄지 안보여줄지 정의
    public void setBackVisivility(int visivility) {
        getBack().setVisibility(visivility);
    }

    /////////////////////////////////////////////메뉴바/////////////////////////////////////////

    public void setMenuBarView(FrameLayout frameLayout) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        menuBar = (FrameLayout) inflater.inflate(R.layout.header_menubar, frameLayout, true);
        Log.d("호출", "setMenuBar");
        //하위레이아웃 눌러짐 방지
        menuBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        HeaderLogin login = new HeaderLogin(this);

    }

    //검은색 화면 정의
    public ImageView getBlack() {
        ImageView black = (ImageView) findViewById(R.id.black);
        black.getBackground().setAlpha(80);
        return black;
    }

    ////////////////////////////////////////메뉴바 리스트 셋팅//////////////////////////////////////

    public void setMenuListSelectView(String menuSelct) {

        LinearLayout cunsumerMenuList = (LinearLayout) findViewById(R.id.cunsumerMenuList);
        LinearLayout supplierMenuList = (LinearLayout) findViewById(R.id.supplierMenuList);

        if (menuSelct.equals(cunsumerMenuListSelect)) {
            supplierMenuList.setVisibility(View.GONE);
            cunsumerMenuList.setVisibility(View.VISIBLE);
        } else if (menuSelct.equals(supplierMenuListSelect)) {
            cunsumerMenuList.setVisibility(View.GONE);
            supplierMenuList.setVisibility(View.VISIBLE);
        }
    }

    ////////////////////////////////////////메뉴바 애니메이션 효과//////////////////////////////////
    //레이아웃 넣어줘야한다.
    public void setMenuBarAnimation(View v) {

        menuBartranslateLeft = AnimationUtils.loadAnimation(this, R.anim.menubar_translate_left);
        menuBartranslateRight = AnimationUtils.loadAnimation(this, R.anim.menubar_translate_right);


        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //스크롤 뷰일 경우에는 막아준다.
                if (v.isScrollContainer()) {
                    return false;
                }
                return true;
            }
        });

        //애니메이션 클래스 정의
        menuBarAnimation = new HeaderAnimation(v, actionbar, menuBar, getBlack(), getMenu(),
                menuBartranslateRight, menuBartranslateLeft);

        //각각의 애니메이션에 이벤트 셋팅
        menuBartranslateLeft.setAnimationListener(menuBarAnimation);
        menuBartranslateRight.setAnimationListener(menuBarAnimation);

        getMenu().setOnClickListener(menuBarAnimation);
        getBlack().setOnClickListener(menuBarAnimation);

    }
    /////////////////////////////////////////////////////메모리 로딩중//////////


    @Override
    protected void onResume() {
        super.onResume();

        if (menuBar.getVisibility() == View.VISIBLE) {
            menuBar.setVisibility(View.GONE);
        }
    }

    ///////////////////////////////////////////////////////뒤로가기 버튼///////////////////////////////////////////
    @Override
    public void onBackPressed() {

        Log.d("onBackPressed", "onBackPress");
        if (menuBar.getVisibility() == View.VISIBLE) {
            Log.d("onBackPressed", "animatioin");
            menuBar.startAnimation(menuBartranslateRight);
            //블랙버튼 안눌려지도록
            getBlack().setEnabled(false);
            //메뉴바 사라짐
            menuBar.setVisibility(View.GONE);
            //플래그값 true
            menuBarAnimation.setPageOpen(true);
            //핸들러 이용하여 0.5초 이후에 눌려질 수 있도록한다.
//            Handler button = new Handler();
//            button.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    getBlack().setEnabled(true);
//                }
//            }, 300);

        } else {


                finish();
            }
//            Log.d("onBackPressed", "elseanimatioin");
//                //2초안에 눌렀을 경우 액티비티 종료
//                if(System.currentTimeMillis()>backKeyPressedCloseActivity+2000) {
//                    backKeyPressedCloseActivity = System.currentTimeMillis();
//                    Toast.makeText(this, "뒤로 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
//                    return;
//                }else if(System.currentTimeMillis()<=backKeyPressedCloseActivity+2000){
//                    super.onBackPressed();
//                    finish();
//                }
        }
    }



