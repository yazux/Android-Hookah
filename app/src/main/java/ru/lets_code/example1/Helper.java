package ru.lets_code.example1;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Helper {
    private static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService( (Context.CONNECTIVITY_SERVICE) );
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return (netInfo != null && netInfo.isConnectedOrConnecting());
    }


    public static boolean checkConnection(Activity activity, Context context, Boolean showMessage) {
        Boolean online = isOnline(context);
        if (online) {
            if (showMessage) Toast.makeText(activity, R.string.yes_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            if (showMessage) Toast.makeText(activity, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }

        return online;
    }
}
