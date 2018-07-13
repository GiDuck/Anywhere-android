package org.androidtown.anywhere.any_09_favorite_store;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.LikeListVO;
import org.androidtown.anywhere.any_newVO.LikeVO;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;

import static org.androidtown.anywhere.httpcontrol.ImageURLParser.imageURLParser;

//즐겨찾기
public class FavoriteStore extends Header {

    FavoriteAdapter favoriteAdapter;
    GridLayoutManager llm;
    RecyclerView rv;
    ArrayList<ArrayList<String>> items;
    ArrayList<LikeVO> likeVO;
    ArrayList<StoreVO> storeVO;
    RelativeLayout actionbar;
    FrameLayout menubar;
    LikeListVO likeListVO;

    String user_nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere09_favorite_store);

        //세션 유지를 위한 데이터 세팅
        SharedPreferences infoStoarge = getSharedPreferences("anywhere", MODE_PRIVATE);
        user_nick = infoStoarge.getString("user_nick", "");


        items = new ArrayList<>();

        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        rv = (RecyclerView) findViewById(R.id.favoriteRecycler);
        //액션바 셋팅
        setActionBar(actionbar);
        //액션바 이름 셋팅
        setTitleName("즐겨찾기");
        //액션바 안에 백버튼 보일지 안보일지 셋팅
        setBackVisivility(View.VISIBLE);
        //메뉴바 셋팅
        setMenuBarView(menubar);
        //메뉴바 에니메이션 셋팅
        setMenuBarAnimation(rv);
        //메뉴바 안에 리스트 셋
        setMenuListSelectView(cunsumerMenuListSelect);

        favoriteAdapter = new FavoriteAdapter(this, items); //어뎁터 초기화

        //리사이클러뷰를 그리드 레이아웃 형태로 지정
        llm = new GridLayoutManager(this, 2); //그리드 레이아웃을 사용하기 위한 그리드 레이아웃 매니저 호출
        //컬럼 카운트를 2로 설정 (한 줄에 2개씩)
        rv.setLayoutManager(llm);
        rv.setAdapter(favoriteAdapter);

        addData();


    }

    //서버로 부터 즐겨찾기 데이터를 들고와서 어뎁터에 추가하는 메소드
    public void addData() {

        try {
            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createRetrofitObject();
            CustomerController apiService = hrs.createApiserverObject();

            Call call = apiService.getLikeVOList(user_nick);

            hrs.HttpRequestExecute(call);
            hrs.makeGsonObject();

            Type type = new TypeToken<LikeListVO>() {
            }.getType();
            likeListVO = (LikeListVO) hrs.parsingFunc(type);


            ArrayList<StoreVO> storeVOList = likeListVO.getStoreVO();

            Log.d("testCode", "storeVO" + storeVOList.get(0).getStore_name());
            Log.d("testCode", "storeVO" + storeVOList.get(0).getStore_addr());

            ArrayList<String> tempStorage = new ArrayList<>();

            for (StoreVO storeVO : storeVOList) {
                tempStorage = new ArrayList<>();
                tempStorage.add(imageURLParser(storeVO.getStore_mainurl()));
                tempStorage.add(storeVO.getStore_name());
                tempStorage.add(String.valueOf(storeVO.getStore_num()));
                items.add(tempStorage);

            } //어뎁터에 데이터 set


        } catch (Exception e) {

            e.printStackTrace();
        }

    }

}
