package es.mxcircuit.mxcircuit.models;

/**
 * Created by gashelopodo on 25/7/17.
 */

public class Setting {

    private String id;
    private String register_id;
    private String circuit_id;
    private String send;
    private String created_at;
    private String name_circuit;
    private String name_city;
    private String id_city;
    private int position;
    private boolean control;

    public Setting(String id, String register_id, String circuit_id, String send, String created_at, String name_circuit, String name_city, String id_city) {
        this.id = id;
        this.register_id = register_id;
        this.circuit_id = circuit_id;
        this.send = send;
        this.created_at = created_at;
        this.name_circuit = name_circuit;
        this.name_city = name_city;
        this.id_city = id_city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegister_id() {
        return register_id;
    }

    public void setRegister_id(String register_id) {
        this.register_id = register_id;
    }

    public String getCircuit_id() {
        return circuit_id;
    }

    public void setCircuit_id(String circuit_id) {
        this.circuit_id = circuit_id;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName_circuit() {
        return name_circuit;
    }

    public void setName_circuit(String name_circuit) {
        this.name_circuit = name_circuit;
    }

    public String getName_city() {
        return name_city;
    }

    public void setName_city(String name_city) {
        this.name_city = name_city;
    }

    public String getId_city() {
        return id_city;
    }

    public void setId_city(String id_city) {
        this.id_city = id_city;
    }

    public boolean isControl() {
        return control;
    }

    public void setControl(boolean control) {
        this.control = control;
    }
}
