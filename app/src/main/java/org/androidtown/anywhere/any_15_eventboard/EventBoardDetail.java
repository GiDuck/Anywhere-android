package org.androidtown.anywhere.any_15_eventboard;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.EventVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.text.SimpleDateFormat;

import info.hoang8f.widget.FButton;
import retrofit2.Call;

import static org.androidtown.anywhere.httpcontrol.ImageURLParser.imageURLParser;

public class EventBoardDetail extends Header implements View.OnClickListener {

    EventVO boardvo;
    TextView title;
    TextView date;
    TextView content;
    TextView eventEndTime;
    TextView eventStartTime;
    FButton eventApplyBtn;
    Intent intent;
    ImageView imageView;

    RelativeLayout actionbar;
    FrameLayout menubar;
    RelativeLayout container;

    boolean dialogisTrue;
    String user_nick;
    String user_division;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere15_event_board_detail);

        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container = (RelativeLayout) findViewById(R.id.container1);
        SharedPreferences shef = getSharedPreferences("anywhere", MODE_PRIVATE);
        user_nick = shef.getString("user_nick", "");
        user_division=shef.getString("user_division", "");
        //액션바 셋팅
        setActionBar(actionbar);
        //액션바 이름 셋팅
        setTitleName("이벤트");
        //액션바 안에 백버튼 보일지 안보일지 셋팅
        setBackVisivility(View.VISIBLE);
        //메뉴바 셋팅
        setMenuBarView(menubar);
        //메뉴바 에니메이션 셋팅
        setMenuBarAnimation(container);
        //메뉴바 안에 리스트 셋
        if(user_division.equals("0")) {
            setMenuListSelectView(cunsumerMenuListSelect);
        }else if(user_division.equals("1")){
            setMenuListSelectView(supplierMenuListSelect);
        }



        boardvo = new EventVO();
        Intent intent = getIntent();
        boardvo = (EventVO) intent.getSerializableExtra("boardVO");

        eventEndTime = (TextView) findViewById(R.id.eventEndTime);
        eventStartTime = (TextView) findViewById(R.id.eventStartTime);
        title = (TextView) findViewById(R.id.eventBoardDetail_title);
        date = (TextView) findViewById(R.id.eventBoardDetail_date);
        content = (TextView) findViewById(R.id.eventBoardDetail_content);
        content.setMovementMethod(new ScrollingMovementMethod());
        eventApplyBtn = (FButton) findViewById(R.id.eventBoard_applyBtn);
        imageView = (ImageView) findViewById(R.id.eventimage);

        title.setText(boardvo.getEvent_title());
        content.setText(boardvo.getEvent_content());
        date.setText(new SimpleDateFormat("yyyy년 MM월 dd일 HH : mm").format(boardvo.getEvent_date()));
        eventStartTime.setText(new SimpleDateFormat("yyyy년 MM월 dd일 HH : mm").format(boardvo.getEvent_start()));
        eventEndTime.setText(new SimpleDateFormat("yyyy년 MM월 dd일 HH : mm").format(boardvo.getEvent_end()));

        Glide.with(this).load(imageURLParser(boardvo.getEvent_url())).fitCenter().into(imageView);
        eventApplyBtn.setOnClickListener(this);
        dialogisTrue = false;

    }

    public void makeDialog(final Activity activity) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("이벤트를 신청합니다. 진행 하시겠습니까?");

        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HttpRequestSyncObject hrs = new HttpRequestSyncObject();
                hrs.createRetrofitObject();
                CustomerController apiService = hrs.createApiserverObject();
                Call call = apiService.applyEvent(user_nick, boardvo.getEvent_num());
                hrs.HttpRequestExecute(call);
                hrs.makeGsonObject();
                String result = hrs.parsingStringFunc("result");

                if (result.equals("true")) {
                    Toast.makeText(activity, "이벤트 신청 성공하였습니다.", Toast.LENGTH_SHORT).show();
                    return;

                } else if (result.equals("false")) {
                    Toast.makeText(activity, "이미 신청하셨습니다.", Toast.LENGTH_SHORT).show();
                    return;

                }
            }
        });

        alertDialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;

            }
        });

        AlertDialog alert = alertDialog.create();
        alert.setTitle("이벤트 신청");
        alert.show();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eventBoard_applyBtn:
                makeDialog(this);

        }
    }

}

