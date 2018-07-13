package org.androidtown.anywhere.any_16_0_qnaboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_16_1_qnaboard_reply.QnaBoardReplyAdapter;
import org.androidtown.anywhere.any_newVO.QaReplyVO;
import org.androidtown.anywhere.any_newVO.QaVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.hoang8f.widget.FButton;
import retrofit2.Call;

public class QnaBoardDetail extends Header implements View.OnClickListener {
    QaVO boardvo;
    TextView title;
    TextView date;
    TextView content;
    TextView writer;
    FButton modifyBtn;
    FButton deleteBtn;
    FButton replyBtn;
    Intent intent;

    RelativeLayout actionbar;
    FrameLayout menubar;
    RelativeLayout container;

    FButton QnaBoard_reply_write;
    EditText QnaBoard_reply_content;

    LinearLayoutManager llm;
    QnaBoardReplyAdapter Adapter;
    ArrayList<QaReplyVO> items;
    RecyclerView QnaBoard_reply_recycler;

    //fragment 영역

    SharedPreferences shef;
    String user_division;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.anywhere16_qna_board_detail);
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container = (RelativeLayout) findViewById(R.id.container1);
        shef = getSharedPreferences("anywhere", MODE_PRIVATE);
        user_division=shef.getString("user_division", "");


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



        boardvo = new QaVO();
        Intent intent = getIntent();
        boardvo = (QaVO) intent.getSerializableExtra("QaVO");


        title = (TextView) findViewById(R.id.qnaBoardDetail_title);
        date = (TextView) findViewById(R.id.qnaBoardDetail_date);
        content = (TextView) findViewById(R.id.qnaBoardDetail_content);
        writer = (TextView) findViewById(R.id.qnaBoardDetail_writer);
        modifyBtn = (FButton) findViewById(R.id.qnaBoard_modifyBtn);
        deleteBtn = (FButton) findViewById(R.id.qnaBoard_deleteBtn);
        QnaBoard_reply_write = (FButton) findViewById(R.id.QnaBoard_reply_write);
        QnaBoard_reply_content = (EditText) findViewById(R.id.QnaBoard_reply_content);

        QnaBoard_reply_recycler = (RecyclerView) findViewById(R.id.QnaBoard_reply_recycler);
        llm = new LinearLayoutManager(this);
        items = new ArrayList<>();
        Adapter = new QnaBoardReplyAdapter(items, this, boardvo);
        QnaBoard_reply_recycler.setLayoutManager(llm);
        QnaBoard_reply_recycler.setAdapter(Adapter);


        title.setText(boardvo.getQa_title());
        content.setText(boardvo.getQa_content());
        date.setText(new SimpleDateFormat("yyyy년 MM월 dd일 HH : mm").format(boardvo.getQa_date()));
        writer.setText(boardvo.getQa_nick());

        modifyBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        QnaBoard_reply_write.setOnClickListener(this);


        setData();


    }

    public void setData() {

        try {
            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createRetrofitObject();
            CustomerController apiService = hrs.createApiserverObject();
            Call call = apiService.getQnaReplyVO(boardvo.getQa_num());
            hrs.HttpRequestExecute(call);
            hrs.makeGsonObjectTypeDate();
            Type type = new TypeToken<List<QaReplyVO>>() {
            }.getType();
            ArrayList<QaReplyVO> replyList = (ArrayList<QaReplyVO>) hrs.ObjectParsingFunc(type);

            for (QaReplyVO reply : replyList) {

                items.add(reply);


            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("errorCode", "Qna 댓글 없음");
        }

    }

    public void deleteAction() {

        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
        alertdialog.setMessage("게시글이 삭제 됩니다. 진행 하시겠습니까?");

        alertdialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteQnaBoard();

            }
        });

        alertdialog.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(QnaBoardDetail.this, "삭제가 취소 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alert = alertdialog.create();
        alert.setTitle("게시글 삭제");
        alert.show();

    }


    public void deleteQnaBoard() {

        try {
            HttpRequestSyncObject hrs = new HttpRequestSyncObject();
            hrs.createRetrofitObject();
            CustomerController apiService = hrs.createApiserverObject();
            Call call = apiService.deleteQnaBoard(boardvo.getQa_num());
            hrs.HttpRequestExecute(call);
            String result = hrs.parsingStringFunc("result");

            if (result.equals("true")) {


                Toast.makeText(this, "삭제 성공", Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), QnaBoard.class);
                startActivity(intent);
                finish();
            } else if (result.equals("false")) {
                Toast.makeText(this, "삭제 실패", Toast.LENGTH_SHORT).show();
                intent = new Intent(getApplicationContext(), QnaBoard.class);
                startActivity(intent);
                finish();


            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "삭제 실패", Toast.LENGTH_SHORT).show();
            Log.d("errorCode", "qna 게시글 삭제 예외 발생");
        }

    }

    public void submit_reply() {


        SharedPreferences shef = getSharedPreferences("anywhere", MODE_PRIVATE);
        HttpRequestSyncObject hrs = new HttpRequestSyncObject();
        hrs.createRetrofitObject();
        CustomerController apiService = hrs.createApiserverObject();
        HashMap<String, String> getReplyData = new HashMap<>();
        getReplyData.put("reply_nick", shef.getString("user_nick", ""));
        getReplyData.put("reply_content", QnaBoard_reply_content.getText().toString());
        getReplyData.put("qa_num", String.valueOf(boardvo.getQa_num()));
        Call call = apiService.writeQnAReply(getReplyData);
        hrs.HttpRequestExecute(call);
        String result = hrs.parsingStringFunc("result");
        if (result.equals("true")) {

            QnaBoard_reply_content.setText(" ");
            Toast.makeText(this, "댓글 등록이 완료 되었습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, QnaBoardDetail.class);
            intent.putExtra("QaVO", boardvo);
            startActivity(intent);
            this.finish();


        } else if (result.equals("false")) {

            Toast.makeText(this, "댓글 등록이 실패하였습니다.", Toast.LENGTH_SHORT).show();
        } else {

            Log.d("errorCode", "qna 댓글 등록 실패");
        }
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.qnaBoard_modifyBtn:
                intent = new Intent(getApplicationContext(), QnaBoardModify.class);
                intent.putExtra("QaVO", boardvo);
                startActivity(intent);

                break;

            case R.id.qnaBoard_deleteBtn:
                deleteAction();
                break;

            case R.id.QnaBoard_reply_write:
                submit_reply();
                break;

        }

    }


}
