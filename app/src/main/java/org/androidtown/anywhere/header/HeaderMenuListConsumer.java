package org.androidtown.anywhere.header;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_02_main.Main;
import org.androidtown.anywhere.any_03_login.Login;
import org.androidtown.anywhere.any_05_myinfo.InfomationDialog;
import org.androidtown.anywhere.any_07_customer_reservation.CustomerReservation;
import org.androidtown.anywhere.any_09_favorite_store.FavoriteStore;
import org.androidtown.anywhere.any_14_noticeboard.NoticeBoard;
import org.androidtown.anywhere.any_15_eventboard.EventBoard;
import org.androidtown.anywhere.any_16_0_qnaboard.QnaBoard;
import org.androidtown.anywhere.any_25_anyOther.ServiceInfo;
import org.androidtown.anywhere.any_tools.CustomerReservationStateService;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by user on 2017-06-30.
 */
//사용자 메뉴바
public class HeaderMenuListConsumer extends LinearLayout {

    TextView login;
    TextView myInfo;
    TextView reservation;
    TextView favorite;
    TextView notice;
    TextView event;
    TextView help;
    TextView service;
    TextView home;
    TextView question;

    SharedPreferences shef;
    String session_id;
    String user_division;
    String user_nick;
    Context context;

    public HeaderMenuListConsumer(Context context) {
        super(context);
        this.context = context;
        init(context);


    }

    public HeaderMenuListConsumer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context);
    }

    public void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.header_menulist_consumer, this, true);


        CunsumerListClick click = new CunsumerListClick();

        login = (TextView) findViewById(R.id.login);
        myInfo = (TextView) findViewById(R.id.myInfo);
        reservation = (TextView) findViewById(R.id.reservation);
        favorite = (TextView) findViewById(R.id.favorite);
        notice = (TextView) findViewById(R.id.notice);
        event = (TextView) findViewById(R.id.event);
        help = (TextView) findViewById(R.id.help);
        service = (TextView) findViewById(R.id.service);
        question=(TextView)findViewById(R.id.question);
        home = (TextView) findViewById(R.id.home);

        shef = context.getSharedPreferences("anywhere", MODE_PRIVATE);
        session_id=shef.getString("user_email", "");
        user_division = shef.getString("user_division","");
        user_nick = shef.getString("user_nick","");

        Log.d("shef",session_id+" "+user_division+" "+user_nick);
        if(session_id.equals("")&&user_division.equals("")&&user_nick.equals("")){
            login.setText("로그인");

        }else{
            login.setText("로그아웃");
        }

        login.setOnClickListener(click);
        myInfo.setOnClickListener(click);
        reservation.setOnClickListener(click);
        favorite.setOnClickListener(click);
        notice.setOnClickListener(click);
        event.setOnClickListener(click);
        help.setOnClickListener(click);
        service.setOnClickListener(click);
        home.setOnClickListener(click);
        question.setOnClickListener(click);


    }



    public class CunsumerListClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            int select = v.getId();
            Handler handler = new Handler();
            switch (select) {


                case R.id.myInfo: {

                    if(user_nick.trim().length()==0){

                        Toast.makeText(getContext(), "로그인을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    }


                    InfomationDialog dialog = new InfomationDialog(getContext());
                    dialog.setInfoDialog();


                    break;
                }

                case R.id.login: {

                    if(login.getText().equals("로그인")){
                        Intent intent = new Intent(getContext(), Login.class);
                        getContext().startActivity(intent);
                    }else if(login.getText().equals("로그아웃")){
//                      세션 아이디 삭제
                        SharedPreferences.Editor editor = shef.edit();
                        editor.remove("user_email");
                        editor.remove("user_division");
                        editor.remove("user_nick");
                        editor.commit();

                        Intent serviceIntent = new Intent(getContext(), CustomerReservationStateService.class);
                        CustomerReservationStateService.customerServiceFlag = false;
                        context.stopService(serviceIntent);


//                      액티비티 가장 상위 스택 빼고 전부 제거
                        Intent intent = new Intent(getContext(), Main.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);
                    }
                    break;

                }


                case R.id.reservation: {

                    if(user_nick.trim().length()==0){

                        Toast.makeText(getContext(), "로그인을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    }

                    Intent intent = new Intent(getContext(), CustomerReservation.class);
                    getContext().startActivity(intent);
                    break;
                }
                case R.id.favorite: {

                    if(user_nick.trim().length()==0){

                        Toast.makeText(getContext(), "로그인을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    }

                    Intent intent = new Intent(getContext(), FavoriteStore.class);
                    getContext().startActivity(intent);
                    break;

                }
                case R.id.notice: {

                    Intent intent = new Intent(getContext(), NoticeBoard.class);
                    getContext().startActivity(intent);
                    break;

                }
                case R.id.event: {
                    Intent intent = new Intent(getContext(), EventBoard.class);
                    getContext().startActivity(intent);
                    break;

                }

                case R.id.help: {
                    Toast.makeText(getContext(), "서비스 준비중 입니다.", Toast.LENGTH_SHORT).show();
                    break;

                }
                case R.id.question: {

                    if(user_nick.trim().length()==0){

                        Toast.makeText(getContext(), "로그인을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    }

                    Intent intent = new Intent(getContext(), QnaBoard.class);
                    getContext().startActivity(intent);
                          break;
                }
                case R.id.service: {
                    Intent intent = new Intent(getContext(), ServiceInfo.class);
                    getContext().startActivity(intent);
                    break;
                }
                case R.id.home: {
                    Intent intent = new Intent(getContext(), Main.class);
                    getContext().startActivity(intent);
                    break;
                }
            }
        }
    }
}
//    CircularProgressButton cpb;
//        cpb = (CircularProgressButton)findViewById(R.id.cpb);
//                cpb.setIndeterminateProgressMode(true);
//                cpb.setProgress(0);
//
//
//                cpb.setOnTouchListener(new OnTouchListener() {
//@Override
//public boolean onTouch(View v, MotionEvent event) {
//        int action = event.getAction();
//        Log.d("확인","cpb");
//        if(action==MotionEvent.ACTION_DOWN){
//        cpb.setProgress(60);
//        }else if(action==MotionEvent.ACTION_UP){
//        cpb.setProgress(100);
//        cpb.setCompleteText("완료");
//        }
//        return true;
//        }
//        });

