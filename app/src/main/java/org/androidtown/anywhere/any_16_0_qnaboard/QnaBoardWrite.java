package org.androidtown.anywhere.any_16_0_qnaboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.QaVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.util.HashMap;

import info.hoang8f.widget.FButton;
import retrofit2.Call;

public class QnaBoardWrite extends Header {

    EditText title;
    EditText content;
    FButton submitBtn;
    QaVO boardVo;

    RelativeLayout actionbar;
    FrameLayout menubar;
    RelativeLayout container;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere16_qna_board_write);
        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container = (RelativeLayout) findViewById(R.id.container1);


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
        setMenuListSelectView(cunsumerMenuListSelect);


        final SharedPreferences shef = getSharedPreferences("anywhere", MODE_PRIVATE);


        title = (EditText) findViewById(R.id.qnaBoard_write_title);
        content = (EditText) findViewById(R.id.qnaBoard_write_content);
        submitBtn = (FButton) findViewById(R.id.qnaBoard_Write_SubmitBtn);
        content.setMovementMethod(new ScrollingMovementMethod());
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> qnaMap = new HashMap<String, String>();

                qnaMap.put("qa_nick", shef.getString("user_nick", ""));

                qnaMap.put("qa_title", title.getText().toString());

                qnaMap.put("qa_content", content.getText().toString());


                HttpRequestSyncObject hrs = new HttpRequestSyncObject();
                hrs.createRetrofitObject();
                CustomerController apiService = hrs.createApiserverObject();


                Call call = apiService.insertQnaBoard(qnaMap);
                hrs.HttpRequestExecute(call);
                String result =hrs.parsingStringFunc("result");

                if(result.equals("true")) {

                    Toast.makeText(QnaBoardWrite.this, "글쓰기 업로드 완료", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QnaBoardWrite.this, QnaBoard.class);
                    startActivity(intent);
                    finish();
                }else if(result.equals("false")){
                    Toast.makeText(QnaBoardWrite.this, "업로드에 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QnaBoardWrite.this, QnaBoard.class);
                    startActivity(intent);
                    finish();

                }


            }
        });


    }
}
