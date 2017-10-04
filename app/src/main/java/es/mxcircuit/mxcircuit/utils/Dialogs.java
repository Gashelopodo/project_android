package es.mxcircuit.mxcircuit.utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Gashe on 21/5/17.
 */

public class Dialogs extends DialogFragment {

    public static final String ERROR_TITLE_GENERAL = "Ha ocurrido un problema";
    private String message = "";
    private String title = "";

    public static Dialogs newInstance(String message, String title) {
        Dialogs frag = new Dialogs();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("message", message);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle bundle) {

            message = getArguments().getString("message");
            title = getArguments().getString("title");

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getActivity());

            builder.setMessage(message)
                    .setTitle(title)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            return builder.create();

    }
}
