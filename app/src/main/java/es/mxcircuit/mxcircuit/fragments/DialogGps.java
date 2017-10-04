package es.mxcircuit.mxcircuit.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import es.mxcircuit.mxcircuit.R;

/**
 * Created by Gashe on 13/6/17.
 */

public class DialogGps extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.error_location)
                .setTitle(R.string.error_title)
                .setPositiveButton(R.string.error_location_positive_button, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        Intent viewIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);//la petición va destinada al grupo
                        getActivity().startActivityForResult(viewIntent, 500);// Settings.ACTION_LOCATION_SOURCE_SETTINGS no devuelve ningún resultcode de acorde la selección del usuario: solución: chequear de nuevo en OnResume si el gps está activo :S
                        dialog.cancel();
                    }
                });
        return builder.create();

    }
}
