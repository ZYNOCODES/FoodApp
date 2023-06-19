package com.example.foodapp.Models;

public class OrderModel {
    private String ID;
    private String Type;
    private String Location;
    private String LocationNotes;
    private String Price;
    private String ClientID;
    private Boolean Confirmation;

    public OrderModel(String ID, String type, String location, String locationNotes, String price, String clientID, Boolean confirmation) {
        this.ID = ID;
        Type = type;
        Location = location;
        LocationNotes = locationNotes;
        Price = price;
        ClientID = clientID;
        Confirmation = confirmation;
    }

    public OrderModel(String ID, String type, String location, String locationNotes, String price) {
        this.ID = ID;
        Type = type;
        Location = location;
        LocationNotes = locationNotes;
        Price = price;
    }

    public OrderModel(String ID, String price, Boolean confirmation) {
        this.ID = ID;
        Price = price;
        Confirmation = confirmation;
    }

    public OrderModel(String ID, String type, String price, Boolean confirmation) {
        this.ID = ID;
        Type = type;
        Price = price;
        Confirmation = confirmation;
    }

    public OrderModel() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getLocationNotes() {
        return LocationNotes;
    }

    public void setLocationNotes(String locationNotes) {
        LocationNotes = locationNotes;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public Boolean getConfirmation() {
        return Confirmation;
    }

    public void setConfirmation(Boolean confirmation) {
        Confirmation = confirmation;
    }
}
