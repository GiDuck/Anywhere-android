package org.androidtown.anywhere.any_01_firstview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_02_main.Main;
import org.androidtown.anywhere.any_17_supplier_main.SupplierMain;

public class Firstview extends AppCompatActivity {

    private Handler handler;
    String session_id;
    String user_division;
    String user_nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere01_firstview);

        SharedPreferences shef = getSharedPreferences("anywhere", MODE_PRIVATE);
        String session_id = shef.getString("user_email", "");
        user_division = shef.getString("user_division", "");
        final String user_nick = shef.getString("user_nick", "");

        //핸들러를 이용
        handler = new Handler();
        //몇초후에 실행 가능하도록 하는 핸들러 메소드
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;
                if (user_division.equals("0")) {
                    intent = new Intent(Firstview.this, Main.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else if (user_division.equals("1")) {
                    intent = new Intent(Firstview.this, SupplierMain.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    intent = new Intent(Firstview.this, Main.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        }, 2000);
    }
}