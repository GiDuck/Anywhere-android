package org.androidtown.anywhere.any_04_join;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_02_main.Main;
import org.androidtown.anywhere.header.BasedFontApp;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.util.regex.Matcher;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Retrofit;

import static org.androidtown.anywhere.header.RegularExpression.VALID_EMAIL_ADDRESS_REGEX;
import static org.androidtown.anywhere.header.RegularExpression.VALID_PASSWOLD_REGEX_ALPHA_NUM;

public class JoinForm extends BasedFontApp implements View.OnClickListener {


    FButton btnJoin;
    FButton btnAuthEmail;
    FButton btnCheckNickName;
    EditText nickname;
    EditText email;
    EditText pw;
    EditText repw;
    TextView time_counter;

    View dialogLayout;
    String emailStr;
    String nickStr;
    String pwStr;
    String repwStr;
    int emailAuthCount;
    EditText emailAuth_number;
    Button emailAuth_btn;
    int emailAuthMinCount;

    String emailAuthNum;
    String emailCheck;

    Matcher vaildEmail;
    Matcher vaildPw;


    Dialog myDialog;

    private static final int MILLISINFUTURE = 300 * 1000;
    private static final int COUNT_DOWN_INTERVAL = 1000;

    private boolean nickCheckToken;
    private boolean emailCheckToken;

    CountDownTimer countDownTimer;

    long pressedTime1;
    long pressedTime2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere04_join_form);
        pressedTime1 = 0;
        pressedTime2 = 0;
        initialize();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void initialize() {

        nickCheckToken = false;
        emailCheckToken = false;
        btnJoin = (FButton) findViewById(R.id.joinSubmitBtn);
        btnAuthEmail = (FButton) findViewById(R.id.BtnAuthEmail);
        btnCheckNickName = (FButton) findViewById(R.id.BtnAuthNickname);
        pw = (EditText) findViewById(R.id.inputJoinPw);
        repw = (EditText) findViewById(R.id.inputPwRecheck);
        nickname = (EditText) findViewById(R.id.inputJoinNickname);

        email = (EditText) findViewById(R.id.inputJoinEmail);
        btnAuthEmail.setOnClickListener(this);
        btnCheckNickName.setOnClickListener(this);
        btnJoin.setOnClickListener(this);

     /* pw.setOnFocusChangeListener(onFocusChangeListener);
        repw.setOnFocusChangeListener(onFocusChangeListener);
        nickname.setOnFocusChangeListener(onFocusChangeListener);
        email.setOnFocusChangeListener(onFocusChangeListener);*/
        emailAuthCount = 300;

        emailStr = email.getText().toString();
        nickStr = nickname.getText().toString();
        pwStr = pw.getText().toString();
        repwStr = repw.getText().toString();


    }


  /*  View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (v == nickname) {
                if (hasFocus) {
                    nickname.setBackgroundColor(getResources().getColor(R.color.anywhere_main_color2));


                } else {

                    nickname.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
            }

            if (v == pw) {

                if (hasFocus) {
                    pw.setBackgroundColor(getResources().getColor(R.color.anywhere_main_color2));

                } else {

                    pw.setBackgroundColor(getResources().getColor(R.color.colorWhite));

                }
            }

            if (v == repw) {

                if (hasFocus) {
                    repw.setBackgroundColor(getResources().getColor(R.color.anywhere_main_color2));

                } else {

                    repw.setBackgroundColor(getResources().getColor(R.color.colorWhite));

                }
            }

            if (v == email) {

                if (hasFocus) {
                    email.setBackgroundColor(getResources().getColor(R.color.anywhere_main_color2));

                } else {

                    email.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                }
            }


        }


    };*/


    @Override
    public void onClick(View v) {

        HttpRequestSyncObject hrs;
        Retrofit retrofit;
        CustomerController apiService;
        Call call;
        String str;


        switch (v.getId()) {

            case R.id.BtnAuthNickname:


                if (nickname.getText().toString().getBytes().length < 4) {
                    Toast.makeText(this, "닉네임이 올바른 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                    break;
                } else if (nickname.getText().toString().contains(" ")) {
                    Toast.makeText(this, "닉네임에 공백이 포함되어 있습니다.", Toast.LENGTH_SHORT).show();
                    break;

                }


                hrs = new HttpRequestSyncObject(); //HttpRequestSyncObject 객체 호출
                retrofit = hrs.createRetrofitObject();
                apiService = hrs.createApiserverObject();
                call = apiService.nickCheck(nickname.getText().toString());
                hrs.HttpRequestExecute(call);
                hrs.makeGsonObject();
                str = hrs.parsingStringFunc("result");


                if (str.equals("true")) {

                    Toast.makeText(this, "닉네임 인증 성공", Toast.LENGTH_SHORT).show();
                    nickCheckToken = true;


                } else {
                    Toast.makeText(this, "닉네임 인증 실패", Toast.LENGTH_SHORT).show();

                }


                break;


            case R.id.BtnAuthEmail: {

                if (pressedTime2 == 0) {
                    CheckEmail();
                    pressedTime2 = System.currentTimeMillis();
                } else {
                    int seconds = (int) (System.currentTimeMillis() - pressedTime2);
                    if (seconds < 3000) {
                        Toast.makeText(this, "기다리세요..", Toast.LENGTH_SHORT).show();
                    } else {

                        CheckEmail();
                    }
                }
                break;
            }

            case R.id.joinSubmitBtn: {

                Matcher vaildEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText().toString());
                Matcher vaildPw = VALID_PASSWOLD_REGEX_ALPHA_NUM.matcher(pw.getText().toString());

                if (!nickCheckToken) {
                    //1. 닉네임 중복 체크 실패
                    Log.d("joinTest", "1");
                    Toast.makeText(this, "닉네임이 인증 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (nickname.getText().toString().getBytes().length < 4) {
                    //2. 닉네임 형식 불일치
                    Log.d("joinTest", "2");
                    Toast.makeText(this, "닉네임이 올바른 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (nickname.getText().toString().contains(" ")) {
                    //3.닉네임 공백 포함
                    Log.d("joinTest", "3");
                    Toast.makeText(this, "닉네임에 공백이 포함 되어 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!emailCheckToken) {
                    //4.이메일 인증 실패
                    Log.d("joinTest", "4");
                    Toast.makeText(this, "이메일이 인증 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (email.getText().toString().contains(" ")) {
                    //5.이메일 공백 포함
                    Log.d("joinTest", "5");
                    Toast.makeText(this, "이메일에 공백이 포함되어 있습니다..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!vaildEmail.matches()) {
                    //6.이메일 형식 불일치
                    Log.d("joinTest", "6");
                    Toast.makeText(this, "이메일이 올바른 형식이 아닙니다..", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!pw.getText().toString().equals(repw.getText().toString())) {
                    // 7. 비밀번호 중복
                    Log.d("joinTest", "7");
                    Toast.makeText(this, "비밀번호가 같지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!vaildPw.matches() || pw.getText().toString().length() < 4 || pw.getText().toString().length() > 16) {
                    //8. 비밀번호 형식 불일치
                    Log.d("joinTest", "8");
                    Toast.makeText(this, "비밀 번호 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (nickname.getText().toString().equals(pwStr)) {
                    //9. 닉네임과 비밀번호 일치
                    Toast.makeText(this, "닉네임과 비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    hrs = new HttpRequestSyncObject(); //HttpRequestSyncObject 객체 호출
                    retrofit = hrs.createRetrofitObject();
                    apiService = hrs.createApiserverObject();
                    hrs.makeGsonObject();
                    call = apiService.sendUserInfo(email.getText().toString(), nickname.getText().toString(), pw.getText().toString());
                    hrs.HttpRequestExecute(call);
                    str = hrs.parsingStringFunc("result");

                    if (str.equals("true")) {

                        Toast.makeText(this, nickname.getText().toString() + "님 가입을 환영합니다! 로그인을 해 주세요.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Main.class);
                        startActivity(intent);
                        break;

                    } else {

                        Toast.makeText(this, "가입에 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    }


                }
            }
        }

    }


    private void CheckEmail() {


        try {
            vaildEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(email.getText().toString());


            if (vaildEmail.matches() == false) {

                Toast.makeText(this, "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                return;

            } else if (email.getText().toString().contains(" ")) {

                Toast.makeText(this, "이메일에 공백이 포함 되어 있습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            HttpRequestSyncObject hrs = new HttpRequestSyncObject(); //HttpRequestSyncObject 객체 호출
            Retrofit retrofit = hrs.createRetrofitObject();
            CustomerController apiService = hrs.createApiserverObject();
            Call call = apiService.emailCheck(email.getText().toString());
            hrs.HttpRequestExecute(call);
            hrs.makeGsonObject();


            emailAuthNum = hrs.parsingStringFunc("randomNum");
            emailCheck = hrs.parsingStringFunc("emailCheck");


            if (emailCheck.equals("false")) {

                Toast.makeText(this, "같은 이메일이 존재합니다.", Toast.LENGTH_SHORT).show();
                return;
            } else if (emailCheck.equals("true")) {

                ShowDialog();
            } else {
                Toast.makeText(this, "오류가 발생 하였습니다.", Toast.LENGTH_SHORT).show();
                return;
            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void ShowDialog() {
        LayoutInflater dialog = LayoutInflater.from(this);
        dialogLayout = dialog.inflate(R.layout.anywhere03_dialog_auth_email, null);
        myDialog = new Dialog(this);
        myDialog.setContentView(dialogLayout);
        myDialog.setCanceledOnTouchOutside(false); //다이얼로그 이외 터치해도 안 사라지게

        myDialog.show();


        countDownTimer();
        countDownTimer.start();

        myDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                countDownTimer.cancel();
            }
        });

    }


    public void emailAuthNumChecker() {

        if (emailAuthNum.equals(emailAuth_number.getText().toString())) {

            Toast.makeText(this, "이메일 인증 성공", Toast.LENGTH_SHORT).show();
            emailAuthNum = null;
            emailCheckToken = true;
            myDialog.cancel();
            countDownTimer.cancel();


        } else {
            Toast.makeText(this, "인증 번호가 틀립니다.", Toast.LENGTH_SHORT).show();

        }
    }


    public void countDownTimer() {
        emailAuthCount = 300;
        time_counter = (TextView) dialogLayout.findViewById(R.id.emailAuth_time_counter);
        emailAuth_number = (EditText) dialogLayout.findViewById(R.id.emailAuth_number);
        emailAuth_btn = (Button) dialogLayout.findViewById(R.id.emailAuth_btn);


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_counter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                emailAuthCount--;

            }


            @Override
            public void onFinish() {

                myDialog.cancel();

            }
        };


        emailAuth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailAuthNumChecker();
            }
        });
    }
}
