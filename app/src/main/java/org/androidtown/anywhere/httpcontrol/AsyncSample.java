package org.androidtown.anywhere.httpcontrol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_VO.before_VO.MemberVO;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

public class AsyncSample extends AppCompatActivity {
    private Retrofit retrofit;
    private Call<ResponseBody> call;
    private CustomerController apiService;
    private Type type;
    //동기식 retrofit2 테스트를 위한 액티비티임
    private ArrayList<Object> memberBox;
    //넘겨 받을 필드 ArrayList를 선언 (Object형으로 받아서 캐스팅할꺼니까 Object형으로 선언)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_sample);

        memberBox = new ArrayList<>(); //필드 초기화

        HttpRequestSyncObject hrs = new HttpRequestSyncObject(); //HttpRequestSyncObject 객체 호출

        apiService = hrs.createApiserverObject(); //apiService인터페이스 구현



        type = new TypeToken<ArrayList<MemberVO>>() {
        }.getType(); //서버 호출 후 json 객체에서 받은 List를 파싱할 형태를 type에 정의


        ArrayList<MemberVO> mv = new ArrayList<>(); //내가 집어 넣을 데이터 타입의 ArrayList를 정의

        for (int i = 0; i < memberBox.size(); i++) {
            mv.add((MemberVO) memberBox.get(i)); //for문으로 Object형 데이터를 캐스팅
            Log.d("mv", mv.get(i).getMember_email());
        }



       /* HttpRequestSyncObject hrs = new HttpRequestSyncObject(); //HttpRequestSyncObject 객체 호출

        retrofit = hrs.createRetrofitObject(); //레트로핏 객체 생성

        apiService = hrs.createApiserverObject(); //apiService인터페이스 구현


        call = apiService.getPostComment("giduck1","hi","bye"); //Call 객체에 ApiService에 정의 되어 있는 맵핑 메소드 호출

        Object temp = hrs.HttpRequestGetOneExecute(call, String.class); // call 객체와 type 객체를 넘기고 Object형 List로 리턴받음
        Log.d("giduck", ""+temp);*/


    }
}