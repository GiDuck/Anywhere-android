package org.androidtown.anywhere.any_23_1_supplier_appraiseManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.StoreReplyVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.SupplierController;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;

public class AppraiseManager extends Header {

    RelativeLayout actionbar;
    FrameLayout menubar;
    TextView totalRating;
    RatingBar totalRatingBar;

    //댓글 리사이클러 뷰
    RecyclerView rv;
    AppraiseManagerAdapter Adapter;

    String store_num;



    //레트로핏에서 받아오는 데이터
    ArrayList<StoreReplyVO> replyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.anywhere23_appreise_manager);

        Intent intent = getIntent();
        store_num = intent.getStringExtra("store_num");

        setData();
        viewInit();
        setHeader();

        calculateTotalRating();

        Adapter = new AppraiseManagerAdapter(replyList, store_num, this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setAdapter(Adapter);

    }


    public void viewInit(){
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        rv = (RecyclerView) findViewById(R.id.appraiseManager_rv);
        totalRating = (TextView) findViewById(R.id.appraiseManager_total_userRating);
        totalRatingBar = (RatingBar) findViewById(R.id.appraiseManager_total_userRatingBar);

    }

    public void setHeader(){
        //액션바 셋팅
        setActionBar(actionbar);
        //액션바 이름 셋팅
        setTitleName("평점 관리");
        //액션바 안에 백버튼 보일지 안보일지 셋팅
        setBackVisivility(View.VISIBLE);
        //메뉴바 셋팅
        setMenuBarView(menubar);
        //메뉴바 에니메이션 셋팅
        setMenuBarAnimation(rv);
        //메뉴바 안에 리스트 셋
        setMenuListSelectView(supplierMenuListSelect);
    }

    public void calculateTotalRating() {

        float totalRatingValue = 0;
        int totalVaildItemSize = 0;

        for(int i=0; i<replyList.size(); i++){

            totalRatingValue += replyList.get(i).getReply_star().floatValue();

            if(replyList.get(i).getReply_lev()==0){

                totalVaildItemSize++;
            }
        }


        totalRatingValue = totalRatingValue / totalVaildItemSize;

        DecimalFormat doubleFormat = new DecimalFormat("0.#");
        String transTotalRatingValue = doubleFormat.format(totalRatingValue);

        if(transTotalRatingValue.equals("NaN")){
            totalRating.setText("0.0");
        }else{
            totalRating.setText(transTotalRatingValue);
        }

        totalRatingBar.setRating(totalRatingValue);
    }


    public void setData() {

        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createSupplierRetrofitObject();
        SupplierController apiService = hrs.createSupplierApiserverObject();
        Call call = apiService.getReplyVO(store_num);
        hrs.HttpRequestExecute(call);
        hrs.makeGsonObjectTypeDate();
        Type type = new TypeToken<ArrayList<StoreReplyVO>>() {
        }.getType();
        replyList = (ArrayList<StoreReplyVO>) hrs.parsingFunc(type);
    }


}
