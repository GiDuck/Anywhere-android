package org.androidtown.anywhere.any_19_store_defalut_modify;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.reflect.TypeToken;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.any_tools.PermissionFlag;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol.ImageURLParser;
import org.androidtown.anywhere.httpcontrol_retrofitController.SupplierController;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gun0912.tedbottompicker.TedBottomPicker;
import info.hoang8f.widget.FButton;
import retrofit2.Call;

public class StoreDefalutModifyFirst extends Header implements TedBottomPicker.OnMultiImageSelectedListener
        , TedBottomPicker.OnImageSelectedListener
        , View.OnClickListener, OnMapReadyCallback
        , View.OnTouchListener
        , PlaceSelectionListener{


    RelativeLayout actionbar;
    FrameLayout menubar;
    ScrollView container1;
    //구글맵
    MapFragment map;
    //사진이미지 픽
    TedBottomPicker imagePick;
    //지오코딩
    Geocoder geocoder;
    List<Address> address;

    FButton storeMainResister;
    FButton storeIntroResister;

    FButton storeSearch;
    Button next;
    ImageView storeMainImage;
    ImageView storeIntroImage1;
    ImageView storeIntroImage2;
    ImageView storeIntroImage3;
    ImageView storeIntroImage4;

    EditText storeName;
    EditText storeAddress;
    EditText storePhone;

    StoreVO storeVO;
    int store_num;


    List<String> slideUrl;
    String titleUrl;


    //이미지 픽
    List<Uri> slideImageList;
    Uri titlePhoto;

    //경도, 위도
    Double latitude; //위도
    Double longitude; //경도

    Boolean titleImageIsSelected;
    Boolean slideListIsSelected;
    View scrollBlock;

    MaterialSpinner operationStartTime;
    MaterialSpinner operationEndTime;

    //사진 따로 수정안할때의 FLAGH

    String[] timeTable = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"
            , "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"};

    PlaceAutocompleteFragment storeAutoAddress;
    String addressStr = null;
    String real_address;

    public static final Pattern VALID_HAND_PHONE_NUMBER =
            Pattern.compile("/^\\d{3}-\\d{3,4}-\\d{4}$/;", Pattern.CASE_INSENSITIVE); //핸드폰 번호 정규식

    public static final Pattern VALID_PHONE_NUMBER =
            Pattern.compile("/^\\d{2,3}-\\d{3,4}-\\d{4}$/;", Pattern.CASE_INSENSITIVE); //일반 전화번호 정규식

    Matcher vaild_handphone_number;
    Matcher vaild_phone_number;

    int searchCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere19_store_defalut_modify_01);

        titleImageIsSelected = false;
        slideListIsSelected = false;
        Intent intent = getIntent();


        this.store_num = intent.getIntExtra("store_num", store_num);

        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createSupplierRetrofitObject();
        SupplierController apiService = hrs.createSupplierApiserverObject();
        Call call = apiService.getTargetStoreVO(store_num);
        hrs.HttpRequestExecute(call);
        hrs.makeGsonObjectTypeDate();
        Type type = new TypeToken<StoreVO>() {
        }.getType();
        storeVO = (StoreVO) hrs.parsingFunc(type);


        initialize();
        setHeader();

        //검색창
        storeAutoAddress.setOnPlaceSelectedListener(this);
        storeAutoAddress.setHint("ex)부산시 해운대구 반송2동");
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .build();
        storeAutoAddress.setFilter(typeFilter);
        storeAddress.setHint("상세주소를 입력해 주세요.");

    }

    //받아온 데이터들을 set 해주는 메소드
    public void setData() {


        slideUrl = new ArrayList<>();
        //editText set

        storeName.setText(storeVO.getStore_name());
        storeAddress.setText(storeVO.getStore_addr());
        storePhone.setText(storeVO.getStore_phone());
        searchStore((storeVO.getStore_addr()));
        operationStartTime.setText(String.valueOf(storeVO.getStore_starttime()));
        operationEndTime.setText(String.valueOf(storeVO.getStore_endtime()));


        //이미지 set

        ImageView introImage[] = {storeIntroImage1, storeIntroImage2, storeIntroImage3, storeIntroImage4};



        slideUrl = storeVO.getPicture_url();
        titleUrl = storeVO.getStore_mainurl();

        for (int i = 0; i < slideUrl.size(); i++) {
            Glide.with(this).load(new ImageURLParser().imageURLParser(slideUrl.get(i))).into(introImage[i]);
        }

        Glide.with(this).load(new ImageURLParser().imageURLParser(titleUrl)).into(storeMainImage);


    }

    @Override
    public void onPlaceSelected(Place place) {
        addressStr = place.getAddress().toString();
    }

    @Override
    public void onError(Status status) {

    }

    public void initialize() {
        //헤더 데이터
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container1 = (ScrollView) findViewById(R.id.container1);

        storeMainResister = (FButton) findViewById(R.id.storeMainResister);
        storeIntroResister = (FButton) findViewById(R.id.storeIntroResister);

        storeMainImage = (ImageView) findViewById(R.id.storeMainImage);
        storeIntroImage1 = (ImageView) findViewById(R.id.storeIntroImage1);
        storeIntroImage2 = (ImageView) findViewById(R.id.storeIntroImage2);
        storeIntroImage3 = (ImageView) findViewById(R.id.storeIntroImage3);
        storeIntroImage4 = (ImageView) findViewById(R.id.storeIntroImage4);

        map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        geocoder = new Geocoder(this);
        next = (Button) findViewById(R.id.next);
        storeSearch = (FButton) findViewById(R.id.storeSearch);

        storeName = (EditText) findViewById(R.id.storeName);
        storeAddress = (EditText) findViewById(R.id.storeAddress);
        storePhone = (EditText) findViewById(R.id.storePhone);

        operationStartTime = (MaterialSpinner) findViewById(R.id.operationStartTime);
        operationEndTime = (MaterialSpinner) findViewById(R.id.operationEndTime);

        operationStartTime.setItems(timeTable);
        operationEndTime.setItems(timeTable);

        scrollBlock = (View) findViewById(R.id.scrollBlock);

        storeAutoAddress = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.storeAutoAddress);
        next.setOnClickListener(this);
        storeMainResister.setOnClickListener(this);
        storeIntroResister.setOnClickListener(this);
        storeSearch.setOnClickListener(this);
        setData();

    }

    public void setHeader() {
        setActionBar(actionbar);
        setTitleName("가게 수정하기");
        setBackVisivility(View.VISIBLE);
        setMenuBarView(menubar);
        setMenuBarAnimation(container1);
        setMenuListSelectView(supplierMenuListSelect);
    }

    @Override
    public void onClick(View v) {
        imagePick = null;
        switch (v.getId()) {
            case R.id.storeMainResister: {
                if (PermissionFlag.storageFlag) {

                    Glide.with(this).load(" ").into(storeMainImage);
                    storeMainImage.setBackgroundColor(Color.GRAY);

                    imagePick = new TedBottomPicker.Builder(StoreDefalutModifyFirst.this)
                            .setOnImageSelectedListener(this)
                            .create();
                    imagePick.show(getSupportFragmentManager());
                } else {
                    Toast.makeText(this, "설정에 들어가서 파일저장소를 허가해 주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.storeIntroResister: {

                ImageView introImage[] = {storeIntroImage1, storeIntroImage2, storeIntroImage3, storeIntroImage4};

                for (int i = 0; i < 4; i++) {
                    Glide.with(this).load(" ").into(introImage[i]);
                    introImage[i].setBackgroundColor(Color.GRAY);
                }

                if (PermissionFlag.storageFlag) {
                    imagePick = new TedBottomPicker.Builder(StoreDefalutModifyFirst.this)
                            .setOnMultiImageSelectedListener(this)
                            .setSelectMaxCount(4)
                            .create();

                    imagePick.show(getSupportFragmentManager());
                } else {
                    Toast.makeText(this, "설정에 들어가서 파일저장소를 허가해 주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.storeSearch: {
                searchCount = 1;
                searchStore(addressStr+storeAddress.getText().toString());

                break;
            }
            case R.id.next: {
                String[] phoneNumberCheck = storePhone.getText().toString().split("-");

                vaild_handphone_number=VALID_PHONE_NUMBER.matcher(storePhone.getText().toString());
                vaild_phone_number=VALID_PHONE_NUMBER.matcher(storePhone.getText().toString());


               /* HttpRequestSyncObject hrs = new HttpRequestSyncObject();
                hrs.createSupplierRetrofitObject();
                hrs.createSupplierApiserverObject();
                SupplierController apiService = hrs.createSupplierApiserverObject();
                Call call = apiService.storeAuth(storeName.getText().toString(), storeAddress.getText().toString(), "giduck");
                hrs.HttpRequestExecute(call);
                String result = hrs.parsingStringFunc("result");
                Log.d("giduckTest", "result" + result);

                if (result.equals("false")) {

                    Toast.makeText(this, "이미 가게가 등록 되어 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }*/
                Log.d("giduckTest", "Urlsize" + slideUrl.size());

                ImageURLParser imageParser = new ImageURLParser();

                if (!titleImageIsSelected) {

                    titlePhoto = Uri.parse(" ");
                }

                if (!slideListIsSelected) {

                    slideImageList = new ArrayList<>();
                    for (int i = 0; i < 4; i++) {
                        slideImageList.add(Uri.parse(" "));
                    }
                }


                if (storeName.getText().toString().trim().length() == 0) {

                    Toast.makeText(this, "가게 이름을 작성하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else if(storeAddress.getText().toString().trim().length() == 0||storeAddress.getText().toString().length()==0){

                    Toast.makeText(this, "가게 상세주소를 작성하세요.", Toast.LENGTH_SHORT).show();
                    return;

                }else if (storePhone.getText().toString().trim().length() == 0) {

                    Toast.makeText(this, "가게 전화번호를 작성하세요.", Toast.LENGTH_SHORT).show();
                    return;

                }else if(phoneNumberCheck.length!=3){

                    Toast.makeText(this,"가게 전화번호를 정확히 입력해 주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }else if ((Integer.parseInt(operationEndTime.getText().toString()) - Integer.parseInt(operationStartTime.getText().toString())) <= 0) {

                    Toast.makeText(this, "운영 시간은 항상 시작 시간이 먼저 선택되어야 합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }else if (titlePhoto == null) {

                    Toast.makeText(this, "대표 사진을 지정하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }else if (slideImageList.size() <4) {
                    Toast.makeText(this, "슬라이드 사진을 4장 지정하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!titleImageIsSelected) {

                    titlePhoto = null;
                }

                if (!slideListIsSelected) {

                    slideImageList = null;
                }


                /*else{

                    Toast.makeText(this, "오류가 발생 하였습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("errorCode", "가게 등록 오류 발생");
                    Intent intent = new Intent(StoreResisterFirst.this, SupplierMain.class);
                    startActivity(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    return;

                }*/

                if(addressStr==null){
                    real_address = storeAddress.getText().toString();
                }else if(storeAddress.getText().toString()==null){
                    real_address = addressStr;
                }else{
                    real_address = addressStr + " " + storeAddress.getText().toString();

                }

                storeVO.setStore_name(storeName.getText().toString());
                storeVO.setStore_addr(real_address);
                storeVO.setStore_phone(storePhone.getText().toString());
                storeVO.setStore_latitude(latitude);
                storeVO.setStore_longitude(longitude);
                storeVO.setStore_starttime(Integer.parseInt(operationStartTime.getText().toString()));
                storeVO.setStore_endtime(Integer.parseInt(operationEndTime.getText().toString()));


                Intent intent = new Intent(this, StoreDefalutModifySecond.class);
                intent.putExtra("storeVO", storeVO);
                intent.putExtra("slideImageList", (Serializable) slideImageList);
                intent.putExtra("titlePhoto", titlePhoto);
                startActivity(intent);
                break;
            }

        }
    }


    //주소 검색해 주는 메소드
    public void searchStore(String addressStr) {

        Log.d("확인", "확인1");
        address = null;
        try {
            address = geocoder.getFromLocationName(addressStr, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (address != null) {
            if (address.size() == 0) {
                Log.d("확인", "확인2");
                Toast.makeText(this, "해당되는 주소가 없습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("확인", "확인3");
                map.getMapAsync(this);
            }
        }

    }

    //맵뷰를 터치할때 스크롤 터치이벤트를 방지하기 위함
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        int action = event.getAction();

        if (id == R.id.scrollBlock) {
            switch (action) {
                case MotionEvent.ACTION_DOWN: {
                    container1.requestDisallowInterceptTouchEvent(true);
                    return false;

                }
                case MotionEvent.ACTION_UP: {
                    container1.requestDisallowInterceptTouchEvent(false);
                    return true;

                }
                case MotionEvent.ACTION_MOVE: {
                    container1.requestDisallowInterceptTouchEvent(true);
                    return false;

                }
                default:
                    return true;
            }
        }
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = null;

        if(searchCount==0){
            latLng  = new LatLng(storeVO.getStore_latitude(), storeVO.getStore_longitude());
            latitude = storeVO.getStore_latitude();
            longitude = storeVO.getStore_longitude();

        }else{
            latLng  = new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude());
            latitude = address.get(0).getLatitude();
            longitude = address.get(0).getLongitude();

        }


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);


        googleMap.clear();
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    //단일 이미지 선택 시
    @Override
    public void onImageSelected(Uri uri) {


        Glide.with(this).load(uri).into(storeMainImage);
        titlePhoto = uri;
        titleImageIsSelected = true;
    }

    //여러가지 이미지 선택일 시
    @Override
    public void onImagesSelected(ArrayList<Uri> uriList) {
        ImageView introImage[] = {storeIntroImage1, storeIntroImage2, storeIntroImage3, storeIntroImage4};


        for (int i = 0; i < uriList.size(); i++) {
            Glide.with(this).load(uriList.get(i)).into(introImage[i]);
        }

        slideImageList = uriList;
        slideListIsSelected = true;

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
