package org.androidtown.anywhere.any_13_search_result;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.any_tools.GpsSearch;
import org.androidtown.anywhere.any_tools.PermissionFlag;
import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.LikeVO;
import org.androidtown.anywhere.any_newVO.MenuVO;
import org.androidtown.anywhere.any_newVO.StoreListVO;
import org.androidtown.anywhere.any_newVO.StoreReplyVO;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class SearchResult extends Header {

    SearchResultAdapter searchAdapter;
    LinearLayoutManager llm;
    RecyclerView rv;
    ArrayList<StoreVO> items;

    RelativeLayout actionbar;
    FrameLayout menubar;

    StoreListVO storeListVO;
    //GPS위치 찾기위한 클래스
    GpsSearch gpsSearch;

    ArrayList<Double> distances;
    ArrayList<TempVO> initTempVO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere13_search_result);


        requestHttp();
        initialize();
        setHeader();


    }

    //초기화화
    public void initialize() {
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        rv = (RecyclerView) findViewById(R.id.resultRecycler);


        items = new ArrayList<>();
        if (initTempVO != null) {
            searchAdapter = new SearchResultAdapter(initTempVO, this);
            llm = new LinearLayoutManager(this);
            rv.setLayoutManager(llm);
            rv.setAdapter(searchAdapter);
        }

    }

    //헤더 셋팅
    public void setHeader() {
        setActionBar(actionbar);
        setTitleName("검색결과");
        setBackVisivility(View.VISIBLE);
        setMenuBarView(menubar);
        setMenuBarAnimation(rv);
        setMenuListSelectView(cunsumerMenuListSelect);
    }

    //레트로핏 요청
    public void requestHttp() {
        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createRetrofitObject();

        Intent intent = getIntent();
        Call call = null;

        if (intent.getStringExtra("GPS") != null) {
            if (!PermissionFlag.locationFlag) {
                Toast.makeText(this, "GPS권한을 허가해 주세요.", Toast.LENGTH_SHORT).show();
            } else {


                gpsSearch = new GpsSearch(this);
                CustomerController apiService = hrs.createApiserverObject();
                call = apiService.getStoreListVO("GPS");
                hrs.HttpRequestExecute(call);
                hrs.makeGsonObject();
                Type type = new TypeToken<StoreListVO>() {
                }.getType();
                storeListVO = (StoreListVO) hrs.parsingFunc(type);
                double latitude = gpsSearch.getLat();
                double longitude = gpsSearch.getLon();
                Log.d("내위치", "" + latitude);
                Log.d("내위치", "" + longitude);

                distance(latitude, longitude);
            }

        } else if (intent.getStringExtra("search") != null) {

            HashMap<String, String> dataMap = setDataMap(intent);
            String addressStr = intent.getStringExtra("selectAddress");

            try {
                CustomerController apiService = hrs.createApiserverObject();
                call = apiService.getSearchDetail(dataMap);
                hrs.HttpRequestExecute(call);
                hrs.makeGsonObject();
                Type type = new TypeToken<StoreListVO>() {
                }.getType();
                storeListVO = (StoreListVO) hrs.parsingFunc(type);
                //지오코딩을 위한 Address 리스트
                List<Address> searchAddress = geoCoder(addressStr);

                double latitude = searchAddress.get(0).getLatitude();
                double longitude = searchAddress.get(0).getLongitude();
                distance(latitude, longitude);
            } catch (Exception e) {
                Toast.makeText(this, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show();
            }


        } else if (intent.getStringExtra("searchByTag") != null) { //모임 타입에 따라 검색 결과 출력

            try {
                hrs = new HttpRequestSyncObject();
                hrs.createRetrofitObject();
                CustomerController apiService = hrs.createApiserverObject();
                call = apiService.tagSearch(intent.getStringExtra("searchByTag"));
                hrs.HttpRequestExecute(call);
                hrs.makeGsonObject();
                Type type = new TypeToken<StoreListVO>() {
                }.getType();
                storeListVO = (StoreListVO) hrs.parsingFunc(type);

                initTempVO = new ArrayList<>();

                for (int i = 0; i < storeListVO.getStoreVO().size(); i++) {
                    TempVO vo = new TempVO(i);
                    initTempVO.add(vo);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //지오코딩
    public List<Address> geoCoder(String addressStr) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> address = null;

        try {
            address = geocoder.getFromLocationName(addressStr, 10);
            Log.d("위도", "" + address.get(0).getLatitude());
            Log.d("경도", "" + address.get(0).getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("error", "지오코딩 에러");
            return null;
        }

        return address;
    }

    //Gps 거리 측정
    public void distance(double latitude, double longitude) {
        double distance = 0;
        distances = null;
        ArrayList<TempVO> tempListVO = null;
        //가게가 존재하지 않을때
        if (storeListVO == null) {
            //가게가 존재할때
        } else {
            distances = new ArrayList<>();
            Location myLocation = new Location("myLocation");
            myLocation.setLatitude(latitude);
            myLocation.setLongitude(longitude);

            Location location = new Location("point");

            tempListVO = new ArrayList<>();
            for (int i = 0; i < storeListVO.getStoreVO().size(); i++) {

                location.setLatitude(storeListVO.getStoreVO().get(i).getStore_latitude());
                location.setLongitude(storeListVO.getStoreVO().get(i).getStore_longitude());
                distance = myLocation.distanceTo(location);
                Log.d("거리", distance + "");
                distances.add(distance);
                TempVO tempVO = new TempVO(i);
                tempListVO.add(tempVO);
            }
            sort(tempListVO);
        }


    }

    //정렬 코드
    public void sort(ArrayList<TempVO> tempListVO) {


        if (tempListVO.size() > 1) {
            TempVO[] tempVOArray = new TempVO[tempListVO.size()];
            tempVOArray = tempListVO.toArray(tempVOArray);

            for (int i = 0; i < tempVOArray.length; i++) {

                for (int j = 0; j < tempVOArray.length - 1; j++) {

                    if (tempVOArray[j].tempDistance > tempVOArray[j + 1].tempDistance) {
                        TempVO tempVO = tempVOArray[j];
                        tempVOArray[j] = tempVOArray[j + 1];
                        tempVOArray[j + 1] = tempVO;
                    }
                }
            }

            ArrayList<TempVO> listVO = new ArrayList<>();
            Collections.addAll(listVO, tempVOArray);


            initTempVO = listVO;


        } else {
            initTempVO = tempListVO;
        }

    }

    //상세검색 요청 데이터
    public HashMap<String, String> setDataMap(Intent intent) {
        intent.getStringExtra("selectDate");
        intent.getStringExtra("selectMinTime");
        intent.getStringExtra("selectMaxTime");
        intent.getStringExtra("slectReservationPeopleNum");
        intent.getStringExtra("selectRadio");


        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("selectDate", intent.getStringExtra("selectDate"));
        dataMap.put("selectMinTime", intent.getStringExtra("selectMinTime"));
        dataMap.put("selectMaxTime", intent.getStringExtra("selectMaxTime"));
        dataMap.put("slectReservationPeopleNum", intent.getStringExtra("slectReservationPeopleNum"));
        dataMap.put("selectRadio", intent.getStringExtra("selectRadio"));

        return dataMap;
    }


    public class TempVO {

        public StoreReplyVO tempStoreReplyVO;
        public StoreVO tempStoreVO;
        public MenuVO tempMenuVO;
        public LikeVO tempLikeVO;
        public double tempDistance;

        public TempVO(int i) {
            tempStoreVO = storeListVO.getStoreVO().get(i);
            tempMenuVO = storeListVO.getMenuVO().get(i);
            tempLikeVO = storeListVO.getLikeVO().get(i);
            tempStoreReplyVO = storeListVO.getStoreReplyVO().get(i);
            if (distances != null) {
                tempDistance = distances.get(i);
            }
        }

    }
}