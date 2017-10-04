package es.mxcircuit.mxcircuit.https;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import es.mxcircuit.mxcircuit.activities.NotificationsActivity;
import es.mxcircuit.mxcircuit.models.Notification;
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
import java.util.ArrayList;

/**
 * Created by gashelopodo on 18/7/17.
 */

public class GetNotificationsUser extends AsyncTask<Void, Void, ArrayList<Notification>> {

    private Context context;

    public GetNotificationsUser(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Notification> doInBackground(Void... voids) {

        ArrayList<Notification> notifications = null;
        String urlParameters;
        String url_image = null;
        URL object_url = null;
        HttpURLConnection http = null;
        int response = -1;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        Log.d("MENSAJE", Constants.URL_SERVER+Constants.API.URL+Constants.API.NOTIFICATION_USER_LIST);
        try {

            object_url = new URL(Constants.URL_SERVER+Constants.API.URL+Constants.API.NOTIFICATION_USER_LIST);
            http = (HttpURLConnection) object_url.openConnection();
            http.setReadTimeout(10000);
            http.setConnectTimeout(15000);
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            Prefs prefs = new Prefs(context);
            String token = prefs.getToken();
            urlParameters = "token="+token;
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

                notifications = gson.fromJson(response_string, new TypeToken<ArrayList<Notification>>(){}.getType());
                Log.d("MENSAJE", response_string);
            } else {
                Log.d("MENSAJE", "Algo fue mal");
            }

        }catch (Throwable t){

            Log.d("MENSAJE", "Algo fue mal" + t);

        }finally {
            http.disconnect();
        }

        return notifications;

    }

    @Override
    protected void onPostExecute(ArrayList<Notification> notifications) {
        if(notifications != null) {
            NotificationsActivity notificationsActivity = (NotificationsActivity) context;
            notificationsActivity.loadNotifications(notifications);
        }
        super.onPostExecute(notifications);
    }
}
