package es.mxcircuit.mxcircuit.https;

import android.os.AsyncTask;

import es.mxcircuit.mxcircuit.models.Circuit;

import java.util.ArrayList;

/**
 * Created by gashelopodo on 18/7/17.
 */

public class GetDistanceMatrix extends AsyncTask<ArrayList<Circuit>, Void, ArrayList<Circuit>> {

    @Override
    protected ArrayList<Circuit> doInBackground(ArrayList<Circuit>... arrayLists) {

        for (Circuit circuit: arrayLists[0]) {

        }

        return arrayLists[0];

    }

    @Override
    protected void onPostExecute(ArrayList<Circuit> circuits) {
        super.onPostExecute(circuits);
    }
}
