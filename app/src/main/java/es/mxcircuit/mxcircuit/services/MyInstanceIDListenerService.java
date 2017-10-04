package es.mxcircuit.mxcircuit.services;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Gashe on 21/5/17.
 */

public class MyInstanceIDListenerService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        Intent i = new Intent(this, RegistrationTokenNotification.class);
        startService(i);
    }

}
