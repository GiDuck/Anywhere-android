package org.androidtown.anywhere.any_21_supplier_addplace1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_17_supplier_main.SupplierMain;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.SupplierController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class AddPlaceFirst extends Header {

    private RelativeLayout actionbar;
    private FrameLayout menubar;
    private RecyclerView rv;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<StoreVO> items;
    private AddplaceFirstAdapter Adapter;
    private ArrayList<StoreVO> storeVOList;

    private String user_nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData();
        setContentView(R.layout.anywhere21_add_place1);

        initialize();
        setHeader();
        Adapter = new AddplaceFirstAdapter(this);

        linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(Adapter);
        Adapter.add(items);
    }

    public void initialize(){
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        rv = (RecyclerView) findViewById(R.id.addplace1_recycler);

    }
    public void setHeader(){
        //액션바 셋팅
        setActionBar(actionbar);
        //액션바 이름 셋팅
        setTitleName("예약 등록");
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
        SharedPreferences infoStoarge = getSharedPreferences("anywhere", MODE_PRIVATE);
        user_nick=infoStoarge.getString("user_nick","");

        try {
            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createSupplierRetrofitObject();
            SupplierController supplierApiservice = hrs.createSupplierApiserverObject();

        /*nick*/
            Call call = supplierApiservice.getStoreVO(user_nick);
            hrs.HttpRequestExecute(call);
            Type type = new TypeToken<List<StoreVO>>() {
            }.getType();
            hrs.makeGsonObject();

            storeVOList = (ArrayList<StoreVO>) hrs.parsingFunc(type);
            Log.d("giduckTest", storeVOList.size() + "");

            items = storeVOList;


        } catch (Exception e) {
            Toast.makeText(this, "죄송합니다. 통신에 문제가 발생하였습니다.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            Intent intent = new Intent(AddPlaceFirst.this, SupplierMain.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }


}
