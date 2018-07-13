package org.androidtown.anywhere.any_00_test;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidtown.anywhere.R;

public class test3 extends AppCompatActivity implements View.OnClickListener {

    EditText user_nick;
    Button login;
    Button logout;
    SharedPreferences shef;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);

        user_nick = (EditText) findViewById(R.id.edit_nick);
        login = (Button) findViewById(R.id.login_btn);
        logout = (Button) findViewById(R.id.logout_btn);


        shef = getSharedPreferences("anywhere", MODE_PRIVATE);
        editor = shef.edit();

        login.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.login_btn) {


            editor.putString("user_nick", user_nick.getText().toString());
            editor.commit();
            Toast.makeText(this, shef.getString("user_nick", "") + " 님 환영합니다.", Toast.LENGTH_SHORT).show();

        }

        if (v.getId() == R.id.logout_btn) {

            editor.remove("user_nick");
            editor.commit();
            Toast.makeText(this, "현재 세션 : " + shef.getString("user_nick", ""), Toast.LENGTH_SHORT).show();

            if(shef.getString("user_nick", "")==null){

                Log.d("giduckTest", "현재 세션은 null이 들어있음");
            }

            if(shef.getString("user_nick", "").equals(" "));
            {
                Log.d("giduckTest", "현재 세션은 공백이 들어있음");

            }

        }

    }
}
