package es.mxcircuit.mxcircuit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import es.mxcircuit.mxcircuit.R;
import es.mxcircuit.mxcircuit.adapters.NotificationListAdapter;
import es.mxcircuit.mxcircuit.https.GetNotificationsUser;
import es.mxcircuit.mxcircuit.models.Notification;
import es.mxcircuit.mxcircuit.utils.Dialogs;
import es.mxcircuit.mxcircuit.utils.NetworkStatus;
import es.mxcircuit.mxcircuit.utils.Utils;

import java.util.ArrayList;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout loadLayout;
    private NotificationListAdapter notificationListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        if(NetworkStatus.getStatusFinal(this) == NetworkStatus.TYPE_NOT_CONNECTED){
            Utils.alertNetwork(this, NetworkStatus.ERROR_NO_CONNECTED, Dialogs.ERROR_TITLE_GENERAL);
        }else {
            // escuchamos menu lateral
            Utils.loadNavigationListener(this);

            // obtener notificaciones del usuario
            GetNotificationsUser getNotificationsUser = new GetNotificationsUser(this);
            getNotificationsUser.execute();
        }
    }

    public void loadNotifications(ArrayList<Notification> notifications){

        recyclerView = (RecyclerView) findViewById(R.id.notification_list);

        if(notifications != null) {

            loadLayout = (LinearLayout) findViewById(R.id.loadNotification);
            loadLayout.setVisibility(LinearLayout.INVISIBLE);

            notificationListAdapter = new NotificationListAdapter(notifications, this);
            recyclerView.setAdapter(notificationListAdapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

        }else{
            Log.d("MENSAJE", "NOTIFICATION ES NULL");
        }

    }



}
