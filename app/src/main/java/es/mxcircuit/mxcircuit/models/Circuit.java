package es.mxcircuit.mxcircuit.models;

import java.util.ArrayList;

/**
 * Created by Gashe on 11/6/17.
 */

public class Circuit {

    private String id;
    private String name;
    private String name_url;
    private String description;
    private String logo;
    private String picture;
    private String video;
    private String data;
    private String address;
    private String city_id;
    private String contact_name;
    private String contact_email;
    private String contact_phone;
    private String hours;
    private String price;
    private String facebook;
    private String bar;
    private String bathroom;
    private String photo;
    private String lavado;
    private String cronos;
    private String lat;
    private String lng;
    private String control;
    private String updated_at;
    private String created_at;
    private ArrayList<Review> reviews;
    private int position;
    private float distanceInKm;

    public Circuit(String id, String name, String name_url, String description, String logo, String picture, String video, String data, String address, String city_id, String contact_name, String contact_email, String contact_phone, String hours, String price, String facebook, String bar, String bathroom, String photo, String lavado, String cronos, String lat, String lng, String control, String updated_at, String created_at, ArrayList<Review> reviews) {
        this.id = id;
        this.name = name;
        this.name_url = name_url;
        this.description = description;
        this.logo = logo;
        this.picture = picture;
        this.video = video;
        this.data = data;
        this.address = address;
        this.city_id = city_id;
        this.contact_name = contact_name;
        this.contact_email = contact_email;
        this.contact_phone = contact_phone;
        this.hours = hours;
        this.price = price;
        this.facebook = facebook;
        this.bar = bar;
        this.bathroom = bathroom;
        this.photo = photo;
        this.lavado = lavado;
        this.cronos = cronos;
        this.lat = lat;
        this.lng = lng;
        this.control = control;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.reviews = reviews;
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

    public String getName_url() {
        return name_url;
    }

    public void setName_url(String name_url) {
        this.name_url = name_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_email() {
        return contact_email;
    }

    public void setContact_email(String contact_email) {
        this.contact_email = contact_email;
    }

    public String getContact_phone() {
        return contact_phone;
    }

    public void setContact_phone(String contact_phone) {
        this.contact_phone = contact_phone;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getBathroom() {
        return bathroom;
    }

    public void setBathroom(String bathroom) {
        this.bathroom = bathroom;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public float getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(float distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public String getLavado() {
        return lavado;
    }

    public void setLavado(String lavado) {
        this.lavado = lavado;
    }

    public String getCronos() {
        return cronos;
    }

    public void setCronos(String cronos) {
        this.cronos = cronos;
    }
}
