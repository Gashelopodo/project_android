package es.mxcircuit.mxcircuit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import es.mxcircuit.mxcircuit.R;
import es.mxcircuit.mxcircuit.adapters.SettingAdapter;
import es.mxcircuit.mxcircuit.api.Register;
import es.mxcircuit.mxcircuit.https.GetSetting;
import es.mxcircuit.mxcircuit.listeners.SwitchSettingListener;
import es.mxcircuit.mxcircuit.models.Setting;
import es.mxcircuit.mxcircuit.utils.Dialogs;
import es.mxcircuit.mxcircuit.utils.NetworkStatus;
import es.mxcircuit.mxcircuit.utils.Prefs;
import es.mxcircuit.mxcircuit.utils.Utils;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout loadLayout;
    private SettingAdapter settingAdapter;
    private ArrayList<Setting> settingsAux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if(NetworkStatus.getStatusFinal(this) == NetworkStatus.TYPE_NOT_CONNECTED){
            Utils.alertNetwork(this, NetworkStatus.ERROR_NO_CONNECTED, Dialogs.ERROR_TITLE_GENERAL);
        }else {

            // escuchamos menu lateral
            Utils.loadNavigationListener(this);

            // obtener settings
            GetSetting getSetting = new GetSetting(this);
            getSetting.execute();

            // load data user
            Prefs prefs = new Prefs(this);
            Register register = prefs.getRegister();

            TextView nameUser = (TextView) findViewById(R.id.nameUser);
            nameUser.setText(register.getName());
            TextView emailUser = (TextView) findViewById(R.id.emailUser);
            emailUser.setText(register.getEmail());

        }

    }

    public void loadSetting(ArrayList<Setting> settings){

        settingsAux = settings;
        recyclerView = (RecyclerView) findViewById(R.id.setting_notification_list);
        recyclerView.setNestedScrollingEnabled(false);

        if(settings != null) {

            loadLayout = (LinearLayout) findViewById(R.id.loadSetting);
            loadLayout.setVisibility(LinearLayout.INVISIBLE);

            settingAdapter = new SettingAdapter(settings, this);
            recyclerView.setAdapter(settingAdapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            // escuchar switch all
            Switch switchAll = (Switch) findViewById(R.id.switchAll);
            switchAll.setOnCheckedChangeListener(new SwitchSettingListener(this, settings));


        }else{
            Log.d("MENSAJE", "settings ES NULL");
        }

    }

    public void reloadSetting(ArrayList<Setting> settings){
        Prefs prefs = new Prefs(this);
        prefs.setCitySetting(0);
        settingsAux  = settings;
        settingAdapter.clear(settings);
    }

}
