package es.mxcircuit.mxcircuit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import es.mxcircuit.mxcircuit.R;
import es.mxcircuit.mxcircuit.utils.Dialogs;
import es.mxcircuit.mxcircuit.utils.NetworkStatus;
import es.mxcircuit.mxcircuit.utils.Utils;

public class NetworkOff extends AppCompatActivity implements View.OnClickListener {

    LinearLayout load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_off);

        load = (LinearLayout) findViewById(R.id.loading);

        Button button = (Button) findViewById(R.id.buttonReintentar);
        button.setOnClickListener(this);

    }

    public void checkStatus(){

        NetworkStatus networkStatus = new NetworkStatus(this);
        int status = networkStatus.getStatus();

        if(status != networkStatus.TYPE_NOT_CONNECTED){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            Utils.alert(this, NetworkStatus.ERROR_NO_CONNECTED, Dialogs.ERROR_TITLE_GENERAL);
        }

    }

    @Override
    public void onClick(View view) {
        checkStatus();
    }
}
