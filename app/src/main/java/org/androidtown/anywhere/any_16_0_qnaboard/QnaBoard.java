package org.androidtown.anywhere.any_16_0_qnaboard;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import org.androidtown.anywhere.any_newVO.QaVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;

public class QnaBoard extends Header {

    QnaBoardAdapter Adapter;
    LinearLayoutManager llm;
    RecyclerView rv;
    ArrayList<QaVO> items;
    FloatingActionButton fab;
    Intent intent;
    RelativeLayout actionbar;
    FrameLayout menubar;
    CoordinatorLayout container;
    FloatingActionButton fabWrite;
    FloatingActionButton fabSearch;
    View dialogLayout;
    Button searchBtn;
    EditText search_keyword;
    CheckBox titleCheckBox;
    CheckBox writerCheckBox;
    CheckBox contentCheckBox;
    Dialog myDialog;

    List<QaVO> boardVOList;
    String user_nick;
    String user_division;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere16_qna_board);

        SharedPreferences infoStoarge = getSharedPreferences("anywhere", MODE_PRIVATE);
        user_nick = infoStoarge.getString("user_nick", "");
user_division=infoStoarge.getString("user_division", "");
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container = (CoordinatorLayout) findViewById(R.id.container1);
        rv = (RecyclerView) findViewById(R.id.qnaBoard_recycler);

        items = new ArrayList<>();


        //액션바 셋팅
        setActionBar(actionbar);
        //액션바 이름 셋팅
        setTitleName("Q&A");
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

        Adapter = new QnaBoardAdapter(items, this);
        llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setAdapter(Adapter);
        addTestData();

        LayoutInflater dialog = LayoutInflater.from(this);
        View dialogLayout = dialog.inflate(R.layout.dialog_search, null);

        findViewById(R.id.qnaBoard_searchFloatBtn).setOnClickListener(floatBtnClickListener);
        findViewById(R.id.qnaBoard_writeFloatBtn).setOnClickListener(floatBtnClickListener);

    }

    FloatingActionButton.OnClickListener floatBtnClickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            fabWrite = (FloatingActionButton) findViewById(R.id.qnaBoard_writeFloatBtn);
            fabSearch = (FloatingActionButton) findViewById(R.id.qnaBoard_searchFloatBtn);
            switch (v.getId()) {
                case R.id.qnaBoard_writeFloatBtn:
                    intent = new Intent(QnaBoard.this, QnaBoardWrite.class);
                    startActivity(intent);
                    break;
                case R.id.qnaBoard_searchFloatBtn:
                    ShowDialog();
                    break;

            }
        }
    };


    public void addTestData() {

        try {
            boardVOList = new ArrayList<>();
            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createRetrofitObject();
            CustomerController apiService = hrs.createApiserverObject();
            Call call = apiService.getQnaBoard(user_nick);
            hrs.HttpRequestExecute(call);
            hrs.makeGsonObjectTypeDate();
            Type type = new TypeToken<ArrayList<QaVO>>() {
            }.getType();
            boardVOList = (ArrayList<QaVO>) hrs.parsingFunc(type);


            for (QaVO qaVo : boardVOList) {
                items.add(qaVo);
            }


        } catch (Exception e) {

            e.printStackTrace();

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
        Log.d("searchResult", "" + search_keyword.toString().getBytes().length);

        if (search_keyword.getText().length() <= 0) {
            Toast.makeText(this, "검색어를 입력하세요!", Toast.LENGTH_SHORT).show();
            return;

        }

        if (!titleCheckBox.isChecked() && !writerCheckBox.isChecked() && !contentCheckBox.isChecked()) {
            Toast.makeText(this, "검색 조건은 한 가지 이상 선택 되어야 합니다.", Toast.LENGTH_SHORT).show();
            return;

        }



        //검색 로직
        HashSet<QaVO> boardSet = new HashSet<>();
        String keyword = search_keyword.getText().toString();

        for (QaVO boardVO : boardVOList) {

            if (titleCheckBox.isChecked()) {

                if (boardVO.getQa_title().contains(keyword)) {
                    boardSet.add(boardVO);
                }
            }

            if (writerCheckBox.isChecked()) {

                if (boardVO.getQa_nick().contains(keyword)) {
                    boardSet.add(boardVO);
                }
            }

            if (contentCheckBox.isChecked()) {

                if (boardVO.getQa_content().contains(keyword)) {
                    boardSet.add(boardVO);
                }
            }

        }

        Adapter.setItems(new ArrayList<>(boardSet));
        Adapter.notifyDataSetChanged();

        Toast.makeText(this, "검색 완료", Toast.LENGTH_SHORT).show();
        myDialog.dismiss();


    }

}
