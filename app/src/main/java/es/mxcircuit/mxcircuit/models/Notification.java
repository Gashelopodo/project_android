package es.mxcircuit.mxcircuit.models;

/**
 * Created by gashelopodo on 18/7/17.
 */

public class Notification {

    private String id;
    private String name_circuit;
    private String notification_id;
    private String register_id;
    private String circuit_id;
    private String title;
    private String description;
    private String send;
    private String status;
    private String message_status;
    private String created_at;
    private int position;

    public Notification(String id, String notification_id, String register_id, String circuit_id, String title, String description, String send, String status, String message_status, String created_at, String name_circuit) {
        this.id = id;
        this.name_circuit = name_circuit;
        this.notification_id = notification_id;
        this.register_id = register_id;
        this.circuit_id = circuit_id;
        this.title = title;
        this.description = description;
        this.send = send;
        this.status = status;
        this.message_status = message_status;
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage_status() {
        return message_status;
    }

    public void setMessage_status(String message_status) {
        this.message_status = message_status;
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
}
