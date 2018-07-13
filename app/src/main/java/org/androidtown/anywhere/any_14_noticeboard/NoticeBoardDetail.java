package org.androidtown.anywhere.any_14_noticeboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.NoticeVO;
import org.androidtown.anywhere.header.Header;

import java.text.SimpleDateFormat;

import static org.androidtown.anywhere.httpcontrol.ImageURLParser.imageURLParser;

public class NoticeBoardDetail extends Header {

    NoticeVO noticeVO;
    TextView title;
    TextView date;
    TextView content;
    ImageView noticeImage;

    RelativeLayout actionbar;
    FrameLayout menubar;
    RelativeLayout container;

    SharedPreferences shef;
    String user_division;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere14_notice_board_detail);
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container = (RelativeLayout) findViewById(R.id.container1);

        shef=getSharedPreferences("anywhere", MODE_PRIVATE);
        user_division=shef.getString("user_division", "");

        //액션바 셋팅
        setActionBar(actionbar);
        //액션바 이름 셋팅
        setTitleName("공지사항");
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




        noticeVO = new NoticeVO();
        Intent intent = getIntent();
        noticeVO = (NoticeVO) intent.getSerializableExtra("NoticeVO");


        title = (TextView) findViewById(R.id.noticeDetail_title);
        date = (TextView) findViewById(R.id.noticeBoardDetail_date);
        content = (TextView) findViewById(R.id.noticeBoardDetail_content);
        noticeImage=(ImageView)findViewById(R.id.noticeimage);

        title.setText(noticeVO.getNotice_title());
        content.setText(noticeVO.getNotice_content());
        date.setText(new SimpleDateFormat("yyyy년 MM월 dd일 HH : mm").format(noticeVO.getNotice_date()));
        Glide.with(this).load(imageURLParser(noticeVO.getNotice_url())).into(noticeImage);

    }
}
