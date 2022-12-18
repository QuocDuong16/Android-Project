package com.example.bt4_googleapi;

import android.graphics.Color;

import java.io.Serializable;

public class Official implements Serializable {
    String name;
    Address[] address;
    String party;
    String[] phone;
    String[] urls;
    String[] emails;
    Channels[] channels;
    String img;
    public String getNameParty() {
        return name + " (" + party + ")\n";
    }

    public boolean imgExists() {
        return !img.equals("No Data Provided");
    }

    public String getIdChannels(String type) {
        String id = "No Data Provided";
        for (Channels c : channels) {
            if (c.type.equals(type)) {
                id = c.id;
            }
        }
        return id;
    }

    public int getBackgroundColor() {
        if (party.equals("Democratic Party")) return Color.BLUE;
        if (party.equals("Republican Party")) return Color.RED;
        return Color.BLACK;
    }
}
