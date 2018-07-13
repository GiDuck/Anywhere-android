package org.androidtown.anywhere.any_tools;

import android.Manifest;
import android.app.Activity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

/**
 * Created by user on 2017-07-14.
 */

public class PermissionStorage implements PermissionListener {
    TedPermission permissionCheck;
    String []permissions={Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public PermissionStorage(Activity activity) {

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
        PermissionFlag.storageFlag = true;
    }

    @Override
    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
        PermissionFlag.storageFlag = false;
    }
}
