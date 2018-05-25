package com.example.alber.prova_tab_menu.Extras;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class INTERNET {
    private static ConnectivityManager manager;

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
}
