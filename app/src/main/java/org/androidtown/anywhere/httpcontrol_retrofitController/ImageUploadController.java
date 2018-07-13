package org.androidtown.anywhere.httpcontrol_retrofitController;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by gdtbg on 2017-07-20.
 */

public interface ImageUploadController {
//172.22.234.22:8089


    // http://192.168.0.25:8080/controller/android/uploadForm/2d141cd3-58c4-47c0-9951-24cd400737ff_Screenshot_2017-07-19-09-21-56.png

    @Multipart
    @POST("uploadForm")
    Call<ResponseBody> uploadImageAndObject(
            @Part("description") RequestBody description,
            @Part List<MultipartBody.Part> files,
            @Part ("memberVO") Object object

    );

    @Multipart
    @POST("defalutStoreResister")
    Call<ResponseBody> uploadMultiImageAndObject(
            @Part("description") RequestBody description,
            @Part List<MultipartBody.Part> sub,
            @Part MultipartBody.Part main,
            @Part ("storeVO") Object object

    );


    @Multipart
    @POST("defalutStoreModify")
    Call<ResponseBody> updateMultiImageAndObject(
            @Part("description") RequestBody description,
            @Part List<MultipartBody.Part> sub,
            @Part MultipartBody.Part main,
            @Part ("storeVO") Object object

    );


    @Multipart
    @POST("uploadForm")
    Call<ResponseBody> uploadAlbum(
            @Part("description") RequestBody description,
            @Part List<MultipartBody.Part> files

    );

}
