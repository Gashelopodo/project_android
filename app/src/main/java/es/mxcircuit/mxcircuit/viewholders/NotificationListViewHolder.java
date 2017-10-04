package es.mxcircuit.mxcircuit.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.mxcircuit.mxcircuit.R;
import es.mxcircuit.mxcircuit.activities.ProfileCircuitActivity;
import es.mxcircuit.mxcircuit.models.Notification;
import es.mxcircuit.mxcircuit.utils.Constants;
import es.mxcircuit.mxcircuit.utils.Utils;

import com.google.gson.Gson;

/**
 * Created by gashelopodo on 18/7/17.
 */

public class NotificationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private TextView name_circuit;
    private TextView description;
    private TextView time;
    private LinearLayout boxNotification;
    private Notification notificationAux;

    public NotificationListViewHolder(View itemView) {
        super(itemView);
        name_circuit = (TextView) itemView.findViewById(R.id.name_circuit);
        description = (TextView) itemView.findViewById(R.id.description);
        boxNotification = (LinearLayout) itemView.findViewById(R.id.box_notification);
        context = itemView.getContext();
        time = (TextView) itemView.findViewById(R.id.time);
    }

    public void loadDataHolder(Notification notification){
        notificationAux = notification;
        name_circuit.setText(notification.getName_circuit());
        description.setText(notification.getDescription());
        time.setText(Utils.convertDate(notification.getCreated_at()));
        boxNotification.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.d("MENSAJE", "CLICK: "+notificationAux.getName_circuit());
        if(Integer.parseInt(notificationAux.getCircuit_id()) != 55) {
            Intent intent = new Intent(context, ProfileCircuitActivity.class);
            Gson gson = new Gson();
            intent.putExtra(Constants.BUNDLE.ID_CIRCUIT, notificationAux.getCircuit_id());
            context.startActivity(intent);
        }
    }
}
