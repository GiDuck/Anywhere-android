package org.androidtown.anywhere.httpcontrol;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.any_newVO.IconVO;
import org.androidtown.anywhere.any_newVO.MenuVO;
import org.androidtown.anywhere.any_newVO.PictureVO;
import org.androidtown.anywhere.any_newVO.ReservationListVO;
import org.androidtown.anywhere.any_newVO.ReservationVO;
import org.androidtown.anywhere.any_newVO.StoreReplyVO;
import org.androidtown.anywhere.any_newVO.StoreVO;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;
import org.androidtown.anywhere.httpcontrol_retrofitController.SupplierController;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.androidtown.anywhere.httpcontrol_retrofitController.URL_setup.API_SUPPLIER_URL;
import static org.androidtown.anywhere.httpcontrol_retrofitController.URL_setup.API_URL;

/**
 * 객체를 전송하는 메소드
 */

public class HttpRequestSyncObject {

    private Retrofit retrofit; //레트로핏 객체
    private CustomerController apiService; //apiService 인터페이스
    private SupplierController apiService_supplier;//
    private String jsonString; //json으로 전송 받은 결과를 받을 필드
    private Object object; //VO 여러개를 받아오기 위한 List
    private Call<ResponseBody> call;
    private Type parsingType;
    private Class classBox;
    private Object result;
    private JSONArray jsonArray;
    private Gson gson;

    private StoreVO storeVO;
    private ArrayList<PictureVO> pictureVO;
    private ArrayList<StoreReplyVO> replyVO;
    private ArrayList<IconVO> iconVO;
    private ReservationVO reservationVO;
    private ArrayList<MenuVO> menuVO;


    public HttpRequestSyncObject() {
    }

    public void HttpRequestExecute(Call<ResponseBody> param) {

        call = param;

        try {
            jsonString = new HttpRequestAsync().execute().get(); //jsonString에 AsyncTask로 전송 받은 String 형 json 객체를 다시 건네받음
            //get메서드 사용하면 AsyncTask에서 doInBackground의 리턴값를 리턴 받을 수 있음.
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }




       /*  ------------------------json 객체를 typeToken을 이용해 파싱하는 메소드 ---------------------------*/


    public Object ObjectParsingFunc(Type type) {

        try {

            object = gson.fromJson(jsonString, type);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return object; //호출한 곳으로 결과 리턴


    }

       /*  ------------------------json 객체를 typeToken을 이용해 파싱하는 메소드 ---------------------------*/


    public Object ObjectParsingFunc2(Type type) {
        try {

            object = gson.fromJson(jsonString, type);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return object; //호출한 곳으로 결과 리턴


    }





   /*  ------------------------- 한 개의 객체로 받은 json 객체를 파싱하는 메소드 ---------------------------*/

    public Object parsingFunc(Type type) {


        try {
            Log.d("jsonObject", jsonString);
            return gson.fromJson(jsonString, type);


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public ArrayList<ReservationListVO> parsingTestFunc() {


        try {


            return gson.fromJson(jsonString, new TypeToken<List<ReservationListVO>>() {
            }.getType());


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    public void makeGsonObject() {

        gson = new Gson();
    }

    public void makeGsonObjectTypeDate() {
        gson = new GsonBuilder().setDateFormat("yyyy/mm/dd").create();


    }

     /*  ------------------------- 하나의 문자열을 리턴하는 메소드 (key값 필요) ---------------------------*/

    public String parsingStringFunc(String key) {

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            if (jsonObject == null) {

            } else {


                String str = jsonObject.getString(key);
                return str;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;


    }


 /*  ------------------------- 실제적으로 서버와 통신하는 스레드 (AsyncTask) ---------------------------*/


    private class HttpRequestAsync extends AsyncTask<Void, Void, String> {


        @Override
        protected String doInBackground(Void... params) {

            try {

                String result = call.execute().body().string();

                return result;

            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();

            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String jsonObject) {
            super.onPostExecute(jsonObject);
        }


    }

     /*  ------------------------- 레트로핏 객체를 생성하는 메소드 ---------------------------*/


    public Retrofit createRetrofitObject() {

        retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }

    public Retrofit createSupplierRetrofitObject() {

        retrofit = new Retrofit.Builder()
                .baseUrl(API_SUPPLIER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }

/*---테스트*//*

    public Retrofit createTESTRetrofitObject() {

        retrofit = new Retrofit.Builder()
                .baseUrl(TEST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;

    }*/
    /*  ------------------------- retrofitController 인터페이스를 생성하는 메소드 ---------------------------*/
//memberController

    public CustomerController createApiserverObject() {
        apiService = retrofit.create(CustomerController.class);

        return apiService;
    }

      /*  ------------------------- retrofitController 인터페이스를 생성하는 메소드 ---------------------------*/


    public SupplierController createSupplierApiserverObject() {
        //supplierController
        apiService_supplier = retrofit.create(SupplierController.class);

        return apiService_supplier;
    }


}
