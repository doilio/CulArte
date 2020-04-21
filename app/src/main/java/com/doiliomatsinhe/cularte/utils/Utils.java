package com.doiliomatsinhe.cularte.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class Utils {

    private static final int FLAG = 0;

    public static boolean isAppInstalled(Context context, String appName) {
        PackageManager packageManager = context.getPackageManager();
        boolean isInstalled = false;
        List<PackageInfo> packages = packageManager.getInstalledPackages(FLAG);
        for (PackageInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(appName)) {
                isInstalled = true;
                break;
            }
        }
        return isInstalled;
    }
}
