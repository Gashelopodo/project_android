package es.mxcircuit.mxcircuit.listeners;

import android.content.Context;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import es.mxcircuit.mxcircuit.R;
import es.mxcircuit.mxcircuit.activities.SettingActivity;
import es.mxcircuit.mxcircuit.api.Response;
import es.mxcircuit.mxcircuit.https.SetSetting;
import es.mxcircuit.mxcircuit.models.Setting;
import es.mxcircuit.mxcircuit.utils.Prefs;

import java.util.ArrayList;

/**
 * Created by gashelopodo on 25/7/17.
 */

public class SwitchSettingListener implements CompoundButton.OnCheckedChangeListener {

    private Context context;
    private ArrayList<Setting> settings;
    private Setting setting;

    public SwitchSettingListener(Context context) {
        this.context = context;
    }

    public SwitchSettingListener(Context context, ArrayList<Setting> settings) {
        this.context = context;
        this.settings = settings;
    }

    public SwitchSettingListener(Context context, Setting setting) {
        this.context = context;
        this.setting = setting;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        String checked = "0";
        if(b) checked = "1";
        Prefs prefs = new Prefs(context);

        if(compoundButton.getId() == R.id.switchAll){

            if(b) prefs.setAllSetting(1);
            else prefs.setAllSetting(2);

            Log.d("MENSAJE", "ALL: "+prefs.getAllSetting());

            for(Setting setting : this.settings){
                setting.setSend(checked);
            }

            SetSetting setSetting = new SetSetting(context, this);
            setSetting.execute();

            SettingActivity activity = (SettingActivity) context;
            activity.reloadSetting(this.settings);

        }else{
            prefs.setAllSetting(0);
            Log.d("MENSAJE", "circuito seleccionado:" + this.setting.getName_circuit());

            this.setting.setSend(checked);
            SetSetting setSetting = new SetSetting(context, this);
            setSetting.execute(this.setting);

        }

    }

    public void responseChangeSetting(Response response){

        Toast.makeText(context, response.getMessage_error(), Toast.LENGTH_SHORT).show();

    }

}
