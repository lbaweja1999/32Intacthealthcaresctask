package com.example.a32intacthealthcare.Broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.a32intacthealthcare.Firebase.GmailIntegration;


public class MyInternetReceiver extends BroadcastReceiver {
    public  MyInternetReceiver(){

}
//    @SuppressLint("LongLogTag")
    @Override
    public void onReceive(Context context, Intent intent) {
        try {

            boolean isVisible = MyApplication.isActivityVisible();// Check if
            // activity
            // is
            // visible
            // or not
            Log.i("ActivityModel is Visible ", "Is activity visible : " + isVisible);

            // If it is visible then trigger the task else do nothing
            if (isVisible == true) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager
                        .getActiveNetworkInfo();

                // Check internet connection and accrding to state change the
                // text of activity by calling method
                if (networkInfo != null && networkInfo.isConnected()) {

                    new GmailIntegration().showSnack(true);
                } else {
                    new GmailIntegration().showSnack(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
