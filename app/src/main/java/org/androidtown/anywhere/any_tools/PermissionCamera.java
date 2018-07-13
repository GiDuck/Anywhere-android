package org.androidtown.anywhere.any_tools;

import android.Manifest;
import android.app.Activity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

/**
 * Created by gdtbg on 2017-08-18.
 */

public class PermissionCamera implements PermissionListener {

    TedPermission permissionCheck;
    String permissions = Manifest.permission.CAMERA;

    public PermissionCamera(Activity activity) {
        initialize(activity);


    }


    public void initialize(Activity activity) {
        permissionCheck = new TedPermission(activity);
        permissionCheck.setPermissionListener(this)
                .setPermissions(permissions)
                .check();
    }


    @Override
    public void onPermissionGranted() {
        PermissionFlag.cameraFlag = true;
    }

    @Override
    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
        PermissionFlag.cameraFlag = false;

    }
}
