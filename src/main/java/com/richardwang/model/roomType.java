package com.richardwang.model;

public enum roomType {
    ENTIRE("Entire home/apt"),
    PRIVATE("Private room"),
    SHARED("Shared room");

    private String type;

    roomType(String type){
        this.type = type;
    }

    public String type(){
        return type;
    }
}
