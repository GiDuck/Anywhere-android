package org.androidtown.anywhere.httpcontrol_retrofitController;

import org.androidtown.anywhere.any_newVO.ReservationVO;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by gdtbg on 2017-07-22.
 */

public interface SupplierController {

    /*가게 정보 수정 - StoreVO List 받아오기*/
    @POST("defalutStoreList")
    @FormUrlEncoded
    Call<ResponseBody> getStoreVO(@Field("store_nick") String nick);

    /* *//*가게 정보 수정 - 가게 등록 중복 확인*//*
    @POST("defalutStroeCheck")
    @FormUrlEncoded
    Call<ResponseBody> storeAuth(@Field("store_name") String store_name, @Field("store_addr") String store_addr, @Field("store_nick") String store_nick);
*/
    /*가게 정보 수정 - 해당 StoreVO 받아오기*/
    @POST("defalutStoreSelect")
    @FormUrlEncoded
    Call<ResponseBody> getTargetStoreVO(@Field("store_num") int store_num);

    /*가게 삭제*/
    @POST("defalutStoreDelete")
    @FormUrlEncoded
    Call<ResponseBody> deleteStore(@Field("store_num") int store_num);

    /*가게 예약 등록 / VO받아오기*/
    @POST("storeReservationResister")
    @FormUrlEncoded
    Call<ResponseBody> getReservationVO(@Field("store_num") int store_num);

    /*가게 예약 등록 / VO 넘기기*/
    @Multipart
    @POST("storeReservationResister")
    Call<ResponseBody> setReservationVO(@Part("reservationVO") ReservationVO reservationVO);

    /*가게 예약 등록 / VO받아오기*/
    @POST("storeReservationCheck")
    @FormUrlEncoded
    Call<ResponseBody> getReservationListVO(@Field("store_num") int store_num);

    /*가게 예약 등록 / VO받아오기*/

    @POST("storeReplyList")
    @FormUrlEncoded
    Call<ResponseBody> getReplyVO(@Field("store_num") String store_num);

    /* --------------- 후기 관리 대댓글 달기기 -----------*/

    @POST("storeReReplyInsert")
    @FormUrlEncoded
    Call<ResponseBody> writeReFeedBack(@FieldMap Map<String, String> map);

      /* --------------- 후기 댓글 삭제 -----------*/

    @POST("storeReplyDelete")
    @FormUrlEncoded
    Call<ResponseBody> deleteFeedBack(@FieldMap Map<String, String> map);

        /* --------------- 예약 관리 - 예약 정보 받아오기 -----------*/

    @POST("reservationList")
    @FormUrlEncoded
    Call<ResponseBody> getReservationData(@Field("store_num") int store_num);

         /* --------------- 예약 관리  - 예약 정보 상세보기 - 메뉴 리스트 받아오기 -----------*/

    @POST("reservationDetail")
    @FormUrlEncoded
    Call<ResponseBody> getMenuList(@Field("store_num") int store_num, @Field("reservation_num") int reservation_num);

           /* --------------- 예약 관리  - 예약 삭제 -----------*/

    @POST("reservationDelete")
    @FormUrlEncoded
    Call<ResponseBody> deleteReservation(@Field("store_num") int store_num, @Field("reservation_num") int reservation_num);

    /*서비스 객체 사용하기*/
    @POST("reservationStateCheck")
    @FormUrlEncoded
    Call<ResponseBody> getReservationStateCheck(@Field("member_nick") String member_nick);


}
