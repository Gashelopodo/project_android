package es.mxcircuit.mxcircuit.https;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import es.mxcircuit.mxcircuit.activities.LoginActivity;
import es.mxcircuit.mxcircuit.api.Register;
import es.mxcircuit.mxcircuit.api.Response;
import es.mxcircuit.mxcircuit.utils.Constants;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by Gashe on 15/5/17.
 */

public class CheckLogin extends AsyncTask<Register, Void, Register> {

    private String URL_CHECK_LOGIN;
    private Register data;
    private String POST_PARAMS;
    private Context context;

    public CheckLogin(Context context) {
        this.context = context;
    }

    @Override
    protected Register doInBackground(Register... registers) {

        Register register = registers[0];
        String urlParameters;
        String url_image = null;
        URL object_url = null;
        HttpURLConnection http = null;
        int response = -1;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        Log.d("MENSAJE", Constants.URL_SERVER+Constants.API.URL+Constants.API.USER_EXIST);
        try {

            object_url = new URL(Constants.URL_SERVER+Constants.API.URL+Constants.API.USER_EXIST);
            http = (HttpURLConnection) object_url.openConnection();
            http.setReadTimeout(10000);
            http.setConnectTimeout(15000);
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            Gson gson = new Gson();
            String jsonRegister = gson.toJson(register);

            urlParameters = "register="+jsonRegister;
            Log.d("MENSAJE", urlParameters);
            //Send request
            DataOutputStream wr = new DataOutputStream(http.getOutputStream());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();

            response = http.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                Log.d("MENSAJE", "Todo OK");
                inputStream = http.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, Charset.forName("ISO-8859-15"));
                bufferedReader = new BufferedReader(inputStreamReader);
                String response_string = bufferedReader.readLine();
                Log.d("MENSAJE", response_string);
                register = gson.fromJson(response_string, Register.class);

            } else {
                Log.d("MENSAJE", "Algo fue mal");
            }

        }catch (Throwable t){

            Log.d("MENSAJE", "Algo fue mal" + t);

        }finally {
            http.disconnect();
        }

        return register;
    }

    @Override
    protected void onPostExecute(Register register) {
        Response response;
        if(register != null && Integer.parseInt(register.getId()) != 0){
            response = new Response(1, "Ya existe el dispositivo. Logeamos.");
            LoginActivity activity = (LoginActivity) context;
            activity.isLogin(response, register);
        }
        super.onPostExecute(register);
    }
}
