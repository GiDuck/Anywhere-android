package org.androidtown.anywhere.any_tools;

import android.Manifest;
import android.app.Activity;
import android.util.Log;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-14.
 */

public class PermissionLocation implements PermissionListener {

    TedPermission permissionCheck;
    String []permissions={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

    public PermissionLocation(Activity activity) {

        initialize(activity);
    }

    public void initialize(Activity activity){
        permissionCheck = new TedPermission(activity);
        permissionCheck.setPermissionListener(this)
                .setPermissions(permissions)
                .check();
    }

    @Override
    public void onPermissionGranted() {
        PermissionFlag.locationFlag = true;
        if(PermissionFlag.locationFlag){
            Log.d("locationFlag","true");
        }
    }

    @Override
    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
        PermissionFlag.locationFlag = false;
        if(!PermissionFlag.locationFlag){
            Log.d("locationFlag","false");
        }
        for(int i=0;i<deniedPermissions.size();i++){
            Log.d("permission",deniedPermissions.get(i));
        }

    }
}
