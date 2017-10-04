package es.mxcircuit.mxcircuit.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import es.mxcircuit.mxcircuit.activities.ListCircuitsActivity;
import es.mxcircuit.mxcircuit.activities.ProfileCircuitActivity;
import es.mxcircuit.mxcircuit.utils.Constants;
import es.mxcircuit.mxcircuit.utils.Utils;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by Gashe on 21/5/17.
 */

public class MyGcmListenerService extends GcmListenerService {

    String circuit_id;

    @Override
    public void onMessageReceived(String s, Bundle bundle) {

        String body = (String) bundle.getString("message");
        String title = (String) bundle.getString("title");
        String circuit_id = (String) bundle.getString("circuit_id");
        this.circuit_id = circuit_id;

        Log.d("MENSAJE", "Message: " + title + " ("+circuit_id+")" + ": " + body);

        showNotification(title, body, circuit_id);


        super.onMessageReceived(s, bundle);
    }

    private void showNotification(String title, String body, String circuit_id){

        // preparar la notificación
        PendingIntent pendingIntentActivity = getPendingIntent();
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(es.mxcircuit.mxcircuit.R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntentActivity)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(Notification.PRIORITY_HIGH)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        notificationManager.notify(Utils.numberUnique(), notification);


    }

    public PendingIntent getPendingIntent(){

        PendingIntent pendingIntent = null;
        Intent intent;

        if(!circuit_id.equals("55")){
            intent = new Intent(this, ProfileCircuitActivity.class);
            intent.putExtra(Constants.BUNDLE.ID_CIRCUIT, circuit_id);
        }else{
            intent = new Intent(this, ListCircuitsActivity.class);
        }

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent = PendingIntent.getActivity(this, 200, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        return pendingIntent;
    }



}
