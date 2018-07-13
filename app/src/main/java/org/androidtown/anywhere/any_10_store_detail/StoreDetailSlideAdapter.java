package org.androidtown.anywhere.any_10_store_detail;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.androidtown.anywhere.R;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-06.
 */

public class StoreDetailSlideAdapter extends PagerAdapter{

    ArrayList<StoreDetailSlideData> datas;
    Context context;

    public StoreDetailSlideAdapter(Context context) {
        datas = new ArrayList<>();
        this.context = context;
    }


    public void add(StoreDetailSlideData data){
        datas.add(data);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.anywhere10_store_detail_slide_viewpage,null);

        ImageView testData = (ImageView) v.findViewById(R.id.testData);
        Glide.with(context).load(datas.get(position).getTestData()).fitCenter().into(testData);

        testData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(datas.get(position).getTestData());
            }
        });

        container.addView(v);
        return v;
    }

    //내부 사진 클릭시 크게 보여주는 다이얼로그
    private void showDialog(String url) {

        LayoutInflater dialog = LayoutInflater.from(context);
        View dialogLayout = dialog.inflate(R.layout.anywhere10_store_detail_photo_dialog, null);
        Dialog myDialog = new Dialog(context);
        myDialog.setContentView(dialogLayout);
        ImageView bigImage = (ImageView) dialogLayout.findViewById(R.id.bigImage);
        Glide.with(dialogLayout.getContext()).load(url).into(bigImage);

        myDialog.show();


    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }


}
