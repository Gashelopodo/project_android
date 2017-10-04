package es.mxcircuit.mxcircuit.models;

/**
 * Created by Gashe on 11/6/17.
 */

public class Review {

    private String id;
    private String register_id;
    private String circuit_id;
    private String installations;
    private String terrain;
    private String irrigation;
    private String jumps;
    private String security;
    private String created_at;
    public static String[] CATEGORIES = {"installation","terrain","irrigation","jumps","security"};
    public static int TOTAL_STARS = 5;

    public Review(String id, String register_id, String circuit_id, String installations, String terrain, String irrigation, String jumps, String security, String created_at) {
        this.id = id;
        this.register_id = register_id;
        this.circuit_id = circuit_id;
        this.installations = installations;
        this.terrain = terrain;
        this.irrigation = irrigation;
        this.jumps = jumps;
        this.security = security;
        this.created_at = created_at;
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

    public String getInstallations() {
        return installations;
    }

    public void setInstallations(String installations) {
        this.installations = installations;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(String irrigation) {
        this.irrigation = irrigation;
    }

    public String getJumps() {
        return jumps;
    }

    public void setJumps(String jumps) {
        this.jumps = jumps;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
