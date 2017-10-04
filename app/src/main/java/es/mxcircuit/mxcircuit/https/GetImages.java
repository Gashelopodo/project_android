package es.mxcircuit.mxcircuit.https;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import es.mxcircuit.mxcircuit.activities.ProfileCircuitActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gashelopodo on 19/7/17.
 */

public class GetImages extends AsyncTask<String, Void, Bitmap[]> {

    private Context context;

    public GetImages(Context context) {
        this.context = context;
    }

    @Override
    protected Bitmap[] doInBackground(String... url) {

        Bitmap[] bitmap = new Bitmap[url.length];
        String url_image = null;
        URL object_url = null;
        HttpURLConnection http = null;
        int response = -5;
        InputStream inputStream = null;


        try {

            for(int i = 0; i < url.length; i++) {

                url_image = url[i];
                object_url = new URL(url_image);
                http = (HttpURLConnection) object_url.openConnection();

                response = http.getResponseCode();
                if (response == HttpURLConnection.HTTP_OK) {
                    Log.d("MENSAJE", "Todo OK");
                    inputStream = http.getInputStream();
                    bitmap[i] = BitmapFactory.decodeStream(inputStream);
                } else {
                    Log.d("MENSAJE", "Algo fue mal");
                }

            }

        }catch (Throwable t){

            Log.d("MENSAJE", "Algo fue mal" + t);

        }finally {
            http.disconnect();
        }

        return bitmap;

    }

    @Override
    protected void onPostExecute(Bitmap[] bitmaps) {
        if(bitmaps != null){
            ProfileCircuitActivity profileCircuitActivity = (ProfileCircuitActivity) context;
            profileCircuitActivity.loadImages(bitmaps);
        }
        super.onPostExecute(bitmaps);
    }
}
