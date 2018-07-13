package org.androidtown.anywhere.any_tools;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by gdtbg on 2017-07-28.
 */

public class GpsSearch implements LocationListener {
    private Activity activity;
    //gps 사용유무
    private boolean isGPSEnabled;

    //네트워크 사용유무
    private boolean isNetworkEnabled;

    //GPS 상태값
    private boolean isGPSLocation;

    //위도 경도 구하기 위해서 필요한 객체
    private Location location;
    private double lat;//위도
    private double lon;//경도

    //최소 GPS 정보 업데이트 거리 10미터
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    //최소 GPS 정보 업데이트 시간 밀리세컨이므로 1분
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private LocationManager locationManager;



    public GpsSearch(Activity activity){
        this.activity = activity;
        getLocation();
    }

    @SuppressWarnings("MissingPermission")
    public Location getLocation() {

        if (Build.VERSION.SDK_INT >= 23) {
            locationPermissionCheck();
        } else {
            //locationManager선언
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            //GPS정보 가져오기
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //현재 네트워크 상태값 가져오기
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }

        if (isGPSEnabled && isNetworkEnabled) {
            if (isNetworkEnabled) {
                //locationManager 업데이트 할 시
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this
                );

                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                }
            }

            if(isNetworkEnabled){
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this
                );

                if(locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if(location!=null){
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    }
                }
            }
        }else{
            Log.d("location","null");
            return null;
        }
        return location;
    }





    public void locationPermissionCheck(){

        if(!PermissionFlag.locationFlag){
            Log.d("GpsSearch","권한 거절");

            if(locationManager!=null){
                locationManager.removeUpdates(this);
            }
        }else{
            Log.d("GpsSearch","권한 승인");
            //locationManager 선언
            locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            //GPS정보 가져오기
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //현재 네트워크 상태값 가져오기
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
    }

    public double getLat() {
        if(location !=null){
            lat= location.getLatitude();
            Log.d("GpsSearch",String.valueOf(lat));
        }
        return lat;
    }

    public double getLon() {
        if(location != null){
            lon = location.getLongitude();
            Log.d("GpsSearch",String.valueOf(lon));
        }
        return lon;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}