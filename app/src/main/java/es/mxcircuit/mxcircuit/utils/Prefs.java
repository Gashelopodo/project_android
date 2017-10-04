package es.mxcircuit.mxcircuit.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import es.mxcircuit.mxcircuit.api.Register;
import com.google.gson.Gson;

/**
 * Created by Gashe on 21/5/17.
 */

public class Prefs {

    private Context context;
    private SharedPreferences prefs = null;
    public static final String TOKEN = "register_token";
    public static final String SESSION = "session";
    public static final String LAT = "latitude";
    public static final String LNG = "longitude";
    public static final String CITY_ID = "city_id";
    public static final String ALL_CITY = "all_city";
    public static final String REGISTER = "register";
    public static final String LOCATION = "location";

    public Prefs(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public void setToken(String token){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

    public String getToken(){
        return prefs.getString(TOKEN, "");
    }

    public void setSession(boolean isSession){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SESSION, isSession);
        editor.commit();
    }

    public boolean getSession(){
        return prefs.getBoolean(SESSION, false);
    }

    public void setCoordinates(double lat, double lng){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(LAT, (float) lat);
        editor.putFloat(LNG, (float) lng);
        editor.commit();
    }

    public void setRegister(Register register){
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String jsonRegister = gson.toJson(register);
        editor.putString(REGISTER, jsonRegister);
        editor.commit();
    }

    public Register getRegister(){
        Gson gson = new Gson();
        Register register = gson.fromJson(prefs.getString(REGISTER, ""), Register.class);
        return register;
    }

    public double getLatitude(){
        return (double) prefs.getFloat(LAT, 0);
    }

    public double getLongitude(){
        return (double) prefs.getFloat(LNG, 0);
    }

    public void setCitySetting(int id_city){
        SharedPreferences.Editor editor = prefs.edit();
        if(id_city != 0) {
            editor.putInt(CITY_ID, id_city);
            editor.commit();
        }else{
            editor.remove(CITY_ID);
        }
    }

    public int getCitySetting(){
        return prefs.getInt(CITY_ID, 0);
    }

    public void setAllSetting(int i){
        SharedPreferences.Editor editor = prefs.edit();
        Log.d("MENSAJE", "all entro en añadir valor:" + i);
        editor.putInt(ALL_CITY, i);
        editor.commit();


    }

    public int getAllSetting(){
        return prefs.getInt(ALL_CITY, 0);
    }

}
