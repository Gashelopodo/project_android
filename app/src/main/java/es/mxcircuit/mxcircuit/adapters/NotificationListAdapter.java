package es.mxcircuit.mxcircuit.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.mxcircuit.mxcircuit.models.Notification;
import es.mxcircuit.mxcircuit.viewholders.NotificationListViewHolder;

import java.util.ArrayList;

/**
 * Created by gashelopodo on 18/7/17.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListViewHolder> {

    private ArrayList<Notification> notifications;
    private Context context;

    public NotificationListAdapter(ArrayList<Notification> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    @Override
    public NotificationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NotificationListViewHolder notificationListViewHolder = null;
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(es.mxcircuit.mxcircuit.R.layout.row_notification, parent, false);
        notificationListViewHolder = new NotificationListViewHolder(view);
        return notificationListViewHolder;
    }

    @Override
    public void onBindViewHolder(NotificationListViewHolder holder, int position) {
        Notification notification = (Notification) notifications.get(position);
        notification.setPosition(position);
        holder.loadDataHolder(notification);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
