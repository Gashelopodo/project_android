package es.mxcircuit.mxcircuit.models;

/**
 * Created by gashelopodo on 27/7/17.
 */

public class Weather {

    private String  id;
    private String circuit_id;
    private String weather;
    private String update_at;
    private String created_at;

    public Weather(String id, String circuit_id, String weather, String update_at, String created_at) {
        this.id = id;
        this.circuit_id = circuit_id;
        this.weather = weather;
        this.update_at = update_at;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCircuit_id() {
        return circuit_id;
    }

    public void setCircuit_id(String circuit_id) {
        this.circuit_id = circuit_id;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
