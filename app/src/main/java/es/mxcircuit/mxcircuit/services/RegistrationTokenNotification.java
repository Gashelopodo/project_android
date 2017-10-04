package es.mxcircuit.mxcircuit.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import es.mxcircuit.mxcircuit.api.Response;
import es.mxcircuit.mxcircuit.utils.Constants;
import es.mxcircuit.mxcircuit.utils.Prefs;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

public class RegistrationTokenNotification extends IntentService {

    public RegistrationTokenNotification() {
        super("RegistrationTokenNotification");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("MENSAJE", "ENTRO EN INTENTSERVICE TOKEN");
        Prefs prefs = new Prefs(this);
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken(getString(es.mxcircuit.mxcircuit.R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            if(prefs.getSession()){
                if(!prefs.getToken().equals(token) && prefs.getToken() != ""){
                    setToken(prefs.getToken(), token);
                    prefs.setToken(token);
                }
            }else{
                prefs.setToken(token);
                Log.d("MENSAJE", "CAMBIAMOS TOKENSERVICE");
            }
            Log.d("MENSAJE", "TOKENSERVICE "+prefs.getToken());

        }catch (Exception e){
            Log.d("MENSAJE", "HA FALLADO INTENTSERVICE TOKEN" + prefs.getToken());
            e.printStackTrace();
        }


    }

    private void setToken(String lastToken, String newToken){
        Response respuesta = null;
        String urlParameters;
        String url_image = null;
        URL object_url = null;
        HttpURLConnection http = null;
        int response = -1;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        Log.d("MENSAJE", Constants.URL_SERVER+Constants.API.URL+Constants.API.CHANGE_TOKEN);
        try {

            object_url = new URL(Constants.URL_SERVER+Constants.API.URL+Constants.API.CHANGE_TOKEN);
            http = (HttpURLConnection) object_url.openConnection();
            http.setReadTimeout(10000);
            http.setConnectTimeout(15000);
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            urlParameters = "lastToken="+lastToken+"&newToken="+newToken;
            Log.d("MENSAJE", urlParameters);
            //Send request
            DataOutputStream wr = new DataOutputStream(http.getOutputStream());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();

            response = http.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                Log.d("MENSAJE", "Todo OK");
                Gson gson = new Gson();
                inputStream = http.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, Charset.forName("ISO-8859-15"));
                bufferedReader = new BufferedReader(inputStreamReader);
                String response_string = bufferedReader.readLine();
                respuesta = gson.fromJson(response_string, new TypeToken<Response>(){}.getType());
                Log.d("MENSAJE", response_string);

            } else {
                Log.d("MENSAJE", "Algo fue mal");
            }

        }catch (Throwable t){

            Log.d("MENSAJE", "Algo fue mal" + t);

        }finally {
            http.disconnect();
        }

        if(respuesta != null){
            Log.d("MENSAJE", respuesta.getMessage_error());
        }

    }

}
