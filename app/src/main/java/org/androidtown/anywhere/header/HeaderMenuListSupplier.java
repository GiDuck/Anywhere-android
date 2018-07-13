package org.androidtown.anywhere.header;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import org.androidtown.anywhere.any_14_noticeboard.NoticeBoard;
import org.androidtown.anywhere.any_15_eventboard.EventBoard;
import org.androidtown.anywhere.any_16_0_qnaboard.QnaBoard;
import org.androidtown.anywhere.any_17_supplier_main.SupplierMain;
import org.androidtown.anywhere.any_20_0_supplier_reservation_manager_storeList.ReservationManagerStoreList;
import org.androidtown.anywhere.any_21_supplier_addplace1.AddPlaceFirst;
import org.androidtown.anywhere.any_24_myStoreManager.MyStoreManager;
import org.androidtown.anywhere.any_25_anyOther.ServiceInfo;

import static android.content.Context.MODE_PRIVATE;
import static org.androidtown.anywhere.R.id.Supplierlogin;

/**
 * Created by user on 2017-07-01.
 */
//점주 메뉴바
public class HeaderMenuListSupplier extends LinearLayout {

    TextView supplierlogin;
    TextView spaceResister;
    TextView reservation;
    TextView sales;
    TextView management;
    TextView notice;
    TextView question;
    TextView service;
    TextView home;
    TextView myInfo;
    TextView event;

    SharedPreferences shef;
    String session_id;
    String user_division;
    String user_nick;
    Context context;

    public HeaderMenuListSupplier(Context context) {
        super(context);
        init(context);
    }

    public HeaderMenuListSupplier(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.header_menulist_supplier, this, true);

        supplierlogin = (TextView) findViewById(Supplierlogin);
        spaceResister = (TextView) findViewById(R.id.spaceResister);
        reservation = (TextView) findViewById(R.id.reservation);
        sales = (TextView) findViewById(R.id.sales);
        management = (TextView) findViewById(R.id.management);
        notice = (TextView) findViewById(R.id.notice);
        question = (TextView) findViewById(R.id.question);
        service = (TextView) findViewById(R.id.service);
        home = (TextView) findViewById(R.id.home);
        myInfo = (TextView) findViewById(R.id.myInfoSupplier);
        event = (TextView) findViewById(R.id.event);


        shef = context.getSharedPreferences("anywhere", MODE_PRIVATE);
        session_id = shef.getString("user_email", "");
        user_division = shef.getString("user_division", "");
        user_nick = shef.getString("user_nick", "");

        Log.d("shef", session_id + " " + user_division + " " + user_nick);
        if (session_id.equals("") && user_division.equals("") && user_nick.equals("")) {
            supplierlogin.setText("로그인");

        } else {
            supplierlogin.setText("로그아웃");
        }


        SupplierListClick click = new SupplierListClick();
        supplierlogin.setOnClickListener(click);
        spaceResister.setOnClickListener(click);
        reservation.setOnClickListener(click);
        sales.setOnClickListener(click);
        management.setOnClickListener(click);
        notice.setOnClickListener(click);
        question.setOnClickListener(click);
        service.setOnClickListener(click);
        event.setOnClickListener(click);
        home.setOnClickListener(click);
        myInfo.setOnClickListener(click);

    }

    public class SupplierListClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            int click = v.getId();
            switch (click) {

                case R.id.myInfoSupplier: {
                    if(user_nick.trim().length()==0){

                        Toast.makeText(getContext(), "로그인을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
                        break;

                    }
                    InfomationDialog dialog = new InfomationDialog(getContext());
                    dialog.setInfoDialog();


                    break;
                }

                case R.id.Supplierlogin: {
                    Log.d("사업자", "로그인클릭");
                    if (supplierlogin.getText().equals("로그인")) {
                        Intent intent = new Intent(getContext(), Login.class);
                        getContext().startActivity(intent);
                    } else if (supplierlogin.getText().equals("로그아웃")) {
                        Log.d("사업자", "로그아웃 클릭");

//                      세션 아이디 삭제
                        SharedPreferences.Editor editor = shef.edit();
                        editor.remove("user_email");
                        editor.remove("user_division");
                        editor.remove("user_nick");
                        editor.commit();

//                      액티비티 가장 상위 스택 빼고 전부 제거
                        Intent intent = new Intent(getContext(), Main.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getContext().startActivity(intent);
                    }
                    break;

                }

                case R.id.event: {
                    Intent intent = new Intent(getContext(), EventBoard.class);
                    getContext().startActivity(intent);
                    break;

                }

                case R.id.spaceResister: {

                    Intent intent = new Intent(getContext(), AddPlaceFirst.class);
                    getContext().startActivity(intent);
                    break;
                }
                case R.id.reservation: {
                    Intent intent = new Intent(getContext(), ReservationManagerStoreList.class);
                    getContext().startActivity(intent);
                    break;
                }
                case R.id.sales: {
                    Toast.makeText(getContext(), "현재 서비스 준비중입니다", Toast.LENGTH_SHORT).show();
                    break;
                }
                case R.id.management: {
                    Intent intent = new Intent(getContext(), MyStoreManager.class);
                    getContext().startActivity(intent);

                    break;
                }
                case R.id.notice: {
                    Intent intent = new Intent(getContext(), NoticeBoard.class);
                    getContext().startActivity(intent);
                    break;
                }
                case R.id.question: {
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
                    Intent intent = new Intent(getContext(), SupplierMain.class);
                    getContext().startActivity(intent);
                    break;
                }
            }
        }
    }
}
