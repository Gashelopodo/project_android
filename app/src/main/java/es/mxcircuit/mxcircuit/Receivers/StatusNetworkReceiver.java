package es.mxcircuit.mxcircuit.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import es.mxcircuit.mxcircuit.utils.NetworkStatus;

public class StatusNetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkStatus networkStatus = new NetworkStatus(context);
        int status = networkStatus.getStatus();
        Log.d("MENSAJE", "STATUS NETWORK CHANGE: " + status);


        // quitamos esto y cambiamos a un método menos molesto
        /*if(status == networkStatus.TYPE_NOT_CONNECTED){
            //Utils.alert(context, Dialogs.ERROR_TITLE_GENERAL, NetworkStatus.ERROR_NO_CONNECTED);
            Intent intent1 = new Intent(context, NetworkOff.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }*/

    }
}
