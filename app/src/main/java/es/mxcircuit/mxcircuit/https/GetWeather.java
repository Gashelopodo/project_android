package es.mxcircuit.mxcircuit.https;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import es.mxcircuit.mxcircuit.activities.ProfileCircuitActivity;
import es.mxcircuit.mxcircuit.models.Circuit;
import es.mxcircuit.mxcircuit.models.Weather;
import es.mxcircuit.mxcircuit.utils.Constants;
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
 * Created by gashelopodo on 27/7/17.
 */

public class GetWeather extends AsyncTask<Circuit, Void, Weather> {

    private Context context;

    public GetWeather(Context context) {
        this.context = context;
    }

    @Override
    protected Weather doInBackground(Circuit... circuits) {
        Weather weather = null;
        String urlParameters;
        String url_image = null;
        URL object_url = null;
        HttpURLConnection http = null;
        int response = -1;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        Log.d("MENSAJE", Constants.URL_SERVER+Constants.API.URL+Constants.API.WEATHER);
        try {

            object_url = new URL(Constants.URL_SERVER+Constants.API.URL+Constants.API.WEATHER);
            http = (HttpURLConnection) object_url.openConnection();
            http.setReadTimeout(10000);
            http.setConnectTimeout(15000);
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            Gson gson = new Gson();
            String circuitJson = gson.toJson(circuits[0]);

            urlParameters = "circuit="+circuitJson;
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
                weather = gson.fromJson(response_string, new TypeToken<Weather>(){}.getType());

            } else {
                Log.d("MENSAJE", "Algo fue mal");
            }

        }catch (Throwable t){

            Log.d("MENSAJE", "Algo fue mal" + t);

        }finally {
            http.disconnect();
        }

        return weather;
    }

    @Override
    protected void onPostExecute(Weather weather) {
        if(weather != null) {
            ProfileCircuitActivity activity = (ProfileCircuitActivity) context;
            activity.loadWeather(weather);
        }
        super.onPostExecute(weather);
    }
}
