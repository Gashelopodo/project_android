package es.mxcircuit.mxcircuit.https;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import es.mxcircuit.mxcircuit.activities.ProfileCircuitActivity;
import es.mxcircuit.mxcircuit.models.Circuit;
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
 * Created by gashelopodo on 27/7/17.
 */

public class GetCircuit extends AsyncTask<String, Void, Circuit> {

    private Context context;
    private Gson gson;

    public GetCircuit(Context context) {
        this.context = context;
    }

    @Override
    protected Circuit doInBackground(String... strings) {
        Circuit circuit = null;
        String circuit_id = strings[0];
        String urlParameters;
        String url_image = null;
        URL object_url = null;
        HttpURLConnection http = null;
        int response = -1;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        Log.d("MENSAJE", Constants.URL_SERVER+Constants.API.URL+Constants.API.DATA_CIRCUIT);
        try {

            object_url = new URL(Constants.URL_SERVER+Constants.API.URL+Constants.API.DATA_CIRCUIT);
            http = (HttpURLConnection) object_url.openConnection();
            http.setReadTimeout(10000);
            http.setConnectTimeout(15000);
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            Prefs prefs = new Prefs(context);
            String token = prefs.getToken();
            urlParameters = "circuit_id="+circuit_id;
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
                Log.d("MENSAJE", response_string);
                circuit = gson.fromJson(response_string, new TypeToken<Circuit>(){}.getType());

            } else {
                Log.d("MENSAJE", "Algo fue mal");
            }

        }catch (Throwable t){

            Log.d("MENSAJE", "Algo fue mal" + t);

        }finally {
            http.disconnect();
        }

        return circuit;
    }

    @Override
    protected void onPostExecute(Circuit circuit) {
        String circuitJson = gson.toJson(circuit);
        ProfileCircuitActivity activity = (ProfileCircuitActivity) context;
        activity.loadInit(circuitJson);
        super.onPostExecute(circuit);
    }
}
