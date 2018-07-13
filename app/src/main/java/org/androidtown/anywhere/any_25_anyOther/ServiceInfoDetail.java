package org.androidtown.anywhere.any_25_anyOther;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import org.androidtown.anywhere.R;

import static org.androidtown.anywhere.httpcontrol_retrofitController.URL_setup.ROOT_URL;

public class ServiceInfoDetail extends AppCompatActivity {
    String token;
    WebView webView;
    WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info_detail);

        token = null;
        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        webView=(WebView)findViewById(R.id.webview);
        webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);


            if (token.equals("serviceInfo")) {

                webView.loadUrl(ROOT_URL+"controller/customer/serviceTos");


            } else if (token.equals("personalInfo")) {
                webView.loadUrl(ROOT_URL+"controller/customer/userInfoTos");




        } else if (token.equals("locationInfo")) {
            webView.loadUrl(ROOT_URL+"controller/customer/locationTos");


        }else{

            Toast.makeText(this, "에러 입니다", Toast.LENGTH_SHORT).show();
            return;
        }


    }
}
