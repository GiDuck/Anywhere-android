package org.androidtown.anywhere.any_10_store_detail;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.reflect.TypeToken;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.LocationTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_11_store_reservation.StoreReservation;
import org.androidtown.anywhere.any_newVO.IconVO;
import org.androidtown.anywhere.any_newVO.LikeVO;
import org.androidtown.anywhere.any_newVO.PictureVO;
import org.androidtown.anywhere.any_newVO.StoreDetailVO;
import org.androidtown.anywhere.any_newVO.StoreReplyVO;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import info.hoang8f.widget.FButton;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;

import static org.androidtown.anywhere.httpcontrol.ImageURLParser.iconURLParser;
import static org.androidtown.anywhere.httpcontrol.ImageURLParser.imageURLParser;

public class StoreDetail extends Header implements View.OnClickListener, OnLikeListener, OnMapReadyCallback, View.OnTouchListener {

    private final SimpleDateFormat simpleDate = new SimpleDateFormat("yy-MM-dd");
    private RelativeLayout actionbar;
    private FrameLayout menubar;
    private ScrollView container1;
    String businessTime;

    //경도, 위도
    Double latitude; //위도
    Double longitude; //경도

    //구글맵
    MapFragment map;

    //지오코딩
    Geocoder geocoder;

    //슬라이드
    private ViewPager storeMainSlidePage;
    private CircleIndicator mainIndicator;
    //즐겨찾기 버튼
    private LikeButton storeLike;

    //가게시설 아이콘
    RecyclerView storeFacility;
    //댓글
    RecyclerView storeDetailReply;
    //에약하기,전화걸기 버튼
    FButton storeCall;
    FButton storeReservation;

    //가게 이름
    TextView storeName;
    //가게 소개 한 줄
    TextView storeImpactIntro;
    //사용 시간
    TextView storeUseTime;
    //1인 가격
    TextView storeFreePrice;
    //가능 인원
    TextView storePeopleNum;

    //가게 이미지 4장
    ImageView storeIntroImage_01;
    ImageView storeIntroImage_02;
    ImageView storeIntroImage_03;
    ImageView storeIntroImage_04;

    //가게 소개

    TextView storeIntro;

    //예약시 주의사항
    TextView storeAttention;

    //스토어 디테일 VO
    StoreDetailVO storeDetailVO;

    //다이얼로그 레퍼런스
    View dialogLayout;
    Dialog myDialog;

    //초기에 가게 정보 받기
    int store_num;
    String member_nick;
    //시설안내 아이콘 매칭 문자열
    final ArrayList<String> facility_name = new ArrayList<>();
    ImageView[] storeIntroImages;

    //카카오톡 링크 공유
    LinearLayout kakao_link_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere10_store_detail);

        //세션 유지를 위한 데이터 세팅
        SharedPreferences infoStoarge = getSharedPreferences("anywhere", MODE_PRIVATE);
        member_nick = infoStoarge.getString("user_nick", "");

        Intent intent = getIntent();
        store_num = intent.getIntExtra("store_num", store_num);


        initialize();
        setHeader();
        setData();
        setMainInfo();


        //뷰페이지 어뎁터 생성
        StoreDetailSlideAdapter storeDetailSlideAdapter = new StoreDetailSlideAdapter(this);
        //어뎁터 안의 데이터
        setStoreDetailSlidePageAdd(storeDetailSlideAdapter);
        //어뎁터 셋팅
        storeMainSlidePage.setAdapter(storeDetailSlideAdapter);
        //Indicator셋팅
        mainIndicator.setViewPager(storeMainSlidePage);

        //좋아요 버튼 클릭했을 때와 아니였을 때
        storeLike.setOnLikeListener(this);

        GridLayoutManager facilityManager = new GridLayoutManager(this, 3);
        storeFacility.setLayoutManager(facilityManager);
        StoreDetailFacilityAdapter facilityAdapter = new StoreDetailFacilityAdapter(this);
        setStoreDetailFacilityAdd(facilityAdapter);
        storeFacility.setAdapter(facilityAdapter);


        LinearLayoutManager replyManager = new LinearLayoutManager(this);
        storeDetailReply.setLayoutManager(replyManager);
        StoreDetailReplyAdapter replyAdapter = new StoreDetailReplyAdapter(this, member_nick);
        setStoreDetailReplyAdd(replyAdapter);
        storeDetailReply.setAdapter(replyAdapter);

        storeReservation.setOnClickListener(this);
        storeCall.setOnClickListener(this);

        map.getMapAsync(this);
    }

    public void setData() {


        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createRetrofitObject();
        CustomerController apiService = hrs.createApiserverObject();
        Call call = apiService.getStoreDetailVO(store_num, member_nick);
        hrs.HttpRequestExecute(call);
        hrs.makeGsonObjectTypeDate();
        Type type = new TypeToken<StoreDetailVO>() {
        }.getType();
        storeDetailVO = (StoreDetailVO) hrs.parsingFunc(type);
        if (storeDetailVO != null) {

            try {
                if (storeDetailVO.getLikeVO().getLike_nick().equals(member_nick)) {
                    storeLike.setLiked(true);
                }
            } catch (Exception e) {

                storeLike.setLiked(false);
            }

        } else {
        }

    }


    public void initialize() {


        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container1 = (ScrollView) findViewById(R.id.container1);
        //뷰 슬라이드 페이지
        storeMainSlidePage = (ViewPager) findViewById(R.id.storeMainSlidePage);
        mainIndicator = (CircleIndicator) findViewById(R.id.mainIndicator);
        //즐겨찾기 버튼
        storeLike = (LikeButton) findViewById(R.id.storeLike);
        //가게시설 아이콘
        storeFacility = (RecyclerView) findViewById(R.id.storeFacility);
        //전화 하기 버튼
        storeCall = (FButton) findViewById(R.id.storeCall);
        //예약 하기 버튼
        storeReservation = (FButton) findViewById(R.id.storeReservation);
        //가게 이름
        storeName = (TextView) findViewById(R.id.storeName);
        //가게 소개 한 줄
        storeImpactIntro = (TextView) findViewById(R.id.storeImpactIntro);
        //사용 시간
        storeUseTime = (TextView) findViewById(R.id.storeUseTime);
        //1인 가격
        storeFreePrice = (TextView) findViewById(R.id.storeFreePrice);
        //가능 인원
        storePeopleNum = (TextView) findViewById(R.id.storePeopleNum);

        //가게 이미지 4장

        storeIntroImage_01 = (ImageView) findViewById(R.id.storeIntroImage_01);
        storeIntroImage_02 = (ImageView) findViewById(R.id.storeIntroImage_02);
        storeIntroImage_03 = (ImageView) findViewById(R.id.storeIntroImage_03);
        storeIntroImage_04 = (ImageView) findViewById(R.id.storeIntroImage_04);


        //예약 가게 소개
        storeIntro = (TextView) findViewById(R.id.storeIntro);
        storeAttention = (TextView) findViewById(R.id.storeAttention);

        //주의 사항
        storeAttention = (TextView) findViewById(R.id.storeAttention);
        //구글맵
        map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        //카카오톡 링크 공유 (테스트)
        kakao_link_btn = (LinearLayout) findViewById(R.id.kakao_link_btn);
        kakao_link_btn.setOnClickListener(this);
    }

    public void setMainInfo() {

        StoreVO storeVO = storeDetailVO.getStoreVO();

        //가게 이름
        storeName.setText(storeVO.getStore_name());
        Log.d("giduckTest1", storeVO.getStore_name());
        //가게 소개 한 줄
        storeImpactIntro.setText(storeVO.getStore_intro());
        Log.d("giduckTest1", storeVO.getStore_intro());

        //가게 공간 소개
        storeIntro.setText(storeVO.getStore_spaceintro());
        //가게 주의 사항
        storeAttention.setText(storeVO.getStore_attention());
        //사용 시간
        int startTime = storeVO.getStore_starttime();
        int endTime = storeVO.getStore_endtime();

        businessTime = null;
        if (startTime > 0 && startTime <= 12) {

            businessTime = "오전 " + startTime + "시" + " ~ ";
        } else if (startTime > 12 && startTime < 24) {

            businessTime = "오후 " + (24 - startTime) + "시" + " ~ ";
        }

        if (endTime > 0 && endTime <= 12) {

            businessTime += "오전 " + endTime + "시";
        } else if (endTime > 12 && endTime < 24) {

            businessTime += "오후 " + (24 - endTime) + "시";
        }
        storeUseTime.setText(businessTime);

        //1인 가격
        storeFreePrice.setText(String.valueOf(storeDetailVO.getFree_price()));
        //가능 인원
        String vaildPeopleNum = storeVO.getStore_min() + " ~ " + storeVO.getStore_max();
        storePeopleNum.setText(vaildPeopleNum);
        storeIntroImages = new ImageView[]{storeIntroImage_01, storeIntroImage_02, storeIntroImage_03, storeIntroImage_04};

        //가게 내부 이미지 푸쉬
        for (int i = 0; i < storeDetailVO.getPictureVO().size(); i++) {
            Glide.with(this).load(imageURLParser(storeDetailVO.getPictureVO().get(i).getPicture_url())).into(storeIntroImages[i]);
            storeIntroImages[i].setOnClickListener(this);


        }
        //시설 안내 문자열 set
       /* facility_name.add("빔프로젝트");
        facility_name.add("화이트보드");
        facility_name.add("냉/난방시설");
        facility_name.add("음향기기");
        facility_name.add("복사/인쇄기");
        facility_name.add("팩스");
        facility_name.add("콘센트");
        facility_name.add("WI-FI");
        facility_name.add("TV");
        facility_name.add("주차장");*/
    }

    public void setHeader() {
        setActionBar(actionbar);
        setTitleName("가게 정보");
        setBackVisivility(View.VISIBLE);

        setMenuBarView(menubar);
        setMenuBarAnimation(container1);
        setMenuListSelectView(cunsumerMenuListSelect);
        storeDetailReply = (RecyclerView) findViewById(R.id.storeDetailReply);
    }


    public boolean hasReservationCheck() {


        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createRetrofitObject();
        CustomerController apiService = hrs.createApiserverObject();
        Call call = apiService.hasReservationList(storeDetailVO.getStoreVO().getStore_num());
        hrs.HttpRequestExecute(call);
        hrs.makeGsonObject();
        String result = hrs.parsingStringFunc("result");
        if (result.equals("true")) {

            return true;

        } else {

            return false;
        }


    }

    //클릭 이벤트 메소드
    @Override
    public void onClick(View v) {
        int click = v.getId();
        Intent intent = null;
        boolean isCheck;
        switch (click) {
            case R.id.storeCall: {


                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + storeDetailVO.getStoreVO().getStore_phone()));
                startActivity(intent);
                break;
            }
            case R.id.storeReservation: {


                if (member_nick.trim().length() == 0) {

                    Toast.makeText(this, "로그인을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                    return;

                }

                isCheck = false;
                isCheck = hasReservationCheck();
                if (isCheck) {

                    intent = new Intent(StoreDetail.this, StoreReservation.class);
                    intent.putExtra("storeDetailVO", storeDetailVO);
                    startActivity(intent);
                } else if (!isCheck) {
                    Toast.makeText(this, "예약 리스트가 존재 하지 않습니다", Toast.LENGTH_SHORT).show();

                }

                break;
            }
            case R.id.storeIntroImage_01: {
                showDialog(imageURLParser(storeDetailVO.getPictureVO().get(0).getPicture_url()));
                break;
            }
            case R.id.storeIntroImage_02: {
                showDialog(imageURLParser(storeDetailVO.getPictureVO().get(1).getPicture_url()));
                break;
            }
            case R.id.storeIntroImage_03: {
                showDialog(imageURLParser(storeDetailVO.getPictureVO().get(2).getPicture_url()));
                break;
            }
            case R.id.storeIntroImage_04: {
                showDialog(imageURLParser(storeDetailVO.getPictureVO().get(3).getPicture_url()));
                break;
            }
            case R.id.kakao_link_btn: {

                kakaoLinkTest();
                break;
            }
        }
    }


    //내부 사진 클릭시 크게 보여주는 다이얼로그
    private void showDialog(String url) {

        LayoutInflater dialog = LayoutInflater.from(this);
        dialogLayout = dialog.inflate(R.layout.anywhere10_store_detail_photo_dialog, null);
        myDialog = new Dialog(this);
        myDialog.setContentView(dialogLayout);
        ImageView bigImage = (ImageView) dialogLayout.findViewById(R.id.bigImage);
        Glide.with(dialogLayout.getContext()).load(url).into(bigImage);
        myDialog.show();


    }


    ////////////////////////////////////////////////////즐겨찾기 버튼////////////////////////////////////////////
    @Override
    public void liked(LikeButton likeButton) {

        if (member_nick.trim().length() == 0) {

            likeButton.setLiked(false);
            Toast.makeText(this, "로그인을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
            return;

        }


        if (likeButton == storeLike) {
            LikeVO likeVO = new LikeVO();
            likeVO.setStore_num(storeDetailVO.getStoreVO().getStore_num());
            likeVO.setLike_nick(member_nick);

            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createRetrofitObject();
            CustomerController apiService = hrs.createApiserverObject();
            Call call = apiService.onLike(likeVO);
            hrs.HttpRequestExecute(call);
            String result = hrs.parsingStringFunc("result");
            Log.d("giduckTest43", "추가 " + result);

            if (result.equals("true")) {
                Toast.makeText(this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                return;
            } else if (result.equals("false")) {
                Toast.makeText(this, "즐겨찾기 실패하였습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    @Override
    public void unLiked(LikeButton likeButton) {

        if (member_nick.trim().length() == 0) {

            likeButton.setLiked(false);
            Toast.makeText(this, "로그인을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
            return;

        }

        if (likeButton == storeLike) {
            LikeVO likeVO = new LikeVO();
            likeVO.setStore_num(storeDetailVO.getStoreVO().getStore_num());
            likeVO.setLike_nick(member_nick);

            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createRetrofitObject();
            CustomerController apiService = hrs.createApiserverObject();
            Call call = apiService.unLike(likeVO);
            hrs.HttpRequestExecute(call);
            String result = hrs.parsingStringFunc("result");
            if (result.equals("true")) {
                Toast.makeText(StoreDetail.this, "즐겨찾기가 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                return;
            } else if (result.equals("false")) {

                Toast.makeText(StoreDetail.this, "즐겨찾기 취소가 실패하였습니다.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }


    public void setStoreDetailReplyAdd(StoreDetailReplyAdapter replyAdapter) {
        try {
            ArrayList<StoreReplyVO> replyList = storeDetailVO.getReplyVO();
            for (StoreReplyVO reply : replyList) {


                StoreDetailReplyData data1 = new StoreDetailReplyData();
                data1.setReplyNick(reply.getReply_nick());
                try {
                    data1.setReplyStar(Float.parseFloat(String.valueOf(reply.getReply_star())));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                data1.setReplyContent(reply.getReply_content());
                data1.setReplyDate(simpleDate.format(reply.getReply_date()));
                replyAdapter.add(data1);
            }

            replyAdapter.setReplyList(replyList);

        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    //가게아이콘 어뎁터 데이터추가 메소드
    public void setStoreDetailFacilityAdd(StoreDetailFacilityAdapter adapter) {

        ArrayList<IconVO> iconList = storeDetailVO.getIconVO();
        for (int i = 0; i < iconList.size(); i++) {
            StoreDetailFacilityData data1 = new StoreDetailFacilityData();

            if (storeDetailVO.getIconVO().get(i).getFacility_num() == 1) {
                data1.setFacilityName("빔프로젝트");

            } else if (storeDetailVO.getIconVO().get(i).getFacility_num() == 2) {

                data1.setFacilityName("화이트 보드");
            } else if (storeDetailVO.getIconVO().get(i).getFacility_num() == 3) {

                data1.setFacilityName("에어컨");
            } else if (storeDetailVO.getIconVO().get(i).getFacility_num() == 4) {

                data1.setFacilityName("음향기기");
            } else if (storeDetailVO.getIconVO().get(i).getFacility_num() == 5) {

                data1.setFacilityName("복사-인쇄기");
            } else if (storeDetailVO.getIconVO().get(i).getFacility_num() == 6) {

                data1.setFacilityName("팩스");
            } else if (storeDetailVO.getIconVO().get(i).getFacility_num() == 7) {

                data1.setFacilityName("콘센트");
            } else if (storeDetailVO.getIconVO().get(i).getFacility_num() == 8) {

                data1.setFacilityName("WIFI");
            } else if (storeDetailVO.getIconVO().get(i).getFacility_num() == 9) {

                data1.setFacilityName("TV");
            } else if (storeDetailVO.getIconVO().get(i).getFacility_num() == 10) {

                data1.setFacilityName("주차장");
            }

            data1.setFacilityIcon(iconURLParser(iconList.get(i).getFacility_icon()));
            Log.d("giduckTest4", "아이콘 url " + iconURLParser(iconList.get(i).getFacility_icon()));
            adapter.add(data1);
        }
    }

    //가게정보 메인사진 어뎁터 데이터 추가 메소드
    public void setStoreDetailSlidePageAdd(StoreDetailSlideAdapter adapter) {
        ArrayList<PictureVO> imageList = storeDetailVO.getPictureVO();
        StoreDetailSlideData data1 = new StoreDetailSlideData();

        data1.setTestData(imageURLParser(storeDetailVO.getStoreVO().getStore_mainurl()));
        adapter.add(data1);

        for (PictureVO image : imageList) {
            data1 = new StoreDetailSlideData();
            data1.setTestData(imageURLParser(image.getPicture_url()));
            adapter.add(data1);
        }


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latLng = new LatLng(storeDetailVO.getStoreVO().getStore_latitude(), storeDetailVO.getStoreVO().getStore_longitude());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);


        googleMap.clear();
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

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
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }


    public void kakaoLinkTest() { //카카오톡으로 주소 링크 공유하기
        StoreVO storeInfo = storeDetailVO.getStoreVO();


        LocationTemplate params = LocationTemplate.newBuilder(storeInfo.getStore_addr(),
                ContentObject.newBuilder(storeInfo.getStore_name(),
                        "http://image14.hanatour.com/uploads/2013/08/%EB%B8%94%EB%A1%9C%EA%B7%B8DSC05805-780x518.jpg",
                        LinkObject.newBuilder()
                                .setWebUrl("https://www.daum.net")
                                .setMobileWebUrl("http://www.daum.net")
                                .build()).setDescrption("우리 여기서 만나요! - 커피 한 잔 가격으로 우리 동네에서 편하게 모임을 가지세요!" +
                        "유휴 공간 플랫폼, 애니웨어 ").build())
                .setAddressTitle(storeInfo.getStore_name()).build();

        KakaoLinkService.getInstance().sendDefault(this, params, new ResponseCallback<KakaoLinkResponse>() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Toast.makeText(StoreDetail.this, "링크 공유 실패", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(KakaoLinkResponse result) {
            }
        });


    }

}
