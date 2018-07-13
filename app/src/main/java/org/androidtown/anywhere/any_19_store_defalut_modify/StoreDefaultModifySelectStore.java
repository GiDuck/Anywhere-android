package org.androidtown.anywhere.any_19_store_defalut_modify;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.SupplierController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class StoreDefaultModifySelectStore extends Header {

    private RelativeLayout actionbar;
    private FrameLayout menubar;
    private RecyclerView rv;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<StoreVO> items;
    private StoreDefaultModifySelectStoreAdapter Adapter;
    private ArrayList<StoreVO> storeVOList;
    SupplierController supplierApiservice;
    String user_nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData();
        setContentView(R.layout.anywhere19_store_default_modify_select_store);

        initialize();
        setHeader();


        Adapter = new StoreDefaultModifySelectStoreAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(Adapter);
        Adapter.add(items);


    }

    public void initialize() {
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        rv = (RecyclerView) findViewById(R.id.addplace1_recycler);
    }

    public void setHeader() {
        //액션바 셋팅
        setActionBar(actionbar);
        //액션바 이름 셋팅
        setTitleName("가게 수정");
        //액션바 안에 백버튼 보일지 안보일지 셋팅
        setBackVisivility(View.VISIBLE);
        //메뉴바 셋팅
        setMenuBarView(menubar);
        //메뉴바 에니메이션 셋팅
        setMenuBarAnimation(rv);
        //메뉴바 안에 리스트 셋
        setMenuListSelectView(supplierMenuListSelect);

    }

    public void setData() {
        try {
            SharedPreferences infoStoarge = getSharedPreferences("anywhere", MODE_PRIVATE);
            user_nick = infoStoarge.getString("user_nick", "");

            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createSupplierRetrofitObject();
            supplierApiservice = hrs.createSupplierApiserverObject();


            Call call = supplierApiservice.getStoreVO(user_nick);
            hrs.HttpRequestExecute(call);
            Type type = new TypeToken<List<StoreVO>>() {
            }.getType();
            hrs.makeGsonObject();

            storeVOList = (ArrayList<StoreVO>) hrs.parsingFunc(type);

            items = storeVOList;
        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}
