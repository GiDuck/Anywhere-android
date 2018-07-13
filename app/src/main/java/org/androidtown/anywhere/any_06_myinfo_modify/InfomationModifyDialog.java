package org.androidtown.anywhere.any_06_myinfo_modify;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.util.HashMap;
import java.util.Map;

import me.drakeet.materialdialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by user on 2017-08-16.
 */

public class InfomationModifyDialog {

    private Context context;
    SharedPreferences pref;
    private String user_email;
    private String user_nick;
    private String user_division;

    boolean isNickAuth = false;

    public InfomationModifyDialog(Context context){

        this.context = context;
        pref = context.getSharedPreferences("anywhere", Context.MODE_PRIVATE);

        user_email = pref.getString("user_email", "");
        user_nick = pref.getString("user_nick", "");
        user_division = pref.getString("user_division", "");


    }

    public void setInfoModifyDialog(){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.anywhere06_myinfo_modify_dialog,null);

        TextView phoneText = (TextView) dialogView.findViewById(R.id.phoneText);
        final EditText nick = (EditText) dialogView.findViewById(R.id.nick);
        final EditText phone = (EditText) dialogView.findViewById(R.id.phone);
        final EditText pass = (EditText) dialogView.findViewById(R.id.pass);
        final EditText passCheck = (EditText) dialogView.findViewById(R.id.passCheck);
        Button nickCheck = (Button) dialogView.findViewById(R.id.nickCheck);




        final MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle("회원 정보 수정");
        dialog.setContentView(dialogView);

        if(user_division.equals("0")){

            phoneText.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);

        }else if(user_division.equals("1")){

            phoneText.setVisibility(View.VISIBLE);
            phone.setVisibility(View.VISIBLE);

        }

        nickCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequestSyncObject hrs = new HttpRequestSyncObject();
                Retrofit retrofit = hrs.createRetrofitObject();
                CustomerController apiService = hrs.createApiserverObject();
                Call call = apiService.nickCheck(nick.getText().toString());
                hrs.makeGsonObject();
                hrs.HttpRequestExecute(call);
                String result = hrs.parsingStringFunc("result");

                if (result.equals("true")) {

                    isNickAuth = true;
                    Toast.makeText(context, "사용 가능한 닉네임 입니다.", Toast.LENGTH_SHORT).show();

                } else if (result.equals("false")) {

                    Toast.makeText(context, "닉네임이 중복 되었습니다.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("errorCode", "회원 정보 수정 오류 발생 1");
                }


            }
        });



        dialog.setPositiveButton("수정 하기", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isNickAuth) {

                    Toast.makeText(context, "닉네임이 인증 되지 않았습니다", Toast.LENGTH_SHORT).show();
                    return;


                } else if (!pass.getText().toString().equals(passCheck.getText().toString())) {

                    Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;

                }else if(nick.getText().toString().equals(pass.getText().toString())){


                    Toast.makeText(context, "닉네임과 비밀번호가 일치합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String,String> map = new HashMap<String, String>();
                map.put("user_email",user_email);
                map.put("user_nick",nick.getText().toString());
                map.put("user_password",pass.getText().toString());
                map.put("user_division",user_division);
                map.put("user_phone",phone.getText().toString());

                HttpRequestSyncObject hrs = new HttpRequestSyncObject();
                Retrofit retrofit = hrs.createRetrofitObject();
                CustomerController apiService = hrs.createApiserverObject();
                Call call = apiService.sendModifyUserInfo(map);
                hrs.HttpRequestExecute(call);
                hrs.makeGsonObject();
                String result = hrs.parsingStringFunc("result");


                if (result.equals("true")) {

                    Toast.makeText(context, "회원 정보 수정 성공", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove("user_email");
                    editor.remove("user_nick");
                    editor.putString("user_email", user_email);
                    editor.putString("user_nick", nick.getText().toString());
                    editor.commit();

                    dialog.dismiss();


                } else if (result.equals("false")) {

                    Toast.makeText(context, "회원 정보 수정 실패", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(context, "알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("codeError", "회원 정보 수정 오류 발생 2");

                }

            }


        });


        dialog.setNegativeButton("닫기", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
