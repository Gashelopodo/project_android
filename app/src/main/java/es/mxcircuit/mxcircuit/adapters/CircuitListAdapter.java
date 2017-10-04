package es.mxcircuit.mxcircuit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.mxcircuit.mxcircuit.R;
import es.mxcircuit.mxcircuit.models.Circuit;
import es.mxcircuit.mxcircuit.viewholders.CircuitListViewHolder;

import java.util.ArrayList;

/**
 * Created by Gashe on 11/6/17.
 */

public class CircuitListAdapter extends RecyclerView.Adapter<CircuitListViewHolder> {

    private ArrayList<Circuit> circuits;
    private Context context;

    public CircuitListAdapter(ArrayList<Circuit> circuits, Context context) {
        this.circuits = circuits;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return circuits.size();
    }

    @Override
    public CircuitListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CircuitListViewHolder circuitListViewHolder = null;
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.row_circuit, parent, false);
        circuitListViewHolder = new CircuitListViewHolder(view);
        return circuitListViewHolder;
    }

    @Override
    public void onBindViewHolder(CircuitListViewHolder holder, int position) {
        Circuit circuit = (Circuit) circuits.get(position);
        circuit.setPosition(position);
        holder.loadDataHolder(circuit);
    }

    public void clear(){
        int size = circuits.size();
        circuits.clear();
        notifyItemRangeRemoved(0, size);
    }


}
