package es.mxcircuit.mxcircuit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.mxcircuit.mxcircuit.activities.SettingActivity;
import es.mxcircuit.mxcircuit.models.Setting;
import es.mxcircuit.mxcircuit.viewholders.SettingViewHolder;

import java.util.ArrayList;

/**
 * Created by gashelopodo on 25/7/17.
 */

public class SettingAdapter extends RecyclerView.Adapter<SettingViewHolder> {

    private ArrayList<Setting> settings;
    private Context context;

    public SettingAdapter(ArrayList<Setting> settings, Context context) {
        this.settings = settings;
        this.context = context;
    }

    @Override
    public SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SettingViewHolder settingViewHolder = null;
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(es.mxcircuit.mxcircuit.R.layout.row_setting, parent, false);
        settingViewHolder = new SettingViewHolder(view, context);
        return settingViewHolder;
    }

    @Override
    public void onBindViewHolder(SettingViewHolder holder, int position) {
        Setting setting = (Setting) settings.get(position);
        setting.setPosition(position);
        holder.loadDataHolder(setting);
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

    public void clear(ArrayList<Setting> settings){
        int size = settings.size();
        notifyItemRangeRemoved(0, size);
        SettingActivity activity = (SettingActivity) context;
        activity.loadSetting(settings);
    }

}
