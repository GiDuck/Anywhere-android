package org.androidtown.anywhere.any_03_login;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_02_main.Main;
import org.androidtown.anywhere.any_04_join.JoinNotifyRoles;
import org.androidtown.anywhere.any_17_supplier_main.SupplierMain;
import org.androidtown.anywhere.header.BasedFontAppExtendsActivity;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import info.hoang8f.widget.FButton;
import retrofit2.Call;
import retrofit2.Retrofit;

public class Login extends BasedFontAppExtendsActivity implements View.OnClickListener {

    String randomNum;
    String authEmail;
    ImageView imv;

    EditText inputId;
    EditText inputPw;

    FButton textLogin;
    FButton textJoin;
    FButton textFindPW;

    Button sendBtn;
    Button authNumberSendBtn;
    EditText findPwAuthNumber;
    Dialog myDialog;
    View dialogLayout;

    HashMap<String, String> mailBox;

    HttpRequestSyncObject hrs;
    Retrofit retrofit;
    CustomerController apiService;

    String id;
    String pw;

    ProgressBar progressBar;
    ProgressBar dialog_progressBar;





    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("id", inputId.getText().toString());
        outState.putString("pw", inputPw.getText().toString());


    }

    /* @Override
        public void onConfigurationChanged(Configuration newConfig) {

            finish();
            super.onConfigurationChanged(newConfig);

            String id = inputId.getText().toString();
            String pw = inputPw.getText().toString();
            setContentView(R.layout.anywhere03_login);

            inputId = (EditText) findViewById(R.id.inputId);
            inputPw = (EditText) findViewById(R.id.inputPW);

            inputId.setText(id);
            inputPw.setText(pw);
        }
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.anywhere03_login);


        //애니메이션 이후 배경색 #002c3b

        hrs = new HttpRequestSyncObject(); //HttpRequestSyncObject 객체 호출
        retrofit = hrs.createRetrofitObject();
        apiService = hrs.createApiserverObject();

      /*  imv = (ImageView) findViewById(R.id.backgroundGif);

        GlideDrawableImageViewTarget ivTarget = new GlideDrawableImageViewTarget(imv);
        Glide.with(this).load(R.drawable.giphy_downsized).into(ivTarget);

        //gif 파일 실행하는 소스
*/

        inputId = (EditText) findViewById(R.id.inputId);
        inputPw = (EditText) findViewById(R.id.inputPW);
        textLogin = (FButton) findViewById(R.id.textLogin);
        textJoin = (FButton) findViewById(R.id.textJoin);
        textFindPW = (FButton) findViewById(R.id.textFindPW);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mailBox = new HashMap<>();

        textJoin.setOnClickListener(this);
        textLogin.setOnClickListener(this);
        textFindPW.setOnClickListener(this);

        if (savedInstanceState != null) {


            id = savedInstanceState.getString("id");
            pw = savedInstanceState.getString("pw");

            Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, pw, Toast.LENGTH_SHORT).show();

            inputId = (EditText) findViewById(R.id.inputId);
            inputPw = (EditText) findViewById(R.id.inputPW);
            inputId.setText(id);
            inputPw.setText(pw);


        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textLogin: { //로그인
                progressBar.setVisibility(View.VISIBLE);
                try {

                    Call call = apiService.login(inputId.getText().toString(), inputPw.getText().toString());
                    //작성한 아이디와 패스워드를 call 객체를 통해 서버로 전송한다.

                    hrs.HttpRequestExecute(call);
                    hrs.makeGsonObject();
                    HashMap<String, String> userInfo = new HashMap<>();
                    Type type = new TypeToken<HashMap<String, String>>() { //Map 형식으로 필드를 받아온다.
                    }.getType();
                    userInfo = (HashMap<String, String>) hrs.ObjectParsingFunc(type);

                    String result = userInfo.get("result"); //로그인 성공여부
                    String division = userInfo.get("division"); // 유저 타입(일반 사용자인지 사업자인지)
                    String nick = userInfo.get("nick"); //유저 닉네임 받아옴



                    if (result.equals("success")) {

                        Toast.makeText(this, nick + "님 접속을 환영합니다!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        SharedPreferences infoStoarge = getSharedPreferences("anywhere", MODE_PRIVATE);
                        SharedPreferences.Editor editor = infoStoarge.edit();
                        editor.putString("user_email", inputId.getText().toString());
                        editor.putString("user_division", division);
                        editor.putString("user_nick", nick);
                        editor.commit(); //성공시 세션 유지를 위해 sharedPreference에 유저 정보 저장


                        //일반회원일 경우
                        if (division.equals("0")) {
                            //액티비티 가장 상위 스택 빼고 전부 제거
                            Intent intent = new Intent(Login.this, Main.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            //업주일경우
                        } else if (division.equals("1")) {
                            Intent intent = new Intent(Login.this, SupplierMain.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }

                    } else if (result.equals("fail")) {

                        Toast.makeText(this, "아이디 혹은 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    } else {

                        Toast.makeText(this, "블랙리스트 회원이어서 로그인 하실 수 없습니다.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "로그인이 실패 하였습니다.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                break;


            }

            case R.id.textJoin: //회원 가입 페이지로 이동
                Intent intent = new Intent(Login.this, JoinNotifyRoles.class);
                startActivity(intent);
                break;

            case R.id.textFindPW:
                ShowDialog();
                break;

            case R.id.sendPwAuthBtn:
                dialog_progressBar.setVisibility(View.VISIBLE);
                SendAuthEmail();
                break;
            case R.id.findPwAuthBtn:
                CheckAndSendAuthNumber();
                break;
        }
    }

    private void ShowDialog() {
        LayoutInflater dialog = LayoutInflater.from(this);
        dialogLayout = dialog.inflate(R.layout.anywhere03_dialog_find_pw, null);
        myDialog = new Dialog(this);
        myDialog.setContentView(dialogLayout);
        myDialog.show();

        dialog_progressBar = (ProgressBar) dialogLayout.findViewById(R.id.progressBar);
        sendBtn = (Button) dialogLayout.findViewById(R.id.sendPwAuthBtn);
        authNumberSendBtn = (Button) dialogLayout.findViewById(R.id.findPwAuthBtn);
        findPwAuthNumber = (EditText) dialogLayout.findViewById(R.id.findPwAuthNumber);

        sendBtn.setOnClickListener(this);
        authNumberSendBtn.setOnClickListener(this);

    }


    private void SendAuthEmail() {


        final EditText authPw = (EditText) dialogLayout.findViewById(R.id.sendPwAuth);

        HashMap<String, String> map = new HashMap<>();

        authEmail = authPw.getText().toString();


        Call call = apiService.pwEmailCheck(authEmail);
        hrs.HttpRequestExecute(call);

        hrs.makeGsonObject();
        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();

        map = (HashMap<String, String>) hrs.ObjectParsingFunc(type);



        try {
            if (map.get("emailCheck").equals("true")) {
                dialog_progressBar.setVisibility(View.GONE);

                Toast.makeText(this, "이메일 인증 번호 전송 완료", Toast.LENGTH_SHORT).show();
                randomNum = map.get("randomNum"); //이메일 인증 번호를 서버로 부터 받아옴
                //차후 유저가 적은 인증 번호와 일치하는지 확인하는데 사용.

                LinearLayout authCheckNumLinear = (LinearLayout) dialogLayout.findViewById(R.id.findPwAuthFrame);
                authCheckNumLinear.setVisibility(View.VISIBLE);
                //인증 번호 확인 창 open (리니어 레이아웃 동적으로 사용)

            } else if (map.get("emailCheck").equals("false")) {
                dialog_progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "이메일이 존재 하지 않습니다!", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            dialog_progressBar.setVisibility(View.GONE);
            e.printStackTrace();
            Toast.makeText(this, "비밀 번호 찾기 실패하였습니다.", Toast.LENGTH_SHORT).show();
            Log.d("errorCode", "비밀 번호 찾기 통신 에러, Exception");
        }


    }


    private void CheckAndSendAuthNumber() { //인증번호를 확인하고 서버로 확인 여부를 전송하는 메소드

        String findPwAuthNumberString = findPwAuthNumber.getText().toString(); //

        if (findPwAuthNumberString.equals(randomNum)) {


            Map<String, String> map = new HashMap<>();
            map.put("member_email", authEmail);
            map.put("randomNum", randomNum);
            Call call = apiService.randomNumCheck(map);
            hrs.HttpRequestExecute(call);

            String authNumber = hrs.parsingStringFunc("result");
            Toast.makeText(this, "인증번호가 임시 비밀번호로 설정 되었습니다.", Toast.LENGTH_LONG).show();
            myDialog.cancel();

        } else {

            Toast.makeText(this, "인증 번호가 일치 하지 않습니다", Toast.LENGTH_LONG).show();

        }


    }

}
