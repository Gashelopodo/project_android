package es.mxcircuit.mxcircuit.activities;

import android.Manifest;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;

import es.mxcircuit.mxcircuit.adapters.CircuitListAdapter;
import es.mxcircuit.mxcircuit.fragments.DialogGps;
import es.mxcircuit.mxcircuit.https.GetCircuits;
import es.mxcircuit.mxcircuit.listeners.MyLocationListener;
import es.mxcircuit.mxcircuit.models.Circuit;
import es.mxcircuit.mxcircuit.utils.Constants;
import es.mxcircuit.mxcircuit.utils.Dialogs;
import es.mxcircuit.mxcircuit.utils.NetworkStatus;
import es.mxcircuit.mxcircuit.utils.Prefs;
import es.mxcircuit.mxcircuit.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListCircuitsActivity extends AppCompatActivity{

    LocationManager locationManager;
    MyLocationListener myLocationListener;
    private String provider;
    CircuitListAdapter circuitListAdapter;
    ArrayList<Circuit> arrayCircuits;
    RecyclerView recyclerView;
    LinearLayout loadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(es.mxcircuit.mxcircuit.R.layout.activity_list_circuits);

        if(NetworkStatus.getStatusFinal(this) == NetworkStatus.TYPE_NOT_CONNECTED){
            Utils.alertNetwork(this, NetworkStatus.ERROR_NO_CONNECTED, Dialogs.ERROR_TITLE_GENERAL);
        }else {

            // escuchamos menu lateral
            Utils.loadNavigationListener(this);

            // solicitamos permisos para localización
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            provider = LocationManager.GPS_PROVIDER;
            myLocationListener = new MyLocationListener(this);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.CODES.PERMISSION_LOCATION);
        }

    }

    public void loadCircuit(ArrayList<Circuit> circuits){

        recyclerView = (RecyclerView) findViewById(es.mxcircuit.mxcircuit.R.id.circuit_list);
        arrayCircuits = circuits;

        recyclerView.setNestedScrollingEnabled(false);

        if(circuits != null) {

            loadLayout = (LinearLayout) findViewById(es.mxcircuit.mxcircuit.R.id.loadcircuit);
            loadLayout.setVisibility(LinearLayout.INVISIBLE);

            circuitListAdapter = new CircuitListAdapter(circuits, this);
            recyclerView.setAdapter(circuitListAdapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            reloadKms();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        Log.d("MENSAJE", "VUELTA DE PEDIR PERMISOS");

        if (requestCode == Constants.CODES.PERMISSION_LOCATION) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.d("MENSAJE", "PERMISO DE LOCALIZACIÓN OK");

                //Para quitar el error
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION); //si incluyo esta línea, aunque no haga nada con el valor, A studio deja de dar la vara con la SecurityException


                if (locationManager.isProviderEnabled(provider)) {
                    Log.d("MENSAJE", "EL Acceso fino por GPS está habilitado");
                    //Location location_inicial = locationManager.getLastKnownLocation(provider);//"gps"
                    Location location_inicial = getLastKnownLocation();
                    mostrarLocalizacion(location_inicial);


                }
                else
                {
                    Log.d("MENSAJE", "Pidiendo que habilite el acceso");
                    solicitarActivarLocalizacion();


                }


            } else {

                Log.d("MENSAJE", "NO ACEPTÓ PERMISOS LOCATION");

            }
        }

    }

    private Location getLastKnownLocation() {

        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public void mostrarLocalizacion(Location location) {

        double lat = 0;
        double lng = 0;
        double alt = 0;

        if (null != location) {

            lat = location.getLatitude();
            lng = location.getLongitude();
            alt = location.getAltitude();

            //TextView textView = (TextView)findViewById(R.id.titleListCircuit);
            //textView.setText("LAT:" + lat + " LNG: " +lng);

            Log.d("MENSAJE", "LATITUD = " + lat);
            Log.d("MENSAJE", "LONGITUD = " + lng);
            Log.d("MENSAJE", "ALTITUD = " + alt);
            Log.d("MENSAJE", "Proveedor = " + location.getProvider());

            Prefs prefs = new Prefs(this);
            prefs.setCoordinates(lat, lng);


            //TODO: ordenar array según distancia y recargar adapter
            if(circuitListAdapter != null){
                reloadKms();
            }else{
                // cargamos circuitos
                GetCircuits getCircuits = new GetCircuits(this);
                getCircuits.execute();
            }


        } else {

            Log.d("MENSAJE", "LOCALIZACIÓN null ");
        }

    }

    public void reloadKms(){

        loadLayout.setVisibility(LinearLayout.VISIBLE);

        Location myLocation = Utils.myLocation(this);
        if(myLocation != null){
            // pintamos la distancia entre los circuitos
            for (Circuit circuit: arrayCircuits) {
                //localización circuito
                float distanceInKm = Utils.distanceBetween(myLocation, Double.parseDouble(circuit.getLat()), Double.parseDouble(circuit.getLng()));
                circuit.setDistanceInKm(distanceInKm);

                Log.d("MENSAJE", "distacenInkm = " + circuit.getDistanceInKm());

            }

            Collections.sort(arrayCircuits, new Comparator<Circuit>() {
                @Override
                public int compare(Circuit circuit, Circuit t1) {
                    return Float.compare(circuit.getDistanceInKm(), t1.getDistanceInKm());
                }
            });

            circuitListAdapter.notifyDataSetChanged();
        }

        loadLayout.setVisibility(LinearLayout.INVISIBLE);

    }

    public void solicitarActivarLocalizacion ()

    {
        FragmentManager fm = this.getFragmentManager();
        DialogGps dialog = new DialogGps();
        dialog.show(fm, "Aviso");

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(NetworkStatus.getStatusFinal(this) == NetworkStatus.TYPE_NOT_CONNECTED){
            Utils.alertNetwork(this, NetworkStatus.ERROR_NO_CONNECTED, Dialogs.ERROR_TITLE_GENERAL);
        }else {
            Log.d("MENSAJE", "La aplicación entra (o vuelve a entrar) a primer plano, ACTIVO");

            try {
                if(locationManager != null) locationManager.requestLocationUpdates(provider, 5000, 0, myLocationListener);
            } catch (SecurityException se) {
                Log.e("MENSAJE", "ERROR", se);
            } catch (Throwable se) {
                Log.e("MENSAJE", "ERROR", se);
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(NetworkStatus.getStatusFinal(this) == NetworkStatus.TYPE_NOT_CONNECTED){
            Utils.alertNetwork(this, NetworkStatus.ERROR_NO_CONNECTED, Dialogs.ERROR_TITLE_GENERAL);
        }else {
            Log.d("MENSAJE", "La aplicación entra en pausa (deja de estar visible), paro para optimizar");

            try {
                if (locationManager != null) locationManager.removeUpdates(myLocationListener);

            } catch (SecurityException se) {
                Log.e("MENSAJE", "Sin permisos");
            }
        }
    }



}
