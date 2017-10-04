package es.mxcircuit.mxcircuit.https;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import es.mxcircuit.mxcircuit.api.Response;
import es.mxcircuit.mxcircuit.listeners.ListenerVoteCircuit;
import es.mxcircuit.mxcircuit.models.Review;
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
 * Created by gashelopodo on 24/7/17.
 */

public class SetReview extends AsyncTask<Review, Void, Response> {

    private Context context;
    private ListenerVoteCircuit listenerVoteCircuit;

    public SetReview(Context context, ListenerVoteCircuit listenerVoteCircuit) {
        this.context = context;
        this.listenerVoteCircuit = listenerVoteCircuit;
    }

    @Override
    protected Response doInBackground(Review... reviews) {
        Response respuesta = null;
        String urlParameters;
        String url_image = null;
        URL object_url = null;
        HttpURLConnection http = null;
        int response = -1;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        Log.d("MENSAJE", Constants.URL_SERVER+Constants.API.URL+Constants.API.VOTE_CIRCUIT);
        try {

            object_url = new URL(Constants.URL_SERVER+Constants.API.URL+Constants.API.VOTE_CIRCUIT);
            http = (HttpURLConnection) object_url.openConnection();
            http.setReadTimeout(10000);
            http.setConnectTimeout(15000);
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            Prefs prefs = new Prefs(context);
            String token = prefs.getToken();
            Gson gson = new Gson();
            String jsonReview = gson.toJson(reviews[0]);

            urlParameters = "token="+token+"&review="+jsonReview;
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
        listenerVoteCircuit.getResponse(response);
        super.onPostExecute(response);
    }


}
