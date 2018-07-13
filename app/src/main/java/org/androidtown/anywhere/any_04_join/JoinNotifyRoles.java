package org.androidtown.anywhere.any_04_join;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.header.BasedFontApp;

import java.io.IOException;
import java.io.InputStream;

public class JoinNotifyRoles extends BasedFontApp implements View.OnClickListener{

    TextView notifyText;
    CheckBox notifyCheckBox;
    Button notifyCheckedBtn;
    String tempStringStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anywhere04_join_notify_roles);

        initialize();
        notifyCheckBox.setOnClickListener(this);
        notifyCheckedBtn.setOnClickListener(this);

        notifyText.setText(tempStringStorage);
        notifyText.setTextColor(Color.BLACK);
        notifyText.setPadding(40, 40, 30, 30);
        notifyText.setMovementMethod(new ScrollingMovementMethod());

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    public void initialize(){
        notifyText = (TextView) findViewById(R.id.notifyText);
        notifyCheckBox = (CheckBox) findViewById(R.id.notifyCheckBox);
        notifyCheckedBtn = (Button) findViewById(R.id.notifyCheckedBtn);

        tempStringStorage = readText("anywhere_join_notify.txt");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.notifyCheckBox: {
                if(!notifyCheckBox.isChecked())
                {
                    notifyCheckedBtn.setVisibility(View.INVISIBLE);

                }
                else if(notifyCheckBox.isChecked())
                {
                    notifyCheckedBtn.setVisibility(View.VISIBLE);
                }

                break;
            }
            case R.id.notifyCheckedBtn:{
                Intent intent = new Intent(getApplicationContext(), JoinForm.class);
                startActivity(intent);
                break;
            }
        }
    }

    private String readText(String strFileName) {
        String fileContext = null;

        try {

            InputStream is = getAssets().open(strFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            fileContext = new String(buffer);


        } catch (IOException e) {
            throw new RuntimeException(e);

        }

        return fileContext;

    }
}
