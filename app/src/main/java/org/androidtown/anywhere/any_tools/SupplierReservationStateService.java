package org.androidtown.anywhere.any_tools;

/**
 * Created by gdtbg on 2017-08-07.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_20_1_supplier_reservation_manager.ReservationManager;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.SupplierController;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by user on 2017-08-03.
 */

public class SupplierReservationStateService extends Service {


    String user_nick;

    public static boolean supplierServiceFlag;

    NotificationManager notiManager;
    int notificationId;
    SharedPreferences shef;

    Vibrator vibrator;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();

        shef = getSharedPreferences("anywhere", MODE_PRIVATE);

        //알람 매니저
        notiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //진동
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);
            Runnable runnable = new SupplierReservationStateThread();
            Thread thread = new Thread(runnable);
            thread.start();

        return START_STICKY;


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Intent intent = new Intent(SupplierReservationStateService.this, ReservationManager.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if (msg.what == 1) {


                Notification notification = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Anywhere 알람 메세지")
                        .setContentText("예약이 된 공간이 있습니다.")
                        .setSmallIcon(R.drawable.logo)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build();

                vibrator.vibrate(1000);

                notiManager.notify((int)msg.obj, notification);


            } else if (msg.what == 2) {

                Notification notification = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Anywhere 알람 메세지")
                        .setContentText("결제가 취소되었습니다.")
                        .setSmallIcon(R.drawable.logo)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build();

                vibrator.vibrate(1000);

                notiManager.notify((int)msg.obj, notification);
            }


        }



    };

    public class SupplierReservationStateThread implements Runnable {

        HttpRequestSyncObject hrs;
        Retrofit retrofit;
        SupplierController apiService;


        @Override
        public void run() {
            while (supplierServiceFlag) {
                try {
                    String user_nick = shef.getString("user_nick", "");
                    hrs = new HttpRequestSyncObject();
                    retrofit = hrs.createSupplierRetrofitObject();
                    apiService = hrs.createSupplierApiserverObject();
                    Call call = apiService.getReservationStateCheck(user_nick);
                    hrs.HttpRequestExecute(call);
                    hrs.makeGsonObject();
                    Type type = new TypeToken<ArrayList<Integer>>() {
                    }.getType();
                    ArrayList<Integer> state = (ArrayList<Integer>) hrs.parsingFunc(type);

                    if (state.size() != 0) {



                        for (int i = 0; i < state.size(); i++) {
                            int stateByNotification = state.get(i);


                            Message msg = new Message();
                            msg.obj = i;
                            if (stateByNotification == 1) {

                                msg.what = 1;

                            } else if (stateByNotification == 2) {

                                msg.what = 2;
                            }

                            handler.sendMessage(msg);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }

}