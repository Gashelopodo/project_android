package es.mxcircuit.mxcircuit.api;

/**
 * Created by Gashe on 15/5/17.
 */

public class Login {

    private String name;
    private String email;

    public Login(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
