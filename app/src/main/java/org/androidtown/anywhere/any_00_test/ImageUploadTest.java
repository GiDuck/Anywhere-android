package org.androidtown.anywhere.any_00_test;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.MemberVO;
import org.androidtown.anywhere.httpcontrol.HttpImageAndObjectUpload;

import java.lang.reflect.Type;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

public class ImageUploadTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload_test);


        Button uploadBtn = (Button) findViewById(R.id.imageUploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(ImageUploadTest.this)
                        .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                            @Override
                            public void onImagesSelected(ArrayList<Uri> uriList) {

                                MemberVO mv = new MemberVO();
                                mv.setMember_email("gi");
                                mv.setMember_date(new Date(20000000));
                                mv.setMember_password("123");
                                mv.setMember_nick("hihi");
                                mv.setMember_black(1);
                                mv.setMember_business_name("gigi");
                                mv.setMember_business_num("123");
                                mv.setMember_division(1);
                                mv.setMember_warning(0);

                                ArrayList<MemberVO> member = new ArrayList<MemberVO>();
                                member.add(mv);
                                member.add(mv);
                                member.add(mv);


                                HttpImageAndObjectUpload upload = new HttpImageAndObjectUpload(ImageUploadTest.this);
                                upload.createRetrofitObject();
                                upload.createApiserverObject();
                                upload.uploadImages(uriList, "giduckStore", member);
                                String result = upload.parsingStringFunc("result");



                                ArrayList<String> box = new ArrayList<>();
                                Type type = new TypeToken<List<String>>() {
                                }.getType();
                                box = (ArrayList<String>) upload.parsingFunc(type);

                                for (String s : box) {

                                    Log.d("giduckTest",s);

                                }

                                ;
                                ImageView im1 = (ImageView) findViewById(R.id.image1);
                                ImageView im2 = (ImageView) findViewById(R.id.image2);
                                ImageView im3 = (ImageView) findViewById(R.id.image3);
                                ImageView im4 = (ImageView) findViewById(R.id.image4);


                                ImageView[] ivArray = {im1, im2, im3, im4};


                                for (int i = 0; i < 3; i++) {
                                    Glide.with(ImageUploadTest.this).load("http://192.168.0.25:8080/controller/resources/upload/"+box.get(i)).into(ivArray[i]);
                                }




                         /*       if (result.equals("true")) {
                                    Toast.makeText(ImageUploadTest.this, "upload Complete", Toast.LENGTH_SHORT).show();
                                } else if (result.equals("false")) {
                                    Toast.makeText(ImageUploadTest.this, "upload Fail", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ImageUploadTest.this, "문제 발생", Toast.LENGTH_SHORT).show();
                                }*/
                            }
                        })
                        .setSelectMaxCount(3)
                        .setEmptySelectionText("선택된 이미지가 없습니다! 이미지를 선택해 주세요!")
                        .create();


                tedBottomPicker.show(getSupportFragmentManager());

            }
        });


    }
}
