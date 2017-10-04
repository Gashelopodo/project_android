package es.mxcircuit.mxcircuit.listeners;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

/**
 * Created by Gashe on 12/6/17.
 */

public class ListenerMenu implements View.OnClickListener {

    private Context context;

    public ListenerMenu(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        Activity activity = (Activity)context;
        DrawerLayout drawerLayout = (DrawerLayout) activity.findViewById(es.mxcircuit.mxcircuit.R.id.myDrawer);
        drawerLayout.openDrawer(Gravity.START);
    }
}
