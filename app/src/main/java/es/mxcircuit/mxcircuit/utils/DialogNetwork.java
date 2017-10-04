package es.mxcircuit.mxcircuit.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by Gashe on 21/5/17.
 */

public class DialogNetwork extends DialogFragment {

    public static final String ERROR_TITLE_GENERAL = "Ha ocurrido un problema";
    private String message = "";
    private String title = "";

    public static DialogNetwork newInstance(String message, String title) {
        DialogNetwork frag = new DialogNetwork();
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
        final Activity activity = getActivity();

        AlertDialog.Builder builder =
                new AlertDialog.Builder(activity);

        builder.setMessage(message)
                .setTitle(title)
                .setPositiveButton("Reintertar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.finish();
                        activity.startActivity(activity.getIntent());
                    }
                });

        return builder.create();

    }
}
