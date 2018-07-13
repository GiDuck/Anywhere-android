package org.androidtown.anywhere.any_22_supplier_addplace2;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;
import java.util.List;

/**
 * Created by user on 2017-08-14.
 */

public class EventDecorator implements DayViewDecorator {

    private int state;
    private List<CalendarDay> dates;
    private HashSet<CalendarDay> dateSet;


    public void setState(int state) {
        this.state = state;
    }

    public void setDates(List<CalendarDay> dates){
        this.dates = dates;

        dateSet=new HashSet<>();

        for(CalendarDay cd : dates)
        {
            dateSet.add(cd);

        }
    }



    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Log.d("giduck", "test2");
        Log.d("giduck",day.getDate().toString());


        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        Log.d("giduck", "test3");
        if(state==1) {
            view.addSpan(new ForegroundColorSpan((Color.RED)));
        }else if(state==0){
            view.addSpan(new ForegroundColorSpan((Color.BLUE)));
        }

    }
}