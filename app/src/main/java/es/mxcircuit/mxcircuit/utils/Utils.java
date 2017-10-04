package es.mxcircuit.mxcircuit.utils;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.location.Location;
import android.support.design.widget.NavigationView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import es.mxcircuit.mxcircuit.R;
import es.mxcircuit.mxcircuit.api.Register;
import es.mxcircuit.mxcircuit.listeners.ListenerMenu;
import es.mxcircuit.mxcircuit.listeners.NavigationMenuListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Gashe on 21/5/17.
 */

public class Utils {

    public static final int SHORT = 1;
    public static final int LONG = 2;
    public static final String SI = "Si";
    public static final String NO = "No";
    public static final String AVECES = "A veces";
    public static final String SIEMPRE = "Siempre";
    public static final int MIN = 0;
    public static final int MAX = 999999999;

    public static void alert(Context context, String message, String title){

        Activity activity = (Activity) context;
        DialogFragment newFragment = Dialogs.newInstance(message, title);
        newFragment.show(activity.getFragmentManager(), "alert");

    }

    public static void alertNetwork(Context context, String message, String title){

        Activity activity = (Activity) context;
        DialogFragment newFragment = DialogNetwork.newInstance(message, title);
        newFragment.show(activity.getFragmentManager(), "alert");

    }

    public static boolean isEmail(String email){
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static Location myLocation(Context context){

        Location location = null;

        Prefs prefs = new Prefs(context);
        double lat = prefs.getLatitude();
        double lng = prefs.getLongitude();

        if(lat != 0 && lng != 0){
            location = new Location("");
            location.setLatitude(lat);
            location.setLongitude(lng);
        }

        return location;

    }

    public static float distanceBetween(Location myLocation, double lat, double lng){

        Location circuitLocation = new Location("");
        circuitLocation.setLatitude(lat);
        circuitLocation.setLongitude(lng);

        float distanceInMeters = myLocation.distanceTo(circuitLocation);

        return distanceInMeters/1000;

    }

    public static void loadNavigationListener(Context context){
        Activity activity = (Activity) context;

        ImageView menu = (ImageView) activity.findViewById(es.mxcircuit.mxcircuit.R.id.imgmenu);
        menu.setOnClickListener(new ListenerMenu(context));

        NavigationView navigationView = (NavigationView)activity.findViewById(es.mxcircuit.mxcircuit.R.id.myNavigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationMenuListener(context));
        ImageView setting = (ImageView) navigationView.getHeaderView(0).findViewById(es.mxcircuit.mxcircuit.R.id.setting);

        // set data user
        Prefs prefs = new Prefs(context);
        Register register = prefs.getRegister();

        TextView nameUser = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nameUser);
        nameUser.setText(register.getName());
        TextView emailUser = (TextView) navigationView.getHeaderView(0).findViewById(R.id.emailUser);
        emailUser.setText(register.getEmail());

        // listener
        setting.setOnClickListener(new NavigationMenuListener(context));

    }

    public static String translateDay(String day, int type){

        String translateDay = "";
        String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
        String[] daysT = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo"};
        int pos = 0;

        for(int i=0;i<days.length;i++){
            if(days[i].equals(day)) pos = i;
        }

        switch (type){
            case SHORT:
                translateDay = daysT[pos].substring(0, 3);
                break;
            case LONG:
                translateDay = daysT[pos];
                break;
        }

        return translateDay;
    }

    public static int toCentigrados(double far){
        return (int) ((far-32)/1.8000);
    }

    public static boolean getInteres(String s){
        boolean b = true;
        switch (s){
            case NO: b = false; break;
        }
        return b;
    }


    public static int numberUnique(){
        long number = 0;
        int random = 0;
        Random rand = new Random();

        random = rand.nextInt((MAX-MIN) + 1) + MIN;

        Calendar calendar = Calendar.getInstance();
        number = calendar.getTimeInMillis() + random;

        return (int) number;
    }

    public static String convertDate(String date){

        String newDate = "";
        Calendar dateNotification = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateNotification.setTime(formatter.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long timeNotification = dateNotification.getTimeInMillis();
        long timeNow = Calendar.getInstance().getTimeInMillis();

        long seconds = (timeNow - timeNotification) / 1000;
        long minutes = seconds/60;
        long hours = minutes/60;
        long days =  hours/24;

        if(days < 1){
            if(hours < 1){
                if(minutes < 1){
                    //seconds = seconds.rounded(.down)
                    if(seconds > 1){
                        newDate = "hace "+seconds+" segundos";
                    }else{
                        newDate = "hace "+seconds+" segundo";
                    }
                }else{
                    //minutes = minutes.rounded(.down)
                    if(minutes > 1){
                        newDate = "hace "+minutes+" minutos";
                    }else{
                        newDate = "hace "+minutes+" minuto";
                    }
                }
            }else{
                //hours = hours.rounded(.down)
                if(hours > 1){
                    newDate = "hace "+hours+" horas";
                }else{
                    newDate = "hace "+hours+" hora";
                }
            }
        }else{
            //days = days.rounded(.down)
            if(days > 1){
                newDate = "hace "+days+" días";
            }else{
                newDate = "hace "+days+" día";
            }
        }

        return newDate;

    }

}
