package org.androidtown.anywhere.header;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by user on 2017-06-30.
 */

public class HeaderAnimation implements Animation.AnimationListener,View.OnClickListener{

    private boolean pageOpen;
    private View v;
    private RelativeLayout actionbar;
    private FrameLayout menubar;
    private ImageView black;
    private ImageView menu;
    private Animation translateRight;
    private Animation translateLeft;

    public HeaderAnimation(View v, RelativeLayout actionbar, FrameLayout menubar, ImageView black, ImageView menu, Animation translateRight, Animation translateLeft) {

        this.pageOpen = true;
        this.v = v;
        this.actionbar = actionbar;
        this.menubar = menubar;
        this.black = black;
        this.menu = menu;
        this.translateRight = translateRight;
        this.translateLeft = translateLeft;
    }

    public void setPageOpen(boolean pageOpen){
        this.pageOpen = pageOpen;
    }

    //클릭 이벤트
    @Override
    public void onClick(View v) {
        if(v==menu){
            Log.d("확인","확인1");
            //레이아웃 인플레이션
            menubar.startAnimation(translateLeft);
            //메뉴바 생김
            menubar.setVisibility(View.VISIBLE);
            //메뉴바를 가장 상단의 메뉴바로 가지고옴
            menubar.bringToFront();
            black.setEnabled(true);
            //플래그 값 false
            pageOpen = false;
        }else if(v==black) {
            Log.d("확인", "확인2");
            //블랙버튼 안눌려지도록
            black.setEnabled(false);
            menubar.startAnimation(translateRight);
            //메뉴바 사라짐
            menubar.setVisibility(View.GONE);
            //플래그값 true
            pageOpen = true;
            //핸들러 이용하여 0.5초 이후에 눌려질 수 있도록한다.
//            Handler button = new Handler();
//            button.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    black.setEnabled(true);
//                }
//            }, 300);
        }
    }

    //애니메이션 이벤트
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if(!pageOpen){
            //플래그값 false일때, 플래그값을 true로 변환
            pageOpen=true;
        }else{
            //플래그값 ture
//            //메뉴바 클릭안되도록 한다.
//            menubar.setClickable(false);
            //액션바와 그아래에 있는 가장 부모 레이아웃 가장 상단의 레이아웃으로 지정
            actionbar.bringToFront();
            v.bringToFront();
            //플래그값 false로 지정
            pageOpen=false;
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
