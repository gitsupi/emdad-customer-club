package com.salinteam.emdadcustomerclub.model;

public enum LevelName {
    BLUE("30"),
    RED("120"),
    GREEN("150"),
    VIOLET("250");

    private String value;

    LevelName(String value) {
        this.value = value;
    }

    LevelName() {

    }

    private String getValue(){
        return value;
    }
}
