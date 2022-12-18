package com.example.bt4_googleapi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Address implements Serializable {
    ArrayList<String> line = new ArrayList<>();
    String city;
    String state;
    String zip;

    public Address() { }

    public Address(String no_data_provided) {
        line.add(no_data_provided);
        city = no_data_provided;
        state = no_data_provided;
        zip = no_data_provided;
    }

    public String getAddress() {
        String nonData = "No Data Provided";
        StringBuilder stringBuilder = new StringBuilder();
        for (String l : line) {
            if (!l.equals(nonData)) {
                stringBuilder.append(l).append(" ");
            }
        }
        if (!Objects.equals(city, nonData) && line.get(0).equals(nonData))
            stringBuilder.append(", ").append(city);
        else {
            stringBuilder.append(city);
        }
        if (!Objects.equals(state, nonData))
            stringBuilder.append(", ").append(state);
        if (!Objects.equals(zip, nonData))
            stringBuilder.append(" ").append(zip);

        return stringBuilder.toString();
    }
}
