package org.androidtown.anywhere.any_06_myinfo_modify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_02_main.Main;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import me.drakeet.materialdialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by user on 2017-07-05.
 */

//다이얼로그
public class InfomationCheckDialog {

    private Context context;
    private Intent intent;

    private String user_email;
    private String user_division;

    //회원탈퇴인지 개인정보 수정인지를 확인하는 메소드
    private int infoCheckSelect;

    private SharedPreferences pref;
    private String findPassword;

    public InfomationCheckDialog(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("anywhere", Context.MODE_PRIVATE);
        user_email = pref.getString("user_email", "");
        user_division = pref.getString("user_division","");

        HttpRequestSyncObject hrs = new HttpRequestSyncObject(); //HttpRequestSyncObject 객체 호출
        Retrofit retrofit = hrs.createRetrofitObject();
        CustomerController apiService = hrs.createApiserverObject();
        Call call = apiService.getPassword(user_email);
        hrs.HttpRequestExecute(call);

        String resultToken = hrs.parsingStringFunc("result");
        if (resultToken.equals("true")) {

            findPassword = hrs.parsingStringFunc("password");
            Log.d("findPassword",findPassword);

        } else {

            Log.d("errorCode", "회원 정보 수정 다이얼로그 에러");
        }

    }


    public void setInfoCheckSelect(int infoCheckSelect) {
        this.infoCheckSelect = infoCheckSelect;
    }


    public void setinfoCheckDialog() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.anywhere06_myinfo_check_dialog,null);


        final MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle("비밀번호 확인");

        dialog.setContentView(dialogView);

        final EditText password = (EditText) dialogView.findViewById(R.id.password);

        dialog.setPositiveButton("확인", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!password.getText().toString().equals(findPassword)){

                    password.setText("");
                    password.setHint("비밀번호가 틀렸습니다. 다시 입력해주세요");
                    Toast.makeText(context,"비밀번호가 틀렸습니다. 다시 입력해주세요",Toast.LENGTH_SHORT).show();

                }else{

                    if(infoCheckSelect ==1){

                        InfomationModifyDialog modifyDialog = new InfomationModifyDialog(context);
                        modifyDialog.setInfoModifyDialog();

                    }else if(infoCheckSelect == 2){

                        infoLeaveDialog();

                    }
                    dialog.dismiss();

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

    //회원 탈퇴 다이얼로그,값받는 것은 가능
    public void infoLeaveDialog() {


        final MaterialDialog dialog = new MaterialDialog(context);
        dialog.setTitle("회원탈퇴");
        dialog.setMessage("회원탈퇴를 하시겠습니까?");

        dialog.setPositiveButton("탈퇴", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequestSyncObject hrs = new HttpRequestSyncObject();
                Retrofit retrofit = hrs.createRetrofitObject();
                CustomerController apiService = hrs.createApiserverObject();
                Call call = apiService.deleteInfo(user_email);
                hrs.HttpRequestExecute(call);
                String resultToken = hrs.parsingStringFunc("result");

                if (resultToken.equals("true")) {

                    Toast.makeText(context, "회원 탈퇴 성공, 이용해 주셔서 감사합니다.", Toast.LENGTH_SHORT).show();

                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove("user_email");
                    editor.remove("user_nick");
                    editor.remove("user_division");
                    editor.commit();

                    Intent intent = new Intent(context, Main.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                } else if (resultToken.equals("false")) {

                    Toast.makeText(context, "회원 탈퇴 실패", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(context, "알 수 없는 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("codeError", "회원 정보 수정 오류 발생 - 탈퇴");

                }

                dialog.dismiss();

            }
        });

        dialog.setNegativeButton("취소", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }


}
