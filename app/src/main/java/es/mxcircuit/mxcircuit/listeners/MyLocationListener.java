package es.mxcircuit.mxcircuit.listeners;

/**
 * Created by Gashe on 13/6/17.
 */

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import es.mxcircuit.mxcircuit.activities.ListCircuitsActivity;

public class MyLocationListener implements LocationListener
{
    private ListCircuitsActivity activity;

    public MyLocationListener (ListCircuitsActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("MENSAJE", " Localización cambiada");
        activity.mostrarLocalizacion (location);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        switch(status){
            case LocationProvider.AVAILABLE:
                Log.d("MENSAJE", "Proveedor " + provider + " DISPONIBLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("MENSAJE", "Proveedor " + provider + " FUERA DE SERVICIO ");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("MENSAJE", "Proveedor " + provider + " TEMPORALMENTE NO DISPONIBLE");
                break;

        }

    }

    @Override
    public void onProviderEnabled(String provider) {
        //este método también es invocado al volver de la actividad de los ajustes de localización
        Log.d("MENSAJE", "Proveedor " + provider + " ACTIVADO");
    }

    @Override
    public void onProviderDisabled(String provider) {
        //este método también es invocado al volver de la actividad de los ajustes de localización
        activity.solicitarActivarLocalizacion();
        Log.d("MENSAJE", "Proveedor " + provider + " DESACTIVADO");
    }
}