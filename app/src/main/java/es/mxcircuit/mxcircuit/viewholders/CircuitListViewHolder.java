package es.mxcircuit.mxcircuit.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.mxcircuit.mxcircuit.R;
import es.mxcircuit.mxcircuit.activities.ProfileCircuitActivity;
import es.mxcircuit.mxcircuit.models.Circuit;
import es.mxcircuit.mxcircuit.utils.Constants;
import com.google.gson.Gson;

/**
 * Created by Gashe on 11/6/17.
 */

public class CircuitListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    private Context context;
    private TextView name_circuit;
    private TextView km_circuit;
    private Circuit circuitAux;
    private LinearLayout boxCircuit;

    public CircuitListViewHolder(View itemView) {
        super(itemView);

        name_circuit = (TextView) itemView.findViewById(R.id.name_circuit);
        km_circuit = (TextView) itemView.findViewById(R.id.km_circuit);
        boxCircuit = (LinearLayout) itemView.findViewById(R.id.box_circuit);
        context = itemView.getContext();

    }

    public void loadDataHolder(Circuit circuit){

        circuitAux = circuit;
        name_circuit.setText(circuit.getName());
        String distanceInKm = String.format("%.02f", circuit.getDistanceInKm());
        km_circuit.setText(distanceInKm+" km");
        boxCircuit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        Log.d("MENSAJE", "CLICK: "+circuitAux.getName());
        Intent intent = new Intent(context, ProfileCircuitActivity.class);
        Gson gson = new Gson();
        String circuitoJson = gson.toJson(circuitAux);
        intent.putExtra(Constants.BUNDLE.DATA_CIRCUIT, circuitoJson);
        context.startActivity(intent);

    }

}
