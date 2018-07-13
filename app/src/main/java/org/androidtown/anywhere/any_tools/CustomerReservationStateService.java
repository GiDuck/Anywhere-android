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
import android.util.Log;

import org.androidtown.anywhere.R;
import org.androidtown.anywhere.any_07_customer_reservation.CustomerReservation;
import org.androidtown.anywhere.httpcontrol.HttpRequestSyncObject;
import org.androidtown.anywhere.httpcontrol_retrofitController.CustomerController;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by user on 2017-08-01.
 */

public class CustomerReservationStateService extends Service {


    public static boolean customerServiceFlag;
    NotificationManager notiManager;
    Vibrator vibrator;
    SharedPreferences shef;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("서비스","수요자 서비스");
        shef = getSharedPreferences("anywhere", MODE_PRIVATE);

        //알람 매니저
        notiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //진동
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        super.onStartCommand(intent, flags, startId);
            Runnable runnable = new CustomerReservationStateThread();
            Thread thread = new Thread(runnable);
            thread.start();

        return START_STICKY;


    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){

                Intent intent = new Intent(CustomerReservationStateService.this,CustomerReservation.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                Notification notification = new Notification.Builder(getApplicationContext())
                        .setContentTitle("Anywhere 알람 메세지")
                        .setContentText("사용하신 공간은 어떠셨나요?")
                        .setSmallIcon(R.drawable.logo)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build();

                vibrator.vibrate(1000);

                notiManager.notify(1,notification);
            }
        }
    };



    public class CustomerReservationStateThread implements Runnable {

        HttpRequestSyncObject hrs;
        Retrofit retrofit;
        CustomerController apiService;



        @Override
        public void run() {
            while (customerServiceFlag) {
                try {
                    String user_nick = shef.getString("user_nick", "");

                    hrs = new HttpRequestSyncObject();
                    retrofit = hrs.createRetrofitObject();
                    apiService = hrs.createApiserverObject();
                    Call call = apiService.getReservationStateCheck(user_nick);
                    hrs.HttpRequestExecute(call);

                    String returnToken = hrs.parsingStringFunc("result");
                    if (returnToken.equals("true")) {

                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    } else if (returnToken.equals("false")) {

                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
