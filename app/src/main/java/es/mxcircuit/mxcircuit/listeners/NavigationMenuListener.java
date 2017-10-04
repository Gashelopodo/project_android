package es.mxcircuit.mxcircuit.listeners;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import es.mxcircuit.mxcircuit.activities.ListCircuitsActivity;
import es.mxcircuit.mxcircuit.activities.NotificationsActivity;
import es.mxcircuit.mxcircuit.activities.SettingActivity;
import es.mxcircuit.mxcircuit.activities.WebViewActivity;
import es.mxcircuit.mxcircuit.utils.Constants;

/**
 * Created by Gashe on 12/6/17.
 */

public class NavigationMenuListener implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Context context;

    public NavigationMenuListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Activity activity = (Activity)context;
        DrawerLayout drawerLayout = (DrawerLayout)activity.findViewById(es.mxcircuit.mxcircuit.R.id.myDrawer);
        int option = item.getItemId();
        Intent intent;

        Log.d("MENSAJE", "ENTRO:" + option);

        drawerLayout.closeDrawer(Gravity.START);

        switch (option){
            case es.mxcircuit.mxcircuit.R.id.go_circuits:
                intent = new Intent(context, ListCircuitsActivity.class);
                context.startActivity(intent);
                break;
            case es.mxcircuit.mxcircuit.R.id.go_notifications:
                intent = new Intent(context, NotificationsActivity.class);
                context.startActivity(intent);
                break;
            case es.mxcircuit.mxcircuit.R.id.go_register_circuit:
                intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(Constants.BUNDLE.URL_WEBVIEW, Constants.URL_SERVER+Constants.WEB.ADD_CIRCUIT);
                context.startActivity(intent);
                break;
            case es.mxcircuit.mxcircuit.R.id.go_how_work:
                intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(Constants.BUNDLE.URL_WEBVIEW, Constants.URL_SERVER+Constants.WEB.DOSSIER);
                context.startActivity(intent);
                break;
            case es.mxcircuit.mxcircuit.R.id.go_admin_circuit:
                intent = new Intent(context, WebViewActivity.class);
                intent.putExtra(Constants.BUNDLE.URL_WEBVIEW, Constants.URL_SERVER+Constants.WEB.LOGIN_CIRCUIT);
                context.startActivity(intent);
                break;
            case es.mxcircuit.mxcircuit.R.id.go_share:
                intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(es.mxcircuit.mxcircuit.R.string.app_name));
                intent.putExtra(Intent.EXTRA_TEXT, Constants.URL_SERVER);
                context.startActivity(intent);
                break;
            case es.mxcircuit.mxcircuit.R.id.go_contact:
                intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", Constants.MAIL, null));
                context.startActivity(Intent.createChooser(intent, "Enviar email a "+Constants.MAIL));
                break;
        }

        return false;


    }

    @Override
    public void onClick(View view) {
        Activity activity = (Activity)context;
        DrawerLayout drawerLayout = (DrawerLayout)activity.findViewById(es.mxcircuit.mxcircuit.R.id.myDrawer);
        int option = view.getId();
        Intent intent;

        drawerLayout.closeDrawer(Gravity.START);

        switch (option){
            case es.mxcircuit.mxcircuit.R.id.setting:
                intent = new Intent(context, SettingActivity.class);
                context.startActivity(intent);
                break;
        }

    }
}
