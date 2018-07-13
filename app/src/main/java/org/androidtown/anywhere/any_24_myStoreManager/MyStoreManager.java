package org.androidtown.anywhere.any_24_myStoreManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_18_store_resister.StoreResisterFirst;
import org.androidtown.anywhere.any_19_store_defalut_modify.StoreDefaultModifySelectStore;
import org.androidtown.anywhere.any_23_0_supplier_appraiseManager_storeList.AppraiseManagerStoreList;
import org.androidtown.anywhere.header.Header;

public class MyStoreManager extends Header {
    FrameLayout frameLayout;
    RelativeLayout myStoreManagerFrame;

    RelativeLayout addStore;
    RelativeLayout manageStore;
    RelativeLayout inquirement;
    RelativeLayout storeFeedBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere24_my_store_manager);


        setHeader();
        initialize();

        addStore.setOnClickListener(onClickListener);
        manageStore.setOnClickListener(onClickListener);
        inquirement.setOnClickListener(onClickListener);
        storeFeedBack.setOnClickListener(onClickListener);


    }

    public void setHeader(){
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.actionbar);
        //액션바 셋팅
        setActionBar(relativeLayout);
        //액션바 이름 셋팅
        setTitleName("내 업소 관리");
        //액션바 안에 백버튼 보일지 안보일지 셋팅
        setBackVisivility(View.VISIBLE);
        //전체 레이아웃
        frameLayout = (FrameLayout) findViewById(R.id.menubar);
        //액션바 밑에 있는 레이아웃
        myStoreManagerFrame = (RelativeLayout) findViewById(R.id.myStoreManagerFrame);
        //메뉴바 셋팅
        setMenuBarView(frameLayout);
        //메뉴바 에니메이션 셋팅
        setMenuBarAnimation(myStoreManagerFrame);
        setMenuListSelectView(supplierMenuListSelect);

    }

    public void initialize(){

        addStore=(RelativeLayout)findViewById(R.id.addStore);
        manageStore=(RelativeLayout)findViewById(R.id.manageStore);
        inquirement=(RelativeLayout)findViewById(R.id.supplierInquirement);
        storeFeedBack=(RelativeLayout)findViewById(R.id.storeFeedBack);

    }


    RelativeLayout.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId())
            {
                case R.id.addStore :
                    intent = new Intent(MyStoreManager.this, StoreResisterFirst.class);
                    startActivity(intent);
                    break;

                case R.id.manageStore :
                    try {

                        intent = new Intent(MyStoreManager.this, StoreDefaultModifySelectStore.class);
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "통신 에러 발생",Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.supplierInquirement :
                    Toast.makeText(MyStoreManager.this, "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.storeFeedBack :
                    intent = new Intent(MyStoreManager.this, AppraiseManagerStoreList.class);
                    startActivity(intent);
                    break;


            }

        }
    };


}
