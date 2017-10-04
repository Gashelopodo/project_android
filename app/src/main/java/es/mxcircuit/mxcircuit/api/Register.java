package es.mxcircuit.mxcircuit.api;

/**
 * Created by Gashe on 21/5/17.
 */

public class Register {

    private String id;
    private String name;
    private String email;
    private String device_uuid;
    private String device_model;
    private String device_cordova;
    private String device_platform;
    private String device_version;
    private String device_serial;
    private String device_token;
    private String control;
    private String created_at;


    public Register(String id, String name, String email, String device_uuid, String device_model, String device_cordova, String device_platform, String device_version, String device_serial, String device_token, String control, String created_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.device_uuid = device_uuid;
        this.device_model = device_model;
        this.device_token = device_token;
        this.device_cordova = device_cordova;
        this.device_platform = device_platform;
        this.device_version = device_version;
        this.device_serial = device_serial;
        this.control = control;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDevice_uuid() {
        return device_uuid;
    }

    public void setDevice_uuid(String device_uuid) {
        this.device_uuid = device_uuid;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public String getDevice_cordova() {
        return device_cordova;
    }

    public void setDevice_cordova(String device_cordova) {
        this.device_cordova = device_cordova;
    }

    public String getDevice_platform() {
        return device_platform;
    }

    public void setDevice_platform(String device_platform) {
        this.device_platform = device_platform;
    }

    public String getDevice_version() {
        return device_version;
    }

    public void setDevice_version(String device_version) {
        this.device_version = device_version;
    }

    public String getDevice_serial() {
        return device_serial;
    }

    public void setDevice_serial(String device_serial) {
        this.device_serial = device_serial;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}