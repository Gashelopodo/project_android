package es.mxcircuit.mxcircuit.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import es.mxcircuit.mxcircuit.listeners.SwitchSettingListener;
import es.mxcircuit.mxcircuit.models.Setting;
import es.mxcircuit.mxcircuit.utils.Prefs;

/**
 * Created by gashelopodo on 25/7/17.
 */

public class SettingViewHolder extends RecyclerView.ViewHolder {

    private TextView name_circuit;
    private Switch switch_circuit;
    private LinearLayout box_city;
    private TextView city;
    private Context context;

    public SettingViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        name_circuit = (TextView) itemView.findViewById(es.mxcircuit.mxcircuit.R.id.name_circuit);
        switch_circuit = (Switch) itemView.findViewById(es.mxcircuit.mxcircuit.R.id.switchNotification);
        box_city = (LinearLayout) itemView.findViewById(es.mxcircuit.mxcircuit.R.id.boxCity);
        city = (TextView) itemView.findViewById(es.mxcircuit.mxcircuit.R.id.nameCity);
    }

    public void loadDataHolder(Setting setting){

        name_circuit.setText(setting.getName_circuit());
        switch_circuit.setChecked((Integer.parseInt(setting.getSend()) == 1) ? true : false);
        int id_city_int = Integer.parseInt(setting.getId_city());
        box_city.setVisibility(LinearLayout.GONE);

        // comprobamos cabecera ciudad
        Prefs prefs = new Prefs(context);
        int id_city = prefs.getCitySetting();

        if(id_city != 0){
            if(id_city != id_city_int){
                prefs.setCitySetting(id_city_int);
                city.setText(setting.getName_city());
                box_city.setVisibility(LinearLayout.VISIBLE);
            }
        }else{
            city.setText(setting.getName_city());
            box_city.setVisibility(LinearLayout.VISIBLE);
            prefs.setCitySetting(id_city_int);
        }

        // listener para switch
        switch_circuit.setOnCheckedChangeListener(new SwitchSettingListener(context, setting));

    }

}
