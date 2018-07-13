package org.androidtown.anywhere.httpcontrol_retrofitController;

import org.androidtown.anywhere.any_newVO.CustomerReservationVO;
import org.androidtown.anywhere.any_newVO.LikeVO;
import org.androidtown.anywhere.any_newVO.MemberVO;
import org.androidtown.anywhere.any_newVO.QaVO;

import java.util.HashMap;
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
 * Created by user on 2017-07-17.
 */

public interface CustomerController {

    /*로그인*/
    @POST("login")
    @FormUrlEncoded
    Call<ResponseBody> login(@Field("member_email") String email, @Field("member_password") String pw);

    /*닉네임 체크*/

    @POST("nickCheck")
    @FormUrlEncoded
    Call<ResponseBody> nickCheck(@Field("member_nick") String nick);

   /*이메일 체크*/

    @POST("emailCheck")
    @FormUrlEncoded
    Call<ResponseBody> emailCheck(@Field("member_email") String email);

    /*성공 시 회원 정보 전송*/
    @POST("join")
    @FormUrlEncoded
    Call<ResponseBody> sendUserInfo(@Field("member_email") String email, @Field("member_nick") String nickname, @Field("member_password") String pw);

     /*비밀번호 찾기 - 이메일 체크*/

    @POST("passFindFirst")
    @FormUrlEncoded
    Call<ResponseBody> pwEmailCheck(@Field("member_email") String email);

    /*비밀번호 찾기 - 인증 번호 전송*/

    @POST("passFindSecond")
    @FormUrlEncoded
    Call<ResponseBody> randomNumCheck(@FieldMap Map<String, String> randomNum);


    /*---------------------------- 회원 정보 수정 클래스 -----------------------------*/

   /* 비밀 번호 가져오기*/

    @POST("passCheck")
    @FormUrlEncoded
    Call<ResponseBody> getPassword(@Field("member_email") String email);

     /* 수정된 회원 정보 전송 */

    @POST("memberUpdate")
    @FormUrlEncoded
    Call<ResponseBody> sendModifyUserInfo(@FieldMap Map<String, String> modifyUserInfo);

    /* 회원 탈퇴*/

    @POST("memberDelete")
    @FormUrlEncoded
    Call<ResponseBody> deleteInfo(@Field("member_email") String email);

    /* test*/

    @Multipart
    @POST("uploadForm")
    Call<ResponseBody> test(
            @Part("memberVO") MemberVO memberVO);



    /* StoreDetail VO받아오기*/

    @POST("storeDetail")
    @FormUrlEncoded
    Call<ResponseBody> getStoreDetailVO(@Field("store_num") int store_num, @Field("member_nick") String member_nick);

    /* 예약 정보 전송*/

    @Multipart
    @POST("customerReservation")
    Call<ResponseBody> reservationInfoSend(@Part("customerReservationVO") CustomerReservationVO reservationInfo);

     /* 즐겨 찾기 on*/

    @Multipart
    @POST("storeliked")
    Call<ResponseBody> onLike(@Part("likeVO") LikeVO likeVO);

     /* 즐겨 찾기 off*/

    @Multipart
    @POST("storeUnLiked")
    Call<ResponseBody> unLike(@Part("likeVO") LikeVO likeVO);

    /* 즐겨 찾기 VO 받아오기*/

    @POST("storeLikeList")
    @FormUrlEncoded
    Call<ResponseBody> getLikeVOList(@Field("like_nick") String like_nick);


   /* ---------------가게 예약--------------*/


    @POST("reservationDetail")
    @FormUrlEncoded
    Call<ResponseBody> getReservationListVO(@Field("store_num") int store_num);


    /* ---------------GPS 현재위치에서 검색--------------*/
    @POST("searchGps")
    @FormUrlEncoded
    Call<ResponseBody> getStoreListVO(@Field("gps") String gps);

        /* ---------------예약 리스트 있는지 확인--------------*/

    @POST("reservationCheck")
    @FormUrlEncoded
    Call<ResponseBody> hasReservationList(@Field("store_num") int store_num);

    /* --------------- 상세검색 --------------*/


    @POST("searchDetail")
    @FormUrlEncoded
    Call<ResponseBody> getSearchDetail(@FieldMap HashMap<String, String> map);

        /* --------------- Qna 게시판 글쓰기 --------------*/


    @POST("qnaInsert")
    @FormUrlEncoded
    Call<ResponseBody> insertQnaBoard(@FieldMap HashMap<String, String> map);

    /* --------------- Qna 게시판 글쓰기 --------------*/


    @POST("qnaList")
    @FormUrlEncoded
    Call<ResponseBody> getQnaBoard(@Field("qa_nick") String nickname);

    /* --------------- Qna 게시판 수정 --------------*/
    @Multipart
    @POST("qnaUpdate")
    Call<ResponseBody> updateQnaBoard(@Part("qaVO") QaVO qaVO);

    /* --------------- Qna 게시판 삭제 --------------*/

    @POST("qnaDelete")
    @FormUrlEncoded
    Call<ResponseBody> deleteQnaBoard(@Field("qa_num") int qa_num);

     /* --------------- Event 게시판 VO 받아오기 --------------*/

    @POST("eventList")
    @FormUrlEncoded
    Call<ResponseBody> getEventBoard(@Field("event") String event);

      /* --------------- Notice 게시판 VO 받아오기 --------------*/

    @POST("noticeList")
    @FormUrlEncoded
    Call<ResponseBody> getNoticeBoard(@Field("notice") String notice);

   /* --------------- Notice 게시판 VO 받아오기 --------------*/

    @POST("eventApply")
    @FormUrlEncoded
    Call<ResponseBody> applyEvent(@Field("check_nick") String check_nick, @Field("event_num") int event_num);

       /* --------------- QnA 게시판 Reply 달기 --------------*/

    @POST("qnaReplyInsert")
    @FormUrlEncoded
    Call<ResponseBody> writeQnAReply(@FieldMap Map<String, String> userInfo);

           /* --------------- QnA 게시판 Reply 받아오기 --------------*/

    @POST("qnaReplyList")
    @FormUrlEncoded
    Call<ResponseBody> getQnaReplyVO(@Field("qa_num") int qa_num);

       /* --------------- 대댓글 달기기 -------------*/

    @POST("qnaReReplyInsert")
    @FormUrlEncoded
    Call<ResponseBody> appendReply(@FieldMap Map<String, String> appenReply);


       /* --------------- 댓글 삭제 -------------*/

    @POST("qnaReplyDelete")
    @FormUrlEncoded
    Call<ResponseBody> deleteReply(@FieldMap Map<String, String> appenReply);


      /* --------------- 댓글 수정 -------------*/

    @POST("qnaReplyUpdate")
    @FormUrlEncoded
    Call<ResponseBody> modifyReply(@FieldMap Map<String, String> appenReply);


    /* --------------- 예약 내역 받아오기기 ------------*/

    @POST("reservaionList")
    @FormUrlEncoded
    Call<ResponseBody> getReservationList(@Field("member_nick") String member_nick);

     /* --------------- 예약 상세 내역 받기 ------------*/

    @POST("reservaionListDetail")
    @FormUrlEncoded
    Call<ResponseBody> getDetailReservationList(@Field("store_num") int store_num, @Field("reservation_num") int reservation_num);


    /* --------------- 예약 취소 ------------*/

    @POST("reservationCancel")
    @FormUrlEncoded
    Call<ResponseBody> cancelReservation(@FieldMap Map<String, String> map);

     /* --------------- 후기 쓰기 ------------*/

    @POST("storeReplyInsert")
    @FormUrlEncoded
    Call<ResponseBody> writeFeedBack(@FieldMap Map<String, String> map);

     /* --------------- 후기 삭제 ------------*/

    @POST("storeReplyDelete")
    @FormUrlEncoded
    Call<ResponseBody> deleteFeedBack(@FieldMap Map<String, String> map);

      /* --------------- Main - 모임 타입을 통해 검색 결과로 넘어가기 ------------*/

    @POST("mainTypeSearch")
    @FormUrlEncoded
    Call<ResponseBody> tagSearch(@Field("reservation_type") String reservationType);

          /* --------------- 서비스 객체 사용하기 ------------*/


    @POST("reservationStateCheck")
    @FormUrlEncoded
    Call<ResponseBody> getReservationStateCheck(@Field("member_nick") String member_nick);




}
