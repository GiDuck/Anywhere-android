package org.androidtown.anywhere.any_12_search_store;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_13_search_result.SearchResult;

/**
 * Created by user on 2017-07-03.
 */

public class SearchStore extends RelativeLayout implements View.OnClickListener {

    Context context;

    Button searchGPS;

    private ImageView iv;
    private TextView text;
    private AnimatedVectorDrawable searchToBar;
    private AnimatedVectorDrawable barToSearch;
    private float offset;
    private Interpolator interp;
    private int duration;
    private boolean expanded = false;
    private LinearLayout detailSearch;
    private LocationManager locationManager;


    public SearchStore(Context context) {
        super(context);
        this.context = context;
        init();



    }

    public SearchStore(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void init() {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.anywhere12_search_store, this, true);


        iv = (ImageView) findViewById(R.id.search);
        text = (TextView) findViewById(R.id.text);
        detailSearch = (LinearLayout) findViewById(R.id.detailSearch);
        searchToBar = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_search_to_bar);
        barToSearch = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_bar_to_search);
        interp = AnimationUtils.loadInterpolator(context, android.R.interpolator.linear_out_slow_in);
        duration = getResources().getInteger(R.integer.duration_bar);
        // iv is sized to hold the search+bar so when only showing the search icon, translate the
        // whole view left by half the difference to keep it centered
        offset = -71f * (int) getResources().getDisplayMetrics().scaledDensity;
        iv.setTranslationX(offset);
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        searchGPS = (Button) findViewById(R.id.searchGPS);

        iv.setOnClickListener(this);
        searchGPS.setOnClickListener(this);
        detailSearch.setOnClickListener(this);


        iv.setImageDrawable(barToSearch);
        barToSearch.start();
        iv.animate().translationX(offset).setDuration(duration).setInterpolator(interp);
        text.setAlpha(0f);
        expanded = !expanded;


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void animate(View view) {

        if (!expanded) {
            //검색 버튼 눌렀을때 (길게 펴짐)


            iv.setImageDrawable(barToSearch);
            barToSearch.start();
            iv.animate().translationX(offset).setDuration(duration).setInterpolator(interp);
            text.setAlpha(0f);




        } else {
            //검색 버튼 다시 한 번 눌렀을때 (짧아짐)

            iv.setImageDrawable(searchToBar);
            searchToBar.start();
            iv.animate().translationX(0f).setDuration(duration).setInterpolator(interp);
            text.animate().alpha(1f).setStartDelay(duration - 100).setDuration(100).setInterpolator(interp);

            iv.animate().withEndAction(new Runnable() { //애니메이션이 종료될 때의 액션 지정
                @Override
                public void run() {
                    Intent intent = new Intent(context, SearchDetailStore.class);
                    context.startActivity(intent);

                }
            });


        }
        expanded = !expanded;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        int click = v.getId();
        Intent intent = null;
        switch (click) {

            case R.id.searchGPS: {

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


                    intent = new Intent(context, SearchResult.class);
                    intent.putExtra("GPS", "GPS");
                    context.startActivity(intent);
                    break;

                } else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


                    Toast.makeText(context, "위치 정보에 접근할 수 없습니다. 먼저 GPS 사용 설정을 해 주세요.", Toast.LENGTH_LONG).show();
                    intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    context.startActivity(intent);
                    break;


                }

            }

            case R.id.search: {
                animate(iv);
                break;
            }


        }
    }
}
