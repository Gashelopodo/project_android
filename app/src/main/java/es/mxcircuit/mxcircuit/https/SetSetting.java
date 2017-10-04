package es.mxcircuit.mxcircuit.https;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import es.mxcircuit.mxcircuit.api.Response;
import es.mxcircuit.mxcircuit.listeners.SwitchSettingListener;
import es.mxcircuit.mxcircuit.models.Setting;
import es.mxcircuit.mxcircuit.utils.Constants;
import es.mxcircuit.mxcircuit.utils.Prefs;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by gashelopodo on 25/7/17.
 */

public class SetSetting extends AsyncTask<Setting, Void, Response> {

    private Context context;
    private SwitchSettingListener switchSettingListener;

    public SetSetting(Context context, SwitchSettingListener switchSettingListener) {
        this.context = context;
        this.switchSettingListener = switchSettingListener;
    }

    @Override
    protected Response doInBackground(Setting... settings) {
        Response respuesta = null;
        String urlParameters;
        String url_image = null;
        URL object_url = null;
        HttpURLConnection http = null;
        int response = -1;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        Log.d("MENSAJE", Constants.URL_SERVER+Constants.API.URL+Constants.API.CHANGE_SETTING);
        try {

            object_url = new URL(Constants.URL_SERVER+Constants.API.URL+Constants.API.CHANGE_SETTING);
            http = (HttpURLConnection) object_url.openConnection();
            http.setReadTimeout(10000);
            http.setConnectTimeout(15000);
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            Prefs prefs = new Prefs(context);
            int all = prefs.getAllSetting();
            Gson gson = new Gson();
            Log.d("MENSAJE", "ALL: "+all);
            if(all <= 0){
                String jsonSetting = gson.toJson(settings[0]);
                urlParameters = "setting="+jsonSetting;
            }else{
                Log.d("MENSAJE", "ENTRO EN ALL");
                String token = prefs.getToken();
                urlParameters = "all="+all+"&token="+token;
            }

            Log.d("MENSAJE", urlParameters);
            //Send request
            DataOutputStream wr = new DataOutputStream(http.getOutputStream());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();

            response = http.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                Log.d("MENSAJE", "Todo OK");
                gson = new Gson();
                inputStream = http.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, Charset.forName("ISO-8859-15"));
                bufferedReader = new BufferedReader(inputStreamReader);
                String response_string = bufferedReader.readLine();

                respuesta = gson.fromJson(response_string, new TypeToken<Response>(){}.getType());
                Log.d("MENSAJE", response_string);
            } else {
                respuesta = new Response(Response.ERROR, Response.ERROR_GENERAL);
                Log.d("MENSAJE", "Algo fue mal");
            }

        }catch (Throwable t){
            respuesta = new Response(Response.ERROR, Response.ERROR_GENERAL);
            Log.d("MENSAJE", "Algo fue mal" + t);

        }finally {
            http.disconnect();
        }

        return respuesta;
    }

    @Override
    protected void onPostExecute(Response response) {
        switchSettingListener.responseChangeSetting(response);
        super.onPostExecute(response);
    }

}
