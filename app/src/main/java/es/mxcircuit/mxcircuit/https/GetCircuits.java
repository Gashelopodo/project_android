package es.mxcircuit.mxcircuit.https;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import es.mxcircuit.mxcircuit.activities.ListCircuitsActivity;
import es.mxcircuit.mxcircuit.models.Circuit;
import es.mxcircuit.mxcircuit.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Gashe on 11/6/17.
 */

public class GetCircuits extends AsyncTask<Void, Void, ArrayList<Circuit>> {

    private Context context;

    public GetCircuits(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Circuit> doInBackground(Void... voids) {


        ArrayList<Circuit> circuits = null;
        String url_image = null;
        URL object_url = null;
        HttpURLConnection http = null;
        int response = -1;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        Log.d("MENSAJE", Constants.URL_SERVER+Constants.API.URL+Constants.API.CIRCUIT_LIST);
        try {

            object_url = new URL(Constants.URL_SERVER+Constants.API.URL+Constants.API.CIRCUIT_LIST);
            http = (HttpURLConnection) object_url.openConnection();

            response = http.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                Log.d("MENSAJE", "Todo OK");
                Gson gson = new Gson();
                inputStream = http.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, Charset.forName("ISO-8859-15"));
                bufferedReader = new BufferedReader(inputStreamReader);
                String response_string = bufferedReader.readLine();

                circuits = gson.fromJson(response_string, new TypeToken<ArrayList<Circuit>>(){}.getType());
                Log.d("MENSAJE", response_string);
            } else {
                Log.d("MENSAJE", "Algo fue mal");
            }

        }catch (Throwable t){
            Log.d("MENSAJE", "Algo fue mal" + t);

        }finally {
            http.disconnect();
        }

        return circuits;


    }



    @Override
    protected void onPostExecute(ArrayList<Circuit> circuits) {
        if(circuits != null) {
            ListCircuitsActivity listCircuitsActivity = (ListCircuitsActivity) context;
            listCircuitsActivity.loadCircuit(circuits);
        }
        super.onPostExecute(circuits);
    }



}
