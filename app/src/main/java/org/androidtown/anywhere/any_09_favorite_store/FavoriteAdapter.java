package org.androidtown.anywhere.any_09_favorite_store;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_10_store_detail.StoreDetail;
import org.androidtown.anywhere.any_newVO.LikeVO;
import org.androidtown.anywhere.any_newVO.StoreVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gdtbg on 2017-07-03.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteViewHolder> implements FavoriteOnListItemClickListener {

    private Context context;
    private List<ArrayList<String>> items;
    private Uri uri;
    private ArrayList<String> item; /*가게이름과 사진 url를 건네받은 arraylist (수정 예정)*/
    private FavoriteViewHolder holder;
    ArrayList<LikeVO> likeVO;
    ArrayList<StoreVO> storeVO;

    public FavoriteAdapter(Context context, List<ArrayList<String>> item) {
        items = new ArrayList<>();
        items = item;
        this.context = context;
        this.item = new ArrayList<>();


    }


    public void add(ArrayList<String> data) {

        items.add(data);
        notifyDataSetChanged();

    }

    public void remove(int position) {
        items.remove(position);
        notifyDataSetChanged();

    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.anywhere09_favorite_store_custom, parent, false);

        FavoriteViewHolder holder = new FavoriteViewHolder(v);
        holder.setOnListitemClickListener(this);

        return holder;
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, final int position) {

        this.holder = holder;
        Log.d("position1", "" + position);
        /*가게 이름과 사진 url를 건네받은 Arraylist에서 추출 하여 뷰에 입력 시킨다. */
        /*사진 url의 이미지 뷰에 거치 시키기 위한 글라이드 라이브러리 사용*/

        item = items.get(position);
        holder.storeName.setText(item.get(1));
        Glide.with(context)
                .load(item.get(0))
                .into(holder.storePhoto);
       /*

       //즐겨찾기 삭제
        holder.likeButton.setLiked(true);
        holder.likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

            }

            @Override
            public void unLiked(LikeButton likeButton) {
                makeRemoveAlterDialog(position, likeButton);
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onListItemClick(int position) {

        /*온 클릭 이벤트 발생 시 해당 가게 소개 페이지로 넘어감
        (인텐트에 가게 정보 VO 담아서 같이 보냄)*/

        Intent intent = new Intent(context, StoreDetail.class);
        intent.putExtra("store_num", Integer.parseInt(item.get(2)));
        intent.putExtra("member_nick", "giduck");
        context.startActivity(intent);
    }

   /*
   //즐겨찾기 삭제 다이얼로그

   private void makeRemoveAlterDialog(final int position, final LikeButton likeButton) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context); //다이얼로그 바디
        alertDialog.setMessage("해당 항목이 삭제됩니다. 삭제 하시겠습니까?"); //다이얼로그 메세지
        alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() { //확인 버튼 클릭리스너 부착
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    remove(position);

             *//*  LikeVO likeVO = new LikeVO();
               likeVO.setLike_nick("giduck");


               HttpRequestSyncObject hrs =new HttpRequestSyncObject();
               hrs.createRetrofitObject();
               CustomerController apiService = hrs.createApiserverObject();*//*


                    Toast.makeText(context, "삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "삭제 하지 못헀습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alertDialog.setNegativeButton("취소", new DialogInterface.OnClickListener() { //취소 버튼 클릭리스너 부착
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (likeButton.isLiked()) {
                    likeButton.setLiked(false);
                } else {
                    likeButton.setLiked(true);
                }

            }
        });

        AlertDialog alert = alertDialog.create(); //메인 다이얼로그 샐성
        alert.setTitle("즐겨찾기 삭제"); //제목 설정
        alert.show(); //다이얼로그 활성화


    }

    public ArrayList<LikeVO> getLikeVO() {
        return likeVO;
    }

    public void setLikeVO(ArrayList<LikeVO> likeVO) {
        this.likeVO = likeVO;
    }

    public ArrayList<StoreVO> getStoreVO() {
        return storeVO;
    }

    public void setStoreVO(ArrayList<StoreVO> storeVO) {
        this.storeVO = storeVO;
    }*/
}
