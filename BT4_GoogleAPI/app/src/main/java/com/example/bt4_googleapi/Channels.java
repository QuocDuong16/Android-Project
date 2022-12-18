package com.example.bt4_googleapi;

import java.io.Serializable;

public class Channels implements Serializable {
    String type;
    String id;
    public Channels() { }

    public Channels(String no_data_provided) {
        type = no_data_provided;
        id = no_data_provided;
    }
}
