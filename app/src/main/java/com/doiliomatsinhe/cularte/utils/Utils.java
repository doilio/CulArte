package com.doiliomatsinhe.cularte.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;

import java.lang.reflect.Type;
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

    public static String getJsonFromObject(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static Object getObjectFromJson(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }
}
