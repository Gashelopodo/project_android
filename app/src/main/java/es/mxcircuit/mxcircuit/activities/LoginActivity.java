package es.mxcircuit.mxcircuit.activities;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import es.mxcircuit.mxcircuit.api.Register;
import es.mxcircuit.mxcircuit.api.Response;
import es.mxcircuit.mxcircuit.https.CheckLogin;
import es.mxcircuit.mxcircuit.listeners.ListenerButtonLogin;

import es.mxcircuit.mxcircuit.services.RegistrationTokenNotification;
import es.mxcircuit.mxcircuit.utils.Dialogs;
import es.mxcircuit.mxcircuit.utils.NetworkStatus;
import es.mxcircuit.mxcircuit.utils.Prefs;
import es.mxcircuit.mxcircuit.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    Prefs prefs;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(es.mxcircuit.mxcircuit.R.layout.activity_login);

        //comprobamos internet
        if(NetworkStatus.getStatusFinal(this) == NetworkStatus.TYPE_NOT_CONNECTED){
            Utils.alertNetwork(this, NetworkStatus.ERROR_NO_CONNECTED, Dialogs.ERROR_TITLE_GENERAL);
        }else {

            //lanzamos servicio para coger token GCM
            Intent intent = new Intent(this, RegistrationTokenNotification.class);
            startService(intent);

            //verificamos la sesión
            prefs = new Prefs(this);
            boolean isSession = prefs.getSession();

            Log.d("MENSAJE", "UUID: " + Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));

            if (!isSession) {

                //miramos si ya existe este dispositivo en la bbdd
                String device_uuid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                Register register = new Register("0", "", "", device_uuid, "", "", "", "", "", "", "", "");

                CheckLogin checkLogin = new CheckLogin(this);
                checkLogin.execute(register);

                //instanciamos elementos del layout
                EditText name = (EditText) findViewById(es.mxcircuit.mxcircuit.R.id.name);
                EditText email = (EditText) findViewById(es.mxcircuit.mxcircuit.R.id.email);
                Button buttonLogin = (Button) findViewById(es.mxcircuit.mxcircuit.R.id.buttonLogin);

                //creamos evento para escuchar al botón login
                buttonLogin.setOnClickListener(new ListenerButtonLogin(this));
            } else {

                //Log.d("MENSAJE", "REGISTER "+prefs.getRegister().getDevice_token());

                intent = new Intent(this, ListCircuitsActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    public void loadingButton(boolean enabled){
        Button button = (Button) findViewById(es.mxcircuit.mxcircuit.R.id.buttonLogin);
        ProgressBar progressBar = (ProgressBar) findViewById(es.mxcircuit.mxcircuit.R.id.loading);
        if(enabled) {
            progressBar.setVisibility(ProgressBar.VISIBLE);
            button.setText("");
        }else{
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            button.setText("ENTRAR");
        }
    }

    public void isLogin(Response response, Register register){
        //quitamos loading
        loadingButton(false);
        //verificamos respuesta del servidor
        if(null != response && response.getCode_error() == Response.OK){
            register.setId(response.getMessage_error());
            // seteamos la session a true
            prefs.setSession(true);
            // seteamos register
            prefs.setRegister(register);
            if(prefs.getToken() == ""){
                prefs.setToken(register.getDevice_token());
            }
            // vamos al listado de circuitos
            intent = new Intent(this, ListCircuitsActivity.class);
            startActivity(intent);
            finish();
        }else{
            // mostramos error
            Utils.alert(this, response.getMessage_error(), getResources().getString(es.mxcircuit.mxcircuit.R.string.error_title));
        }
    }

}
