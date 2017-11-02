package com.richardwang.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum roomType {
    ENTIRE("Entire home/apt"),
    PRIVATE("Private room"),
    SHARED("Shared room");

    private String type;

    private static final Map<String, roomType> lookup = new HashMap<>();

    static {
        for (roomType r : EnumSet.allOf(roomType.class)){
            lookup.put(r.type(), r);
        }
    }

    roomType(String type){
        this.type = type;
    }

    public String type(){
        return type;
    }

    public static roomType get(String room){
        return lookup.get(room);
    }

}
