package org.androidtown.anywhere.any_00_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_newVO.ReservationListVO;

import java.util.ArrayList;
import java.util.HashSet;

public class test04 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test04);

   /*     ArrayList<TestVO> test = new ArrayList<>();

        TestVO testVO = new TestVO();
        testVO.setLon(17.004201);
        testVO.setRat(16.000242122);
        testVO.setStoreName("김기덕");
        test.add(testVO);


        testVO = new TestVO();
        testVO.setLon(19.004201);
        testVO.setRat(18.000242122);
        testVO.setStoreName("나기덕");
        test.add(testVO);

        TestVO testVO2 = new TestVO();
        testVO = new TestVO();
        testVO.setLon(6.004201);
        testVO.setRat(21.000242122);
        testVO.setStoreName("다기덕");
        test.add(testVO2);

        testVO = new TestVO();
        testVO.setLon(23.004201);
        testVO.setRat(20.000242122);
        testVO.setStoreName("라기덕");
        test.add(testVO);

        testVO = new TestVO();
        testVO.setLon(64.004201);
        testVO.setRat(4.000242122);
        testVO.setStoreName("마기덕");
        test.add(testVO);

        Log.d("testTest", test.size()+"");
        Log.d("testTest", "------정렬전-------");
        for(TestVO testVo : test){

            Log.d("testTest", testVo.getLon()+"");
        }

        Collections.sort(test, new Comparator<TestVO>() {
            @Override
            public int compare(TestVO o1, TestVO o2) {
                if (o1.getLon() > o2.getLon()) {
                    return 1;
                } else if (o1.getLon() < o2.getLon()) {
                    return -1;
                } else {

                    return 0;
                }
            }
        });

        Log.d("testTest", "------정렬후--------");

        for(TestVO testVo : test){

            Log.d("testTest", testVo.getLon()+"");
        }
*/


        ArrayList<ReservationListVO> arrayList1 = new ArrayList<>();
        ReservationListVO reservation = new ReservationListVO();
        reservation.setStore_num(2);
        reservation.setReservation_nick("giduck1");
        arrayList1.add(reservation);

        reservation = new ReservationListVO();
        reservation.setStore_num(2);
        reservation.setReservation_nick("giduck2");
        arrayList1.add(reservation);
        reservation = new ReservationListVO();
        reservation.setStore_num(3);
        reservation.setReservation_nick("giduck3");
        arrayList1.add(reservation);
        reservation = new ReservationListVO();
        reservation.setStore_num(4);
        reservation.setReservation_nick("giduck4");
        arrayList1.add(reservation);
        reservation = new ReservationListVO();
        reservation.setStore_num(4);
        reservation.setReservation_nick("giduck5");
        arrayList1.add(reservation);

        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < arrayList1.size(); i++) {

            set.add(arrayList1.get(i).getStore_num());

        }
        ArrayList<Integer> arrayList2 = new ArrayList<>(set);

        for (int i = 0; i < arrayList2.size(); i++) {

            Log.d("giduckTestTest", arrayList2.get(i) + "");
        }


    }


}
