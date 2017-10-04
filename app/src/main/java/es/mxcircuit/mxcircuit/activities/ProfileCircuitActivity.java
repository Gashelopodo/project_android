package es.mxcircuit.mxcircuit.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.mxcircuit.mxcircuit.R;
import es.mxcircuit.mxcircuit.adapters.NotificationListAdapter;
import es.mxcircuit.mxcircuit.https.GetCircuit;
import es.mxcircuit.mxcircuit.https.GetImages;
import es.mxcircuit.mxcircuit.https.GetNotificationUserToCircuit;
import es.mxcircuit.mxcircuit.https.GetWeather;
import es.mxcircuit.mxcircuit.listeners.ListenerVoteCircuit;
import es.mxcircuit.mxcircuit.models.Circuit;
import es.mxcircuit.mxcircuit.models.Notification;
import es.mxcircuit.mxcircuit.models.Review;
import es.mxcircuit.mxcircuit.models.Weather;
import es.mxcircuit.mxcircuit.models.WeatherData;
import es.mxcircuit.mxcircuit.utils.Constants;
import es.mxcircuit.mxcircuit.utils.Dialogs;
import es.mxcircuit.mxcircuit.utils.NetworkStatus;
import es.mxcircuit.mxcircuit.utils.Utils;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ProfileCircuitActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener, View.OnClickListener {

    Circuit circuit;
    LinearLayout loadLayout;
    RecyclerView recyclerView;
    NotificationListAdapter notificationListAdapter;
    private String[] categories = Review.CATEGORIES;
    private int total_stars = Review.TOTAL_STARS;
    private boolean myLocation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_circuit);

        if(NetworkStatus.getStatusFinal(this) == NetworkStatus.TYPE_NOT_CONNECTED){
            Utils.alertNetwork(this, NetworkStatus.ERROR_NO_CONNECTED, Dialogs.ERROR_TITLE_GENERAL);
        }else {

            Intent intent = getIntent();
            String circuitJson = intent.getStringExtra(Constants.BUNDLE.DATA_CIRCUIT);
            if (circuitJson == null) {
                myLocation = false;
                String id_circuit = intent.getStringExtra(Constants.BUNDLE.ID_CIRCUIT);
                GetCircuit getCircuit = new GetCircuit(this);
                getCircuit.execute(id_circuit);
            } else {
                loadInit(circuitJson);
            }
        }

    }

    public void loadInit(String circuitJson){

        Gson gson = new Gson();
        circuit = (Circuit) gson.fromJson(circuitJson, Circuit.class);

        // ssi no tenemos nuestra localizacion (venimos de notificaciones) seteamos la distancia en kms con el circuito
        if(!myLocation){
            Location location = Utils.myLocation(this);
            float distanceInKm = Utils.distanceBetween(location, Double.parseDouble(circuit.getLat()), Double.parseDouble(circuit.getLng()));
            circuit.setDistanceInKm(distanceInKm);
        }

        // cargamos imagenes
        GetImages getImages = new GetImages(this);
        getImages.execute(Constants.URL_SERVER+circuit.getLogo(), Constants.URL_SERVER+circuit.getPicture());

        // escuchamos menu lateral
        Utils.loadNavigationListener(this);

        // cargamos el tiempo
        GetWeather getWeather = new GetWeather(this);
        getWeather.execute(circuit);

        // cargamos notificaciones
        GetNotificationUserToCircuit getNotificationUserToCircuit = new GetNotificationUserToCircuit(this);
        getNotificationUserToCircuit.execute(circuit.getId());

        // escuchamos botón para google maps
        Button buttonMaps = (Button) findViewById(R.id.gomaps);
        buttonMaps.setOnClickListener(this);

        // cargamos video youtube
        loadVideoYoutube();

        // escuchamos botón valorar circuito
        Button vote = (Button) findViewById(R.id.valorarButton);
        vote.setOnClickListener(new ListenerVoteCircuit(this, circuit));

    }

    public void loadImages(Bitmap[] bitmaps){

        ImageView imageView = (ImageView) findViewById(R.id.logoCircuit);
        imageView.setImageBitmap(bitmaps[0]);
        ImageView picture = (ImageView) findViewById(R.id.picturecircuit);
        picture.setImageBitmap(bitmaps[1]);

        loadData();

    }

    public void loadData(){

        TextView nameCircuit = (TextView) findViewById(R.id.name_circuit);
        nameCircuit.setText(circuit.getName());
        TextView kmCircuit = (TextView) findViewById(R.id.km_circuit);
        String distanceInKm = String.format("%.02f", circuit.getDistanceInKm());
        kmCircuit.setText(distanceInKm+" km");
        TextView description = (TextView) findViewById(R.id.description);
        description.setText(circuit.getDescription());
        TextView hour = (TextView) findViewById(R.id.hour);
        hour.setText("Horarios: "+circuit.getHours());
        TextView price = (TextView) findViewById(R.id.price);
        price.setText("Precio: "+circuit.getPrice());
        TextView contact_name = (TextView) findViewById(R.id.contact_name);
        contact_name.setText("Nombre: "+circuit.getContact_name());
        TextView contact_email = (TextView) findViewById(R.id.contact_email);
        contact_email.setText("Email: "+circuit.getContact_email());
        TextView contact_phone = (TextView) findViewById(R.id.contact_phone);
        contact_phone.setText("Teléfono: "+circuit.getContact_phone());
        TextView address = (TextView) findViewById(R.id.address);
        address.setText("Dirección: "+circuit.getAddress());
        TextView totalReviews = (TextView) findViewById(R.id.totalReviews);
        if(circuit.getReviews() != null) totalReviews.setText("("+circuit.getReviews().size()+")");
        else totalReviews.setText("0");

        // interés icons values

        if(Utils.getInteres(circuit.getBathroom())){
            ImageView bathroom = (ImageView) findViewById(R.id.bathroom);
            bathroom.setImageResource(R.drawable.bano_on);
            TextView bathroomText = (TextView) findViewById(R.id.bathroom_text);
            bathroomText.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
        }

        if(Utils.getInteres(circuit.getBar())){
            ImageView bar = (ImageView) findViewById(R.id.bar);
            bar.setImageResource(R.drawable.bar_on);
            TextView barText = (TextView) findViewById(R.id.bar_text);
            barText.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
        }

        if(Utils.getInteres(circuit.getPhoto())){
            ImageView photo = (ImageView) findViewById(R.id.photo);
            photo.setImageResource(R.drawable.fotografo_on);
            TextView photoText = (TextView) findViewById(R.id.photo_text);
            photoText.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
            TextView photoValue = (TextView) findViewById(R.id.photo_value);
            photoValue.setText(circuit.getPhoto());
        }

        if(Utils.getInteres(circuit.getLavado())){
            ImageView lavado = (ImageView) findViewById(R.id.lavado);
            lavado.setImageResource(R.drawable.lavadero_on);
            TextView lavadoText = (TextView) findViewById(R.id.lavado_text);
            lavadoText.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
        }

        if(Utils.getInteres(circuit.getCronos())){
            ImageView cronos = (ImageView) findViewById(R.id.crono);
            cronos.setImageResource(R.drawable.chrono_on);
            TextView cronoText = (TextView) findViewById(R.id.crono_text);
            cronoText.setTextColor(ContextCompat.getColor(this, R.color.colorBlack));
        }

        loadStars();

        loadLayout = (LinearLayout) findViewById(R.id.loadProfileCircuit);
        loadLayout.setVisibility(LinearLayout.INVISIBLE);

    }

    public void loadWeather(Weather weather){

        ArrayList<WeatherData> weatherDatas = null;
        Gson gson = new Gson();
        weatherDatas = gson.fromJson(weather.getWeather(), new TypeToken<ArrayList<WeatherData>>(){}.getType());

        TextView weather_address = (TextView) findViewById(R.id.weather_address);
        TextView day_1 = (TextView) findViewById(R.id.day_1);
        TextView day_2 = (TextView) findViewById(R.id.day_2);
        TextView day_3 = (TextView) findViewById(R.id.day_3);
        TextView day_4 = (TextView) findViewById(R.id.day_4);
        TextView day_5 = (TextView) findViewById(R.id.day_5);
        TextView day_6 = (TextView) findViewById(R.id.day_6);
        ImageView icon_1 = (ImageView) findViewById(R.id.icon_1);
        ImageView icon_2 = (ImageView) findViewById(R.id.icon_2);
        ImageView icon_3 = (ImageView) findViewById(R.id.icon_3);
        ImageView icon_4 = (ImageView) findViewById(R.id.icon_4);
        ImageView icon_5 = (ImageView) findViewById(R.id.icon_5);
        ImageView icon_6 = (ImageView) findViewById(R.id.icon_6);
        TextView high_1 = (TextView) findViewById(R.id.high_1);
        TextView high_2 = (TextView) findViewById(R.id.high_2);
        TextView high_3 = (TextView) findViewById(R.id.high_3);
        TextView high_4 = (TextView) findViewById(R.id.high_4);
        TextView high_5 = (TextView) findViewById(R.id.high_5);
        TextView high_6 = (TextView) findViewById(R.id.high_6);
        TextView low_1 = (TextView) findViewById(R.id.low_1);
        TextView low_2 = (TextView) findViewById(R.id.low_2);
        TextView low_3 = (TextView) findViewById(R.id.low_3);
        TextView low_4 = (TextView) findViewById(R.id.low_4);
        TextView low_5 = (TextView) findViewById(R.id.low_5);
        TextView low_6 = (TextView) findViewById(R.id.low_6);

        weather_address.setText(circuit.getAddress());
        day_1.setText(Utils.translateDay(weatherDatas.get(0).getDay(), Utils.LONG));
        day_2.setText(Utils.translateDay(weatherDatas.get(1).getDay(), Utils.SHORT));
        day_3.setText(Utils.translateDay(weatherDatas.get(2).getDay(), Utils.SHORT));
        day_4.setText(Utils.translateDay(weatherDatas.get(3).getDay(), Utils.SHORT));
        day_5.setText(Utils.translateDay(weatherDatas.get(4).getDay(), Utils.SHORT));
        day_6.setText(Utils.translateDay(weatherDatas.get(5).getDay(), Utils.SHORT));
        icon_1.setImageResource(getResources().getIdentifier("icon_"+weatherDatas.get(0).getCode(), "drawable", getPackageName()));
        icon_2.setImageResource(getResources().getIdentifier("icon_"+weatherDatas.get(1).getCode(), "drawable", getPackageName()));
        icon_3.setImageResource(getResources().getIdentifier("icon_"+weatherDatas.get(2).getCode(), "drawable", getPackageName()));
        icon_4.setImageResource(getResources().getIdentifier("icon_"+weatherDatas.get(3).getCode(), "drawable", getPackageName()));
        icon_5.setImageResource(getResources().getIdentifier("icon_"+weatherDatas.get(4).getCode(), "drawable", getPackageName()));
        icon_6.setImageResource(getResources().getIdentifier("icon_"+weatherDatas.get(5).getCode(), "drawable", getPackageName()));
        high_1.setText(Utils.toCentigrados(Double.parseDouble(weatherDatas.get(0).getHigh()))+"º");
        high_2.setText(Utils.toCentigrados(Double.parseDouble(weatherDatas.get(1).getHigh()))+"º");
        high_3.setText(Utils.toCentigrados(Double.parseDouble(weatherDatas.get(2).getHigh()))+"º");
        high_4.setText(Utils.toCentigrados(Double.parseDouble(weatherDatas.get(3).getHigh()))+"º");
        high_5.setText(Utils.toCentigrados(Double.parseDouble(weatherDatas.get(4).getHigh()))+"º");
        high_6.setText(Utils.toCentigrados(Double.parseDouble(weatherDatas.get(5).getHigh()))+"º");
        low_1.setText(Utils.toCentigrados(Double.parseDouble(weatherDatas.get(0).getLow()))+"º");
        low_2.setText(" "+Utils.toCentigrados(Double.parseDouble(weatherDatas.get(1).getLow()))+"º");
        low_3.setText(" "+Utils.toCentigrados(Double.parseDouble(weatherDatas.get(2).getLow()))+"º");
        low_4.setText(" "+Utils.toCentigrados(Double.parseDouble(weatherDatas.get(3).getLow()))+"º");
        low_5.setText(" "+Utils.toCentigrados(Double.parseDouble(weatherDatas.get(4).getLow()))+"º");
        low_6.setText(" "+Utils.toCentigrados(Double.parseDouble(weatherDatas.get(5).getLow()))+"º");


    }

    public void loadNotifications(ArrayList<Notification> notifications){

        recyclerView = (RecyclerView) findViewById(R.id.notification_list);

        if(notifications != null) {

            if(notifications.size() == 0){
                TextView nonotifications = (TextView) findViewById(R.id.no_notifications);
                nonotifications.setVisibility(TextView.VISIBLE);
            }else {
                notificationListAdapter = new NotificationListAdapter(notifications, this);
                recyclerView.setAdapter(notificationListAdapter);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

            }

        }else{
            Log.d("MENSAJE", "NOTIFICATION ES NULL");
        }

    }

    public void loadStars(){

        double installation = 0;
        double terrain = 0;
        double irrigation = 0;
        double jumps = 0;
        double security = 0;
        int size = 0;
        if(circuit.getReviews() != null) {
            size = circuit.getReviews().size();
            for (Review review : circuit.getReviews()) {
                installation += Double.parseDouble(review.getInstallations());
                terrain += Double.parseDouble(review.getTerrain());
                irrigation += Double.parseDouble(review.getIrrigation());
                jumps += Double.parseDouble(review.getJumps());
                security += Double.parseDouble(review.getSecurity());
            }
        }

        Log.d("MENSAJE", "REVIEWS: " + installation + "," + terrain + "," + irrigation + "," + jumps + "," + security);

        installation = Math.round(installation/size);
        terrain = Math.round(terrain/size);
        irrigation = Math.round(irrigation/size);
        jumps = Math.round(jumps/size);
        security = Math.round(security/size);

        Log.d("MENSAJE", "REVIEWS: " + installation + "," + terrain + "," + irrigation + "," + jumps + "," + security);

        double categoryreview = 0;

        for (String category : categories) {
            for (int i=1; i<=total_stars; i++){
                if(category.equals("installation")) categoryreview = installation;
                else if(category.equals("terrain")) categoryreview = terrain;
                else if(category.equals("irrigation")) categoryreview = irrigation;
                else if(category.equals("jumps")) categoryreview = jumps;
                else if(category.equals("security")) categoryreview = security;

                ImageView star = (ImageView) findViewById(getResources().getIdentifier(category+"_"+i,"id",getPackageName()));
                if(i <= categoryreview) star.setImageResource(R.drawable.staron);
                else star.setImageResource(R.drawable.staroff);
            }
        }
    }

    public void loadVideoYoutube(){

        YouTubePlayerFragment youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeplayerfragment);
        youTubePlayerFragment.initialize(Constants.KEYS.GOOGLE_API, this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b){
            youTubePlayer.cueVideo(circuit.getVideo());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.gomaps){
            // creamos intent para llamar a google maps
            Uri intentUri = Uri.parse("google.navigation:q="+circuit.getLat()+","+circuit.getLng()+"");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }

    }
}
