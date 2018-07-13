package org.androidtown.anywhere.any_16_0_qnaboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.QaVO;
import org.androidtown.anywhere.header.Header;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import retrofit2.Call;

public class QnaBoardModify extends Header {


    EditText title;
    EditText content;
    Button submitBtn;
    QaVO boardVo;

    String stringTitle;
    String stringContent;

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
        Intent intent = getIntent();
        boardVo = (QaVO) intent.getSerializableExtra("QaVO");


        actionbar = (RelativeLayout) findViewById(R.id.actionbar);
        menubar = (FrameLayout) findViewById(R.id.menubar);
        container = (RelativeLayout) findViewById(R.id.container1);

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
        setMenuListSelectView(cunsumerMenuListSelect);



        title = (EditText) findViewById(R.id.qnaBoard_write_title);
        content = (EditText) findViewById(R.id.qnaBoard_write_content);
        submitBtn = (Button) findViewById(R.id.qnaBoard_Write_SubmitBtn);


        title.setText(boardVo.getQa_title());
        content.setText(boardVo.getQa_content());


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences shef = getSharedPreferences("anywhere", MODE_PRIVATE);
                boardVo.setQa_title(title.getText().toString());
                boardVo.setQa_content(content.getText().toString());
                boardVo.setQa_nick(shef.getString("user_nick", ""));
                HttpRequestSyncObject hrs = new HttpRequestSyncObject();
                hrs.createRetrofitObject();
                CustomerController apiService = hrs.createApiserverObject();
                Call call = apiService.updateQnaBoard(boardVo);
                hrs.HttpRequestExecute(call);
                hrs.makeGsonObject();
                String result = hrs.parsingStringFunc("result");

                if(result.equals("true")){

                    Toast.makeText(QnaBoardModify.this, "수정 업로드 완료", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QnaBoardModify.this, QnaBoard.class);
                    startActivity(intent);
                    finish();

                }else if(result.equals("false")){



                    Toast.makeText(QnaBoardModify.this, "수정 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(QnaBoardModify.this, QnaBoard.class);
                    startActivity(intent);
                    finish();



                }




            }
        });


    }
}
