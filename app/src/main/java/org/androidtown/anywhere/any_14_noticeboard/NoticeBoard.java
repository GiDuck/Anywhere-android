package org.androidtown.anywhere.any_14_noticeboard;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.NoticeVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;

public class NoticeBoard extends Header {
    NoticeBoardAdapter Adapter;
    LinearLayoutManager llm;
    RecyclerView rv;
    ArrayList<NoticeVO> items;
    ArrayList<NoticeVO> noticeVOList;
    Intent intent;

    RelativeLayout actionbar;
    FrameLayout menubar;
    EditText search_keyword;
    CheckBox titleCheckBox;
    CheckBox writerCheckBox;
    CheckBox contentCheckBox;
    FloatingActionButton fabSearch;

    View dialogLayout;
    Button searchBtn;
    Dialog myDialog;

    String user_division;
    SharedPreferences shef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere14_notice_board);

        items = new ArrayList<>();

        shef = getSharedPreferences("anywhere", MODE_PRIVATE);
        user_division = shef.getString("user_division", "");

        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        rv = (RecyclerView) findViewById(R.id.noticeBoard_recycler);

        //액션바 셋팅
        setActionBar(actionbar);
        //액션바 이름 셋팅
        setTitleName("공지사항");
        //액션바 안에 백버튼 보일지 안보일지 셋팅
        setBackVisivility(View.VISIBLE);
        //메뉴바 셋팅
        setMenuBarView(menubar);
        //메뉴바 에니메이션 셋팅
        setMenuBarAnimation(rv);

        if(user_division.equals("0")) {
            setMenuListSelectView(cunsumerMenuListSelect);
        }else if(user_division.equals("1")){
            setMenuListSelectView(supplierMenuListSelect);
        }



        Adapter = new NoticeBoardAdapter(items, this);
        llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setAdapter(Adapter);
        addTestData();

        fabSearch = (FloatingActionButton) findViewById(R.id.noticeBoard_searchFloatBtn);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog();

            }
        });

    }

    public void addTestData() {

        try {

            NoticeVO notice = new NoticeVO();

            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createRetrofitObject();
            CustomerController apiService = hrs.createApiserverObject();
            Call call = apiService.getNoticeBoard("true");
            hrs.HttpRequestExecute(call);
            hrs.makeGsonObjectTypeDate();
            Type type = new TypeToken<List<NoticeVO>>() {
            }.getType();
            noticeVOList = (ArrayList<NoticeVO>) hrs.parsingFunc(type);

            for (NoticeVO noticeVO : noticeVOList) {
                items.add(noticeVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "죄송합니다. 통신에 문제가 발생하였습니다.", Toast.LENGTH_LONG).show();

        }

    }

    private void ShowDialog() {
        LayoutInflater dialog = LayoutInflater.from(this);
        dialogLayout = dialog.inflate(R.layout.dialog_search, null);
        myDialog = new Dialog(this);
        myDialog.setContentView(dialogLayout);
        myDialog.show();
        searchBtn = (Button) dialogLayout.findViewById(R.id.search_btn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });


    }

    private void Search() {


        search_keyword = (EditText) dialogLayout.findViewById(R.id.search_keyword);
        titleCheckBox = (CheckBox) dialogLayout.findViewById(R.id.search_condition_title);
        writerCheckBox = (CheckBox) dialogLayout.findViewById(R.id.search_condition_writer);
        contentCheckBox = (CheckBox) dialogLayout.findViewById(R.id.search_condition_content);

        writerCheckBox.setVisibility(View.GONE);


        if (search_keyword.getText().length() <= 0) {
            Toast.makeText(this, "검색어를 입력하세요!", Toast.LENGTH_SHORT).show();
            return;

        }

        if (!titleCheckBox.isChecked() && !writerCheckBox.isChecked() && !contentCheckBox.isChecked()) {
            Toast.makeText(this, "검색 조건은 한 가지 이상 선택 되어야 합니다.", Toast.LENGTH_SHORT).show();
            return;

        }



        /*if (search_keyword.toString().getBytes().length >= 0 && (titleCheckBox.isChecked() || writerCheckBox.isChecked() || contentCheckBox.isChecked() )) {*/


        //검색 로직
        HashSet<NoticeVO> boardSet = new HashSet<>();
        String keyword = search_keyword.getText().toString();

        for (NoticeVO noticeVO : noticeVOList) {

            if (titleCheckBox.isChecked()) {

                if (noticeVO.getNotice_title().contains(keyword)) {
                    boardSet.add(noticeVO);
                }
            }

            if (contentCheckBox.isChecked()) {

                if (noticeVO.getNotice_content().contains(keyword)) {
                    boardSet.add(noticeVO);
                }
            }


        }

        items = new ArrayList<>(boardSet);
        Adapter.setItems(items);
        Adapter.notifyDataSetChanged();
        Toast.makeText(this, "검색 완료", Toast.LENGTH_SHORT).show();


        myDialog.dismiss();


    }

}
