package es.mxcircuit.mxcircuit.listeners;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;

import es.mxcircuit.mxcircuit.BuildConfig;
import es.mxcircuit.mxcircuit.activities.LoginActivity;
import es.mxcircuit.mxcircuit.api.Register;
import es.mxcircuit.mxcircuit.api.Response;
import es.mxcircuit.mxcircuit.https.RegisterUser;
import es.mxcircuit.mxcircuit.utils.Prefs;
import es.mxcircuit.mxcircuit.utils.Utils;

/**
 * Created by Gashe on 15/5/17.
 */

public class ListenerButtonLogin implements View.OnClickListener {

    private Context context;
    private String name;
    private String email;
    private String device_uuid;
    private String device_token;
    private String device_model;
    private String device_platform;
    private String device_version;
    private Register data;

    public ListenerButtonLogin(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {

        //cargamos loading
        LoginActivity loginActivity = (LoginActivity) context;
        loginActivity.loadingButton(true);

        // obtenemos name y email introducidos
        Activity activity = (Activity) context;
        EditText nameInput = (EditText) activity.findViewById(es.mxcircuit.mxcircuit.R.id.name);
        name = nameInput.getText().toString();
        EditText emailInput = (EditText) activity.findViewById(es.mxcircuit.mxcircuit.R.id.email);
        email = emailInput.getText().toString();

        if(Utils.isEmail(email)) {
            if(!name.isEmpty()) {
                // iniciamos el servicio de sistema telephonyManager para recoger datos del dispositivo
                device_uuid = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                device_model = Build.MODEL;
                device_version = BuildConfig.VERSION_NAME;
                device_platform = "Android";

                // recogemos token
                Prefs prefs = new Prefs(context);
                device_token = prefs.getToken();

                data = new Register("0", name, email, device_uuid, device_model, "", device_platform, device_version, "", device_token, "", "");
                RegisterUser registerUser = (RegisterUser) new RegisterUser(context);
                registerUser.execute(data);
            }else{
                Response response = new Response(0, "El nombre no puede estar vacio");
                loginActivity.isLogin(response, data);
            }
        }else{
            Response response = new Response(0, "El email no es correcto");
            loginActivity.isLogin(response, null);
        }

    }

}
