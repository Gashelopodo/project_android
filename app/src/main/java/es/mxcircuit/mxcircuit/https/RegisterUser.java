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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Gashe on 21/5/17.
 */

public class RegisterUser extends AsyncTask<Register, Void, Response> {

    private String URL_REGISTER_USER;
    private Register data;
    private String POST_PARAMS;
    private Context context;

    public RegisterUser(Context context) {
        this.context = context;
    }

    @Override
    protected Response doInBackground(Register... registers) {

        String response_string = null;
        Response response = null;
        URL_REGISTER_USER = Constants.URL_SERVER+Constants.API.URL+Constants.API.REGISTER_USER;
        URL url = null;
        HttpURLConnection http = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        Gson gson = new Gson();
        data = registers[0];

        POST_PARAMS = "data="+gson.toJson(data);

        try {

            url = new URL(URL_REGISTER_USER);
            http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("POST");

            outputStream = http.getOutputStream();
            outputStream.write(POST_PARAMS.getBytes());
            outputStream.flush();
            outputStream.close();

            if(http.getResponseCode() == HttpURLConnection.HTTP_OK){

                inputStream = http.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                bufferedReader = new BufferedReader(inputStreamReader);
                response_string = bufferedReader.readLine();

                response = gson.fromJson(response_string, Response.class);

                Log.d("MENSAJE", response_string);

            }

        } catch (Throwable t) {
            Log.d(getClass().getCanonicalName(), "Error: " + t);
            response = new Response(Response.ERROR, Response.ERROR_GENERAL);
        }finally {
            http.disconnect();
        }

        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        LoginActivity loginActivity = (LoginActivity) context;
        loginActivity.isLogin(response, data);
    }


}
