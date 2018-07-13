package org.androidtown.anywhere.any_02_main.ViewPaper;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.androidtown.anywhere.R;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static org.androidtown.anywhere.R.id.test;
import static org.androidtown.anywhere.any_02_main.Main.mathCounter;

/**
 * Created by user on 2017-07-02.
 */
//슬라이딩하는 뷰페이지
public class MainSlideViewPageAdapter extends PagerAdapter{

    Context context;
    ArrayList<MainSlideData> data;
    int[] pictures = new int[]{R.drawable.tyle1, R.drawable.tyle2, R.drawable.tyle3, R.drawable.tyle4};

    //어느 레이아웃에 사용할지 정하기위해 Context 넣어주기
    public MainSlideViewPageAdapter(Context context, ArrayList<MainSlideData> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        //뷰페이지 슬라이딩 할 레이아웃 인플레이션
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.anywhere02_main_slide_viewpage,null);
        ImageView image = (ImageView) v.findViewById(test);

//        Glide.with(context).load(data.get(position).getUrl()).into(image);
        int math = 0;
        if(position>3){

            math=position/4;

        }
            Glide.with(context).load(pictures[position-(4*math)]).into(image);
            Log.d("giduckTest1", "mathCounter : " + mathCounter);
        mathCounter++;

        //viewPager 만들어낸 뷰 추가가
        container.addView(v);
        return v;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    //instantiateItem() 메소드에서 리턴된 Object가 View가 맍는지 확인하는 메소드
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
